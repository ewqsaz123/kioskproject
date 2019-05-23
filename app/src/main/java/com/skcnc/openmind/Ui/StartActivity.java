package com.skcnc.openmind.Ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.skcnc.openmind.R;
import com.skcnc.openmind.Util.U;

public class StartActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startApp();
            }
        }, 2000);

        /*Button b1 = (Button) findViewById(R.id.go_join);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), JoinActivity.class));
            }
        });

        Button b2 = (Button) findViewById(R.id.go_tutorial);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TutorialActivity.class));
            }
        });

        Button b3 = (Button) findViewById(R.id.go_main);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });*/
    }

    public void startApp(){
        //회원가입 여부 확인
        if(U.getUinstance().checkSP(this)){
            U.getUinstance().log("MainActivity opened");
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }else{
            U.getUinstance().log("JoinActivity opened");
            startActivity(new Intent(getApplicationContext(), JoinActivity.class));
        }
        finish();
    }
}
