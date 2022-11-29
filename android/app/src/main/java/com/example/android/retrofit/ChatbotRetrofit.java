package com.example.android.retrofit;

import com.example.android.dto.ChatBotAnswerDto;
import com.example.android.dto.ChatbotStartDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatbotRetrofit {

    @POST("/meal/start")
    Call<ChatBotAnswerDto> startCommunication(@Body ChatbotStartDto chatbotStartDto);
}
