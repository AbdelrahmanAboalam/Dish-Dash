package com.example.dishdash.network;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListResponse<T> {

    @SerializedName("meals")
    public List<T> allFood;

    @SerializedName("categories")
    public List<T> categories;
}
