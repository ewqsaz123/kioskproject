package com.skcnc.openmind.Sqlite;

import android.app.ActionBar;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
        db.execSQL("INSERT INTO brand VALUES(1,'1.png','공차');");
        db.execSQL("INSERT INTO brand VALUES(2,'2.png','셀렉토 커피');");
        db.execSQL("INSERT INTO brand VALUES(3,'3.png','맘모스 커피');");
        db.execSQL("INSERT INTO brand VALUES(4,'4.png','커피베이');");
        db.execSQL("INSERT INTO brand VALUES(5,'5.png','달콤커피');");
        db.execSQL("INSERT INTO brand VALUES(6,'6.png','빽다방');");
        db.execSQL("INSERT INTO brand VALUES(7,'7.png','투썸플레이스');");
        db.execSQL("INSERT INTO brand VALUES(8,'8.png','커피만');");
        db.execSQL("INSERT INTO brand VALUES(9,'9.png','맘스터치');");
        db.execSQL("INSERT INTO brand VALUES(10,'10.png','토니버거');");
        db.execSQL("INSERT INTO brand VALUES(11,'11.png','맥도날드');");
        db.execSQL("INSERT INTO brand VALUES(12,'12.png','버거킹');");
        db.execSQL("INSERT INTO brand VALUES(13,'13.png','롯데리아');");
        db.execSQL("INSERT INTO brand VALUES(14,'14.png','KFC');");
        db.execSQL("INSERT INTO brand VALUES(15,'15.png','에그드랍');");
        db.execSQL("INSERT INTO brand VALUES(16,'16.png','푸드코트');");
        db.execSQL("INSERT INTO brand VALUES(17,'17.png','그릭데이');");
        db.execSQL("INSERT INTO brand VALUES(18,'18.png','신주쿠 카페');");
        db.execSQL("INSERT INTO brand VALUES(19,'19.png','채선당');");
        db.execSQL("INSERT INTO brand VALUES(20,'20.png','명동 할머니 국수');");
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

}
