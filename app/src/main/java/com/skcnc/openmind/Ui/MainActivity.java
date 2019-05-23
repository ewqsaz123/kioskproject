package com.skcnc.openmind.Ui;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.skcnc.openmind.R;
import com.skcnc.openmind.Util.U;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends Activity {

    String url_str = "http://13.125.254.66/api/users?uuid=";      //url
    String uuid = ""; //기기ID
    String TAG_LIKES = "likes";
    StringBuffer json_Response = new StringBuffer();            //응답 데이터
    URL url = null; HttpURLConnection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int isDBCreated = 0;
        uuid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        //json_Response.append("[{\"likes\":[\"1\",\"23\",\"21\",\"12\",\"53\",\"33\"]}]");

        if(isDBCreated != 1){
            if(isinternetCon()){
                U.getUinstance().toast(getApplicationContext(), "인터넷을 연결해주세요.");
            }else{
                try {
                    process();
                }catch (Exception e){
                    U.getUinstance().log("EEEEEE");
                }
            }
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

            JSONArray jsonArray = new JSONArray(json_Response.toString());
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            JSONArray jsonArray2 = jsonObject.getJSONArray(TAG_LIKES);
            for(int i=0; i<jsonArray2.length(); i++){
                values.add(jsonArray2.getString(i));
            }

            //U.getUinstance().saveSPSet(this, TAG_LIKES, values );   //SP에 추천 튜토리얼 저장

            U.getUinstance().log(U.getUinstance().getTutorials(this, TAG_LIKES).toString());
        }catch (Exception e){
            e.printStackTrace();
            U.getUinstance().log("JsonERROR");
        }
    }

    private boolean isinternetCon(){
        ConnectivityManager cManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return !mobile.isConnected() && !wifi.isConnected();
    }
}
