package com.example.a442dayone;

import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class TalkActivity extends AppCompatActivity implements View.OnClickListener {
    private TTS tts;
    String msg;
//    EditText et;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        Button returnButton = findViewById(R.id.returnButton);
        returnButton.setOnClickListener(this);

        Button talkButton = findViewById(R.id.talkBtn);
        talkButton.setOnClickListener(this);

//        et = findViewById(R.id.talkText);
//        et.setText("Hello");
        tts =  TTS.getInstance(this);
        tts.start();

    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.talkBtn:
                Bundle b = new Bundle();
                String text = "Hello"; //= et.getText().toString();
                b.putString("LM", text);
                if (tts.handler != null) {
                    Message msg = tts.handler.obtainMessage(0);
                    msg.setData(b);
                    tts.handler.sendMessage(msg);
                }
                break;
            case R.id.returnButton:
                finish();
        }
    }
}