package com.example.a442dayone;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button returnBtn = findViewById(R.id.returnBtn);
        returnBtn.setOnClickListener(this);
    }
    public void onClick(View v) {
        finish();
    }
}