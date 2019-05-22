package com.skcnc.openmind.Ui;

import android.app.Activity;
import android.databinding.DataBinderMapper;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.skcnc.openmind.R;
import com.skcnc.openmind.Util.U;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class MainActivity extends Activity {

    String url_str = "http://www.naver.com";                                                        //url
    String uuid = ""; //기기ID
    String json_Response = "";
    URL url = null; HttpURLConnection connection = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int isDBCreated = 1;
        uuid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        if(isDBCreated != 1){
            if(isinternetCon()){
                U.getUinstance().toast(getApplicationContext(), "인터넷을 연결해주세요.");
            }else{
                try {
                    //process();
                }catch (Exception e){
                    U.getUinstance().log("EEEEEE");
                }
            }
        }

        //만들어진 디비 갖다 뿌려줌//
            //showRecomm();//
    }
    public void process() throws Exception {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    url = new URL(url_str);
                    //String s = Settings.ACTION_SYNC_SETTINGS.


                    connection = (HttpURLConnection) url.openConnection();


                }catch (Exception e){

                }

            }
        });
    }


    public void showRecomm(String Json){


    }
    private boolean isinternetCon(){
        ConnectivityManager cManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return !mobile.isConnected() && !wifi.isConnected();
    }
}
