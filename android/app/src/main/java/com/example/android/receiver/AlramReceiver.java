package com.example.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.android.dto.ChatBotAnswerDto;
import com.example.android.dto.ChatbotStartDto;
import com.example.android.dto.EmergencyDto;
import com.example.android.retrofit.ChatbotRetrofit;
import com.example.android.retrofit.EmergencyRetrofit;
import com.example.android.retrofit.FlaskRestrofit;
import com.example.android.retrofit.SpringRetrofit;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlramReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        FlaskRestrofit retrofit = FlaskRestrofit.getInstance();
        ChatbotRetrofit chatbotRetrofit = FlaskRestrofit.getChatbotRetrofit();

        ChatbotStartDto chatbotStartDto = new ChatbotStartDto("010-5017-6452");
        Gson gson = new Gson();
        String chatbotStartInfo = gson.toJson(chatbotStartDto);
        Log.e("JSON",chatbotStartInfo);

        Call<ChatBotAnswerDto> call = chatbotRetrofit.startCommunication(chatbotStartDto);
        call.clone().enqueue(new Callback<ChatBotAnswerDto>() {

            @Override
            public void onResponse(Call<ChatBotAnswerDto> call, Response<ChatBotAnswerDto> response) {
                if (response.isSuccessful()){
                    Log.e("put", "성공");
                    ChatBotAnswerDto result = response.body();
                    Log.e("answer", result.getAnswer());
                }
            }

            @Override
            public void onFailure(Call<ChatBotAnswerDto> call, Throwable t) {
                Log.e("put", "실패");
            }
        });
    }
}
