package com.skcnc.openmind.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class U {
    private static final U uinstance = new U();

    public static U getUinstance() {return uinstance;}

    public  U(){}


    static String SAVE_TAG = "USER";   //SharedPreferences 태그

    //SP 저장
    public void saveSharedPreferences(Context context, ArrayList<String> keys, ArrayList<String> values){
        SharedPreferences.Editor editor = context.getSharedPreferences(SAVE_TAG, 0 ).edit();

        for(int i=0; i<keys.size();i++){
            editor.putString(keys.get(i), values.get(i));
        }
        editor.commit();
    }

    //SP 초기화
    public void clearSharedPreferences(Context context){
        SharedPreferences.Editor editor= context.getSharedPreferences(SAVE_TAG, 0).edit();
        editor.clear();
    }

    //SP 존재하는지 확인
    public boolean checkSharedPreferences(Context context){
        return context.getSharedPreferences(SAVE_TAG, 0).contains("uuid");
    }

    //로그 날리기
    public void log(String msg){
        Log.e("LOGDATA", msg);
    }

    //토스트 날리기
    public void toast(Context context, String msg){
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
