package com.example.dishdash.network;

import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Food;

public interface FoodRemoteDataSource {
    public void makeNetworkCallRandomFood(NetworkCallback<Food> networkCallback);
    public void makeNetworkCallCategoryMeal(NetworkCallback<Category> networkCallBack);
    public void makeNetworkCallCategoryMealById(String category , NetworkCallback<Food> networkCallBack);
}
