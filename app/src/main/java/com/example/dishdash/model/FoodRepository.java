package com.example.dishdash.model;

import androidx.lifecycle.LiveData;

import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.FoodPlan;
import com.example.dishdash.network.NetworkCallback;

import java.util.List;

public interface FoodRepository {
    public void getRandomFood(NetworkCallback networkCallback);
    public void getMealCategories(NetworkCallback<Category> networkCallBack);
    void getMealByCategory(String id , NetworkCallback<Food> callback);

    void getMealByCountry(String country , NetworkCallback<Food> callback);
    void getMealByIngredient(String ingredient , NetworkCallback<Food> callback);
    void getMealById(String id , NetworkCallback<Food> callback);
    void getMealByName(String name , NetworkCallback<Food> callback);

    public LiveData<List<Food>> getStoredFood();
    public void insertFood(Food food);
    public void deleteFood(Food food);
    public void checkFoodExists(Food food);


    public LiveData<List<FoodPlan>> getPlannedFood(String date);
    public void insertFoodPlan(FoodPlan foodPlan);
    public void deleteFoodPlan(FoodPlan foodPlan);
    public void updateFoodPlan(FoodPlan foodPlan);
}
