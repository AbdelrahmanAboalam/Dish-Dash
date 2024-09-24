package com.example.dishdash.network;

import com.example.dishdash.model.response.RandomFoodResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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
    public void makeNetworkCallRandomFood(NetworkCallback networkCallback) {
        foodService.getRandomFood().enqueue(new Callback<RandomFoodResponse>() {
            @Override
            public void onResponse(Call<RandomFoodResponse> call, Response<RandomFoodResponse> response) {
                if (response.isSuccessful()){
                    networkCallback.onSuccessResult(response.body().getFood());
                }
            }

            @Override
            public void onFailure(Call<RandomFoodResponse> call, Throwable throwable) {
                networkCallback.onFailureResult(throwable.getMessage());
            }
        });
    }
}
