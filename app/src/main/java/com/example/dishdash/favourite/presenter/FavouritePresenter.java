package com.example.dishdash.favourite.presenter;

import androidx.lifecycle.LiveData;

import com.example.dishdash.model.response.Food;

import java.util.List;

public interface FavouritePresenter {
    public LiveData<List<Food>> getFavFood();
    public void removeFromFav(Food food);
    public void updateFoodPlanbyId(String mealId, boolean isFav);
}
