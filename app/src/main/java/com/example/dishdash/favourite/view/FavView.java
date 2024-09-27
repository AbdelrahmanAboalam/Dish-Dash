package com.example.dishdash.favourite.view;

import com.example.dishdash.model.response.Food;

import java.util.List;

public interface FavView {

    public void showData(List<Food> foods);
    public void showErrMsg(String error);
}
