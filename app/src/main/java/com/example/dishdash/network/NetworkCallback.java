package com.example.dishdash.network;

import com.example.dishdash.model.response.Food;

import java.util.List;

public interface NetworkCallback<T> {

    public void onSuccessResult(List<T> food);

    public void onFailureResult(String errorMsg);
}
