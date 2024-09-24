package com.example.dishdash.network;

import com.example.dishdash.model.response.RandomFoodResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FoodService {
    @GET("random.php")
    Call<RandomFoodResponse> getRandomFood();
}
