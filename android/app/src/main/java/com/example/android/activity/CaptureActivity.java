package com.example.android.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.R;

public class CaptureActivity extends AppCompatActivity {
    private OutingActivity outingActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture);

        //다이어로그 밖의 화면은 흐리게 만들어준다
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        layoutParams.dimAmount = 0.8f;
        getWindow().setAttributes(layoutParams);

        Intent outIntent = getIntent();
        String out = outIntent.getStringExtra("외출 여부 파악");
        if (out != null) {
            outingActivity = new OutingActivity(this);
            outingActivity.setCancelable(false);
            outingActivity.show();
        }
    }
}
