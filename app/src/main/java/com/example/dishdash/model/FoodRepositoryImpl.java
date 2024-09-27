package com.example.dishdash.model;

import androidx.lifecycle.LiveData;

import com.example.dishdash.db.FoodLoaclDataBase;
import com.example.dishdash.db.FoodLocalDataSourceImp;
import com.example.dishdash.model.response.Food;
import com.example.dishdash.network.FoodRemoteDataSource;
import com.example.dishdash.network.NetworkCallback;

import java.util.List;

public class FoodRepositoryImpl implements FoodRepository{

    FoodLoaclDataBase localDataSource;
    FoodRemoteDataSource remoteSource;
    private static FoodRepositoryImpl repo = null;

    private FoodRepositoryImpl (FoodRemoteDataSource remoteSource, FoodLoaclDataBase localDataSource)
    {
        this.remoteSource=remoteSource;
        this.localDataSource=localDataSource;
    }

    public static FoodRepositoryImpl getInstance(FoodRemoteDataSource remoteSource, FoodLoaclDataBase localDataSource){
        if (repo==null){
            repo =new FoodRepositoryImpl(remoteSource,localDataSource);
        }
        return repo;
    }
    @Override
    public void getRandomFood(NetworkCallback networkCallback) {
            remoteSource.makeNetworkCallRandomFood(networkCallback);
    }

    @Override
    public LiveData<List<Food>> getStoredFood() {
        return localDataSource.getStoredFood();
    }

    @Override
    public void insertFood(Food food) {
        localDataSource.insertFood(food);
    }

    @Override
    public void deleteFood(Food food) {
        localDataSource.removeFood(food);
    }

    @Override
    public void checkFoodExists(Food food) {
        localDataSource.checkProductExists(food);

    }


}
