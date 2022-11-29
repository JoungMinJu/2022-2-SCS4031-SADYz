package com.example.android.activity;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Intent;
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
    ChatbotQuestionlDto result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flask);

        flask = (Button) findViewById(R.id.flask);

        flask.setOnClickListener((v) -> {
            FlaskRestrofit retrofit = FlaskRestrofit.getInstance();
            ChatbotRetrofit chatbotRetrofit = FlaskRestrofit.getChatbotRetrofit();

            ChatbotStartDto chatbotStartDto = new ChatbotStartDto("010-1212-1212");
            Gson gson = new Gson();
            String chatBotStartInfo = gson.toJson(chatbotStartDto);
            Log.e("JSON",chatBotStartInfo);

            Call<ChatbotQuestionlDto> call = chatbotRetrofit.startMealCommunication(chatbotStartDto);
            call.clone().enqueue(new Callback<ChatbotQuestionlDto>() {

                @Override
                public void onResponse(Call<ChatbotQuestionlDto> call, Response<ChatbotQuestionlDto> response) {
                    if (response.isSuccessful()){
                        Log.e("post", "성공");
                        result = response.body();
                        Intent intent = new Intent(getApplicationContext(), CaptureActivity.class);
                        intent.putExtra("answer", result.getAnswer());
                        intent.putExtra("dialog_type", result.getDialog_type());
                        intent.putExtra("conv_id", result.getConv_id());
                        startActivity(intent);
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
