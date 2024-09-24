package com.example.dishdash.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RandomFoodResponse {
    @SerializedName("meals")
    private List<Food> food;

    public RandomFoodResponse(List<Food> food){
        this.food =  food;
    }

    public List<Food> getFood() {

        return food;
    }
    public void setFood(List<Food> food) {
        this.food = food;
    }
}
