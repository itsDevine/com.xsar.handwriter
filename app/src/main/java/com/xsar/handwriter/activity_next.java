package com.xsar.handwriter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class activity_next extends AppCompatActivity {


    ImageButton backButton, capturedAccept, capturedDecline, walktroughNextbut,
            iV0, iV1, iV2, iV3, iV4, iV5, iV6, iV7, iV8, iV9, backgroundWallpaperClose, fontLayoutClose, colorClose;
    Button imageButton1, imageButton2, imageButton3, imageButton4, CaptureButton;
    Button imageButton1Duplicate, imageButton2Duplicate, imageButton3Duplicate, imageButton4Duplicate, CaptureButtonDuplicate;
    Button guidance2, fSel0, fSel1, fSel2, fSel3, fSel4, fSel5, fSel6, fSel7, fSel8, fSel9, fSel10, fSel11, fSel12, fSel13, fsel14, fsel15,
            fSel16, fSel17, fSel18, fSel19, fSel20, fSel21, fSel22, fSel23, fSel24, fSel25, fSel26, fSel27, fSel28, fSel29, fSel30,
            fSel31, fSel32, fSel33, fSel34, fSel35, fSel36, fSel37, fSel38, fSel39, fSel40,
            fSel41, fSel42, fSel43, fSel44, fSel45, fSel46, fSel47, fSel48, fSel49, fSel50, fSel51, fSel52, fSel53, fSel54, fSel55, fSel56,
            cBut0, cBut1, cBut2, cBut3, cBut4, cBut5,cBut6, add_your_font, MarginButton, sideMarginButton, DrawLinesButton;
    EditText editText1;
    ImageView pickImage, selected, selected2, unselected, selected21, selected22, selected31, selected33;
    private Animation fromBottom, toBottom, captureanim;
    LinearLayout linearLayout, exportButton, linesLinearLayout, addBGFromGallery;
    HorizontalScrollView scrollView, textColorScrollView;
    public boolean retake = false, bgImageScroller = false, fontStyleSelector = false, colorSelector = false, capturedImage = false,
            drawMargin = false, linesSelector = false, drawLines = false, drawSideMargin = false, drawlinesboolean=false;;
    int retakeno, backgroundSelected = 0, fontSelected = 0, fontSize = 50, fontColorSelected = 0,
            numOfImages = 0, vpad = 5, hpad = 5;
    float dist2,fontHeight = 8.2f, lineDistance=0, topPadding;
    ArrayList<String> uris;
    View lineView, marginView, sideMarginview;

    Integer[] backgroundPapers = {R.drawable.paper_background_3, R.drawable.paper_background_1, R.drawable.paper_background_2,
            R.drawable.paper_background_3, R.drawable.paper_background_4, R.drawable.paper_background_5,
            R.drawable.paper_background_6, R.drawable.paper_background_7, R.drawable.paper_background_8, R.drawable.paper_background_9};

    ArrayList<Bitmap> capturedImages = new ArrayList<Bitmap>();

    Bitmap saveBitmap, lineBitmap, marginBitmap, sideMarginBitmap;

    Toolbar toolbarcaptured;

    TextView textView;


    private SharedPreferences prefs;

    @SuppressLint({"WrongViewCast", "NewApi", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        findViewById();
        uris = new ArrayList<String>();
        editText1.setText(getIntent().getStringExtra("edittext"));
        editText1.setPadding(40,100,0,0);
        //setUI();
        setInvisible();
        fromBottom = AnimationUtils.loadAnimation(activity_next.this, R.anim.from_bottom_original_size);
        toBottom = AnimationUtils.loadAnimation(activity_next.this, R.anim.to_bottom_original_size);
        captureanim = AnimationUtils.loadAnimation(this,R.anim.captureanimation);



        boolean isFirstRun2 = getSharedPreferences("PREFERENCE2", MODE_PRIVATE).getBoolean("isFirstRun2", true);
        if (isFirstRun2) {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(activity_next.this, WalkThroughActivityInNext.class);
                    startActivity(intent);
                }
            }, 500);

        }
        getSharedPreferences("PREFERENCE2", MODE_PRIVATE).edit().putBoolean("isFirstRun2", false).apply();

        walktroughNextbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_next.this, WalkThroughActivityInNext.class);
                startActivity(intent);
            }
        });

       // defaultButtonsSelected();


        exportButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {

                if (!capturedImage) {


                    if (numOfImages > 0) {
                        goToFinalize();
                    } else {
                        guidance2.setVisibility(View.VISIBLE);
                    }
                }
            }
        });


        guidance2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                guidance2.setVisibility(View.INVISIBLE);
            }
        });

        imageButton1Duplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!capturedImage) {
                    if (!bgImageScroller) {
                        setInvisible();
                        setFalse();
                        // scrollView.startAnimation(fromBottom);
                        scrollView.setVisibility(View.VISIBLE);
                        backgroundWallpaperClose.setVisibility(View.VISIBLE);
                        bgImageScroller = true;
                    } else {
                        //scrollView.startAnimation(toBottom);
                        setInvisible();
                        bgImageScroller = false;
                    }
                }
            }

        });

        imageButton1_OnClickListener();

        imageButton2Duplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!capturedImage) {
                    if (!fontStyleSelector) {
                        setInvisible();
                        setFalse();
                        linearLayout.setVisibility(View.VISIBLE);
                        fontLayoutClose.setVisibility(View.VISIBLE);
                        fontStyleSelector = true;
                    } else {
                        setInvisible();
                        fontStyleSelector = false;
                    }
                }
            }
        });

        imageButton2_OnClickListener();

        imageButton3Duplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!capturedImage) {
                    if (!colorSelector) {
                        setInvisible();
                        setFalse();
                        textColorScrollView.setVisibility(View.VISIBLE);
                        colorClose.setVisibility(View.VISIBLE);
                        colorSelector = true;
                    } else {
                        setInvisible();
                        colorSelector = false;
                    }
                }
            }
        });

        imageButton3_OnClickListener();

        imageButton4Duplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!capturedImage) {
                    if (!linesSelector) {
                        setInvisible();
                        setFalse();
                        linesLinearLayout.setVisibility(View.VISIBLE);
                        linesSelector = true;
                    } else {
                        setInvisible();
                        linesSelector = false;
                    }
                }

            }
        });

        addBGFromGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pick_image_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pick_image_intent, 120);
            }
        });

        /*add_your_font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity_next.this, ActivityAddYourFont.class);
                startActivity(intent);
            }
        });*/


        CaptureButtonDuplicate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureImage();
            }
        });

        capturedAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureAccept();
            }
        });

        capturedDecline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                captureDecline();
            }
        });


        backgroundWallpaperClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInvisible();
                setFalse();
            }
        });

        fontLayoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInvisible();
                setFalse();
            }
        });

        colorClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setInvisible();
                setFalse();
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!capturedImage) {
                    finish();
                }
            }
        });
        SeekBar seekBar = findViewById(R.id.FontSizeBar);
        SeekBar seekBar2 = findViewById(R.id.letterSpacingBar);

        prefs = getPreferences(MODE_PRIVATE);
        float fs = prefs.getFloat("fontsize", 14);
        seekBar.setMin(50);
        seekBar.setMax(80);
        seekBar.setProgress((int) fs);
        editText1.setTextSize(TypedValue.COMPLEX_UNIT_PX, seekBar.getProgress());

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                editText1.setTextSize(TypedValue.COMPLEX_UNIT_PX, seekBar.getProgress());
                fontSize = i;
                if(drawLines){
                    drawLines(fontHeight,i);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                prefs = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putFloat("fontsize", editText1.getTextSize());
                editor.commit();

            }
        });

        seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar2, int progress, boolean b) {
                float floatProgress = (float) progress / 500;
                editText1.setLetterSpacing(floatProgress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar2) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar2) {

            }
        });

        MarginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawMargin) {
                    drawMargin = false;
                    selected.setVisibility(View.INVISIBLE);/*---------------------------------------------------------------*/
                    selected2.setVisibility(View.INVISIBLE);
                    Bitmap newBitmap = Bitmap.createBitmap(marginView.getWidth(), marginView.getHeight(), Bitmap.Config.ARGB_8888);
                    marginView.setBackground(new BitmapDrawable(getResources(), newBitmap));
                    marginBitmap = newBitmap;
                } else {
                    Bitmap newBitmap = Bitmap.createBitmap(marginView.getWidth(), marginView.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas3 = new Canvas(newBitmap);
                    Paint paint3 = new Paint();
                    paint3.setColor(getResources().getColor(R.color.ColourDarkRed));
                    paint3.setStyle(Paint.Style.STROKE);
                    paint3.setStrokeWidth(3);
                    canvas3.drawLine(0,78,marginView.getWidth(),78,paint3);
                    marginView.setBackground(new BitmapDrawable(getResources(), newBitmap));
                    marginBitmap = newBitmap;
                    drawMargin = true;
                    selected.setVisibility(View.VISIBLE);
                    selected2.setVisibility(View.VISIBLE);
                }
            }
        });

        sideMarginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawSideMargin){
                    drawSideMargin=false;
                    selected21.setVisibility(View.INVISIBLE);/*------------side margin onclick Listener--------------------*/
                    selected22.setVisibility(View.INVISIBLE);
                    editText1.setPadding(40,100,0,0);
                    Bitmap newBitmap = Bitmap.createBitmap(marginView.getWidth(), marginView.getHeight(), Bitmap.Config.ARGB_8888);
                    sideMarginview.setBackground(new BitmapDrawable(getResources(), newBitmap));
                    sideMarginBitmap = newBitmap;
                } else {
                    selected21.setVisibility(View.VISIBLE);
                    selected22.setVisibility(View.VISIBLE);
                    editText1.setPadding(60,100,0,0);
                    Bitmap newBitmap = Bitmap.createBitmap(marginView.getWidth(), marginView.getHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas3 = new Canvas(newBitmap);
                    Paint paint3 = new Paint();
                    paint3.setColor(getResources().getColor(R.color.ColourDarkRed));
                    paint3.setStyle(Paint.Style.STROKE);
                    paint3.setStrokeWidth(3);
                    canvas3.drawLine(48,0,48,marginView.getHeight(),paint3);
                    sideMarginview.setBackground(new BitmapDrawable(getResources(), newBitmap));
                    sideMarginBitmap = newBitmap;
                    drawSideMargin=true;
                }
                }
            });

        DrawLinesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawLines){
                    eraseLines();
                    selected31.setVisibility(View.INVISIBLE);/*-------drawLines Button On click listener-------*/
                    selected33.setVisibility(View.INVISIBLE);
                } else {
                    drawLines(fontHeight,fontSize);
                    selected31.setVisibility(View.VISIBLE);
                    selected33.setVisibility(View.VISIBLE);
                }



            }
        });



    }

    private void findViewById(){
        backButton = findViewById(R.id.back_button);
        exportButton = findViewById(R.id.export_btn);
        guidance2 = findViewById(R.id.guidance2);

        editText1 = findViewById(R.id.edit_text_InNext);
        pickImage = findViewById(R.id.Pick_Image_from_gallery);

        scrollView = findViewById(R.id.backgroundImageScroll);
        textColorScrollView = findViewById(R.id.text_colour_ScrollView);
        linearLayout = findViewById(R.id.linearLayout_Font_Selector);
        toolbarcaptured = findViewById(R.id.toolbarcaptured);

        backgroundWallpaperClose = findViewById(R.id.backGround_Wallpaper_close_Button);
        fontLayoutClose = findViewById(R.id.font_Layout_close_Button);
        colorClose = findViewById(R.id.Text_Color_Layout_close_Button);

        linesLinearLayout = (LinearLayout) findViewById(R.id.linesLinearLayout);
        MarginButton = (Button) findViewById(R.id.marginButton);
        sideMarginButton = (Button) findViewById(R.id.marginButton2);
        DrawLinesButton = (Button) findViewById(R.id.lineButton);

        selected = (ImageView) findViewById(R.id.circle_selected);
        selected2 = (ImageView) findViewById(R.id.circle_selected2);
        unselected = (ImageView) findViewById(R.id.circle_unselected);

        selected21 = (ImageView) findViewById(R.id.circle_selected21);
        selected22 = (ImageView) findViewById(R.id.circle_selected22);

        selected31 = (ImageView) findViewById(R.id.circle_selected3);
        selected33 = (ImageView) findViewById(R.id.circle_selected23);

        walktroughNextbut = (ImageButton) findViewById(R.id.walkthroughNext);

        iV0 = findViewById(R.id.paper_image_0);
        iV1 = findViewById(R.id.paper_image_1);
        iV2 = findViewById(R.id.paper_image_2);
        iV3 = findViewById(R.id.paper_image_3);
        //iV4 = findViewById(R.id.paper_image_4);
        iV5 = findViewById(R.id.paper_image_5);
        iV6 = findViewById(R.id.paper_image_6);
        iV7 = findViewById(R.id.paper_image_7);
        iV8 = findViewById(R.id.paper_image_8);
        iV9 = findViewById(R.id.paper_image_9);

        //add_your_font = findViewById(R.id.your_font);
        fSel0 = findViewById(R.id.handWritingFont0);
        fSel1 = findViewById(R.id.handWritingFont1);
        fSel2 = findViewById(R.id.handWritingFont2);
        fSel3 = findViewById(R.id.handWritingFont3);
        fSel4 = findViewById(R.id.handWritingFont4);
        fSel5 = findViewById(R.id.handWritingFont5);
        fSel6 = findViewById(R.id.handWritingFont6);
        fSel7 = findViewById(R.id.handWritingFont7);
        fSel8 = findViewById(R.id.handWritingFont8);
        fSel9 = findViewById(R.id.handWritingFont9);
        fSel10 = findViewById(R.id.handWritingFont10);
        fSel11 = findViewById(R.id.handWritingFont11);
        fSel12 = findViewById(R.id.handWritingFont12);
        fSel13 = findViewById(R.id.handWritingFont13);
        fsel14 = findViewById(R.id.handWritingFont14);
        fsel15 = findViewById(R.id.handWritingFont15);
        fSel16 = findViewById(R.id.handWritingFont16);
        fSel17 = findViewById(R.id.handWritingFont17);
        fSel18 = findViewById(R.id.handWritingFont18);
        fSel19 = findViewById(R.id.handWritingFont19);
        fSel20 = findViewById(R.id.handWritingFont20);
        fSel21 = findViewById(R.id.handWritingFont21);
        fSel22 = findViewById(R.id.handWritingFont22);
        fSel23 = findViewById(R.id.handWritingFont23);
        fSel24 = findViewById(R.id.handWritingFont24);
        fSel25 = findViewById(R.id.handWritingFont25);
        fSel26 = findViewById(R.id.handWritingFont26);
        fSel27 = findViewById(R.id.handWritingFont27);
        fSel28 = findViewById(R.id.handWritingFont28);
        fSel29 = findViewById(R.id.handWritingFont29);
        fSel30 = findViewById(R.id.handWritingFont30);

        fSel31 = findViewById(R.id.handWritingFont31);
        fSel32 = findViewById(R.id.handWritingFont32);
        fSel33 = findViewById(R.id.handWritingFont33);
        fSel34 = findViewById(R.id.handWritingFont34);
        fSel35 = findViewById(R.id.handWritingFont35);
        fSel36 = findViewById(R.id.handWritingFont36);
        fSel37 = findViewById(R.id.handWritingFont37);
        fSel38 = findViewById(R.id.handWritingFont38);
        fSel39 = findViewById(R.id.handWritingFont39);
        fSel40 = findViewById(R.id.handWritingFont40);
        fSel41 = findViewById(R.id.handWritingFont41);
        fSel42 = findViewById(R.id.handWritingFont42);
        fSel43 = findViewById(R.id.handWritingFont43);
        fSel44 = findViewById(R.id.handWritingFont44);
        fSel45 = findViewById(R.id.handWritingFont45);
        fSel46 = findViewById(R.id.handWritingFont46);
        fSel47 = findViewById(R.id.handWritingFont47);
        fSel48 = findViewById(R.id.handWritingFont48);
        fSel49 = findViewById(R.id.handWritingFont49);
        fSel50 = findViewById(R.id.handWritingFont50);
        fSel51 = findViewById(R.id.handWritingFont51);
        fSel52 = findViewById(R.id.handWritingFont52);
        fSel53 = findViewById(R.id.handWritingFont53);
        fSel54 = findViewById(R.id.handWritingFont54);
        fSel55 = findViewById(R.id.handWritingFont55);
        fSel56 = findViewById(R.id.handWritingFont56);

        cBut0 = findViewById(R.id.text_Colour_Black);
        cBut1 = findViewById(R.id.text_Colour_Grey);
        cBut2 = findViewById(R.id.text_Colour_Blue);
        cBut3 = findViewById(R.id.text_Colour_DarkBlue);
        cBut4 = findViewById(R.id.text_Colour_Red);
        cBut5 = findViewById(R.id.text_Colour_DarkRed);
        cBut6 = findViewById(R.id.text_Colour_White);

        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);
        CaptureButton = findViewById(R.id.textCaptureButton);

        imageButton1Duplicate = findViewById(R.id.imageButton1Layout);
        imageButton2Duplicate = findViewById(R.id.imageButton2Layout);
        imageButton3Duplicate = findViewById(R.id.imageButton3Layout);
        imageButton4Duplicate = findViewById(R.id.imageButton4Layout);
        CaptureButtonDuplicate = findViewById(R.id.textCaptureButtonLayout);

        capturedAccept = findViewById(R.id.captured_accept);
        capturedDecline = findViewById(R.id.captured_decline);
        textView = findViewById(R.id.pages_tv);
        lineView = (View) findViewById(R.id.lineView);
        marginView = (View) findViewById(R.id.marginView);
        sideMarginview = (View) findViewById(R.id.sideMarginView);
        addBGFromGallery = (LinearLayout) findViewById(R.id.addFromGallery);

    }


    private void defaultButtonsSelected() {
        Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.times_new_roman);
        editText1.setTypeface(type0);
        defaultFontBackground();
        fSel0.setBackgroundResource(R.drawable.activity_next_bg);

        editText1.setTextColor(getResources().getColor(R.color.ColourBlack));
        defaultTextColorBackground();
        cBut0.setBackgroundResource(R.drawable.activity_next_bg);

    }


    private void imageButton1_OnClickListener() {

        iV0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundSelected = 0;
                editText1.setBackgroundColor(Color.WHITE);
            }
        });

        iV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundSelected = 1;
                editText1.setBackgroundResource(backgroundPapers[1]);
            }
        });

        iV2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundSelected = 2;
                editText1.setBackgroundResource(backgroundPapers[2]);
            }
        });

        iV3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundSelected = 3;
                editText1.setBackgroundResource(backgroundPapers[3]);
            }
        });


        iV5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundSelected = 5;
                editText1.setBackgroundResource(backgroundPapers[5]);
            }
        });

        iV6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundSelected = 6;
                editText1.setBackgroundResource(backgroundPapers[6]);
            }
        });

        iV7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundSelected = 7;
                editText1.setBackgroundResource(backgroundPapers[7]);
            }
        });

        iV8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundSelected = 8;
                editText1.setBackgroundResource(backgroundPapers[8]);
            }
        });

        iV9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backgroundSelected = 9;
                editText1.setBackgroundResource(backgroundPapers[9]);
            }
        });
        // fSize0.setBackgroundResource(R.drawable.activity_next_bg);

    }


    private void imageButton2_OnClickListener() {

        fSel0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.times_new_roman);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel0.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 8.2f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.handwriting_03);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel1.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = -6f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.handwriting_04);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel2.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = -4.9f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.handwriting_02);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel3.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = -4f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.handwriting_01);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel4.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = -3.6f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.handwriting_05);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel5.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = -12f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.handwriterfont6_yashwanth);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel6.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = -3.6f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.handwriterfont7_ananya);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel7.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = -3.9f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.handwriterfont_8_afreedi);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel8.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = -4.5f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }

            }
        });

        fSel9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.handwriting_09);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel9.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = -3.6f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.handwriting_10);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel10.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.handwriting_11);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel11.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.handwriting_12);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel12.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = -3f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts13);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel13.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = -4.2f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fsel14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts14);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fsel14.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = -3.2f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fsel15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts15);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fsel15.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel16.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts16);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel16.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3.2f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts17);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel17.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 3.6f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel18.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts18);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel18.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3.2f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel19.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts19);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel19.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3.2f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts20);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel20.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts21);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel21.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3.2f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts22);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel22.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel23.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts23);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel23.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel24.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts24);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel24.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3.2f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts25);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel25.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3.5f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel26.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts26);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel26.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel27.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts27);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel27.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3.2f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel28.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts28);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel28.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3.4f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel29.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts29);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel29.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-3.4f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts30);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel30.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=-4.8f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel31.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts31);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel31.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=2.1f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel32.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts32);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel32.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=8f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts33);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel33.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=100f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel34.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts34);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel34.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=22f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel35.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts35);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel35.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=6f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel36.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts36);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel36.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=1.06f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel37.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts37);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel37.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=100f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel38.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts57);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel38.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=15f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel39.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts39);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel39.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=4.55f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel40.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts40);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel40.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=4.3f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel41.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts41);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel41.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight=10.05f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }

            }
        });

        fSel42.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts42);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel42.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 2.6f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel43.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts43);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel43.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 1.75f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts44);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel44.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 1.44f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel45.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts45);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel45.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 4f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel46.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts46);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel46.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 2.8f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel47.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts47);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel47.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 1.8f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel48.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts48);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel48.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 1.36f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel49.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts49);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel49.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 100f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts50);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel50.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = -100f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel51.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts51);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel51.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 0.94f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel52.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts52);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel52.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 1.18f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel53.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts53);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel53.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 0.94f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel54.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts54);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel54.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 1.05f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts55);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel55.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 1.95f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });

        fSel56.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Typeface type0 = ResourcesCompat.getFont(activity_next.this, R.font.hwfonts56);
                editText1.setTypeface(type0);
                defaultFontBackground();
                fSel56.setBackgroundResource(R.drawable.activity_next_bg);
                fontHeight = 1.82f;
                if(drawLines){
                    drawLines(fontHeight,fontSize);
                }
            }
        });



    }



    private void imageButton3_OnClickListener() {

        cBut0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fontColorSelected = 0;
                editText1.setTextColor(getResources().getColor(R.color.ColourBlack));
                defaultTextColorBackground();
                cBut0.setBackgroundResource(R.drawable.activity_next_bg);
            }
        });

        cBut1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fontColorSelected = 1;
                editText1.setTextColor(getResources().getColor(R.color.ColourGrey));
                defaultTextColorBackground();
                cBut1.setBackgroundResource(R.drawable.activity_next_bg);
            }
        });

        cBut2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fontColorSelected = 2;
                editText1.setTextColor(getResources().getColor(R.color.ColourBlue));
                defaultTextColorBackground();
                cBut2.setBackgroundResource(R.drawable.activity_next_bg);
            }
        });

        cBut3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fontColorSelected = 3;
                editText1.setTextColor(getResources().getColor(R.color.ColourDarkBlue));
                defaultTextColorBackground();
                cBut3.setBackgroundResource(R.drawable.activity_next_bg);
            }
        });

        cBut4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fontColorSelected = 4;
                editText1.setTextColor(getResources().getColor(R.color.ColourRed));
                defaultTextColorBackground();
                cBut4.setBackgroundResource(R.drawable.activity_next_bg);
            }
        });

        cBut5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fontColorSelected = 5;
                editText1.setTextColor(getResources().getColor(R.color.ColourDarkRed));
                defaultTextColorBackground();
                cBut5.setBackgroundResource(R.drawable.activity_next_bg);
            }
        });

        cBut6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fontColorSelected = 6;
                editText1.setTextColor(getResources().getColor(R.color.white));
                defaultTextColorBackground();
                cBut6.setBackgroundResource(R.drawable.activity_next_bg);
            }
        });

    }

    public void defaultFontBackground() {
        fSel0.setBackgroundResource(R.color.transparent);
        fSel1.setBackgroundResource(R.color.transparent);
        fSel2.setBackgroundResource(R.color.transparent);
        fSel3.setBackgroundResource(R.color.transparent);
        fSel4.setBackgroundResource(R.color.transparent);
        fSel5.setBackgroundResource(R.color.transparent);
        fSel6.setBackgroundResource(R.color.transparent);
        fSel7.setBackgroundResource(R.color.transparent);
        fSel8.setBackgroundResource(R.color.transparent);
        fSel9.setBackgroundResource(R.color.transparent);
        fSel10.setBackgroundResource(R.color.transparent);
        fSel11.setBackgroundResource(R.color.transparent);
        fSel12.setBackgroundResource(R.color.transparent);
        fSel13.setBackgroundResource(R.color.transparent);
        fsel14.setBackgroundResource(R.color.transparent);
        fsel15.setBackgroundResource(R.color.transparent);
        fSel16.setBackgroundResource(R.color.transparent);
        fSel17.setBackgroundResource(R.color.transparent);
        fSel18.setBackgroundResource(R.color.transparent);
        fSel19.setBackgroundResource(R.color.transparent);
        fSel20.setBackgroundResource(R.color.transparent);
        fSel21.setBackgroundResource(R.color.transparent);
        fSel22.setBackgroundResource(R.color.transparent);
        fSel23.setBackgroundResource(R.color.transparent);
        fSel24.setBackgroundResource(R.color.transparent);
        fSel25.setBackgroundResource(R.color.transparent);
        fSel26.setBackgroundResource(R.color.transparent);
        fSel27.setBackgroundResource(R.color.transparent);
        fSel28.setBackgroundResource(R.color.transparent);
        fSel29.setBackgroundResource(R.color.transparent);
        fSel30.setBackgroundResource(R.color.transparent);
        fSel31.setBackgroundResource(R.color.transparent);
        fSel32.setBackgroundResource(R.color.transparent);
        fSel33.setBackgroundResource(R.color.transparent);
        fSel34.setBackgroundResource(R.color.transparent);
        fSel35.setBackgroundResource(R.color.transparent);
        fSel36.setBackgroundResource(R.color.transparent);
        fSel37.setBackgroundResource(R.color.transparent);
        fSel38.setBackgroundResource(R.color.transparent);
        fSel39.setBackgroundResource(R.color.transparent);
        fSel40.setBackgroundResource(R.color.transparent);
        fSel41.setBackgroundResource(R.color.transparent);
        fSel42.setBackgroundResource(R.color.transparent);
        fSel43.setBackgroundResource(R.color.transparent);
        fSel44.setBackgroundResource(R.color.transparent);
        fSel45.setBackgroundResource(R.color.transparent);
        fSel46.setBackgroundResource(R.color.transparent);
        fSel47.setBackgroundResource(R.color.transparent);
        fSel48.setBackgroundResource(R.color.transparent);
        fSel49.setBackgroundResource(R.color.transparent);
        fSel50.setBackgroundResource(R.color.transparent);
        fSel50.setBackgroundResource(R.color.transparent);
        fSel51.setBackgroundResource(R.color.transparent);
        fSel52.setBackgroundResource(R.color.transparent);
        fSel53.setBackgroundResource(R.color.transparent);
        fSel54.setBackgroundResource(R.color.transparent);
        fSel55.setBackgroundResource(R.color.transparent);
        fSel56.setBackgroundResource(R.color.transparent);
    }

    public void defaultTextColorBackground() {
        cBut0.setBackgroundResource(R.color.transparent);
        cBut1.setBackgroundResource(R.color.transparent);
        cBut2.setBackgroundResource(R.color.transparent);
        cBut3.setBackgroundResource(R.color.transparent);
        cBut4.setBackgroundResource(R.color.transparent);
        cBut5.setBackgroundResource(R.color.transparent);
        cBut6.setBackgroundResource(R.color.transparent);

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 120 && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                Bitmap resizedBitmap =Bitmap.createScaledBitmap(bitmap,editText1.getWidth(),editText1.getHeight(),false);
                editText1.setBackground(new BitmapDrawable(getResources(),resizedBitmap));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }


        }
        if (requestCode == 100 && resultCode == RESULT_OK) {
            int actionPerformed = data.getExtras().getInt("actionPerformed");
            uris = data.getStringArrayListExtra("uris");

            if (actionPerformed == 1) {
                capturedImages.clear();
                numOfImages = uris.size();
                textView.setText("Pages : " + numOfImages);
            } else if (actionPerformed == 2) {
                capturedImages.clear();
                numOfImages = uris.size();
                textView.setText("Pages : " + numOfImages);
                exportButton.setVisibility(View.INVISIBLE);
                retake = true;
                retakeno = data.getExtras().getInt("retake");
            } else if (actionPerformed == 3) {
                textView.setText("pages : " + uris.size());
                capturedImages.clear();
            }
        }
    }

    private void setInvisible() {
        scrollView.setVisibility(View.INVISIBLE);
        linearLayout.setVisibility(View.INVISIBLE);
        fontLayoutClose.setVisibility(View.INVISIBLE);
        backgroundWallpaperClose.setVisibility(View.INVISIBLE);
        textColorScrollView.setVisibility(View.INVISIBLE);
        colorClose.setVisibility(View.INVISIBLE);
        linesLinearLayout.setVisibility(View.INVISIBLE);
    }

    private void setFalse() {
        bgImageScroller = false;
        fontStyleSelector = false;
        colorSelector = false;
        linesSelector = false;
    }

    public void onBackPressed() {
        if (retake == true) {
            retake = false;
            exportButton.setVisibility(View.VISIBLE);
            Intent finalIntent = new Intent(activity_next.this, FinalActivity.class);
            finalIntent.putExtra("capturedImages", uris);
            startActivityForResult(finalIntent, 100);
        } else {
            if (!capturedImage) {
                if (!fontStyleSelector & !colorSelector & !bgImageScroller & !linesSelector) {
                    Toast.makeText(this, "Click on Top-Left corner to perform back function", Toast.LENGTH_SHORT).show();
                } else if (!fontStyleSelector || !colorSelector || !bgImageScroller || !linesSelector) {
                    setInvisible();
                    setFalse();
                }
            } else if (capturedImage) {
                Toast.makeText(this, "Save or Discard the captured image", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setUI() {
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();

        ViewGroup.MarginLayoutParams paperparams = (ViewGroup.MarginLayoutParams) imageButton1.getLayoutParams();
        paperparams.width = (screenWidth * 20) / 100;
        imageButton1.setLayoutParams(paperparams);

        ViewGroup.MarginLayoutParams fontParams = (ViewGroup.MarginLayoutParams) imageButton2.getLayoutParams();
        fontParams.width = (screenWidth * 20) / 100;
        imageButton2.setLayoutParams(fontParams);

        ViewGroup.MarginLayoutParams colorParams = (ViewGroup.MarginLayoutParams) imageButton3.getLayoutParams();
        colorParams.width = (screenWidth * 20) / 100;
        imageButton3.setLayoutParams(colorParams);

        ViewGroup.MarginLayoutParams diaParams = (ViewGroup.MarginLayoutParams) imageButton4.getLayoutParams();
        diaParams.width = (screenWidth * 20) / 100;
        imageButton4.setLayoutParams(diaParams);

    }

    private void goToFinalize() {
        for (int i = 0; i < capturedImages.size(); i++) {
            try {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "Handwriter_" + timeStamp;
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File file = File.createTempFile(imageFileName, ".jpg", storageDir);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                capturedImages.get(i).compress(Bitmap.CompressFormat.JPEG, 50, fileOutputStream);
                uris.add(file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Intent finalIntent = new Intent(activity_next.this, FinalActivity.class);
        finalIntent.putExtra("capturedImages", uris);
        startActivityForResult(finalIntent, 100);
    }

    private void drawLines(float fontHeight, int fontSize){
        drawLines = true;
        int dist = (int)(fontSize+(fontSize/fontHeight));
        final int Eheight = lineView.getHeight();
        final int Ewidth = lineView.getWidth();
        Bitmap newBitmap = Bitmap.createBitmap(Ewidth, Eheight, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(newBitmap);
        Paint paint2 = new Paint();
        paint2.setColor(getResources().getColor(R.color.black));
        paint2.setStyle(Paint.Style.STROKE);
        paint2.setStrokeWidth(3);

        if (drawMargin) {
            dist2 = 90 + dist;
        } else {
            dist2 = 50 + dist;
        }
        for (int k = 0; k < Eheight / dist; k++) {

            canvas2.drawLine(0, dist2, Ewidth, dist2, paint2);
            dist2 = dist2 + dist;
        }
        lineView.setBackground(new BitmapDrawable(getResources(), newBitmap));
        lineBitmap = newBitmap;

    }

    private void eraseLines(){
        drawLines = false;
        final int Eheight = lineView.getHeight();
        final int Ewidth = lineView.getWidth();
        Bitmap newBitmap = Bitmap.createBitmap(Ewidth, Eheight, Bitmap.Config.ARGB_8888);
        Canvas canvas2 = new Canvas(newBitmap);
        lineView.setBackground(new BitmapDrawable(getResources(), newBitmap));
        lineBitmap = newBitmap;
    }

    private void captureImage(){
        if (!capturedImage) {
            CaptureButton.startAnimation(captureanim);
            setInvisible();
            setFalse();
            editText1.setCursorVisible(false);
            editText1.clearFocus();
            editText1.buildDrawingCache();
            Bitmap newBItmap1 = Bitmap.createBitmap(editText1.getDrawingCache());
            final int Eheight = lineView.getHeight();
            final int Ewidth = lineView.getWidth();
            Bitmap newBitmap = Bitmap.createBitmap(Ewidth, Eheight, Bitmap.Config.ARGB_8888);
            Canvas canvas2 = new Canvas(newBitmap);
            canvas2.drawBitmap(newBItmap1,0,0,null);
            if(drawLines){
                canvas2.drawBitmap(lineBitmap,0,0,null);
            }
            if(drawMargin){
                canvas2.drawBitmap(marginBitmap,0,0,null);
            }
            if (drawSideMargin){
                canvas2.drawBitmap(sideMarginBitmap,0,0,null);
            }

            saveBitmap = newBitmap;
            pickImage.setVisibility(View.VISIBLE);
            pickImage.setImageBitmap(saveBitmap);
            capturedAccept.setVisibility(View.VISIBLE);
            capturedDecline.setVisibility(View.VISIBLE);
            toolbarcaptured.setVisibility(View.VISIBLE);
            editText1.destroyDrawingCache();
            capturedImage = true;
        }
    }

    private void captureAccept(){
        capturedImages.add(saveBitmap);
        capturedAccept.setVisibility(View.INVISIBLE);
        capturedDecline.setVisibility(View.INVISIBLE);
        pickImage.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.VISIBLE);
        editText1.setCursorVisible(true);
        capturedImage = false;
        numOfImages++;
        textView.setText("Pages : " + numOfImages);
        toolbarcaptured.setVisibility(View.INVISIBLE);
        if (retake == true) {
            retake = false;
            exportButton.setVisibility(View.VISIBLE);
            try {
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String imageFileName = "Handwriter_" + timeStamp;
                File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                File file = File.createTempFile(imageFileName, ".jpg", storageDir);
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                saveBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                uris.set(retakeno - 1, file.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
            Intent finalIntent = new Intent(activity_next.this, FinalActivity.class);
            finalIntent.putExtra("capturedImages", uris);
            startActivityForResult(finalIntent, 100);
        }
    }

    private void captureDecline(){
        capturedAccept.setVisibility(View.INVISIBLE);
        capturedDecline.setVisibility(View.INVISIBLE);
        pickImage.setVisibility(View.INVISIBLE);
        editText1.setCursorVisible(true);
        capturedImage = false;
        toolbarcaptured.setVisibility(View.INVISIBLE);
    }

}

/*

                        <Button
                            android:id="@+id/your_font"
                            android:layout_width="wrap_content"
                            android:layout_height="30dp"
                            android:layout_gravity="center"
                            android:layout_marginLeft="5dp"
                            android:background="@drawable/white_stroke_outline"
                            android:drawableLeft="@drawable/add_pic"
                            android:fontFamily="@font/raleway_semibold"
                            android:paddingLeft="5dp"
                            android:text="add Your Font  "
                            android:textColor="@color/black"
                            android:textSize="15dp" />


 */

/*

    <Button
        android:id="@+id/imageButton1"
        android:layout_width="wrap_content"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:background="@drawable/lines2020"
        android:clickable="true"
        android:foreground="?android:selectableItemBackground"
        app:layout_constraintRight_toLeftOf="@id/imageButton2"
        app:layout_constraintBottom_toBottomOf="@id/tool_bar44"
        app:layout_constraintStart_toStartOf="@+id/tool_bar44"
        app:layout_constraintTop_toTopOf="@+id/tool_bar44" />

    <Button
        android:id="@+id/imageButton2"
        android:layout_width="wrap_content"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:background="@drawable/lines2020"
        android:foreground="?android:selectableItemBackground"
        app:layout_constraintBottom_toBottomOf="@+id/tool_bar44"
        app:layout_constraintStart_toEndOf="@+id/imageButton1"
        app:layout_constraintRight_toLeftOf="@id/textCaptureButton"
        app:layout_constraintTop_toBottomOf="@+id/edit_text_InNext" />


    <Button
        android:id="@+id/textCaptureButton"
        android:layout_width="wrap_content"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:background="@drawable/lines2020"
        android:foreground="?android:selectableItemBackground"
        app:layout_constraintBottom_toBottomOf="@+id/tool_bar44"
        app:layout_constraintEnd_toStartOf="@+id/imageButton3"
        app:layout_constraintStart_toEndOf="@+id/imageButton2"
        app:layout_constraintTop_toBottomOf="@+id/edit_text_InNext" />


    <Button
        android:id="@+id/imageButton3"
        android:layout_width="wrap_content"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:background="@drawable/lines2020"
        android:foreground="?android:selectableItemBackground"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toStartOf="@+id/imageButton4"
        app:layout_constraintTop_toTopOf="@+id/textCaptureButton"
        app:layout_constraintVertical_bias="0.0"
        app:layout_constraintRight_toRightOf="@id/textCaptureButton"
        tools:layout_editor_absoluteX="264dp" />

    <Button
        android:id="@+id/imageButton4"
        android:layout_width="wrap_content"
        android:layout_height="0dp"
        android:layout_weight="1"
        app:layout_constraintLeft_toRightOf="@id/imageButton3"
        android:background="@drawable/lines2020"
        android:foreground="?android:selectableItemBackground"
        app:layout_constraintBottom_toBottomOf="@id/tool_bar44"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="@id/tool_bar44" />


 */