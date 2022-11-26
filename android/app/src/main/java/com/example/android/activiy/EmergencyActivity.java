package com.example.android.activiy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.R;
import com.example.android.dto.EmergencyDto;
import com.example.android.retrofit.EmergencyRetrofit;
import com.example.android.retrofit.Retrofit;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EmergencyActivity extends AppCompatActivity {
    Button emergecyButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);

        emergecyButton = (Button) findViewById(R.id.emergency);

        Retrofit retrofit = Retrofit.getInstance();
        EmergencyRetrofit emergencyRetrofit = Retrofit.getEmergencyRetrofit();

        emergecyButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                EmergencyDto emergencyDto = new EmergencyDto(true);
                Gson gson = new Gson();
                String emergencyInfo = gson.toJson(emergencyDto);
                Log.e("JSON", emergencyInfo);

                Call<EmergencyDto> call = emergencyRetrofit.putEmergency("010-5017-6452", emergencyDto);
                call.clone().enqueue(new Callback<EmergencyDto>() {

                    @Override
                    public void onResponse(Call<EmergencyDto> call, Response<EmergencyDto> response) {
                        if (response.isSuccessful()){
                            Log.e("put", "성공");
                        }
                    }

                    @Override
                    public void onFailure(Call<EmergencyDto> call, Throwable t) {
                            Log.e("put", "실패");
                    }
                });
            }
        });
    }
}
