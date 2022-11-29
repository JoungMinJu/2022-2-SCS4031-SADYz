package com.example.android.retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class SpringRetrofit {

    private static SpringRetrofit instance = null;
    private static EmergencyRetrofit emergencyRetrofit;
    private static String baseUrl = "http://172.30.1.35:8080/";

    private SpringRetrofit() {
        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        emergencyRetrofit = retrofit.create(EmergencyRetrofit.class);
    }

    public static SpringRetrofit getInstance() {
        if (instance == null) {
            instance = new SpringRetrofit();
        }
        return instance;
    }

    public static EmergencyRetrofit getEmergencyRetrofit() {
        return emergencyRetrofit;
    }
}
