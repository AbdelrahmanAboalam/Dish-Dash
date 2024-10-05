package com.example.dishdash.model.response;

import com.google.gson.annotations.SerializedName;

public class Ingred {

    @SerializedName("strIngredient")
    private String strIngredient ;

    public String getStrIngredient() {
        return strIngredient;
    }

    public void setStrIngredient(String strIngredient) {
        this.strIngredient = strIngredient;
    }
}
