package com.example.dishdash.mealditalies.view;

import com.example.dishdash.model.response.Food;

public interface OnFoodClickListener {
    void onLayoutClick(Food food);
    void onAddToFavClick(Food food);
    void onRemoveFavClick(Food food);
    void onCheckClick(Food food);
    void updateFoodPlanbyId(String mealId, boolean isFav);

    void  onDateSelected(String date);

}
