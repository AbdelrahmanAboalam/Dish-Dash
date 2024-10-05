package com.example.dishdash.db;

import androidx.lifecycle.LiveData;

import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.FoodPlan;

import java.util.List;

public interface FoodLoaclDataBase {

    LiveData<List<Food>> getStoredFood();
    void insertFood(Food food);
    void removeFood(Food food);
    void checkProductExists(Food food);

    void updateFoodbyId(String mealId, boolean isFav);
    void updateFoodPlanbyId(String mealId, boolean isFav);

    LiveData<List<FoodPlan>> getPlannedFood(String date);
    void insertFoodPlan(FoodPlan foodPlan);
    void deleteFoodPlan(FoodPlan foodPlan);
    void updateFoodPlan(FoodPlan foodPlan);
    void checkIfMealExistsOnDate(FoodPlan foodPlan,CheckCallBack checkCallBack);
}
