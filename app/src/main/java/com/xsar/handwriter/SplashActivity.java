package com.xsar.handwriter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class SplashActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 200;
    private static int SPLASH_SCREEN =1000;

    ImageView imageView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (ContextCompat.checkSelfPermission(SplashActivity.this,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SplashActivity.this,
                    new String[]{
                            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                    CAMERA_REQUEST_CODE);
        } else {
            splash();
        }

        imageView = findViewById(R.id.xsar_icon);
        imageView = findViewById(R.id.from_icon);
        imageView = findViewById(R.id.logo_pic);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        switch (requestCode) {

            case CAMERA_REQUEST_CODE: {

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
                    splash();
                } else {
                    Toast.makeText(this, "Permission DENIED..!", Toast.LENGTH_SHORT).show();
                    splash();
                }
                break;
            }
        }

    }

    private void splash(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent (SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                finish();

                File path = new File((Environment.getExternalStorageDirectory().getPath()));
                File dir = new File(path + "/HandWriter/" + "/Text Files/");
                if(!dir.exists()) {
                    dir.mkdirs();
                }

                File path1 = Environment.getExternalStorageDirectory();
                File dir1 = new File(path1 + "/HandWriter/" + "/Pdf Files/");
                if (!dir1.exists()){
                    dir1.mkdirs();
                }

            }
        },SPLASH_SCREEN);
    }
}

