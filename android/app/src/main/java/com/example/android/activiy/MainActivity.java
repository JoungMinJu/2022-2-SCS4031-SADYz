package com.example.android.activiy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.android.R;


public class MainActivity extends AppCompatActivity {
    Button sttButton;
    Button ttsButton;
    Button emergencyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sttButton = (Button) findViewById(R.id.sttbutton);
        ttsButton = (Button) findViewById(R.id.ttsbutton);
        emergencyButton = (Button) findViewById(R.id.emergencybutton);

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
    }

}