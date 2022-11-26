package com.example.android.activiy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.android.R;
import com.example.android.firebase.MyFirebaseMessagingService;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {
    Button sttButton;
    Button ttsButton;
    Button emergencyButton;
    Button fcmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sttButton = (Button) findViewById(R.id.sttbutton);
        ttsButton = (Button) findViewById(R.id.ttsbutton);
        emergencyButton = (Button) findViewById(R.id.emergencybutton);
        fcmButton = (Button) findViewById(R.id.fcmbutton);

        sttButton.setOnClickListener((v) ->{
            Intent sttIntent = new Intent(getApplicationContext(), SttActivity.class);
            startActivity(sttIntent);
        });

        ttsButton.setOnClickListener((v) ->{
            Intent ttsIntent = new Intent(getApplicationContext(), TtsActivity.class);
            startActivity(ttsIntent);
        });

        emergencyButton.setOnClickListener((v) -> {
            Intent emergencyIntent = new Intent(getApplicationContext(), EmergencyActivity.class);
            startActivity(emergencyIntent);
        });

        fcmButton.setOnClickListener((v) -> {
            Intent fcm = new Intent(getApplicationContext(),  MyFirebaseMessagingService.class);
            startService(fcm);
        });
    }



}