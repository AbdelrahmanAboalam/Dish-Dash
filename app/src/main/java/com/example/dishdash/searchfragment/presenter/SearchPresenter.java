package com.example.dishdash.searchfragment.presenter;

import com.example.dishdash.model.response.Category;
import com.example.dishdash.model.response.Country;
import com.example.dishdash.model.response.Ingred;

import java.util.List;

public interface SearchPresenter {

    public void getFoodByCategory(String id);
    public void getFoodById(String id);
    public void getFoodName(String name);
    public void getFoodByCountry(String country);
    public void getFoodByIngredient(String ingredent);
    public void getCategory();

    public void filterCategories(String query, List<Category> filteredCategories);
    public void filterIngredients(String query, List<Ingred> filteredIngredients);
    public void filterCountries(String query, List<Country> filteredCountries);

    public void getCountries();
    public void getIngredients();

}
