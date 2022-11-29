package com.example.android.retrofit;

import com.example.android.dto.ChatBotAnswerDto;
import com.example.android.dto.ChatbotQuestionlDto;
import com.example.android.dto.ChatbotStartDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ChatbotRetrofit {

    @POST("/meal/start")
    Call<ChatbotQuestionlDto> startMealCommunication(@Body ChatbotStartDto chatbotStartDto);

    @POST("/meal/chat")
    Call<ChatbotQuestionlDto> mealCommunication(@Body ChatBotAnswerDto chatBotAnswerDto);

    @POST("/bathroom/start")
    Call<ChatbotQuestionlDto> startBathroomCommunication(@Body ChatbotStartDto chatbotStartDto);

    @POST("/bathroom/chat")
    Call<ChatbotQuestionlDto> bathroomCommunication(@Body ChatBotAnswerDto chatBotAnswerDto);

    @POST("/emotion/start")
    Call<ChatbotQuestionlDto> startEmotionCommunication(@Body ChatbotStartDto chatbotStartDto);

    @POST("/emotion/chat")
    Call<ChatbotQuestionlDto> emotionCommunication(@Body ChatBotAnswerDto chatBotAnswerDto);
}
