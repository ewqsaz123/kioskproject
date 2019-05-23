package com.skcnc.openmind.Model;

import java.util.HashMap;
import java.util.Map;

public class LogoData {
    public Map<Integer, String> map = new HashMap<>();

    public LogoData() {
        initMap();
    }

    public void initMap(){
        for(int i=1; i<70; i++){
            map.put(i, i+".png");
        }
    }

}
