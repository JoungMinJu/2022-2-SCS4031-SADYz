package com.example.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.R;

import java.util.Locale;

public class TtsActivity extends AppCompatActivity {
    TextToSpeech tts;
    EditText editText;
    Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tts);
        editText = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = tts.setLanguage(Locale.KOREA);

                    if (result == TextToSpeech.LANG_NOT_SUPPORTED || result == TextToSpeech.LANG_MISSING_DATA) {
                        Log.e("TTS", "Language not supported.");
                    } else {
                        button.setText("Ready To Speak");
                    }
                } else {
                    Log.e("TTS", "Initialization failed.");
                }
                Intent ttsIntent = getIntent();
                if(ttsIntent != null) {
                    editText.setText(ttsIntent.getStringExtra("answer"));
                }
                CharSequence text = editText.getText();
                tts.setPitch((float)1.0);
                tts.setSpeechRate((float)1.0);
                tts.speak(text, TextToSpeech.QUEUE_FLUSH,null,"uid");
            }
        });
    }
}
