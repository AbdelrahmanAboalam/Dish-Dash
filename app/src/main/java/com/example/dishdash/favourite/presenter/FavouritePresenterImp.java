package com.example.dishdash.favourite.presenter;

import androidx.lifecycle.LiveData;

import com.example.dishdash.favourite.view.FavView;
import com.example.dishdash.model.FoodRepository;
import com.example.dishdash.model.response.Food;

import java.util.List;

public class FavouritePresenterImp implements FavouritePresenter{
    private FavView view;
    private FoodRepository _repo;

    public FavouritePresenterImp(FavView view, FoodRepository _repo) {
        this.view = view;
        this._repo = _repo;
    }

    @Override
    public LiveData<List<Food>> getFavFood() {
        return _repo.getStoredFood();
    }

    @Override
    public void removeFromFav(Food food) {
        food.setFav(false);
        _repo.deleteFood(food);

    }

    @Override
    public void updateFoodPlanbyId(String mealId, boolean isFav) {
        _repo.updateFoodPlanbyId(mealId, isFav);
    }
}
