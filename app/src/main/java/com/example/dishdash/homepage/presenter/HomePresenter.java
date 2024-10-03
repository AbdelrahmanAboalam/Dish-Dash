package com.example.dishdash.homepage.presenter;

public interface HomePresenter {
    public void getRandomFood();
    public void getCategoryFood();
    public void getFoodByCategory(String id);
    public void getFoodById(String id);
    public void getCountries();
    public void getFoodByCountry(String id);

}
