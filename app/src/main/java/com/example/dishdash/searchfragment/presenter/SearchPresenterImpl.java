package com.example.dishdash.searchfragment.presenter;

import android.widget.Toast;

import com.example.dishdash.model.FoodRepository;
import com.example.dishdash.model.FoodRepositoryImpl;
import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Country;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.Ingred;
import com.example.dishdash.network.NetworkCallback;
import com.example.dishdash.searchfragment.view.SerView;

import java.util.ArrayList;
import java.util.List;

public class SearchPresenterImpl implements SearchPresenter, NetworkCallback<Food> {

    private FoodRepository _repo;
    private SerView _view;
    private List<Category> allCategories = new ArrayList<>();
    private List<Ingred> allIngredients = new ArrayList<>();
    private List<Country> allCountries = new ArrayList<>();


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

    private class IngredientMealCallback implements NetworkCallback<Ingred> {
        @Override
        public void onSuccessResult(List<Ingred> pojo) {
            _view.showIngredientsData(pojo);
        }

        @Override
        public void onFailureResult(String errorMsg) {
            _view.showCategoryErrMsg(errorMsg);
        }
    }

    public void filterCategories(String query,List<Category> filteredCategories) {
        allCategories.clear();
        if (query == null || query.trim().isEmpty()) {
            allCategories.addAll(filteredCategories);
        } else {
            for (Category category : filteredCategories) {
                if (category.getStrCategory().toLowerCase().contains(query.toLowerCase())) {
                    allCategories.add(category);
                }
            }
        }
        _view.updateCategoryList(allCategories);
    }

    @Override
    public void filterIngredients(String query, List<Ingred> filteredIngredients) {
        allIngredients.clear();
        if (query == null || query.trim().isEmpty()) {
            allIngredients.addAll(filteredIngredients);
        } else {
            for (Ingred ingred : filteredIngredients) {
                if (ingred.getStrIngredient().toLowerCase().contains(query.toLowerCase())) {
                    allIngredients.add(ingred);
                }
            }
        }
        _view.updateIngredientList(allIngredients);
    }

    @Override
    public void filterCountries(String query, List<Country> filteredCountries) {
        allCountries.clear();
        if (query == null || query.trim().isEmpty()) {
            allCountries.addAll(filteredCountries);
        }
        else {
            for (Country country : filteredCountries) {
                if (country.getArea().toLowerCase().contains(query.toLowerCase())) {
                    allCountries.add(country);
                   }
                }
            }
        _view.updateCountryList(allCountries);
    }


    @Override
    public void getCountries() {
        _repo.getCountries(new CountryMealCallback());
    }

    @Override
    public void getIngredients() {
        _repo.getIngredients(new IngredientMealCallback());
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
