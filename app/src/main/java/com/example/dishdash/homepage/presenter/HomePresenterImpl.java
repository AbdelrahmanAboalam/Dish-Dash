package com.example.dishdash.homepage.presenter;

import com.example.dishdash.homepage.view.HomeActivityInterface;
import com.example.dishdash.homepage.view.HomePageActivity;
import com.example.dishdash.model.FoodRepository;
import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Country;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.network.NetworkCallback;

import java.util.List;

public class HomePresenterImpl implements HomePresenter {

        private HomeActivityInterface _view;
        private FoodRepository _repo;

    private class RandomMealCallback implements NetworkCallback<Food> {
        @Override
        public void onSuccessResult(List<Food> pojo) {
            _view.showData(pojo);
        }

        @Override
        public void onFailureResult(String errorMsg) {
            _view.showErrMsg(errorMsg);
        }
    }

    private class CategoryMealCallback implements NetworkCallback<Category> {
        @Override
        public void onSuccessResult(List<Category> pojo) {
            _view.showCategoryData(pojo);
        }

        @Override
        public void onFailureResult(String errorMsg) {
            _view.showCategoryErrMsg(errorMsg);
        }
    }
        private class CategoryFoodCallback implements NetworkCallback<Food> {


            @Override
            public void onSuccessResult(List<Food> food) {
                _view.showCategoryFood(food);
            }

            @Override
            public void onFailureResult(String errorMsg) {
                _view.showCategoryFoodErrMsg(errorMsg);
            }

        }

    private class CountryCallBack implements NetworkCallback<Country> {
        @Override
        public void onSuccessResult(List<Country> pojo) {
            _view.showCountry(pojo);
        }

        @Override
        public void onFailureResult(String errorMsg) {
            _view.showErrMsg(errorMsg);
        }
    }

    private class FoodfromCategory implements NetworkCallback<Food> {
        @Override
        public void onSuccessResult(List<Food> pojo) {
            _view.showFood(pojo);
        }

        @Override
        public void onFailureResult(String errorMsg) {
            _view.showErrMsg(errorMsg);
        }
    }

    private class FoodfromCountry implements NetworkCallback<Food> {
        @Override
        public void onSuccessResult(List<Food> pojo) {
            _view.showFoodFromCountry(pojo);
        }

        @Override
        public void onFailureResult(String errorMsg) {
            _view.showErrMsg(errorMsg);
        }
    }

    public HomePresenterImpl(HomeActivityInterface _view, FoodRepository _repo) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public void getRandomFood() {
        _repo.getRandomFood(new RandomMealCallback());
    }

    @Override
    public void getCategoryFood() {
        _repo.getMealCategories(new CategoryMealCallback());
    }

    @Override
    public void getFoodByCategory(String id) {
        _repo.getMealByCategory(id,new CategoryFoodCallback());
    }

    @Override
    public void getFoodById(String id) {
        _repo.getMealById(id,new FoodfromCategory());
    }

    @Override
    public void getCountries() {
        _repo.getCountries(new CountryCallBack());
    }

    @Override
    public void getFoodByCountry(String id) {
        _repo.getMealByCountry(id,new FoodfromCountry());
    }

//    @Override
//    public void getFoodById(String id) {
//        _repo.getMealById(id,new FoodfromCategory());
//    }

}
