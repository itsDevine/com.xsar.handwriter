package com.xsar.handwriter;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.speech.RecognizerIntent;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class activity_fab4 extends AppCompatActivity {

    private static final String FILE_NAME = "sample.txt";
    EditText editText, filename;
    Button filenameok, filenamecancel;
    View renameBg;
    String mText, textFile, eTuNString;
    String fileName;
    File file,dir;
    LinearLayout renamelayout, saveButton, nextButton;
    boolean saving = false;
    private Animation fadeIn, fadeOut, fadeIn2, fadeOut2;

    int FileReady;
    private static final int READ_REQUEST_CODE = 42;
    private static final int RESULT_SPEECH = 420;
    ImageButton speechButton;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fab4);

        Toolbar toolbar = findViewById(R.id.tool_bar4);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Write your Text here");

        editText = findViewById(R.id.edit_text);
        eTuNString = editText.getText().toString();

        saveButton = findViewById(R.id.save_btn);
        nextButton = findViewById(R.id.next_btn);
        filename = findViewById(R.id.filename2);
        renamelayout = findViewById(R.id.renamelayout2);
        filenameok = findViewById(R.id.renameok2);
        filenamecancel = findViewById(R.id.renamecancel2);
        renameBg = findViewById(R.id.renameBackground);
        speechButton = findViewById(R.id.speechButton);

        //setfabui();
        renameUI();

        fadeIn2 = AnimationUtils.loadAnimation(activity_fab4.this, R.anim.fadein_slow);
        fadeOut2 = AnimationUtils.loadAnimation(activity_fab4.this, R.anim.fadeout_slow);
        fadeIn = AnimationUtils.loadAnimation(activity_fab4.this, R.anim.fadein);
        fadeOut = AnimationUtils.loadAnimation(activity_fab4.this, R.anim.fadeout);

        MobileAds.initialize(this,
                "ca-app-pub-9818307260762254~1966791439");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9818307260762254/8769176969");
        //mInterstitialAd.loadAd(new AdRequest.Builder().build());

        FileReady = getIntent().getExtras().getInt("FileReady");

        switch (FileReady) {

            case 1: {
                performFileSearch();
                saveFunction();
                nextFunction();
                break;
            }

            case 2: {
                saveFunction();
                nextFunction();
                break;
            }

            case 3: {
                textFile = getIntent().getExtras().getString("textFile");
                editText.setText(textFile);
                saveFunction();
                nextFunction();
                break;
            }

            case 4: {
                String filepath = getIntent().getExtras().getString("path");
                try {
                    File myFile = new File(filepath);
                    FileInputStream fIn = new FileInputStream(myFile);
                    BufferedReader bF = new BufferedReader(new InputStreamReader(fIn));
                    String aDataRow = "";
                    String aBuffer = "";
                    while ((aDataRow = bF.readLine()) != null) {
                        aBuffer += aDataRow + "\n";
                    }
                    editText.setText(aBuffer);
                    bF.close();

                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                saveFunction();
                nextFunction();
            }
        }

        speechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent speech = new Intent(RecognizerIntent. ACTION_RECOGNIZE_SPEECH);
                speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                speech.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
                try {
                    startActivityForResult(speech, RESULT_SPEECH);
                   // editText.getText()
                    //editText.setText("");
                } catch (ActivityNotFoundException e){
                    Toast.makeText(activity_fab4.this, "This device do not support Speech-to-text function",
                            Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });

        renamelayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        filenameok.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                saveToTextFile();
                renamelayout.startAnimation(fadeOut);
                renamelayout.setVisibility(View.INVISIBLE);
                renameBg.startAnimation(fadeOut2);
                renameBg.setVisibility(View.INVISIBLE);
                saving = false;
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        });

        filenamecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renamelayout.startAnimation(fadeOut);
                renamelayout.setVisibility(View.INVISIBLE);
                renameBg.startAnimation(fadeOut2);
                renameBg.setVisibility(View.INVISIBLE);
                saving = false;
            }
        });

        renameBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renamelayout.startAnimation(fadeOut);
                renameBg.startAnimation(fadeOut2);
                renamelayout.setVisibility(View.INVISIBLE);
                renameBg.setVisibility(View.INVISIBLE);
                saving = false;
            }
        });


    }


    private void renameUI(){
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeiht = display.getHeight();

        LinearLayout.MarginLayoutParams renameparams = (LinearLayout.MarginLayoutParams) renamelayout.getLayoutParams();
        renameparams.width = (screenWidth*80)/100;
        renamelayout.setLayoutParams(renameparams);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (saving) {
            renamelayout.startAnimation(fadeOut);
            renamelayout.setVisibility(View.INVISIBLE);
            renameBg.startAnimation(fadeOut2);
            renameBg.setVisibility(View.INVISIBLE);saving = false;
        } else{
            finish();
        }
    }


    private void nextFunction() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!saving) {
                    mText = editText.getText().toString().trim();

                    if (mText.isEmpty()) {
                        Toast.makeText(activity_fab4.this, "Text cannot be Empty ", Toast.LENGTH_SHORT).show();
                    } else {
                        String name = editText.getText().toString();
                        Intent intent = new Intent(activity_fab4.this, activity_next.class);
                        intent.putExtra("edittext", name);
                        startActivity(intent);
                    }
                }
            }
        });
    }


    private void performFileSearch() {
        if (ActivityCompat.checkSelfPermission(activity_fab4.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity_fab4.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    100);
            return;
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("text/*");
            startActivityForResult(intent, READ_REQUEST_CODE);
        }

    }
/*
    private void setfabui(){
        Display display = getWindowManager().getDefaultDisplay();
        int Swidth = display.getWidth();

        LinearLayout.MarginLayoutParams renameParams = (LinearLayout.MarginLayoutParams) renamelayout.getLayoutParams();
        renameParams.width = (Swidth*90)/100;
        renameParams.height = (Swidth*30)/100;
        renameParams.setMargins(100,0,100,0);
        renamelayout.setLayoutParams(renameParams);
    }

 */

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                performFileSearch();
                Toast.makeText(this, "Permission Granted!", Toast.LENGTH_SHORT).show();
            } else {
                finish();
                Toast.makeText(this, "Storage Permission required to access text files", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void saveToTextFile() {
        if (ActivityCompat.checkSelfPermission(activity_fab4.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity_fab4.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
            return;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm",
                Locale.getDefault()).format(System.currentTimeMillis());
        try {
            File path = new File((Environment.getExternalStorageDirectory().getPath()));
            File dir = new File(path + "/HandWriter/" + "/Text Files/");
            if(!dir.exists()) {
                dir.mkdirs();
            }
            if(filename.getText().toString().isEmpty()) {
                fileName = "New Text " + timeStamp + ".txt";
            } else {
                fileName = filename.getText().toString()+".txt";
            }
            File file = new File(dir, fileName);

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(mText);
            bw.close();

            Toast.makeText(this, fileName + " is saved to " + dir, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }



    private void saveFunction() {
        saveButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                if (!saving) {
                    mText = editText.getText().toString().trim();

                    if (mText.isEmpty()) {
                        Toast.makeText(activity_fab4.this, "Text cannot be Empty ", Toast.LENGTH_SHORT).show();
                    } else {
                        renamelayout.startAnimation(fadeIn);
                        renamelayout.setVisibility(View.VISIBLE);
                        renameBg.startAnimation(fadeIn2);
                        renameBg.setVisibility(View.VISIBLE);
                        saving = true;
                    }
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            String selectedFile = data.getData().getPath();
            String filename = selectedFile.substring(selectedFile.lastIndexOf("/") + 1);
            editText.setText(filename);
            selectedFile = selectedFile.substring(selectedFile.lastIndexOf(":") + 1);
            try {
                File myFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + selectedFile);
                FileInputStream fIn = new FileInputStream(myFile);
                BufferedReader bF = new BufferedReader(new InputStreamReader(fIn));
                String aDataRow = "";
                String aBuffer = "";
                while ((aDataRow = bF.readLine()) != null) {
                    aBuffer += aDataRow + "\n";
                }
                editText.setText(aBuffer);
                bF.close();
                Toast.makeText(this, "Opening " + filename, Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == RESULT_SPEECH && resultCode == Activity.RESULT_OK) {
            if (data!=null){
                ArrayList<String> text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                editText.getText().insert(editText.getSelectionStart(), " " + text.get(0) + " ");
            }

        }
    }
}














