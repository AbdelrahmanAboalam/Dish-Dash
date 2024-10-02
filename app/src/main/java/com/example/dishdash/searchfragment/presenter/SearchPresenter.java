package com.example.dishdash.searchfragment.presenter;

public interface SearchPresenter {

    public void getFoodByCategory(String id);
    public void getFoodById(String id);
    public void getFoodName(String name);
    public void getFoodByCountry(String country);
    public void getFoodByIngredient(String ingredent);
    public void getCategory();
    public void filterCategories(String query);

    public void getCountries();
}
