package com.example.dishdash.calendar.presenter;

import androidx.lifecycle.LiveData;

import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.FoodPlan;

import java.util.List;

public interface CalendarPresenter {
    public LiveData<List<FoodPlan>> getPlanedFood(String date);
    public void removeFromPlan(FoodPlan foodPlan);
    public void removeFromFav(Food food);
    public void addtoFav(Food food);
    public void updateFoodById(String mealId, boolean isFav);
    public void updateFoodPlanById(String mealId, boolean isFav);

    public void isExist(FoodPlan foodPlan);
}
