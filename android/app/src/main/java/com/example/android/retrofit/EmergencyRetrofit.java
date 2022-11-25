package com.example.android.retrofit;

import com.example.android.dto.EmergencyDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EmergencyRetrofit {
    @PUT("api/dashboard/emergency/{phoneNumber}")
    Call<EmergencyDto> putEmergency(@Path("phoneNumber") String phoneNumber, @Body EmergencyDto emergencyDto);
}
