package com.skcnc.openmind.Ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

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

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends Activity {
    String url_str = "http://13.125.254.66/api/users?uuid=";          //리얼 주소
    //String url_str = "http://www.naver.com";                            //가라
    String uuid = "null"; //기기ID
    String TAG_LIKES = "recommends";
    StringBuffer json_Response = new StringBuffer();            //응답 데이터
    URL url = null; HttpURLConnection connection = null;
    int response_code;      //응답코드

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerRecommAdapter adapter;
    ArrayList<RecyclerRecommItem> mItems = new ArrayList<>();
    ProgressDialog progressDialog;


    MyDBHandler myDBHandler = new MyDBHandler(this, "kiosk.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        //회원가입 여부 확인
        if(U.getUinstance().getSPBoolean(this, "join")){
            //uuid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID); //기기ID
            uuid = "uuid0003";
        }
        U.getUinstance().log("in Main uuid="+uuid);

        /** 서버 연동시 수정 **/
        if(!uuid.equals("null")){    //회원가입 했으면 추천 뿌려줌
            if(isinternetCon()){
                U.getUinstance().toast(getApplicationContext(), "인터넷을 연결해주세요.");
            }else{
                try {
                    progressDialog = ProgressDialog.show(this, "튜토리얼 추천중", "조금만 기다려주세요.",true,false);
                    process();
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

    public void process() throws Exception{

        new Thread(new Runnable() {
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
                    U.getUinstance().log("json_response="+json_Response.toString());
                    U.getUinstance().log(connection.getResponseCode()+"");

                    Thread.sleep(2000);

                    response_code = connection.getResponseCode();
                    handler.sendEmptyMessage(0);
                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run()  {
                            try {
                                if (connection.getResponseCode() == 200) showRecomm();              //요청 성공시
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    });*/

                }catch (Exception e){
                    U.getUinstance().log("JSON RESPONSE ERROR");
                }finally {
                    connection.disconnect();
                }
            }
        }).start();

    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            progressDialog.dismiss();
            showRecomm();
        }
    };

    //json형태
    public void showRecomm(){
        try {
            ArrayList<String> values = new ArrayList<>();
            U.getUinstance().log("JSONDATA==="+json_Response.toString());
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
        try {
            recyclerView = (RecyclerView) findViewById(R.id.main);
            U.getUinstance().log("setRecommView ERROR1");
            recyclerView.setHasFixedSize(true);
            U.getUinstance().log("setRecommView ERROR2");

            layoutManager = new LinearLayoutManager(getApplicationContext());
            U.getUinstance().log("setRecommView ERROR3");
            ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
            U.getUinstance().log("setRecommView ERROR4");
            //layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            U.getUinstance().log("setRecommView ERROR5");
            adapter = new RecyclerRecommAdapter(getApplicationContext(), mItems);
            U.getUinstance().log("setRecommView ERROR6");
            recyclerView.setAdapter(adapter);
            U.getUinstance().log("setRecommView ERROR7");
            setData(values);
        }catch (Exception e){
            e.printStackTrace();
            U.getUinstance().log("setRecommView ERROR");
        }
    }

    //리사이클러뷰에 데이터 추가
    public void setData(ArrayList<String> values){
        try{
            for(int i=0; i<values.size(); i++){
                double tid = 0.0;
                if(i==2){
                    tid = 11.0;
                }else{
                    tid = Double.parseDouble(values.get(i));
                }

                TableBrand tb = myDBHandler.getBrand((int)tid);
                mItems.add(new RecyclerRecommItem(tb.getName(), tb.getImage()));
            }
            for(String s: values){
                double tid = Double.parseDouble(s);

            }
            adapter.notifyDataSetChanged();
        }catch (Exception e){
            U.getUinstance().log("setData() ERROR");
        }


    }

    private boolean isinternetCon(){
        ConnectivityManager cManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return !mobile.isConnected() && !wifi.isConnected();
    }
}
