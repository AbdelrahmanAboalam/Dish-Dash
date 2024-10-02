package com.example.dishdash.model.response;

import com.google.gson.annotations.SerializedName;

public class Country {
    @SerializedName("strArea")
    private String strArea;

    public Country(String area) {
        this.strArea = area;
    }

    public String getArea() {
        return strArea;
    }

    public void setArea(String area) {
        this.strArea = area;
    }
}
