package com.example.dishdash.network;

import com.example.dishdash.model.response.Food;

import java.util.List;

public interface NetworkCallback {

    public void onSuccessResult(List<Food> food);
    public void onFailureResult(String errorMsg);
}
