package com.example.android.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.R;

public class OutingActivity extends Dialog {

    Button outSide;
    Button inSide;

//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_outing);
//    }
    public OutingActivity(@NonNull Context context) {
        super(context);
        setContentView(R.layout.activity_outing);

        outSide = (Button) findViewById(R.id.outSide);
        inSide = (Button) findViewById(R.id.inside);

        outSide.setOnClickListener((v) -> {
            this.dismiss();
        });

        inSide.setOnClickListener((v) -> {
            this.dismiss();
        });
    }
}
