package com.example.dishdash.model;

import com.example.dishdash.network.FoodRemoteDataSource;
import com.example.dishdash.network.NetworkCallback;

public class FoodRepositoryImpl implements FoodRepository{


    FoodRemoteDataSource remoteSource;
    private static FoodRepositoryImpl repo = null;
    private FoodRepositoryImpl (FoodRemoteDataSource remoteSource)
    {
        this.remoteSource=remoteSource;
    }

    public static FoodRepositoryImpl getInstance(FoodRemoteDataSource remoteSource){
        if (repo==null){
            repo =new FoodRepositoryImpl(remoteSource);
        }
        return repo;
    }
    @Override
    public void getRandomFood(NetworkCallback networkCallback) {
            remoteSource.makeNetworkCallRandomFood(networkCallback);
    }
}
