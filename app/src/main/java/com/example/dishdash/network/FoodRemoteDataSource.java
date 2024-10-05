package com.example.dishdash.network;

import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Country;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.Ingred;

public interface FoodRemoteDataSource {
    public void makeNetworkCallRandomFood(NetworkCallback<Food> networkCallback);
    public void makeNetworkCallCategoryMeal(NetworkCallback<Category> networkCallBack);
    public void makeNetworkCallCategoryMealById(String category , NetworkCallback<Food> networkCallBack);
    public void makeNetworkCallCountryMealById(String country , NetworkCallback<Food> networkCallBack);
    public void makeNetworkCallMealByName(String name , NetworkCallback<Food> networkCallBack);

    public void makeNetworkCallIngredients(NetworkCallback<Ingred> networkCallBack);
    public void makeNetworkCallCountries(NetworkCallback<Country> networkCallback);
    public void makeNetworkCallIngradiant(String ingrediant , NetworkCallback<Food> networkCallBack);
    public void makeNetworkCallFoodById(String id , NetworkCallback<Food> networkCallBack);


}
