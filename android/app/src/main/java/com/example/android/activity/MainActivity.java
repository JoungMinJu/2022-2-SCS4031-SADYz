package com.example.android.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.android.R;
import com.example.android.firebase.MyFirebaseMessagingService;
import com.example.android.receiver.AlramReceiver;

import java.util.Calendar;


public class MainActivity extends AppCompatActivity {
    Button sttButton;
    Button ttsButton;
    Button emergencyButton;
    Button fcmButton;
    Button flaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sttButton = (Button) findViewById(R.id.sttbutton);
        ttsButton = (Button) findViewById(R.id.ttsbutton);
        emergencyButton = (Button) findViewById(R.id.emergencybutton);
        fcmButton = (Button) findViewById(R.id.fcmbutton);
        flaskButton = (Button) findViewById(R.id.flaskbutton);

        sttButton.setOnClickListener((v) -> {
            Intent sttIntent = new Intent(getApplicationContext(), SttActivity.class);
            startActivity(sttIntent);
        });

        ttsButton.setOnClickListener((v) -> {
            Intent ttsIntent = new Intent(getApplicationContext(), TtsActivity.class);
            startActivity(ttsIntent);
        });

        emergencyButton.setOnClickListener((v) -> {
            Intent emergencyIntent = new Intent(getApplicationContext(), EmergencyActivity.class);
            startActivity(emergencyIntent);
        });

        fcmButton.setOnClickListener((v) -> {
            Intent fcm = new Intent(getApplicationContext(), MyFirebaseMessagingService.class);
            startService(fcm);
        });

        flaskButton.setOnClickListener((v) ->{
            Intent flask = new Intent(getApplicationContext(), FlaskActivity.class);
            startActivity(flask);
        });
        //알림 만들기
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            Intent intent = new Intent(this, AlramReceiver.class);
            PendingIntent alarmIntent;
            // 버전체크
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
            } else {
                alarmIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            }

                // 12시로 시간 설정
                Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, 20);
            calendar.set(Calendar.MINUTE, 00);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, alarmIntent);
        }
    }


}