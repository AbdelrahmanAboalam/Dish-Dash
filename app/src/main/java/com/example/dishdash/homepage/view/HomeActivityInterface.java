package com.example.dishdash.homepage.view;

import com.example.dishdash.model.response.Food;

import java.util.List;

public interface HomeActivityInterface {
    public void showData(List<Food> food);
    public void showErrMsg(String error);
}
