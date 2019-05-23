package com.skcnc.openmind.List;

public class RecyclerJoinItem {

    public String brand;
    public String logo;
    public Boolean check;

    public RecyclerJoinItem(String brand, String logo) {
        this.brand = brand;
        this.logo = logo;
        this.check = false;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Boolean isChecked() {
        return check;
    }

    public void setSelected(Boolean check) {
        this.check = check;
    }
}
