package com.example.dishdash.model;

import com.example.dishdash.network.NetworkCallback;

public interface FoodRepository {
    public void getRandomFood(NetworkCallback networkCallback);

}
