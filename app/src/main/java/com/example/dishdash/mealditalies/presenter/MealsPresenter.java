package com.example.dishdash.mealditalies.presenter;

import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.FoodPlan;

public interface MealsPresenter {

    public void getProducts();
    public void addToFav(Food food);

    public void addToPlan(FoodPlan foodPlan);
}
