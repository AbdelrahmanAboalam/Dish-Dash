package com.example.dishdash.searchfragment.view;

import com.example.dishdash.model.response.Food;

public interface OnSearchClickListener {

    public void onMealClick(Food food);
    public void onCategoryClick(String id);
    public void onCountryCkick(String id);
    public void onIngredientClick(String id);
}
