package com.example.dishdash.network;

import androidx.annotation.NonNull;

import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Food;

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

    @Override
    public void makeNetworkCallCategoryMeal(NetworkCallback<Category> networkCallBack) {
        foodService.getMealCategories().enqueue(new Callback<ListResponse<Category>>() {

            @Override
            public void onResponse(@NonNull Call<ListResponse<Category>> call, @NonNull retrofit2.Response<ListResponse<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    networkCallBack.onSuccessResult(response.body().categories);
                } else {
                    networkCallBack.onFailureResult("Failed to fetch category");
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Category>> call, Throwable throwable) {
                networkCallBack.onFailureResult(throwable.getMessage());
            }
        });
    }

    @Override
    public void makeNetworkCallCategoryMealById(String category, NetworkCallback<Food> networkCallBack) {
        foodService.getMealByIdCategory(category).enqueue(new Callback<ListResponse<Food>>() {
            @Override
            public void onResponse(Call<ListResponse<Food>> call, Response<ListResponse<Food>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    networkCallBack.onSuccessResult(response.body().allFood);
                } else {
                    networkCallBack.onFailureResult("Failed to fetch category");
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Food>> call, Throwable throwable) {
                networkCallBack.onFailureResult(throwable.getMessage());
            }
        });
    }

    @Override
    public void makeNetworkCallCountryMealById(String country, NetworkCallback<Food> networkCallBack) {
        foodService.getMealByCountry(country).enqueue(new Callback<ListResponse<Food>>() {
            @Override
            public void onResponse(Call<ListResponse<Food>> call, Response<ListResponse<Food>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    networkCallBack.onSuccessResult(response.body().allFood);
                } else {
                    networkCallBack.onFailureResult("Failed to fetch category");
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Food>> call, Throwable throwable) {
                networkCallBack.onFailureResult(throwable.getMessage());
            }
        });
    }

    @Override
    public void makeNetworkCallMealByName(String name, NetworkCallback<Food> networkCallBack) {
        foodService.getFoodByName(name).enqueue(new Callback<ListResponse<Food>>() {
            @Override
            public void onResponse(Call<ListResponse<Food>> call, Response<ListResponse<Food>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    networkCallBack.onSuccessResult(response.body().allFood);
                } else {
                    networkCallBack.onFailureResult("Failed to fetch category");
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Food>> call, Throwable throwable) {
                networkCallBack.onFailureResult(throwable.getMessage());
            }
        });
    }

    @Override
    public void makeNetworkCallIngradiant(String ingrediant, NetworkCallback<Food> networkCallBack) {
        foodService.getMealByIngredient(ingrediant).enqueue(new Callback<ListResponse<Food>>() {
            @Override
            public void onResponse(Call<ListResponse<Food>> call, Response<ListResponse<Food>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    networkCallBack.onSuccessResult(response.body().allFood);
                } else {
                    networkCallBack.onFailureResult("Failed to fetch category");
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Food>> call, Throwable throwable) {
                networkCallBack.onFailureResult(throwable.getMessage());
            }
        });
    }

    @Override
    public void makeNetworkCallFoodById(String id, NetworkCallback<Food> networkCallBack) {
        foodService.getMealById(id).enqueue(new Callback<ListResponse<Food>>() {
            @Override
            public void onResponse(Call<ListResponse<Food>> call, Response<ListResponse<Food>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    networkCallBack.onSuccessResult(response.body().allFood);
                } else {
                    networkCallBack.onFailureResult("Failed to fetch category");
                }
            }

            @Override
            public void onFailure(Call<ListResponse<Food>> call, Throwable throwable) {
                networkCallBack.onFailureResult(throwable.getMessage());
            }
        });

    }
}

