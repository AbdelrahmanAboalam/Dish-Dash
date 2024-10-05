package com.example.dishdash.calendar.presenter;

import androidx.lifecycle.LiveData;

import com.example.dishdash.calendar.view.CalendarView;
import com.example.dishdash.model.FoodRepository;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.FoodPlan;

import java.util.List;

public class CalendarPresenterImpl implements CalendarPresenter{

    private CalendarView _view;
    private FoodRepository _repo;

    public CalendarPresenterImpl(CalendarView _view, FoodRepository _repo ) {
        this._view = _view;
        this._repo = _repo;
    }

    @Override
    public LiveData<List<FoodPlan>> getPlanedFood(String date) {
        return _repo.getPlannedFood(date);
    }

    @Override
    public void removeFromPlan(FoodPlan foodPlan) {
        _repo.deleteFoodPlan(foodPlan);
    }

    @Override
    public void removeFromFav(Food food) {
        _repo.deleteFood(food);
    }

    @Override
    public void addtoFav(Food food) {
        _repo.insertFood(food);
    }

    @Override
    public void updateFoodById(String mealId, boolean isFav) {
        _repo.updateFoodbyId(mealId,isFav);
    }

    @Override
    public void updateFoodPlanById(String mealId, boolean isFav) {
        _repo.updateFoodPlanbyId(mealId,isFav);
    }

    @Override
    public void isExist(FoodPlan foodPlan) {

    }
}
