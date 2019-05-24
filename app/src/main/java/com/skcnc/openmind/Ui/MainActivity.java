package com.skcnc.openmind.Ui;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.skcnc.openmind.List.RecyclerCategoryAdapter;
import com.skcnc.openmind.List.RecyclerCategoryItem;
import com.skcnc.openmind.List.RecyclerJoinAdapter;
import com.skcnc.openmind.List.RecyclerRecommAdapter;
import com.skcnc.openmind.List.RecyclerRecommItem;
import com.skcnc.openmind.R;
import com.skcnc.openmind.Sqlite.MyDBHandler;
import com.skcnc.openmind.Sqlite.TableBrand;
import com.skcnc.openmind.Util.U;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {
    String url_str = "http://13.125.254.66/api/users?uuid=";          //리얼 주소
    //String url_str = "http://www.naver.com";                            //가라
    String uuid = "null"; //기기ID
    String TAG_LIKES = "likes";
    StringBuffer json_Response = new StringBuffer();            //응답 데이터
    URL url = null; HttpURLConnection connection = null;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerRecommAdapter adapter;
    RecyclerCategoryAdapter adapter_c;
    ArrayList<RecyclerRecommItem> mItems = new ArrayList<>();
    ArrayList<RecyclerCategoryItem> cItems = new ArrayList<>();

    MyDBHandler myDBHandler = new MyDBHandler(this, "kiosk.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        //회원가입 여부 확인
        if(U.getUinstance().getSPBoolean(this, "join")){
            uuid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID); //기기ID
        }

        U.getUinstance().log("in Main uuid="+uuid);

        /** 서버 연동시 수정 **/
        //json_Response.append("[{\"likes\":[\"1.0\",\"3.0\",\"5.0\",\"2.0\",\"8.0\",\"6.0\"]}]");

        if(!uuid.equals("null")){    //회원가입 했으면 추천 뿌려줌
            if(isinternetCon()){
                U.getUinstance().toast(getApplicationContext(), "인터넷을 연결해주세요.");
            }else{
                try {
                    //process();
                    showRecomm();
                }catch (Exception e){
                    U.getUinstance().log("EEEEEE");
                }
            }
        }else{  //안했으면 메시지 표시
            U.getUinstance().log("사용자 등록 안됨");

            LinearLayout alert = (LinearLayout) findViewById(R.id.alert);
            alert.setVisibility(View.VISIBLE);

            Button btn_alert = (Button) findViewById(R.id.alert_btn);
            btn_alert.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getApplicationContext(), JoinActivity.class));
                    finish();
                };
            });

        }
    }

    public void process() throws Exception {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    url = new URL(url_str+uuid);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String json;
                    while((json = br.readLine())!=null){
                        json_Response.append(json + "\n");
                    }
                    U.getUinstance().log(json_Response.toString());
                    U.getUinstance().log(connection.getResponseCode()+"");

                    if (connection.getResponseCode() == 200) showRecomm();              //요청 성공시
                }catch (Exception e){
                    U.getUinstance().log("JSON RESPONSE ERROR");
                }finally {
                    connection.disconnect();
                }
            }
        });
    }

    //json형태
    public void showRecomm(){
        try {
            ArrayList<String> values = new ArrayList<>();
            U.getUinstance().log("LOGLOG==="+json_Response.toString());
            JSONArray jsonArray = new JSONArray(json_Response.toString());
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            JSONArray jsonArray2 = jsonObject.getJSONArray(TAG_LIKES);
            for(int i=0; i<jsonArray2.length(); i++){
                values.add(jsonArray2.getString(i));
            }
            setRecommView(values);  //리사이클러뷰 올리기
        }catch (Exception e){
            e.printStackTrace();
            U.getUinstance().log("JsonERROR");
        }
    }

    //리사이클러뷰 설정
    public void setRecommView(ArrayList<String> values){
        recyclerView = (RecyclerView) findViewById(R.id.main);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        //layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerRecommAdapter(mItems);
        recyclerView.setAdapter(adapter);

        setData(values);
    }

    //리사이클러뷰에 데이터 추가
    public void setData(ArrayList<String> values){
        for(String s: values){
            double tid = Double.parseDouble(s);
            TableBrand tb = myDBHandler.getBrand((int)tid);
            mItems.add(new RecyclerRecommItem(tb.getName(), tb.getImage()));
        }

        adapter.notifyDataSetChanged();
    }

    //카테고리 뷰
    /*public void setCategoryView(){

        recyclerView = (RecyclerView) findViewById(R.id.category);
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adapter_c = new RecyclerCategoryAdapter(cItems);

        cItems.add(new RecyclerCategoryItem("패스트푸드", R.drawable.fastfood));
        cItems.add(new RecyclerCategoryItem("교통", R.drawable.transport));
        cItems.add(new RecyclerCategoryItem("병원", R.drawable.hospital));
        cItems.add(new RecyclerCategoryItem("대형마트", R.drawable.mall));
        cItems.add(new RecyclerCategoryItem("커피전문점", R.drawable.cafe));


        cItems.add(new RecyclerCategoryItem("공공기관", R.drawable.office));



        recyclerView.setAdapter(adapter_c);
    }*/

    private boolean isinternetCon(){
        ConnectivityManager cManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return !mobile.isConnected() && !wifi.isConnected();
    }
}
