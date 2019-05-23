package com.skcnc.openmind.Ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.skcnc.openmind.List.RecyclerJoinAdapter;
import com.skcnc.openmind.List.RecyclerJoinItem;
import com.skcnc.openmind.R;
import com.skcnc.openmind.Sqlite.MyDBHandler;
import com.skcnc.openmind.Sqlite.TableBrand;
import com.skcnc.openmind.Util.U;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class JoinActivity extends Activity {

    URL postURL = null; HttpURLConnection connection = null;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<RecyclerJoinItem> mItems = new ArrayList<>();

    MyDBHandler myDBHandler = new MyDBHandler(this, "kiosk.db", null, 1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //db 초기화 1번만
        if(!U.getUinstance().getSPBoolean(this, "db")) setDataBase();

        setRecyclerView();

        //확인버튼 클릭
        Button btn_join = (Button) findViewById(R.id.btn_join);
        final EditText edit_age = (EditText) findViewById(R.id.edit_age);
        final RadioButton radio_f = (RadioButton) findViewById(R.id.radio_f);
        final RadioButton radio_m = (RadioButton) findViewById(R.id.radio_m);

        btn_join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String text_edit_age = edit_age.getText().toString();
                String text_gender = "";
                if(radio_f.isChecked()) text_gender = "female";
                else if(radio_m.isChecked()) text_gender = "male";

                /*** ArrayList로 ***/
                ArrayList<String> tids = U.getUinstance().getBrandList();

                if(isinternetCon()){
                    U.getUinstance().toast(getApplicationContext(), "인터넷을 연결해주세요.");
                }else{
                    try {
                        process(text_edit_age, text_gender, tids);      //가입api 요청
                    }catch (Exception e){
                        U.getUinstance().log("EEEEEE");
                    }
                }
                U.getUinstance().saveSPUser(getApplicationContext(), "age", text_edit_age);
                U.getUinstance().saveSPUser(getApplicationContext(), "gender", text_gender);
                //U.getUinstance().saveSPSet(getApplicationContext(), "tutorials", );
            }
        });
        //건너뛰기 클릭
    }

    private void setRecyclerView(){
            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
            recyclerView.setHasFixedSize(true);
            layoutManager = new GridLayoutManager(this, 2);
            //layoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManager);
            adapter = new RecyclerJoinAdapter(mItems);
            recyclerView.setAdapter(adapter);
            setData();
    }

    //리사이클러뷰에 데이터 리스트 넣기
    private void setData(){
        String s = "d.png";
        for(int i=1; i<=10; i++){
            TableBrand tb = myDBHandler.getBrand(i);
            mItems.add(new RecyclerJoinItem(tb.getName(), tb.getImage()));
        }
        adapter.notifyDataSetChanged();
    }

    //DB생성및 초기화
    private void setDataBase(){
        try {
            myDBHandler.insert();

            U.getUinstance().savaSPBoolean(this, "db", true);
        }catch (Exception e){
            U.getUinstance().log("DBHandler ERROR");
            e.printStackTrace();
        }

        U.getUinstance().log(myDBHandler.getBrand(1 ).toString());
    }

    //post 요청
    public void process(String text_edit_age, String text_gender, ArrayList<String> tids) throws IOException {
        //final String url_str = "http://13.125.254.66/api/users";  //실제 url
        final String url_str = "http://www.naver.com";             //테스트 url
        final String json = convertToJson(Integer.parseInt(text_edit_age), text_gender, tids);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    postURL = new URL(url_str);
                    connection = (HttpURLConnection) postURL.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Cache-Control" , "no-cache");
                    connection.setRequestProperty("Accept", "text/html");
                    connection.setRequestProperty("Content-type", "application/json");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    OutputStreamWriter os = new OutputStreamWriter(connection.getOutputStream());

                    os.write(json);
                    os.flush();

                    U.getUinstance().log(connection.getResponseCode()+"");

                    //성공 시에 MainActivity로 이동
                    if(connection.getResponseCode() == 200){
                        goToMain();
                    }
                }catch (Exception e){
                    U.getUinstance().log("INTERNET ERROR");
                    e.printStackTrace();
                }finally {
                    connection.disconnect();//네트워크 연결해제
                }
            }
        });
    }

    public void goToMain(){
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
    }

    public String convertToJson(int text_edit_age, String text_gender, ArrayList<String> tids){
        String json = "";
        String uuid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID); //기기ID
        //String uuid = "afdn3r";
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid", uuid);
            jsonObject.put("age", text_edit_age);
            jsonObject.put("gender", text_gender);

            JSONArray jsonArray = new JSONArray();

            for(String s: tids){
                U.getUinstance().log(myDBHandler.getTid(s));
                jsonArray.put(myDBHandler.getTid(s));
            }
            jsonObject.put("likes", jsonArray);

            json = jsonObject.toString();
        }catch (Exception e){
            U.getUinstance().log("JSON ERROR");
        }
        U.getUinstance().log(json);
        return json;
    }

    private boolean isinternetCon(){
        ConnectivityManager cManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return !mobile.isConnected() && !wifi.isConnected();
    }
}
