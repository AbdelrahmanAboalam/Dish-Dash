package com.example.dishdash.searchfragment.presenter;

import com.example.dishdash.model.FoodRepository;
import com.example.dishdash.model.FoodRepositoryImpl;
import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Country;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.network.NetworkCallback;
import com.example.dishdash.searchfragment.view.SerView;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenterImpl implements SearchPresenter, NetworkCallback<Food> {

    private FoodRepository _repo;
    private SerView _view;
    private List<Category> allCategories = new ArrayList<>();


    private class CountryMealCallback implements NetworkCallback<Country> {
        @Override
        public void onSuccessResult(List<Country> pojo) {
            _view.showCountryData(pojo);
        }

        @Override
        public void onFailureResult(String errorMsg) {
            _view.showCategoryErrMsg(errorMsg);
        }
    }

    public void filterCategories(String query) {
        List<Category> filteredCategories = new ArrayList<>();
        for (Category category : allCategories) {
            if (category.getStrCategory().toLowerCase().contains(query.toLowerCase())) {
                filteredCategories.add(category);
            }
        }

        // Send the filtered list to the fragment for updating the adapter
        if (_view != null) {
            _view.updateCategoryList(filteredCategories);
        }
    }

    @Override
    public void getCountries() {
        _repo.getCountries(new CountryMealCallback());
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

    public SearchPresenterImpl( SerView _view, FoodRepository _repo) {
        this._repo = _repo;
        this._view = _view;
    }

    @Override
    public void getFoodByCategory(String id) {
        _repo.getMealByCategory(id,this);
    }

    @Override
    public void getFoodById(String id) {
        _repo.getMealById(id,this);
    }

    @Override
    public void getFoodName(String name) {
        _repo.getMealByName(name,this);
    }

    @Override
    public void getFoodByCountry(String country) {
        _repo.getMealByCountry(country,this);
    }

    @Override
    public void getFoodByIngredient(String ingredent) {
        _repo.getMealByIngredient(ingredent,this);
    }

    @Override
    public void getCategory() {
        _repo.getMealCategories(new CategoryMealCallback());
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
