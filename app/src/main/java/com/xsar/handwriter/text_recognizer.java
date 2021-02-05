package com.xsar.handwriter;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;

public class text_recognizer extends AppCompatActivity {

    Button nextButton2,prevText,nextText,addText,okButton,guidance;
    TextView textSelected,copyIns,downPaste;
    public Toolbar upperToolbar2,lowerToolbar2;
    EditText scannedText;
    int textNumber = 0, totalTexts;
    ArrayList<String> recognizedText;
    String textFile,copiedText;
    Boolean finalized = false, pasting = false, guidanceon = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_recognizer);

        upperToolbar2 = (Toolbar) findViewById(R.id.upperToolbar2);
        textSelected = (TextView) findViewById(R.id.textSelected);
        nextButton2 = (Button) findViewById(R.id.nextButton2);
        scannedText = (EditText)findViewById(R.id.scannedText);
        lowerToolbar2 = (Toolbar) findViewById(R.id.lowerToolbar2);
        prevText = (Button) findViewById(R.id.prevText);
        addText = (Button) findViewById(R.id.addText);
        nextText = (Button) findViewById(R.id.nextText);
        okButton = (Button) findViewById(R.id.okbutton);
        copyIns = (TextView) findViewById(R.id.copyInstruction);
        guidance = (Button) findViewById(R.id.guidance);
        downPaste = (TextView) findViewById(R.id.downPaste);
        recognizedText =  new ArrayList<String>();
        setUI();
        recognizedText = getIntent().getStringArrayListExtra("recognizedText");
        totalTexts = recognizedText.size();
        textSelected.setText(String.valueOf(textNumber+1) + "/" + String.valueOf(totalTexts));
        scannedText.setText(recognizedText.get(0));

        setSupportActionBar(upperToolbar2);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");



        boolean isFirstRun3 = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun3", true);
        if (isFirstRun3) {

            guidanceon = true;
            guidance.setVisibility(View.VISIBLE);
            guidance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    guidance.setVisibility(View.INVISIBLE);
                    guidanceon = false;
                }
            });

        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun3", false).apply();

        prevText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textNumber > 0){
                    recognizedText.set(textNumber,scannedText.getText().toString());
                    textNumber--;
                    scannedText.setText(recognizedText.get(textNumber));
                    textSelected.setText(String.valueOf(textNumber+1)+"/" + String.valueOf(totalTexts));
                }
            }
        });

        nextText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textNumber < totalTexts-1){
                    recognizedText.set(textNumber,scannedText.getText().toString());
                    textNumber++;
                    scannedText.setText(recognizedText.get(textNumber));
                    textSelected.setText(String.valueOf(textNumber+1)+"/" + String.valueOf(totalTexts));

                }
            }
        });

        addText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(guidanceon == false){
                    nextButton2.setVisibility(View.VISIBLE);
                    copyIns.setText("Paste Here");
                    scannedText.setBackgroundResource(R.drawable.paste);
                    prevText.setVisibility(View.INVISIBLE);
                    nextText.setVisibility(View.INVISIBLE);
                    addText.setVisibility(View.INVISIBLE);
                    downPaste.setVisibility(View.INVISIBLE);
                    okButton.setVisibility(View.VISIBLE);
                    scannedText.setText(textFile);
                    copiedText = scannedText.getText().toString();
                    pasting = true;
                    finalized = false;
                }
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                nextButton2.setVisibility(View.INVISIBLE);
                textFile = scannedText.getText().toString();
                scannedText.setText(recognizedText.get(textNumber));
                scannedText.setBackground(getDrawable(R.drawable.copy));
                okButton.setVisibility(View.INVISIBLE);
                prevText.setVisibility(View.VISIBLE);
                addText.setVisibility(View.VISIBLE);
                nextText.setVisibility(View.VISIBLE);
                downPaste.setVisibility(View.VISIBLE);
                copyIns.setText("Edit and Copy");
                pasting = false;
                finalized = true;
            }
        });

        nextButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!finalized){
                    if(scannedText.getText().length() == 0){
                        Toast.makeText(text_recognizer.this, "Text cannot be empty", Toast.LENGTH_SHORT).show();
                    }else {
                        textFile = scannedText.getText().toString();
                        Intent nextActivity = new Intent(text_recognizer.this,activity_fab4.class);
                        nextActivity.putExtra("FileReady",3);
                        nextActivity.putExtra("textFile",textFile);
                        startActivity(nextActivity);
                    }

                }

            }
        });


    }

    private void setUI() {
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();

        Toolbar.MarginLayoutParams upperparams = (Toolbar.MarginLayoutParams) upperToolbar2.getLayoutParams();
        upperparams.height = (screenHeight*7)/100;
        upperToolbar2.setLayoutParams(upperparams);

        ViewGroup.MarginLayoutParams nextParams = (ViewGroup.MarginLayoutParams) nextButton2.getLayoutParams();
        nextParams.height = (screenHeight*5)/100;
        nextParams.setMargins(0,0,(screenHeight*1)/100,0);
        nextButton2.setLayoutParams(nextParams);

        Toolbar.MarginLayoutParams lowerParams = (Toolbar.MarginLayoutParams) lowerToolbar2.getLayoutParams();
        lowerParams.height = (screenHeight * 13)/100;
        lowerToolbar2.setLayoutParams(lowerParams);

        ViewGroup.MarginLayoutParams prevTextParams = (ViewGroup.MarginLayoutParams) prevText.getLayoutParams();
        prevTextParams.height = (screenHeight * 5)/100;
        prevTextParams.width = (screenHeight * 5)/100;
        prevTextParams.setMargins((screenWidth * 10)/100,0,0,0);
        prevText.setLayoutParams(prevTextParams);

        ViewGroup.MarginLayoutParams addTextParams = (ViewGroup.MarginLayoutParams) addText.getLayoutParams();
        addTextParams.height = (screenHeight*7)/100;
        addTextParams.width = (screenHeight*7)/100;
        addText.setLayoutParams(addTextParams);

        ViewGroup.MarginLayoutParams okButParams = (ViewGroup.MarginLayoutParams) okButton.getLayoutParams();
        okButParams.height = (screenHeight*7)/100;
        okButParams.width = (screenHeight*7)/100;
        okButton.setLayoutParams(okButParams);

        ViewGroup.MarginLayoutParams nextTextParams = (ViewGroup.MarginLayoutParams) nextText.getLayoutParams();
        nextTextParams.height = (screenHeight * 5)/100;
        nextTextParams.width = (screenHeight * 5)/100;
        nextTextParams.setMargins(0,0,(screenWidth*10)/100, 0);
        nextText.setLayoutParams(nextTextParams);

        okButton.setVisibility(View.INVISIBLE);

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void onBackPressed(){
        if(pasting){
            textFile = scannedText.getText().toString();
            scannedText.setText(recognizedText.get(textNumber));
            okButton.setVisibility(View.INVISIBLE);
            prevText.setVisibility(View.VISIBLE);
            addText.setVisibility(View.VISIBLE);
            nextText.setVisibility(View.VISIBLE);
            downPaste.setVisibility(View.VISIBLE);
            scannedText.setBackgroundResource(R.drawable.copy);
            copyIns.setText("Edit and Copy");
            pasting = false;
            finalized = true;
        } else {
            finish();
        }
    }

}