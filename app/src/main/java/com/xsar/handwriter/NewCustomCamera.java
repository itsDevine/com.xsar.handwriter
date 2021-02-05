package com.xsar.handwriter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewCustomCamera extends AppCompatActivity {

    Camera camera;
    Button captureButton,galleryButton,imagesCaptured,imagesNo,flashButton,okButton;
    ImageButton imagesCaptured2;
    FrameLayout customFramelayout;
    ShowCamera showCamera;
    public File file;
    Camera.Parameters params;
    int picstaken=0, fromActivity, flashmode = 1, imagesCapturedno = 0;
    boolean firrstImageCaptured = false ,captureFinished = true;
    ArrayList<String> fileUris;
    Animation flash1anim, flash2anim, captureanim;
    ImageView flashImage1,flashImage2,flashImage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_custom_camera);

        customFramelayout = (FrameLayout) findViewById(R.id.customCamFrame);
        captureButton = (Button) findViewById(R.id.customCamCapture2);
        galleryButton = (Button) findViewById(R.id.customgallery2);
        imagesCaptured = (Button) findViewById(R.id.customimages21);
        imagesNo = (Button) findViewById(R.id.customimagesno2);
        imagesCaptured2 = (ImageButton) findViewById(R.id.customimages22);
        flashButton = (Button) findViewById(R.id.customflash2);
        okButton = (Button) findViewById(R.id.customnext2);

        flash1anim = AnimationUtils.loadAnimation(this,R.anim.flashanimation);
        flash2anim = AnimationUtils.loadAnimation(this,R.anim.flashanimation2);
        captureanim = AnimationUtils.loadAnimation(this,R.anim.captureanimation);
        flashImage1 = (ImageView) findViewById(R.id.customflashImage1);
        flashImage2 = (ImageView) findViewById(R.id.customflashImage2);
        flashImage3 = (ImageView) findViewById(R.id.customflashImage3);

        fileUris = new ArrayList<String>();


        fromActivity = getIntent().getExtras().getInt("fromActivity");
        if (fromActivity == 2) {
            picstaken = getIntent().getExtras().getInt("picstaken");
        } else if (fromActivity == 3){
            galleryButton.setVisibility(View.INVISIBLE);
            //timer = 4000;
        }


        camera = Camera.open();
        params = camera.getParameters();
        params.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        camera.setParameters(params);

        showCamera = new ShowCamera(this,camera,customFramelayout);
        customFramelayout.addView(showCamera);

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(captureFinished) {
                    captureFinished = false;
                    captureButton.startAnimation(captureanim);
                    CaptureImage(view);
                }
            }
        });

        galleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(NewCustomCamera.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(NewCustomCamera.this,
                            new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            200);
                } else {
                    if (fromActivity == 1) {
                        Intent galleryintent = new Intent(NewCustomCamera.this, image_editor.class);
                        galleryintent.putExtra("fromGallery", true);
                        startActivity(galleryintent);
                        finish();
                    } else {
                        Intent galleryintent = new Intent();
                        galleryintent.putExtra("fromGallery", true);
                        setResult(RESULT_OK, galleryintent);
                        finish();
                    }
                }

            }
        });

        flashButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if(firrstImageCaptured){
                    galleryButton.setAnimation(null);
                    okButton.setAnimation(null);
                    imagesCaptured.setAnimation(null);
                    imagesCaptured2.setAnimation(null);
                    imagesNo.setAnimation(null);
                    galleryButton.setVisibility(View.INVISIBLE);
                }

                if(flashmode == 1){
                    flashmode = 2;
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                    flashImage2.setVisibility(View.VISIBLE);
                    flashImage1.startAnimation(flash1anim);
                    flashImage2.startAnimation(flash2anim);
                    flashImage3.setAnimation(null);
                    camera.setParameters(params);
                } else if (flashmode == 2){
                    flashmode = 3;
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
                    flashImage3.setVisibility(View.VISIBLE);
                    flashImage2.startAnimation(flash1anim);
                    flashImage3.startAnimation(flash2anim);
                    flashImage2.setVisibility(View.INVISIBLE);
                    flashImage1.setAnimation(null);
                    camera.setParameters(params);
                } else if(flashmode == 3){
                    flashmode = 1;
                    params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    flashImage1.setVisibility(View.VISIBLE);
                    flashImage3.startAnimation(flash1anim);
                    flashImage1.startAnimation(flash2anim);
                    flashImage3.setVisibility(View.INVISIBLE);
                    flashImage2.setAnimation(null);
                    camera.setParameters(params);
                }

            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okPressed();
            }
        });

        imagesCaptured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                okPressed();
            }
        });

        customFramelayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    //focusOnTouch(motionEvent);
                }
                return true;
            }
        });


    }

    private void focusOnTouch(MotionEvent motionEvent) {
        if(camera!=null){
            Camera.Parameters focusParams = camera.getParameters();
            if(focusParams.getMaxNumMeteringAreas()>0){

                int left = (int) motionEvent.getX() - 50;
                int top = (int) motionEvent.getY() - 50;
                int right = (int) motionEvent.getX() + 50;
                int bottom = (int) motionEvent.getY() + 50;
                Rect rect = new Rect (left,top,right,bottom);

                focusParams.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
                ArrayList<Camera.Area> meteringAresa = new ArrayList<Camera.Area>();
                meteringAresa.add(new Camera.Area(rect,800));
                focusParams.setFocusAreas(meteringAresa);

                camera.setParameters(focusParams);
               // camera.autoFocus(mAutoFocusTakePictureCallback);
            } else {
                camera.autoFocus(mAutoFocusTakePictureCallback);
            }
        }
    }

    private Camera.AutoFocusCallback mAutoFocusTakePictureCallback = new Camera.AutoFocusCallback() {
        @Override
        public void onAutoFocus(boolean b, Camera camera) {

        }
    };

    public class ShowCamera extends SurfaceView implements SurfaceHolder.Callback{

        Camera camera;
        SurfaceHolder holder;
        FrameLayout frameLaout;
        List<Camera.Size> supportedSizes;
        Camera.Parameters params;

        public ShowCamera(Context context, Camera camera, FrameLayout frameLayout) {
            super(context);
            this.camera = camera;
            this.frameLaout = frameLayout;
            holder = getHolder();
            holder.addCallback(this);
        }

        @Override
        public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {

            Camera.Parameters params = camera.getParameters();
            supportedSizes = camera.getParameters().getSupportedPreviewSizes();

            if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){
                params.set("orientation","portrait");
                camera.setDisplayOrientation(90);
                params.setRotation(90);
            } else {
                params.set("orientation","landscape");
                camera.setDisplayOrientation(0);
                params.setRotation(0);
            }

            camera.setParameters(params);

            try {
                camera.setPreviewDisplay(holder);
                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

        }

        @Override
        public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
            camera.stopPreview();
            camera.release();
        }
    }

    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] bytes, Camera camera) {


            try {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "Handwriter_" + timeStamp;
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                //uri =  storageDir + imageFileName + ".jpg";
                file = File.createTempFile(imageFileName, ".jpg", storageDir);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(bytes);
                fos.close();

                fileUris.add(file.getAbsolutePath());
                captureFinished = true;
                imageCaptured();

                camera.startPreview();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

    public void CaptureImage(View v){
        if(camera!= null){
            camera.takePicture(null,null,mPictureCallback);
        }
    }


    private void imageCaptured() {
        picstaken++;
        imagesCapturedno++;
        if(picstaken >= 50 || fromActivity == 3){
            if(fromActivity == 1){
                Intent cameraintent = new Intent(NewCustomCamera.this,image_editor.class);
                cameraintent.putExtra("imagesCaptured",fileUris);
                cameraintent.putExtra("fromGallery",false);
                startActivity(cameraintent);
                finish();
            } else if (fromActivity == 2) {
                Intent cameraintent = new Intent();
                cameraintent.putExtra("imagesCaptured", fileUris);
                cameraintent.putExtra("fromGallery", false);
                setResult(RESULT_OK, cameraintent);
                finish();
            } else {
                Intent cameraintent = new Intent();
                cameraintent.putExtra("imagesCaptured", file.getAbsolutePath());
                cameraintent.putExtra("fromGallery", false);
                setResult(RESULT_OK, cameraintent);
                finish();
            }
        } else {
            if(!firrstImageCaptured){
                firrstImageCaptured = true;
                imagesCaptured.setVisibility(View.VISIBLE);
                imagesCaptured2.setVisibility(View.VISIBLE);
                imagesNo.setVisibility(View.VISIBLE);
                imagesCaptured.startAnimation(flash2anim);
                imagesCaptured2.startAnimation(flash2anim);
                imagesNo.startAnimation(flash2anim);
                galleryButton.startAnimation(flash1anim);
                okButton.setVisibility(View.VISIBLE);
                okButton.startAnimation(flash2anim);

            }

            imagesNo.setText(String.valueOf(imagesCapturedno));

            Bitmap capturedBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            BitmapDrawable CBD = new BitmapDrawable(getResources(),capturedBitmap);
            imagesCaptured2.setBackground(CBD);
        }
    }

    private  void okPressed(){
        if (captureFinished == true) {
            if (fromActivity == 1) {
                Intent cameraintent2 = new Intent(NewCustomCamera.this, image_editor.class);
                cameraintent2.putExtra("imagesCaptured", fileUris);
                cameraintent2.putExtra("fromGallery", false);
                startActivity(cameraintent2);
                finish();
            } else {
                Intent cameraintent2 = new Intent();
                cameraintent2.putExtra("imagesCaptured", fileUris);
                cameraintent2.putExtra("fromGallery", false);
                setResult(RESULT_OK, cameraintent2);
                finish();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        switch (requestCode) {

            case 200: {
                /*if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    if (fromActivity == 1) {
                        Intent galleryintent = new Intent(NewCustomCamera.this, image_editor.class);
                        galleryintent.putExtra("fromGallery", true);
                        startActivity(galleryintent);
                        finish();
                    } else {
                        Intent galleryintent = new Intent();
                        galleryintent.putExtra("fromGallery", true);
                        setResult(RESULT_OK, galleryintent);
                        finish();
                    }
                } else {
                    Toast.makeText(this, "storage permission required to access images", Toast.LENGTH_SHORT).show();
                }*/


            }


        }

    }
}
