package com.example.dishdash.model;

import androidx.lifecycle.LiveData;

import com.example.dishdash.model.response.Food;
import com.example.dishdash.network.NetworkCallback;

import java.util.List;

public interface FoodRepository {
    public void getRandomFood(NetworkCallback networkCallback);
    public LiveData<List<Food>> getStoredFood();
    public void insertFood(Food food);
    public void deleteFood(Food food);
    public void checkFoodExists(Food food);

}
