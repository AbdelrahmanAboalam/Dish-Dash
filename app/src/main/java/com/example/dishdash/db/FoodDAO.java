package com.example.dishdash.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.dishdash.model.response.Food;

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



}
