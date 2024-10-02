package com.example.dishdash.calendar.presenter;

import androidx.lifecycle.LiveData;

import com.example.dishdash.calendar.view.CalendarView;
import com.example.dishdash.model.FoodRepository;
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
    public void removeFromFav(FoodPlan foodPlan) {
        _repo.deleteFoodPlan(foodPlan);
    }
}
