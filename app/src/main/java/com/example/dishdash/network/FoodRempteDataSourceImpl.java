package com.example.dishdash.network;

import androidx.annotation.NonNull;

import com.example.dishdash.model.response.Food;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FoodRempteDataSourceImpl implements FoodRemoteDataSource {

    public static final String TAG = "MealsActivity";
    private static final String BASE_URL = "https://www.themealdb.com/api/json/v1/1/";
    private FoodService foodService;
    private static FoodRempteDataSourceImpl client = null;

    private FoodRempteDataSourceImpl() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        foodService = retrofit.create(FoodService.class);
    }

    public static FoodRempteDataSourceImpl getInstance() {
        if (client == null) {
            client = new FoodRempteDataSourceImpl();
        }
        return client;
    }


    @Override
    public void makeNetworkCallRandomFood(NetworkCallback<Food> networkCallback) {
        foodService.getRandomFood().enqueue(new Callback<ListResponse<Food>>() {

            @Override
            public void onResponse(@NonNull Call<ListResponse<Food>> call, @NonNull retrofit2.Response<ListResponse<Food>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    networkCallback.onSuccessResult(response.body().allFood);
                } else {
                    networkCallback.onFailureResult("Failed to get random meal");
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Food>> call, Throwable throwable) {
                networkCallback.onFailureResult(throwable.getMessage());

            }


            });

        }
    }

