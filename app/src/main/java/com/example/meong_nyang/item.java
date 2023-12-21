package com.example.meong_nyang;

import androidx.annotation.Keep;

@Keep
public class item {
    String name;
    String image;

    String code;
    String detail;
    int percent;

    public item(){

    }

    public String getImage(){
        return image;
    }

    public String getName(){
        return name;
    }

    public String getDetail(){ return detail;}

    public int getPercent() {
        return percent;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
