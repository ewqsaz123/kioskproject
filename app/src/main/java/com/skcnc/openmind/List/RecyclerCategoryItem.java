package com.skcnc.openmind.List;

public class RecyclerCategoryItem {

    public String brand;
    public int logo;

    public RecyclerCategoryItem(String brand, int logo) {
        this.brand = brand;
        this.logo = logo;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getLogo() {
        return logo;
    }

    public void setLogo(int logo) {
        this.logo = logo;
    }
}
