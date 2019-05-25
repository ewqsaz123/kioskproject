package com.skcnc.openmind.Ui;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.skcnc.openmind.Fragment.FragmentAge;
import com.skcnc.openmind.Fragment.FragmentGender;
import com.skcnc.openmind.Fragment.FragmentInterested;
import com.skcnc.openmind.List.RecyclerJoinItem;
import com.skcnc.openmind.R;
import com.skcnc.openmind.Sqlite.MyDBHandler;
import com.skcnc.openmind.Util.U;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class JoinActivity extends AppCompatActivity {

    URL postURL = null; HttpURLConnection connection = null;
    private RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<RecyclerJoinItem> mItems = new ArrayList<>();

    MyDBHandler myDBHandler = new MyDBHandler(this, "kiosk.db", null, 1);

    Fragment gender = new FragmentGender();
    Fragment age = new FragmentAge();
    Fragment interested = new FragmentInterested();

    String fragdata_age, fragdata_gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        setFragmentInit();

        //db 초기화 1번만
        if(!U.getUinstance().getSPBoolean(this, "db")) setDataBase();
    }

    //프래그먼트 초기화(gender)
    public void setFragmentInit(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.container_join, gender);
        ft.commit();
    }

    //프래그먼트 교체
    public void fragmentSwitch(int pos, String value){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        switch (pos){
            case 2:                         //gender -> age
                fragdata_gender = value;
                U.getUinstance().log(fragdata_gender);
                ft.replace(R.id.container_join, age);
                ft.commit();
                break;
            case 3:                         //age -> interested
                if(value.equals("skip")) setSkip();
                else{
                    fragdata_age = value;
                    U.getUinstance().log(fragdata_age);
                    ft.replace(R.id.container_join, interested);
                    ft.commit();
                }
                break;
            case 4:                         //interested -> submit / skip
                if(value.equals("skip")) setSkip();
                else{
                    setSubmit();
                }
        }
    }
    public void setSkip(){

        new Thread(new Runnable() {
            @Override
            public void run() {
            }
        }).start();

        //건너뛰기//
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    //확인버튼 클릭시
    public void setSubmit(){
        ArrayList<String> tids = U.getUinstance().getBrandList();
        String uuid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID); //기기ID
        String age = fragdata_age;
        String gender = fragdata_gender;

        if(isinternetCon()){
            U.getUinstance().toast(getApplicationContext(), "인터넷을 연결해주세요.");
        }else{
            try {
                process(fragdata_age, fragdata_gender, tids);      //가입api 요청
            }catch (Exception e){
                U.getUinstance().log("EEEEEE");
            }
        }

        //SP 저장
        U.getUinstance().saveSPUser(this, "uuid", uuid);
        U.getUinstance().savaSPBoolean(this, "join", true);

        U.getUinstance().log("tids="+tids.toString());

        U.getUinstance().log("사용자 정보 등록 완료");
    }

    //DB생성및 초기화
    private void setDataBase(){
        try {
            U.getUinstance().log("디비 생성 여부1"+U.getUinstance().getSPBoolean(this, "db"));
            myDBHandler.insert();

            U.getUinstance().savaSPBoolean(this, "db", true);
            U.getUinstance().log("디비 생성 여부2"+U.getUinstance().getSPBoolean(this, "db"));
        }catch (Exception e){
            U.getUinstance().log("DBHandler ERROR");
            e.printStackTrace();
        }

        U.getUinstance().log(myDBHandler.getBrand(1 ).toString());
    }

    //post 요청
    public void process(String text_edit_age, String text_gender, ArrayList<String> tids) throws IOException {
        final String url_str = "http://13.125.254.66/api/users";  //실제 url
        //final String url_str = "http://www.naver.com";             //테스트 url
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
                    }else if(connection.getResponseCode() == 302){
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
        String uuid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID); //기기ID
        String json = "";
        //String uuid = "afdn3r";
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid", uuid);
            jsonObject.put("age", text_edit_age);
            jsonObject.put("gender", text_gender);

            JSONArray jsonArray = new JSONArray();

            for(String s: tids){
                jsonArray.put(myDBHandler.getTid(s)+".0");
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
