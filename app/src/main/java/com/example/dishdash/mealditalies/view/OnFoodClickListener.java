package com.example.dishdash.mealditalies.view;

import com.example.dishdash.model.response.Food;

public interface OnFoodClickListener {
    void onLayoutClick(Food food);
    void onAddToFavClick(Food food);
}
