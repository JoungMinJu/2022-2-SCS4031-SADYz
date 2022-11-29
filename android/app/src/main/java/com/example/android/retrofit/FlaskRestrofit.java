package com.example.android.retrofit;

import retrofit2.converter.gson.GsonConverterFactory;

public class FlaskRestrofit {

    private static FlaskRestrofit instance = null;
    private static ChatbotRetrofit chatbotRetrofit;
    private static String baseUrl = "http://10.70.23.228:5000";

    private FlaskRestrofit() {

        retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        chatbotRetrofit = retrofit.create(ChatbotRetrofit.class);
    }

    public static FlaskRestrofit getInstance() {
        if (instance == null) {
            instance = new FlaskRestrofit();
        }
        return instance;
    }

    public static ChatbotRetrofit getChatbotRetrofit() {
        return chatbotRetrofit;
    }

}
