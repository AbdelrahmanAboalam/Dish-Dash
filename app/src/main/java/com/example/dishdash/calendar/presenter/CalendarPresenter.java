package com.example.dishdash.calendar.presenter;

import androidx.lifecycle.LiveData;

import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.FoodPlan;

import java.util.List;

public interface CalendarPresenter {
    public LiveData<List<FoodPlan>> getPlanedFood(String date);
    public void removeFromFav(FoodPlan foodPlan);
}
