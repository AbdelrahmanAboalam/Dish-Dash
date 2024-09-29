package com.example.dishdash.searchfragment.view;

import com.example.dishdash.model.response.Food;

import java.util.List;

public interface SerView {

    public void showData(List<Food> food);
    public void showErrMsg(String error);
}
