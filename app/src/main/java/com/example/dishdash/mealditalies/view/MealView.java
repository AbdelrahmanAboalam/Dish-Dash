package com.example.dishdash.mealditalies.view;

import com.example.dishdash.model.response.Food;

import java.util.List;

public interface MealView {
    public void showData(List<Food> food);
    public void showErrMsg(String error);
}
