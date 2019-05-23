package com.skcnc.openmind.Ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.skcnc.openmind.R;
import com.skcnc.openmind.Util.U;

import java.net.HttpURLConnection;
import java.net.URL;

public class TutorialActivity extends Activity {

    //String url_str = "https://kimoa-ac3c0.firebaseapp.com/simulator";
    String url_str = "http://192.168.0.78:3000/simulator";
    URL url = null;
    HttpURLConnection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        /******* tid 데이터 인텐트 받아오기 *******/
        String uuid = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID); //기기ID
        String tid = "11";

        //가입여부 확인 후 url추가
        url_str += "?uuid="+ uuid +"&tid="+tid;
        U.getUinstance().log(url_str);

        /**   회원가입 되었는지 확인
            if(U.getUinstance().checkSP(this)){
            url_str += "?uuid="+ uuid +"&tid="+tid;
            U.getUinstance().log(url_str);
        }
         **/

        if(isinternetCon()){
            U.getUinstance().toast(getApplicationContext(), "인터넷을 연결해주세요.");
        }else{
            WebView webView = (WebView) findViewById(R.id.webView);
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setJavaScriptEnabled(true);
            webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
            webView.addJavascriptInterface(new JavaScriptPasser(this),"Android");
            webView.loadUrl(url_str);
        }
    }

    private boolean isinternetCon(){
        ConnectivityManager cManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo mobile = cManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = cManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return !mobile.isConnected() && !wifi.isConnected();
    }
    class JavaScriptPasser{
        Context mContext;

        public JavaScriptPasser(Context c){
            mContext = c;
        }

        @JavascriptInterface
        public void onButtonClick(){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

    }
}



