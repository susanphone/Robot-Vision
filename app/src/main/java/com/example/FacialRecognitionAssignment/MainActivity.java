package com.example.FacialRecognitionAssignment;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Message;
import android.util.Log;
import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;

import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TTS tts;
    String msg;
    private TextView tv;
    private List<Face> fac;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button practice = findViewById(R.id.talkButton);
        practice.setOnClickListener(this);
        tv = findViewById(R.id.textView);
        FaceDetector detector = FaceDetection.getClient();
        InputStream stream = getResources().openRawResource(R.raw.image02);
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        InputImage image = InputImage.fromBitmap(bitmap, 0);

        Task<List<Face>> result =
                detector.process(image).addOnSuccessListener(

                        new OnSuccessListener<List<Face>>() {
                            @Override
                            public void onSuccess(List<Face> faces) {
                                fac = faces;
                                if (fac == null)
                                    Log.v("***DRAW***", "Null");
                            else {FaceView overlay = (FaceView) findViewById(R.id.faceView);
                                overlay.setContent(bitmap, fac);}
                                //Success
                                tv.setText(faces.size() + "Faces Seen");
//                                startSecondActivity();
                            }
                        }).addOnFailureListener(new OnFailureListener(){
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }


                     });
                        }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.talkButton:
                FaceDetector detector = FaceDetection.getClient();
                InputStream stream = getResources().openRawResource(R.raw.image01);
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                InputImage image = InputImage.fromBitmap(bitmap, 0);

                Task<List<Face>> result =
                        detector.process(image).addOnSuccessListener(

                                new OnSuccessListener<List<Face>>(){
                                    @Override
                                    public void onSuccess(List<Face> faces){
                                        fac = faces;
                                        if(fac == null)
                                            Log.v("***DRAW***", "Null");
                                        FaceView overlay = (FaceView) findViewById(R.id.faceView);
                                        overlay.setContent(bitmap, fac);
                                        //Success
                                        tv.setText(faces.size() + "Faces Seen");
                                        startSecondActivity();
                                    }
                                }
                        );
                break;
        }
    }

    public void startSecondActivity(){
        TTS talk = TTS.getInstance(this);
        talk.start();

        Bundle b = new Bundle();
        b.putString("LM", tv.toString());
        if (tts.handler != null){
            Message msg = tts.handler.obtainMessage(0);
            msg.setData(b);
            tts.handler.sendMessage(msg);
        }
    }
    ;



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