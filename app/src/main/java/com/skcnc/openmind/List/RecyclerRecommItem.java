package com.skcnc.openmind.List;

public class RecyclerRecommItem {

    public String brand;
    public String logo;

    public RecyclerRecommItem(String brand, String logo) {
        this.brand = brand;
        this.logo = logo;
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
}
