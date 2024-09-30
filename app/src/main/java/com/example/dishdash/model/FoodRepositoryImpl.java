package com.example.dishdash.model;

import androidx.lifecycle.LiveData;

import com.example.dishdash.db.FoodLoaclDataBase;
import com.example.dishdash.db.FoodLocalDataSourceImp;
import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.FoodPlan;
import com.example.dishdash.network.FoodRemoteDataSource;
import com.example.dishdash.network.NetworkCallback;

import java.util.List;

public class FoodRepositoryImpl implements FoodRepository{

    FoodLoaclDataBase localDataSource;
    FoodRemoteDataSource remoteSource;
    private static FoodRepositoryImpl repo = null;
    private String date;

    private FoodRepositoryImpl (FoodRemoteDataSource remoteSource, FoodLoaclDataBase localDataSource,String date)
    {
        this.remoteSource=remoteSource;
        this.localDataSource=localDataSource;
        this.date=date;
    }

    public static FoodRepositoryImpl getInstance(FoodRemoteDataSource remoteSource, FoodLoaclDataBase localDataSource){
        if (repo==null){
            repo =new FoodRepositoryImpl(remoteSource,localDataSource,"");
        }
        return repo;
    }
    @Override
    public void getRandomFood(NetworkCallback networkCallback) {
            remoteSource.makeNetworkCallRandomFood(networkCallback);
    }

    @Override
    public void getMealCategories(NetworkCallback<Category> networkCallBack) {
        remoteSource.makeNetworkCallCategoryMeal(networkCallBack);
    }

    @Override
    public void getMealByCountry(String country, NetworkCallback<Food> callback) {
        remoteSource.makeNetworkCallCountryMealById(country,callback);
    }

    @Override
    public void getMealByIngredient(String ingredient, NetworkCallback<Food> callback) {
        remoteSource.makeNetworkCallIngradiant(ingredient,callback);
    }

    @Override
    public void getMealById(String id, NetworkCallback<Food> callback) {
        remoteSource.makeNetworkCallFoodById(id,callback);
    }

    @Override
    public void getMealByName(String name, NetworkCallback<Food> callback) {
        remoteSource.makeNetworkCallMealByName(name,callback);
    }

    @Override
    public void getMealByCategory(String id, NetworkCallback callback) {
        remoteSource.makeNetworkCallCategoryMealById(id,callback);
    }

    @Override
    public LiveData<List<Food>> getStoredFood() {
        return localDataSource.getStoredFood();
    }

    @Override
    public void insertFood(Food food) {
        localDataSource.insertFood(food);
    }

    @Override
    public void deleteFood(Food food) {
        localDataSource.removeFood(food);
    }

    @Override
    public void checkFoodExists(Food food) {
        localDataSource.checkProductExists(food);

    }

    @Override
    public LiveData<List<FoodPlan>> getPlannedFood(String date) {
        return localDataSource.getPlannedFood(date);
    }

    @Override
    public void insertFoodPlan(FoodPlan foodPlan) {
        localDataSource.insertFoodPlan(foodPlan);
    }

    @Override
    public void deleteFoodPlan(FoodPlan foodPlan) {
        localDataSource.deleteFoodPlan(foodPlan);
    }

    @Override
    public void updateFoodPlan(FoodPlan foodPlan) {
        localDataSource.updateFoodPlan(foodPlan);
    }


}
