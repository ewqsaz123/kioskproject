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

        db.execSQL("INSERT INTO brand VALUES(1, 'https://s3.amazonaws.com/jsp-here.com/1.png', '공차');");
        db.execSQL("INSERT INTO brand VALUES(2, 'https://s3.amazonaws.com/jsp-here.com/2.png', '셀렉토 커피');");
        db.execSQL("INSERT INTO brand VALUES(3, 'https://s3.amazonaws.com/jsp-here.com/3.png', '맘모스 커피');");
        db.execSQL("INSERT INTO brand VALUES(4, 'https://s3.amazonaws.com/jsp-here.com/4.png', '커피베이');");
        db.execSQL("INSERT INTO brand VALUES(5, 'https://s3.amazonaws.com/jsp-here.com/5.png', '달콤커피');");
        db.execSQL("INSERT INTO brand VALUES(6, 'https://s3.amazonaws.com/jsp-here.com/6.png', '빽다방');");
        db.execSQL("INSERT INTO brand VALUES(7, 'https://s3.amazonaws.com/jsp-here.com/7.png', '투썸플레이스');");
        db.execSQL("INSERT INTO brand VALUES(8, 'https://s3.amazonaws.com/jsp-here.com/8.png', '커피만');");
        db.execSQL("INSERT INTO brand VALUES(9, 'https://s3.amazonaws.com/jsp-here.com/9.png', '맘스터치');");
        db.execSQL("INSERT INTO brand VALUES(10, 'https://s3.amazonaws.com/jsp-here.com/10.png', '토니버거');");

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
