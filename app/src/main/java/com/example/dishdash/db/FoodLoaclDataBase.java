package com.example.dishdash.db;

import androidx.lifecycle.LiveData;

import com.example.dishdash.model.response.Food;

import java.util.List;

public interface FoodLoaclDataBase {

    LiveData<List<Food>> getStoredFood();
    void insertFood(Food food);
    void removeFood(Food food);
    void checkProductExists(Food food);
}
