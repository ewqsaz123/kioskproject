package com.skcnc.openmind.Sqlite;

import android.app.ActionBar;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;

import com.skcnc.openmind.R;

import java.sql.SQLData;

public class MyDBHandler extends SQLiteOpenHelper {

    private final String SQL_CREATE_BRAND = "CREATE TABLE IF NOT EXISTS brand(tid INTEGER primary key, image TEXT, name TEXT);";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_BRAND);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void insert(int tid, String image, String name){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "INSERT INTO brand VALUES("+tid+",'"+image+"','"+name+"');";
    }

    public void insert(){
        SQLiteDatabase db = getWritableDatabase();

        db.execSQL("INSERT INTO brand VALUES(1, '1.png', '공차');");
        db.execSQL("INSERT INTO brand VALUES(2, '2.png', '셀렉토 커피');");
        db.execSQL("INSERT INTO brand VALUES(3, '3.png', '맘모스 커피');");
        db.execSQL("INSERT INTO brand VALUES(4, '4.png', '커피베이');");
        db.execSQL("INSERT INTO brand VALUES(5, '5.png', '달콤커피');");
        db.execSQL("INSERT INTO brand VALUES(6, '6.png', '빽다방');");
        db.execSQL("INSERT INTO brand VALUES(7, '7.png', '투썸플레이스');");
        db.execSQL("INSERT INTO brand VALUES(8, '8.png', '커피만');");
        db.execSQL("INSERT INTO brand VALUES(9, '9.png', '맘스터치');");
        db.execSQL("INSERT INTO brand VALUES(10, '10.png', '토니버거');");
        db.execSQL("INSERT INTO brand VALUES(11, '11.png', '맥도날드');");
        db.execSQL("INSERT INTO brand VALUES(12, '12.png', '버거킹');");
        db.execSQL("INSERT INTO brand VALUES(13, '13.png', '롯데리아');");
        db.execSQL("INSERT INTO brand VALUES(14, '14.png', 'KFC');");
        db.execSQL("INSERT INTO brand VALUES(15, '15.png', '에그드랍');");
        db.execSQL("INSERT INTO brand VALUES(16, '16.png', '푸드코트');");
        db.execSQL("INSERT INTO brand VALUES(17, '17.png', '그릭데이');");
        db.execSQL("INSERT INTO brand VALUES(18, '18.png', '신주쿠 카레');");
        db.execSQL("INSERT INTO brand VALUES(19, '19.png', '채선당');");
        db.execSQL("INSERT INTO brand VALUES(20, '20.png', '명동 할머니 국수');");
        db.execSQL("INSERT INTO brand VALUES(21, '21.png', '역전우동');");
        db.execSQL("INSERT INTO brand VALUES(22, '22.png', '미정국수');");
        db.execSQL("INSERT INTO brand VALUES(23, '23.png', '미분당');");
        db.execSQL("INSERT INTO brand VALUES(24, '24.png', '전티마이 베트남 쌀국수');");
        db.execSQL("INSERT INTO brand VALUES(25, '25.png', '포베이');");
        db.execSQL("INSERT INTO brand VALUES(26, '26.png', '베트남쌀국수');");
        db.execSQL("INSERT INTO brand VALUES(27, '27.png', '돈까스 클럽');");
        db.execSQL("INSERT INTO brand VALUES(28, '28.png', '한솥');");
        db.execSQL("INSERT INTO brand VALUES(29, '29.png', '싸움의 고수');");
        db.execSQL("INSERT INTO brand VALUES(30, '30.png', '모범떡볶이');");
        db.execSQL("INSERT INTO brand VALUES(31, '31.png', '33 떡볶이');");
        db.execSQL("INSERT INTO brand VALUES(32, '32.png', '신전떡볶이');");
        db.execSQL("INSERT INTO brand VALUES(33, '33.png', '롯데백화점');");
        db.execSQL("INSERT INTO brand VALUES(34, '34.png', '홈플러스');");
        db.execSQL("INSERT INTO brand VALUES(35, '35.png', '현대백화점');");
        db.execSQL("INSERT INTO brand VALUES(36, '36.png', '세이브존');");
        db.execSQL("INSERT INTO brand VALUES(37, '37.png', '이마트');");
        db.execSQL("INSERT INTO brand VALUES(38, '38.png', '롯데마트');");
        db.execSQL("INSERT INTO brand VALUES(39, '39.png', '서울대병원');");
        db.execSQL("INSERT INTO brand VALUES(40, '40.png', '청주성모병원');");
        db.execSQL("INSERT INTO brand VALUES(41, '41.png', '경희의료원');");
        db.execSQL("INSERT INTO brand VALUES(42, '42.png', '차병원');");
        db.execSQL("INSERT INTO brand VALUES(43, '43.png', '지샘병원');");
        db.execSQL("INSERT INTO brand VALUES(44, '44.png', 'KT is');");
        db.execSQL("INSERT INTO brand VALUES(45, '45.png', 'Cube Refund');");
        db.execSQL("INSERT INTO brand VALUES(46, '46.png', 'Global Tax Free');");
        db.execSQL("INSERT INTO brand VALUES(47, '47.png', 'Global Blue');");
        db.execSQL("INSERT INTO brand VALUES(48, '48.png', '시외버스');");
        db.execSQL("INSERT INTO brand VALUES(49, '49.png', '고속버스');");
        db.execSQL("INSERT INTO brand VALUES(50, '50.png', '지하철');");
        db.execSQL("INSERT INTO brand VALUES(51, '51.png', '인천공항');");
        db.execSQL("INSERT INTO brand VALUES(52, '52.png', '김포공항');");
        db.execSQL("INSERT INTO brand VALUES(53, '53.png', '김해공항');");
        db.execSQL("INSERT INTO brand VALUES(54, '54.png', '우리은행');");
        db.execSQL("INSERT INTO brand VALUES(55, '55.png', 'KB 국민은행');");
        db.execSQL("INSERT INTO brand VALUES(56, '56.png', '신협');");
        db.execSQL("INSERT INTO brand VALUES(57, '57.png', 'ibk 기업은행');");
        db.execSQL("INSERT INTO brand VALUES(58, '58.png', '국민은행');");
        db.execSQL("INSERT INTO brand VALUES(59, '59.png', 'NH 농협은행');");
        db.execSQL("INSERT INTO brand VALUES(60, '60.png', '신한은행');");
        db.execSQL("INSERT INTO brand VALUES(61, '61.png', '하나은행');");
        db.execSQL("INSERT INTO brand VALUES(62, '62.png', 'sc 제일은행');");
        db.execSQL("INSERT INTO brand VALUES(63, '63.png', '시티은행');");
        db.execSQL("INSERT INTO brand VALUES(64, '64.png', '경남은행');");
        db.execSQL("INSERT INTO brand VALUES(65, '65.png', '부산은행');");
        db.execSQL("INSERT INTO brand VALUES(66, '66.png', '정부청사');");
        db.execSQL("INSERT INTO brand VALUES(67, '67.png', '동사무소');");
        db.execSQL("INSERT INTO brand VALUES(68, '68.png', '면사무소');");
        db.execSQL("INSERT INTO brand VALUES(69, '69.png', '구청');");

        db.close();
    }

    public TableBrand getBrand(int tid){
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM brand WHERE tid="+tid+";";
        TableBrand tableBrand = new TableBrand();
        Cursor cursor = db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            tableBrand.setTid(cursor.getInt(0));
            tableBrand.setImage(cursor.getString(1));
            tableBrand.setName(cursor.getString(2));
        }

        return tableBrand;
    }

    public String getTid(String brandName){
        int s = 0;
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM brand WHERE name='"+brandName+"';";
        Cursor cursor= db.rawQuery(sql, null);
        while(cursor.moveToNext()){
            s = cursor.getInt(0);
        }
        return Integer.toString(s);
    }

}
