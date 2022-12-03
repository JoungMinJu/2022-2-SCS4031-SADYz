package com.example.android.firebase;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.android.activity.MainActivity;
import com.example.android.activity.OutingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private OutingActivity outingActivity;

    public MyFirebaseMessagingService() {
        super();
        Task<String> token = FirebaseMessaging.getInstance().getToken();
        token.addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if(task.isSuccessful()){
                    Log.d("FCM Token", task.getResult());
                }
            }
        });
    }

    @Override
    public void onNewToken(@NonNull String token) {
        Log.d("FCM Log", "Refreshed token : " + token);
        super.onNewToken(token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        Intent outingIntent = new Intent(this, MainActivity.class);
        outingIntent.putExtra("외출 여부 파악","요청");
        startActivity(outingIntent.addFlags(FLAG_ACTIVITY_NEW_TASK));
    }

}
