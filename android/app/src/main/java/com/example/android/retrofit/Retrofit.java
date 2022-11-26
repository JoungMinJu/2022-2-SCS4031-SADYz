package com.example.android.retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {

    private static Retrofit instance = null;
    private static EmergencyRetrofit emergencyRetrofit;
    private static String baseUrl = "http://10.0.2.2:8080/";

    private Retrofit() {
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        emergencyRetrofit = retrofit.create(EmergencyRetrofit.class);
    }

    public static Retrofit getInstance() {
        if (instance == null) {
            instance = new Retrofit();
        }
        return instance;
    }

    public static EmergencyRetrofit getEmergencyRetrofit() {
        return emergencyRetrofit;
    }
}
