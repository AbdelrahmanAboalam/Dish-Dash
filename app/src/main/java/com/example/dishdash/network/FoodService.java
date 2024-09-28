package com.example.dishdash.network;

import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Food;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FoodService {
    @GET("random.php")
    Call<ListResponse<Food>> getRandomFood();

    @GET("categories.php")
    Call<ListResponse<Category>> getMealCategories();

    @GET("filter.php")
    Call<ListResponse<Food>> getMealByIdCategory(@Query("c") String category);

    @GET("lookup.php")
    Call<ListResponse<Food>> getMealById(@Query("c") String id);


}
