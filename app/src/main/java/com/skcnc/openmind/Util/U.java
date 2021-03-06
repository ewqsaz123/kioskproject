package com.skcnc.openmind.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class U {
    private static final U uinstance = new U();

    public static U getUinstance() {return uinstance;}

    public  U(){}

    static String SAVE_TAG = "USER";   //SharedPreferences 태그

    //sp ("join", "db")
    private static ArrayList<String> brandList = new ArrayList<>();

    public String getUser(Context context, String key){
        return context.getSharedPreferences(key, 0).getString(key, "null");
    }

    //SP likes 가져오기
    public ArrayList<String> getTutorials(Context context, String key){
        Set<String> set = context.getSharedPreferences(SAVE_TAG, 0).getStringSet(key,null);
        return set==null? new ArrayList<String>(): new ArrayList<String>(set);
    }

    //SP tutorials, likes 저장
    public void saveSPSet(Context context, String key, ArrayList<String> values){
        SharedPreferences.Editor editor = context.getSharedPreferences(SAVE_TAG, 0).edit();
        Set<String> set = new HashSet<>();
        set.addAll(values);
        editor.putStringSet(key, set);
        editor.apply();
    }

    //SP 저장
    public void saveSPUser(Context context, String key, String value){
        SharedPreferences.Editor editor = context.getSharedPreferences(SAVE_TAG, 0 ).edit();
        //editor.clear();
        editor.putString(key, value);
        editor.commit();
    }

    public boolean getSPBoolean(Context context, String key){
        return context.getSharedPreferences(SAVE_TAG, 0 ).getBoolean(key, false);
    }

    public void savaSPBoolean(Context context, String key, Boolean b){
        SharedPreferences.Editor editor = context.getSharedPreferences(SAVE_TAG, 0 ).edit();
        editor.putBoolean(key, b);
        editor.commit();
    }

    //SP 초기화
    public void clearSP(Context context){
        SharedPreferences.Editor editor= context.getSharedPreferences(SAVE_TAG, 0).edit();
        editor.clear();
    }

    //SP 존재하는지 확인
    public boolean checkSP(Context context){
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

    //성공 타입 프로그레스
    public void showPopup2(Context context, String title, String msg,
                           String cName, SweetAlertDialog.OnSweetClickListener cEvent
    ){
        new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText(title)
                .setContentText(msg)
                .setConfirmText(cName)
                .setConfirmClickListener(cEvent)
                .show();

    }

    public void addBrand(String str){
        brandList.add(str);
    }

    public void removeBrand(String str){
        brandList.remove(str);
    }

    public ArrayList<String> getBrandList(){
        return brandList;
    }
}
