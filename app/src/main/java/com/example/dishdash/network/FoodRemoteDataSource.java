package com.example.dishdash.network;

import com.example.dishdash.model.response.Food;

public interface FoodRemoteDataSource {
    public void makeNetworkCallRandomFood(NetworkCallback<Food> networkCallback);
}
