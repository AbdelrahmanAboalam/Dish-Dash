package com.example.dishdash.db;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.FoodPlan;

import java.util.Collections;
import java.util.List;

public class FoodLocalDataSourceImp implements FoodLoaclDataBase{

    private static FoodLoaclDataBase foodLocalDataSource =null;
    private Context context;
    private AppDataBase db;
    private FoodDAO foodDAO;
    private LiveData<List<Food>> storedFood;
    private LiveData<List<FoodPlan>> plannedFood;

    public FoodLocalDataSourceImp(Context context) {
        this.context = context;
        db = AppDataBase.getInstance(context.getApplicationContext());
        foodDAO = db.getProductDAO();
        storedFood = foodDAO.getAllProducts();
    }
    public static FoodLoaclDataBase getInstance(Context context){

        if (foodLocalDataSource==null)
        {
            foodLocalDataSource=new FoodLocalDataSourceImp(context);
        }
        return foodLocalDataSource;
    }

    @Override
    public LiveData<List<Food>> getStoredFood() {
        return storedFood;
    }

    @Override
    public void insertFood(Food food) {
        new Thread(new Runnable() {
            @Override
            public void run() { foodDAO.insertProduct(food); }
        }).start();

    }

    @Override
    public void removeFood(Food food) {
        new Thread(new Runnable() {
            @Override
            public void run() { foodDAO.deleteProduct(food); }
        }).start();
    }

    @Override
    public void checkProductExists(Food food) {
        new Thread(new Runnable() {
            @Override
            public void run() { food.setFav(foodDAO.isProductExists(food.getMealId())); }
        }).start();

    }

    @Override
    public LiveData<List<FoodPlan>> getPlannedFood(String date) {
        return foodDAO.getPlannedFood(date);
    }

//    @Override
//    public List<FoodPlan> getPlannedFoodList(String date) {
//        return foodDAO.getPlannedFoodList(date);
//    }

    @Override
    public void insertFoodPlan(FoodPlan foodPlan) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                foodDAO.insertFoodPlan(foodPlan);
            }
        }).start();
    }

    @Override
    public void deleteFoodPlan(FoodPlan foodPlan) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                foodDAO.deleteFoodPlan(foodPlan);
            }
        }).start();
    }

    @Override
    public void updateFoodPlan(FoodPlan foodPlan) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                foodDAO.updateFoodPlan(foodPlan);
            }
        }).start();
    }
}
