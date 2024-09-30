package com.example.dishdash.calendar.view;

import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.FoodPlan;

public interface OnCalendarClickListener {

    void onLayoutClick(FoodPlan foodPlan);
    void onRemoveFromFavClick(FoodPlan foodPlan);
}
