package com.example.randomprofile.data.service;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class RetrofitBuilder {

    private static RetrofitBuilder instance;

    private RetrofitBuilder(){

    }

    public static RetrofitBuilder getInstance(){
        if(instance == null)
            instance = new RetrofitBuilder();

        return instance;
    }

    public Retrofit getRetrofit(){

        return new Retrofit.Builder()
                .baseUrl("https://randomuser.me/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }



}
