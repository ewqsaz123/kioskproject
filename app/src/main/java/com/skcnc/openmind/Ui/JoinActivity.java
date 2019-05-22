package com.skcnc.openmind.Ui;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.skcnc.openmind.R;
import com.skcnc.openmind.Util.U;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class JoinActivity extends Activity {

    URL postURL = null; HttpURLConnection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_user);

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
                String[] tids = {"1","23","21","12","53","33"};

                if(isinternetCon()){
                    U.getUinstance().toast(getApplicationContext(), "인터넷을 연결해주세요.");
                }else{
                    try {
                        process(text_edit_age, text_gender, tids);
                    }catch (Exception e){
                        U.getUinstance().log("EEEEEE");
                    }
                }
            }
        });

        //건너뛰기 클릭
    }

    //post 요청
    public void process(String text_edit_age, String text_gender, String[] tids) throws IOException {
        //final String url_str = "http://13.125.254.66/api/users";
        final String url_str = "http://www.naver.com";
        final String json = convertToJson(Integer.parseInt(text_edit_age), text_gender, tids);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    postURL = new URL(url_str);
                    connection = (HttpURLConnection) postURL.openConnection();
                    U.getUinstance().log("INTERNET ERROR1");
                    connection.setRequestMethod("POST");
                    //connection.setRequestProperty("Accept", "application/json");
                    //connection.setRequestProperty("Content-type", "application/json");
                    U.getUinstance().log("INTERNET ERROR2");
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    U.getUinstance().log("INTERNET ERROR3");
                    OutputStream os = connection.getOutputStream();
                    U.getUinstance().log("INTERNET ERROR4");
                    os.write(json.getBytes("UTF-8"));
                    os.flush();

                    //is = connection.getInputStream();
                    //U.getUinstance().log(is.toString());

                    U.getUinstance().log(connection.getResponseCode()+"");

                    //connection.disconnect();
                }catch (Exception e){
                    U.getUinstance().log("INTERNET ERROR");
                    e.printStackTrace();
                }finally {
                    connection.disconnect();//네트워크 연결해제
                }
            }
        });
    }

    public String convertToJson(int text_edit_age, String text_gender, String[] tids){
        String json = "";

        String uuid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID); //기기ID
/*
        StringBuffer sb = new StringBuffer();
        sb.append("{\"");
        sb.append("\"uuid\": \"").append(uuid).append("\", ");
        sb.append("\"age\": ").append(text_edit_age).append(", ");
        sb.append("gender: ").append(text_gender).append(", ");
        sb.append("likes: [");
        for(int i=0; i<tids.length-1; i++){
            sb.append(tids[i]).append(", ");
        }
        sb.append(tids[tids.length-1]).append("] }");*/
        try{
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid", uuid);
            jsonObject.put("age", text_edit_age);
            jsonObject.put("gender", text_gender);

            JSONObject tid_json = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for(String s: tids){
                //tid_json.put("", s);
                jsonArray.put(s);
            }
            //jsonArray.put(tid_json);
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
