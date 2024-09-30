package com.example.dishdash.mealditalies.presenter;

import com.example.dishdash.favourite.view.FavView;
import com.example.dishdash.model.FoodRepository;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.FoodPlan;
import com.example.dishdash.network.NetworkCallback;

import java.util.List;

public class MealsPresenterImp implements MealsPresenter, NetworkCallback<Food> {

    FavView _view;
    FoodRepository _repo;

    public MealsPresenterImp(FavView _view, FoodRepository _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public void getProducts() {
_repo.getStoredFood();

    }

    @Override
    public void addToFav(Food food) {
        food.setFav(true);
        _repo.insertFood(food);
    }

    @Override
    public void addToPlan(FoodPlan foodPlan) {
        _repo.insertFoodPlan(foodPlan);
    }

    @Override
    public void onSuccessResult(List<Food> food) {
        for ( Food foods : food){
            _repo.checkFoodExists(foods);
        }
        _view.showData(food);

    }

    @Override
    public void onFailureResult(String errorMsg) {
        _view.showErrMsg(errorMsg);
    }
}
