package com.example.android.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.R;
import com.example.android.dto.ChatbotQuestionlDto;
import com.example.android.dto.ChatbotStartDto;
import com.example.android.retrofit.ChatbotRetrofit;
import com.example.android.retrofit.FlaskRestrofit;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlaskActivity extends AppCompatActivity {
    Button flask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flask);

        flask = (Button) findViewById(R.id.flask);

        flask.setOnClickListener((v) -> {
            FlaskRestrofit retrofit = FlaskRestrofit.getInstance();
            ChatbotRetrofit chatbotRetrofit = FlaskRestrofit.getChatbotRetrofit();

            ChatbotStartDto chatbotStartDto = new ChatbotStartDto("010-5017-6452");
            Gson gson = new Gson();
            String chatBotStartInfo = gson.toJson(chatbotStartDto);
            Log.e("JSON",chatBotStartInfo);

            Call<ChatbotQuestionlDto> call = chatbotRetrofit.startMealCommunication(chatbotStartDto);
            call.clone().enqueue(new Callback<ChatbotQuestionlDto>() {

                @Override
                public void onResponse(Call<ChatbotQuestionlDto> call, Response<ChatbotQuestionlDto> response) {
                    if (response.isSuccessful()){
                        Log.e("post", "성공");
                        ChatbotQuestionlDto result = response.body();
                        Log.e("answer", result.getAnswer());
                    }
                }

                @Override
                public void onFailure(Call<ChatbotQuestionlDto> call, Throwable t) {
                    Log.e("post", "실패");
                    t.printStackTrace();
                }
            });
        });
    }
}
