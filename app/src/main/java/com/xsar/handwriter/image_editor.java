package com.xsar.handwriter;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.SparseArray;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage;

public class image_editor extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 300;
    private static final int REQUEST_IMAGE_CAPTURE = 200;
    public Toolbar upperToolbar,lowerToolbar;
    public Button retakeButton,nextButton,cropButton,addButton,editorGallery,editorDelete;
    public ImageView imageView;
    boolean freshEntry = true, newImage = true, lastBitmapAdded = false, fromGallery,
            nextPressed = false, imagecaptured = false;
    ArrayList<String> photopaths;
    Bitmap imagebitmap;
    Uri imageuri;
    ArrayList<Uri> uris;
    ArrayList<String> newUris;
    public Uri imageUri;
    public ArrayList<Bitmap> imagesArray;
    public  ArrayList <String> recognizedText;
    public int picsTaken = 0, picDisplay = 0;
    public TextView imagesSelected;
    public String scannedText, currentPhotoPath;
    AnimationDrawable anim;
    ImageView loading;
    View loadingBg;
    TextView loadingText;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_editor);
        upperToolbar = (Toolbar) findViewById(R.id.upperToolbar);
        lowerToolbar = (Toolbar) findViewById(R.id.lowerToolbar);
        retakeButton = (Button) findViewById(R.id.retakeButton);
        nextButton = (Button) findViewById(R.id.nextButton);
        cropButton = (Button) findViewById(R.id.cropButton);
        addButton = (Button) findViewById(R.id.addButton);
        editorGallery = (Button) findViewById(R.id.editorGallery);
        editorDelete = (Button) findViewById(R.id.editorDelete);
        imageView = (ImageView) findViewById(R.id.imageView);
        imagesSelected = (TextView) findViewById(R.id.imagesSelected);
        loading =(ImageView) findViewById(R.id.loadingImageView);
        anim = (AnimationDrawable)loading.getDrawable();
        loadingBg = findViewById(R.id.loadingBG);
        loadingText = findViewById(R.id.loadingTextView);

        imagesArray = new ArrayList<Bitmap>();
        recognizedText = new ArrayList<String>();
        photopaths = new ArrayList<String>();
        uris = new ArrayList<Uri>();
        setUi();

        setSupportActionBar(upperToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");

        fromGallery = getIntent().getExtras().getBoolean("fromGallery");

        if (fromGallery == false && freshEntry==true){
            if (ContextCompat.checkSelfPermission(image_editor.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(image_editor.this,
                        new String[]{
                                Manifest.permission.CAMERA
                        },
                        CAMERA_REQUEST_CODE);
            }else{
                dispatchTakePictureIntent();
                freshEntry = false;
            }
        }

        if (fromGallery == true){
            getImages();
        }


        imageView.setOnTouchListener(new OnSwipeTouchListener(this));

        retakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (picsTaken > 0) {

                    if (ContextCompat.checkSelfPermission(image_editor.this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(image_editor.this,
                                new String[]{
                                        Manifest.permission.CAMERA
                                },
                                CAMERA_REQUEST_CODE);
                    }else{
                        dispatchTakePictureIntent();
                        newImage = false;
                    }
                } else {
                    Toast.makeText(image_editor.this,"Press add or gallery button to take images", Toast.LENGTH_SHORT ).show();
                }

            }
        });

        cropButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (picsTaken > 0) {
                    CropImage.activity(uris.get(picDisplay-1)).start(image_editor.this);
                } else {
                    Toast.makeText(image_editor.this,"Press add or gallery button to take images", Toast.LENGTH_SHORT ).show();
                }

            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(picsTaken < 50){
                    if (ContextCompat.checkSelfPermission(image_editor.this,
                            Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(image_editor.this,
                                new String[]{
                                        Manifest.permission.CAMERA
                                },
                                CAMERA_REQUEST_CODE);
                    }else{
                        dispatchTakePictureIntent();
                        newImage = true;
                    }

                } else{

                }

            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(picsTaken==0){
                    Toast.makeText(image_editor.this, "No Images selected or captured", Toast.LENGTH_SHORT).show();
                }
                if(picsTaken>0) {

                    loading.setVisibility(View.VISIBLE);
                    loadingText.setVisibility(View.VISIBLE);
                    loadingBg.setVisibility(View.VISIBLE);
                    nextButton.setVisibility(View.GONE);
                    editorGallery.setVisibility(View.GONE);
                    cropButton.setVisibility(View.GONE);
                    addButton.setVisibility(View.GONE);
                    retakeButton.setVisibility(View.GONE);
                    editorDelete.setVisibility(View.GONE);
                    anim.start();


                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
                            for (int i = 0; i < imagesArray.size(); i++) {

                                if (!textRecognizer.isOperational()) {

                                } else {
                                    Frame frame = new Frame.Builder().setBitmap(imagesArray.get(i)).build();
                                    SparseArray<TextBlock> item = textRecognizer.detect(frame);
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for (int j = 0; j < item.size(); j++) {
                                        TextBlock myItem = item.valueAt(j);
                                        stringBuilder.append(myItem.getValue() + " ");
                                    }
                                    scannedText = stringBuilder.toString();
                                    recognizedText.add(scannedText);
                                }
                            }

                            anim.stop();


                            Intent nextActivity = new Intent(image_editor.this, text_recognizer.class);
                            nextActivity.putExtra("recognizedText", recognizedText);
                            startActivity(nextActivity);
                            nextPressed = false;
                            recognizedText.clear();
                        }
                    }).start();


                }
/*
                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loading.setVisibility(View.INVISIBLE);
                        loadingText.setVisibility(View.INVISIBLE);
                        loadingBg.setVisibility(View.INVISIBLE);
                        nextButton.setVisibility(View.VISIBLE);
                        editorGallery.setVisibility(View.VISIBLE);
                        cropButton.setVisibility(View.VISIBLE);
                        addButton.setVisibility(View.VISIBLE);
                        retakeButton.setVisibility(View.VISIBLE);
                        editorDelete.setVisibility(View.VISIBLE);
                    }
                }, 10000);

 */
            }
        });


        editorGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getImages();
            }
        });

        editorDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (picsTaken > 0) {
                    uris.remove(picDisplay-1);
                    picsTaken--;
                    imagesArray.remove(picDisplay-1);
                    if (picsTaken >0){
                        if (picsTaken < picDisplay){
                            picDisplay--;
                            imagesSelected.setText(String.valueOf(picDisplay) + "/50");
                        }
                        imagebitmap = imagesArray.get(picDisplay-1);
                        imageView.setImageBitmap(imagebitmap);
                    } else {
                        finish();
                    }
                }  else {
                    Toast.makeText(image_editor.this,"Press add or gallery button to take images", Toast.LENGTH_SHORT ).show();
                }

            }
        });

    }

    @Override
    protected void onRestart() {


        loading.setVisibility(View.INVISIBLE);
        loadingText.setVisibility(View.INVISIBLE);
        loadingBg.setVisibility(View.INVISIBLE);
        nextButton.setVisibility(View.VISIBLE);
        editorGallery.setVisibility(View.VISIBLE);
        cropButton.setVisibility(View.VISIBLE);
        addButton.setVisibility(View.VISIBLE);
        retakeButton.setVisibility(View.VISIBLE);
        editorDelete.setVisibility(View.VISIBLE);

        super.onRestart();
    }

    private void setUi(){
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeiht = display.getHeight();

        ViewGroup.MarginLayoutParams loadingLayoutParams = (ViewGroup.MarginLayoutParams)loading.getLayoutParams();
        loadingLayoutParams.height = (screenWidth*20)/100;
        loadingLayoutParams.width = (screenWidth*40)/100;
        loading.setLayoutParams(loadingLayoutParams);

        Toolbar.MarginLayoutParams upperToolbarParams = (Toolbar.MarginLayoutParams)upperToolbar.getLayoutParams();
        upperToolbarParams.height = (screenHeiht*7)/100;
        upperToolbar.setLayoutParams(upperToolbarParams);

        ViewGroup.MarginLayoutParams nextParams= (ViewGroup.MarginLayoutParams)nextButton.getLayoutParams();
        nextParams.height = (screenHeiht*5)/100;
        nextParams.width = (screenHeiht*7)/100;
        nextParams.setMargins(0,0,(screenHeiht*1)/100,0);
        nextButton.setLayoutParams(nextParams);

        Toolbar.MarginLayoutParams lowerToolbarParams = (Toolbar.MarginLayoutParams)lowerToolbar.getLayoutParams();
        lowerToolbarParams.height = (screenHeiht*13)/100;
        lowerToolbar.setLayoutParams(lowerToolbarParams);

        ViewGroup.MarginLayoutParams prevImageParams = (ViewGroup.MarginLayoutParams) editorGallery.getLayoutParams();
        prevImageParams.height = (screenHeiht*4)/100;
        prevImageParams.width = (screenHeiht*4)/100;
        editorGallery.setLayoutParams(prevImageParams);

        ViewGroup.MarginLayoutParams cropParams= (ViewGroup.MarginLayoutParams)cropButton.getLayoutParams();
        cropParams.height = (screenHeiht*5)/100;
        cropParams.width = (screenHeiht*5)/100;
        cropButton.setLayoutParams(cropParams);

        ViewGroup.MarginLayoutParams addParams= (ViewGroup.MarginLayoutParams)addButton.getLayoutParams();
        addParams.height = (screenHeiht*7)/100;
        addParams.width = (screenHeiht*7)/100;
        addButton.setLayoutParams(addParams);

        ViewGroup.MarginLayoutParams retakeParams= (ViewGroup.MarginLayoutParams)retakeButton.getLayoutParams();
        retakeParams.height = (screenHeiht*5)/100;
        retakeParams.width = (screenHeiht*5)/100;
        retakeButton.setLayoutParams(retakeParams);

        ViewGroup.MarginLayoutParams nextImageParams = (ViewGroup.MarginLayoutParams) editorDelete.getLayoutParams();
        nextImageParams.height = (screenHeiht*4)/100;
        nextImageParams.width = (screenHeiht*4)/100;
        editorDelete.setLayoutParams(nextImageParams);
    }

    private void dispatchTakePictureIntent(){

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "Handwriter_" + timeStamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
            currentPhotoPath = image.getAbsolutePath();
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            imageUri = FileProvider.getUriForFile(this,"com.xsar.handwriter.fileprovider",image);

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } catch (IOException e) {
            e.printStackTrace();
        }




    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);



        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(currentPhotoPath);

            try {
                Bitmap rotatedBitmap;
                ExifInterface exifInterface =new ExifInterface(currentPhotoPath);
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);
                switch (orientation){
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotatedBitmap = rotateImage(imageBitmap,90);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotatedBitmap = rotateImage(imageBitmap,180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotatedBitmap = rotateImage(imageBitmap,270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotatedBitmap = imageBitmap;
                }
                imageBitmap = rotatedBitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }

            imagecaptured = true;
            imagebitmap = imageBitmap;
            imageView.setImageBitmap(imageBitmap);
            imageuri = imageUri;

            if(newImage == true) {
                uris.add(imageUri);
                picsTaken++;
                picDisplay = picsTaken;
                imagesSelected.setText(String.valueOf(picsTaken) + "/50");
                imagesArray.add(imageBitmap);

            } else{
                uris.set(picDisplay-1,imageUri);
                imagesArray.set(picDisplay-1,imageBitmap);
                newImage = true;
            }
            lastBitmapAdded = true;
        }



        if(requestCode == 120 && resultCode == RESULT_OK){
            fromGallery = false;
            ClipData clipData = data.getClipData();

            if(clipData != null){
                int noSelected = clipData.getItemCount();
                if (picsTaken == 0 && noSelected == 0){
                    finish();
                }
                if(noSelected > 50 - picsTaken){
                    noSelected = 50 - picsTaken;
                }
                picsTaken +=noSelected;
                picDisplay += noSelected;
                for (int i=0; i < noSelected; i++){
                    Uri imageUri = clipData.getItemAt(i).getUri();
                    try {
                        InputStream is = getContentResolver().openInputStream(imageUri);
                        Bitmap addBitmap = BitmapFactory.decodeStream(is);
                        imagesArray.add(addBitmap);
                        uris.add(imageUri);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                imageuri = imageUri;
                imagebitmap = imagesArray.get(picsTaken-1);
                imageView.setImageBitmap(imagebitmap);
                imagesSelected.setText(String.valueOf(picDisplay)+"/50");
                lastBitmapAdded = true;
            } else {
                Uri imageUri = data.getData();
                picsTaken++;
                picDisplay = picsTaken;
                try {
                    InputStream is = getContentResolver().openInputStream(imageUri);
                    Bitmap addBitmap = BitmapFactory.decodeStream(is);
                    imagesArray.add(addBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                imageuri = imageUri;
                uris.add(imageUri);
                imagebitmap = imagesArray.get(picsTaken-1);
                imageView.setImageBitmap(imagebitmap);
                imagesSelected.setText(String.valueOf(picDisplay)+"/50");
                lastBitmapAdded = true;
            }
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                imageView.setImageURI(result.getUri());
                uris.set(picDisplay-1,result.getUri());
                try {
                    imagebitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),result.getUri());
                    imagesArray.set(picDisplay-1,imagebitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }

        /*if (requestCode == 150){
            if(resultCode == RESULT_OK){
                fromGallery = data.getExtras().getBoolean("fromGallery");
                if(fromGallery == true) {
                    getImages();
                } else {
                    newUris = data.getStringArrayListExtra("imagesCaptured");
                    cameraImages();
                }
            }
        }

        if (requestCode == 250){
            if(resultCode == RESULT_OK){
                String uri = data.getStringExtra("imagesCaptured");
                uris.set(picDisplay-1,Uri.fromFile(new File(uri)));
                imagebitmap = BitmapFactory.decodeFile(uri);
                imagesArray.set(picDisplay-1,imagebitmap);
                imageView.setImageBitmap(imagebitmap);
            }
        }*/
    }

    private void prevImageDisplay(){
        if(picDisplay > 1){
            imagesArray.set(picDisplay-1,imagebitmap);
            picDisplay--;
            imageView.setImageBitmap(imagesArray.get(picDisplay-1));
            imagebitmap = imagesArray.get(picDisplay-1);
            imagesSelected.setText(String.valueOf(picDisplay) + "/50");
        }
    }

    private  void nextImageDisplay(){
        if(picDisplay < picsTaken){
            imagesArray.set(picDisplay-1,imagebitmap);
            picDisplay++;
            imageView.setImageBitmap(imagesArray.get(picDisplay-1));
            imagebitmap = imagesArray.get(picDisplay-1);
            imagesSelected.setText(String.valueOf(picDisplay) + "/50");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100 ){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getImages();
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                finish();
                Toast.makeText(this, "Storage Permission required to access Photos", Toast.LENGTH_SHORT).show();
            }
        } else if(requestCode == CAMERA_REQUEST_CODE){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                dispatchTakePictureIntent();
            } else{
                finish();
                Toast.makeText(this, "Camera Permission required to take pictures", Toast.LENGTH_SHORT).show();

            }

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed(){
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String[] myfiles;
        myfiles = file.list();
        for (int i=0; i<myfiles.length;i++){
            File myfile = new File(file,myfiles[i]);
            myfile.delete();
            /*if (myfile.getName().startsWith("Handwriter_")) {
                myfile.delete();
            }*/
        }
        finish();
    }

    private  void  getImages(){
        if (ActivityCompat.checkSelfPermission(image_editor.this,Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(image_editor.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
            return;
        } else{
            Intent pick_image_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            pick_image_intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
            startActivityForResult(pick_image_intent, 120);

        }

    }

   /* private void cameraImages(){
        for (int i = 0; i < newUris.size(); i++) {
            uris.add(Uri.fromFile(new File(newUris.get(i))));
            imagebitmap =BitmapFactory.decodeFile(newUris.get(i));

            try {
                Bitmap rotatedBitmap;
                ExifInterface exifInterface =new ExifInterface(newUris.get(i));
                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,ExifInterface.ORIENTATION_UNDEFINED);
                switch (orientation){
                    case ExifInterface.ORIENTATION_ROTATE_90:
                        rotatedBitmap = rotateImage(imagebitmap,90);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        rotatedBitmap = rotateImage(imagebitmap,180);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        rotatedBitmap = rotateImage(imagebitmap,270);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        rotatedBitmap = imagebitmap;
                }
                imagebitmap = rotatedBitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }

            imagesArray.add(imagebitmap);
            picsTaken++;
            picDisplay = picsTaken;
        }
        imageView.setImageBitmap(imagebitmap);
        imagesSelected.setText(String.valueOf(picDisplay)+"/50");
    }*/

    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context context) {
            gestureDetector = new GestureDetector(context, new GestureListener());
        }

        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_DISTANCE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float distanceX = e2.getX() - e1.getX();
                float distanceY = e2.getY() - e1.getY();
                if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (distanceX > 0)
                        prevImageDisplay();
                    else
                        nextImageDisplay();
                    return true;
                }
                return false;
            }
        }
    }


}