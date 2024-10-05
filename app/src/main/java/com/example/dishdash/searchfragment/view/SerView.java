package com.example.dishdash.searchfragment.view;

import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Country;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.Ingred;

import java.util.List;

public interface SerView {

    public void showData(List<Food> food);
    public void showErrMsg(String error);

    public void showIngredientsData(List<Ingred> meals);
    public void showCountryData(List<Country> meals);
    public void showCategoryData(List<Category> meals);
    public void showCategoryErrMsg(String error);

    public void updateCountryList(List<Country> filteredCountries);
    public void updateCategoryList(List<Category> filteredCategories);
    public void updateIngredientList(List<Ingred> filteredCategories);
}
