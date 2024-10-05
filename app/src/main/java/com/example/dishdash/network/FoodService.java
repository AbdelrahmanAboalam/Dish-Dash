package com.example.dishdash.network;

import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Country;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.Ingred;

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

    @GET("filter.php")
    Call<ListResponse<Food>> getMealByIngredient(@Query("i") String ingredient);

    @GET("filter.php")
    Call<ListResponse<Food>> getMealByCountry(@Query("a") String country);

    @GET("lookup.php")
    Call<ListResponse<Food>> getMealById(@Query("i") String id);

    @GET("search.php")
    Call<ListResponse<Food>> getFoodByName(@Query("s") String foodName);

    @GET("list.php?a=list")
    Call<ListResponse<Country>> getCountries();

    @GET("list.php?i=list")
    Call<ListResponse<Ingred>> getIngredients();
}
