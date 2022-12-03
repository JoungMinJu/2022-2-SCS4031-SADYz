package com.example.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.android.dto.ChatbotQuestionlDto;
import com.example.android.dto.ChatbotStartDto;
import com.example.android.retrofit.ChatbotRetrofit;
import com.example.android.retrofit.FlaskRestrofit;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlramReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        FlaskRestrofit retrofit = FlaskRestrofit.getInstance();
        ChatbotRetrofit chatbotRetrofit = FlaskRestrofit.getChatbotRetrofit();

        Long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("hh");
        String getHour = dateFormat.format(date);

        if (getHour.equals("12") || getHour.equals("18")) {
            ChatbotStartDto chatbotStartDto = new ChatbotStartDto("010-5017-6452");
            Gson gson = new Gson();
            String chatbotStartInfo = gson.toJson(chatbotStartDto);
            Log.e("JSON", chatbotStartInfo);

            Call<ChatbotQuestionlDto> call = chatbotRetrofit.startMealCommunication(chatbotStartDto);
            call.clone().enqueue(new Callback<ChatbotQuestionlDto>() {

                @Override
                public void onResponse(Call<ChatbotQuestionlDto> call, Response<ChatbotQuestionlDto> response) {
                    if (response.isSuccessful()) {
                        Log.e("put", "성공");
                        ChatbotQuestionlDto result = response.body();
                        Log.e("answer", result.getAnswer());
                    }
                }

                @Override
                public void onFailure(Call<ChatbotQuestionlDto> call, Throwable t) {
                    Log.e("put", "실패");
                }
            });
        }

        if (getHour.equals("20")) {
            ChatbotStartDto chatbotStartDto = new ChatbotStartDto("010-5017-6452");
            Gson gson = new Gson();
            String chatbotStartInfo = gson.toJson(chatbotStartDto);
            Log.e("JSON",chatbotStartInfo);

            Call<ChatbotQuestionlDto> call = chatbotRetrofit.startBathroomCommunication(chatbotStartDto);
            call.clone().enqueue(new Callback<ChatbotQuestionlDto>() {

                @Override
                public void onResponse(Call<ChatbotQuestionlDto> call, Response<ChatbotQuestionlDto> response) {
                    if (response.isSuccessful()){
                        Log.e("put", "성공");
                        ChatbotQuestionlDto result = response.body();
                        Log.e("answer", result.getAnswer());
                    }
                }

                @Override
                public void onFailure(Call<ChatbotQuestionlDto> call, Throwable t) {
                    Log.e("put", "실패");
                }
            });
        }

        if(getHour.equals("10") || getHour.equals("15") || getHour.equals("21")) {
            ChatbotStartDto chatbotStartDto = new ChatbotStartDto("010-5017-6452");
            Gson gson = new Gson();
            String chatbotStartInfo = gson.toJson(chatbotStartDto);
            Log.e("JSON",chatbotStartInfo);

            Call<ChatbotQuestionlDto> call = chatbotRetrofit.startEmotionCommunication(chatbotStartDto);
            call.clone().enqueue(new Callback<ChatbotQuestionlDto>() {

                @Override
                public void onResponse(Call<ChatbotQuestionlDto> call, Response<ChatbotQuestionlDto> response) {
                    if (response.isSuccessful()){
                        Log.e("put", "성공");
                        ChatbotQuestionlDto result = response.body();
                        Log.e("answer", result.getAnswer());
                    }
                }

                @Override
                public void onFailure(Call<ChatbotQuestionlDto> call, Throwable t) {
                    Log.e("put", "실패");
                }
            });
        }
    }
}
