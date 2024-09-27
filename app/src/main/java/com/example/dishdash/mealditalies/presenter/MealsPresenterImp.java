package com.example.dishdash.mealditalies.presenter;

import com.example.dishdash.favourite.view.FavView;
import com.example.dishdash.model.FoodRepository;
import com.example.dishdash.model.response.Food;

public class MealsPresenterImp implements MealsPresenter{

    FavView _view;
    FoodRepository _repo;

    public MealsPresenterImp(FavView _view, FoodRepository _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public void getProducts() {

    }

    @Override
    public void addToFav(Food food) {
        food.setFav(true);
        _repo.insertFood(food);
    }
}
