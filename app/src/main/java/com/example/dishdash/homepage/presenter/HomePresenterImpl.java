package com.example.dishdash.homepage.presenter;

import com.example.dishdash.homepage.view.HomeActivityInterface;
import com.example.dishdash.homepage.view.HomePageActivity;
import com.example.dishdash.model.FoodRepository;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.network.NetworkCallback;

import java.util.List;

public class HomePresenterImpl implements NetworkCallback<Food>,HomePresenter {

        private HomeActivityInterface _view;
        private FoodRepository _repo;

    public HomePresenterImpl(HomeActivityInterface _view, FoodRepository _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public void getRandomFood() {
        _repo.getRandomFood(this);
    }

    @Override
    public void onSuccessResult(List<Food> food) {
        _view.showData(food);
    }

    @Override
    public void onFailureResult(String errorMsg) {
        _view.showErrMsg(errorMsg);
    }
}
