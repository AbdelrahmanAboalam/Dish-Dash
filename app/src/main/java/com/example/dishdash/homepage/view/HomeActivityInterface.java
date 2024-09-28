package com.example.dishdash.homepage.view;

import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Food;

import java.util.List;

public interface HomeActivityInterface {
    public void showData(List<Food> food);
    public void showErrMsg(String error);

    public void showCategoryData(List<Category> meals);
    public void showCategoryErrMsg(String error);

    public void showCategoryFood(List<Food> food);
    public void showCategoryFoodErrMsg(String error);

}
