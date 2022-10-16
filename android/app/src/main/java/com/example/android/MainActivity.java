package com.example.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    Button sttButton;
    Button ttsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sttButton = (Button) findViewById(R.id.sttbutton);
        ttsButton = (Button) findViewById(R.id.ttsbutton);

        sttButton.setOnClickListener((v) ->{
            Intent sttIntent = new Intent(getApplicationContext(), SttActivity.class);
            startActivity(sttIntent);
        });

        ttsButton.setOnClickListener((v) ->{
            Intent ttsIntent = new Intent(getApplicationContext(), TtsActivity.class);
            startActivity(ttsIntent);
        });
    }

}