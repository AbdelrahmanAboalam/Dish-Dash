package com.example.dishdash.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.FoodPlan;

import java.util.List;

@Dao
public interface FoodDAO {
    @Query("SELECT * FROM food_table")
    LiveData<List<Food>> getAllProducts();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertProduct(Food food);

    @Delete
    void deleteProduct(Food food);

    @Query("SELECT EXISTS(SELECT 1 FROM food_table WHERE mealId = :FoodId)")
    boolean isProductExists(String FoodId);
///////////////////////////////////////////////

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertFoodPlan(FoodPlan foodPlan);

    @Query("SELECT * FROM food_plan WHERE date = :date")
    LiveData<List<FoodPlan>> getPlannedFood(String date);

    @Query("SELECT * FROM food_plan WHERE date = :date")
    List<FoodPlan> getPlannedFoodList(String date);

    @Delete
    void deleteFoodPlan(FoodPlan foodPlan);

    @Update
    void updateFoodPlan(FoodPlan foodPlan);

    @Query("DELETE FROM food_plan WHERE date = :date")
    void deleteFoodPlanByDate(String date);


}
