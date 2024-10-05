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
            public void run() {
                food.setFav(true);
                foodDAO.insertProduct(food);
            }
        }).start();

    }

    @Override
    public void removeFood(Food food) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                food.setFav(false);
                foodDAO.deleteProduct(food);
            }
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
    public void updateFoodbyId(String mealId, boolean isFav) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                foodDAO.updateFoodbyId(mealId, isFav);
            }
        }).start();
    }

    @Override
    public void updateFoodPlanbyId(String mealId, boolean isFav) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                foodDAO.updateFoodPlanbyId(mealId, isFav);
            }
        }).start();
    }

    @Override
    public LiveData<List<FoodPlan>> getPlannedFood(String date) {
        return foodDAO.getPlannedFood(date);
    }



    @Override
    public void insertFoodPlan(FoodPlan foodPlan) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                foodPlan.setExist(true);
                foodDAO.insertFoodPlan(foodPlan);
            }
        }).start();
    }

    @Override
    public void deleteFoodPlan(FoodPlan foodPlan) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                foodPlan.setExist(false);
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

    @Override
    public void checkIfMealExistsOnDate(FoodPlan foodPlan, CheckCallBack checkCallBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                 boolean exist =foodDAO.isMealExistsOnDate(foodPlan.getMealId(), foodPlan.getDate());
                 foodPlan.setExist(exist);
                 checkCallBack.onMealCheckResult(exist);
            }
        }).start();
    }

}
