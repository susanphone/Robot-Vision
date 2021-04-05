package com.example.FacialRecognitionAssignment;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.face.Face;
import com.google.mlkit.vision.face.FaceDetection;
import com.google.mlkit.vision.face.FaceDetector;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewManager;
import android.widget.Button;
import android.widget.TextView;

import java.io.InputStream;
import java.util.List;

public class FacialRecognition extends AppCompatActivity implements View.OnClickListener {
    TextView tv;
    List<Face> fac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button practice = findViewById(R.id.talkButton);
        practice.setOnClickListener(this);
        tv = findViewById(R.id.textView);
        InputStream stream = getResources().openRawResource(R.raw.image02);
        Bitmap bitmap = BitmapFactory.decodeStream(stream);
        InputImage image = InputImage.fromBitmap(bitmap, 0);

        FaceDetector detector = FaceDetection.getClient();

        Task<List<Face>> result =
                detector.process(image).addOnSuccessListener(

                        new OnSuccessListener<List<Face>>() {
                            @Override
                            public void onSuccess(List<Face> faces) {
                                fac = faces;
                                if (fac == null)
                                    Log.v("***DRAW***", "Null");
                                FaceView overlay = (FaceView) findViewById(R.id.faceView);
                                overlay.setContent(bitmap, fac);
                                //Success
                                tv.setText(faces.size() + "Faces Seen");
                            }

                        });
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.returnButton:
                finish();
        }}

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


//    @Override
//    public void analyze(ImageProxy imageProxy);
}