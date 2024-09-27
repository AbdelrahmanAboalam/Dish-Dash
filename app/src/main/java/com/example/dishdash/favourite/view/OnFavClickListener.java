package com.example.dishdash.favourite.view;

import com.example.dishdash.model.response.Food;

public interface OnFavClickListener {

    void onLayoutClick(Food food);
    void onRemoveFromFavClick(Food food);
}
