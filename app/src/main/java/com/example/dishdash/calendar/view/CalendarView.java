package com.example.dishdash.calendar.view;

import com.example.dishdash.model.response.Food;
import com.example.dishdash.model.response.FoodPlan;

import java.util.List;

public interface CalendarView {

    public void showData(List<FoodPlan> foodPlans);
    public void showErrMsg(String error);
}
