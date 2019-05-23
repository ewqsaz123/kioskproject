package com.skcnc.openmind.Sqlite;

public class TableBrand {
    int tid;
    String image;
    String name;


    public TableBrand(){

    }

    public TableBrand(int tid, String image, String name) {
        this.tid = tid;
        this.image = image;
        this.name = name;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
