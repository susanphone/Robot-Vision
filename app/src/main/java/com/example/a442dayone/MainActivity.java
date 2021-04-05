package com.example.a442dayone;


import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button toastButton = findViewById(R.id.toastButton);
        toastButton.setOnClickListener(this);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.talkButton:
//                Toast.makeText(MainActivity.this, "Start talking", Toast.LENGTH_LONG).show();
                startTalkActivity();
                break;
            case R.id.toastButton:
                Toast.makeText(MainActivity.this, "Hello CSCI 442", Toast.LENGTH_LONG).show();
                break;
            case R.id.activityButton:
                Toast.makeText(MainActivity.this, "start new activity", Toast.LENGTH_LONG).show();
                startSecondActivity();
                break;

        }
    }
    ;

    public void startSecondActivity() {
        Intent secondActivity = new Intent(this, SecondActivity.class);
        startActivity(secondActivity);
    }

    public void startTalkActivity() {
        Intent talkActivity = new Intent(this, TalkActivity.class);
        startActivity(talkActivity);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}