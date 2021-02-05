package com.xsar.handwriter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

public class WalkThroughActivityInNext extends AppCompatActivity {

    View walkThroughView;
    Button walkThrough_text, imageButton1, imageButton2, imageButton3, imageButton4, captureButton;
    ImageButton backgroundWallpaperClose, fontLayoutClose, colorClose, capturedAccept, capturedDecline;
    LinearLayout next, clickHere1,click1BG, clickHere2, clickHere3 , click3BG, clickHere4, clickHere5, click5BG,clickHere6,
            clickHere7, clickHere8,click8BG,clickHere9, clickHere10,click10BG,clickHere11,     linesLinearLayout ;
    ImageView arrow1, arrow2, arrow3, arrow4, arrow5,arrow7,arrow8,arrow9,arrow10,arrow11,
            Dot1, Dot2, Dot3, Dot4, Dot5,Dot6,Dot7,Dot8,Dot9,Dot10,Dot11, pickImage;
    EditText editText1;
    Animation fadein, toBottom,fromBottom, rotateOpen, fromRight, fromLeft;
    public boolean capturedImage = false;
    LinearLayout linearLayout, exportButton;
    HorizontalScrollView scrollView, textColorScrollView, handWritingScrollView;
    Toolbar toolbarcaptured, toolbar44;
    CardView cardViewWalkthrough;
    Bitmap saveBitmap;
    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11;
    TextView stv1,stv2,stv3,stv4,stv5,stv6,stv7,stv8,stv9,stv10,stv11;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next);

        next = findViewById(R.id.export_btn);
        toolbar44 = findViewById(R.id.tool_bar44);
        cardViewWalkthrough = findViewById(R.id.cardViewWalkthrough);
        scrollView = findViewById(R.id.backgroundImageScroll);
        handWritingScrollView = findViewById(R.id.handWritingScrollView);
        textColorScrollView = findViewById(R.id.text_colour_ScrollView);
        linearLayout = findViewById(R.id.linearLayout_Font_Selector);
        toolbarcaptured = findViewById(R.id.toolbarcaptured);
        walkThrough_text =findViewById(R.id.walkThrough_text);
        pickImage = findViewById(R.id.Pick_Image_from_gallery);
        linesLinearLayout = (LinearLayout) findViewById(R.id.linesLinearLayout);

        backgroundWallpaperClose = findViewById(R.id.backGround_Wallpaper_close_Button);
        fontLayoutClose = findViewById(R.id.font_Layout_close_Button);
        colorClose = findViewById(R.id.Text_Color_Layout_close_Button);

        editText1 = findViewById(R.id.edit_text_InNext);
        capturedAccept = findViewById(R.id.captured_accept);
        capturedDecline = findViewById(R.id.captured_decline);

        imageButton1 = findViewById(R.id.imageButton1);
        imageButton2 = findViewById(R.id.imageButton2);
        imageButton3 = findViewById(R.id.imageButton3);
        imageButton4 = findViewById(R.id.imageButton4);
        captureButton = findViewById(R.id.textCaptureButton);

        clickHere1 = findViewById(R.id.clickHere1);
        click1BG = findViewById(R.id.clickHere1BG);
        clickHere2 = findViewById(R.id.clickHere2);
        clickHere3 = findViewById(R.id.clickHere3);
        click3BG = findViewById(R.id.clickHere3BG);
        clickHere4 = findViewById(R.id.clickHere4);
        clickHere5 = findViewById(R.id.clickHere5);
        click5BG = findViewById(R.id.clickHere5BG);
        clickHere6 = findViewById(R.id.clickHere6);
        clickHere7 = findViewById(R.id.clickHere7);
        clickHere8 = findViewById(R.id.clickHere8);
        click8BG = findViewById(R.id.clickHere8BG);
        clickHere9 = findViewById(R.id.clickHere9);
        clickHere10 = findViewById(R.id.clickHere10);
        click10BG = findViewById(R.id.clickHere10BG);
        clickHere11 = findViewById(R.id.clickHere11);

        tv1 = (TextView) findViewById(R.id.click_here);
        tv2 = (TextView) findViewById(R.id.click_here2);
        tv3 = (TextView) findViewById(R.id.click_here3);
        tv4 = (TextView) findViewById(R.id.click_here4);
        tv5 = (TextView) findViewById(R.id.click_here5);
        tv6 = (TextView) findViewById(R.id.click_here6);
        tv7 = (TextView) findViewById(R.id.click_here7);
        tv8 = (TextView) findViewById(R.id.click_here8);
        tv9 = (TextView) findViewById(R.id.click_here9);
        tv10 = (TextView) findViewById(R.id.click_here10);
        tv11 = (TextView) findViewById(R.id.click_here11);

        stv1 = (TextView) findViewById(R.id.to_Open_Menu);
        stv2 = (TextView) findViewById(R.id.to_Open_Menu2);
        stv3 = (TextView) findViewById(R.id.to_Open_Menu3);
        stv4 = (TextView) findViewById(R.id.to_Open_Menu4);
        stv5 = (TextView) findViewById(R.id.to_Open_Menu5);
        stv6 = (TextView) findViewById(R.id.to_Open_Menu6);
        stv7 = (TextView) findViewById(R.id.to_Open_Menu7);
        stv8 = (TextView) findViewById(R.id.to_Open_Menu8);
        stv9 = (TextView) findViewById(R.id.to_Open_Menu9);
        stv10 = (TextView) findViewById(R.id.to_Open_Menu10);
        stv11 = (TextView) findViewById(R.id.to_Open_Menu11);

        Dot1 = findViewById(R.id.textBgDot);
        Dot2 = findViewById(R.id.textBgDot2);
        Dot3 = findViewById(R.id.textBgDot3);
        Dot4 = findViewById(R.id.textBgDot4);
        Dot5 = findViewById(R.id.textBgDot5);
        Dot6 = findViewById(R.id.textBgDot6);
        Dot7 = findViewById(R.id.textBgDot7);
        Dot8 = findViewById(R.id.textBgDot8);
        Dot9 = findViewById(R.id.textBgDot9);
        Dot10 = findViewById(R.id.textBgDot10);
        Dot11 = findViewById(R.id.textBgDot11);

        fadein = AnimationUtils.loadAnimation(WalkThroughActivityInNext.this, R.anim.fadein);
        toBottom = AnimationUtils.loadAnimation(WalkThroughActivityInNext.this, R.anim.to_bottom_original_size);
        fromBottom = AnimationUtils.loadAnimation(WalkThroughActivityInNext.this, R.anim.from_bottom_anim);
        fromRight = AnimationUtils.loadAnimation(WalkThroughActivityInNext.this, R.anim.enter_from_right);
        fromLeft = AnimationUtils.loadAnimation(WalkThroughActivityInNext.this, R.anim.enter_from_left);
        rotateOpen = AnimationUtils.loadAnimation(WalkThroughActivityInNext.this, R.anim.rotate_open_anim);

        arrow1 = findViewById(R.id.indicator_arrow1);
        arrow2 = findViewById(R.id.indicator_arrow2);
        arrow3 = findViewById(R.id.indicator_arrow3);
        arrow4 = findViewById(R.id.indicator_arrow4);
        arrow5 = findViewById(R.id.indicator_arrow5);
        arrow7 = findViewById(R.id.indicator_arrow7);
        arrow8 = findViewById(R.id.indicator_arrow8);
        arrow9 = findViewById(R.id.indicator_arrow9);
        arrow10 = findViewById(R.id.indicator_arrow10);
        arrow11 = findViewById(R.id.indicator_arrow11);

        homeUI();
        walkThroughUI();

        walkThroughView = findViewById(R.id.walkThroughInNext);
        walkThroughView.setVisibility(View.VISIBLE);
        walkThroughView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        imageButton1.setVisibility(View.INVISIBLE);
        imageButton2.setVisibility(View.INVISIBLE);
        imageButton3.setVisibility(View.INVISIBLE);
        imageButton4.setVisibility(View.INVISIBLE);
        captureButton.setVisibility(View.INVISIBLE);
        toolbar44.setVisibility(View.INVISIBLE);

        final TranslateAnimation animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, 40.0f);
        animation.setDuration(1000);
        animation.setRepeatCount(Animation.INFINITE);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                editText1.setText("AN OVERVIEW ON HOW TO USE...\n" +
                        "\n" +
                        "Here you can convert text to handwritten form by directly capturing Pictures or by importing them from Gallery.\n "+
                        "\n" + "\n" +
                        "SELECT\n" +
                        "\n" +
                        "Camera : Capture clear image of printed text\n" +
                        "Open Text File : Open .txt Files from External Storage \n"+
                        "Create Text File : Type or Copy/Paste the text you want \n"+
                        "Import from Gallery : On Top-Right Corner, Click Gallery Icon to import Images from the Gallery \n"+
                        "\n" +"\n" +

                        "GET TEXT FROM IMAGES \n"+
                        "\n"
                        + "Copy recognized text from all images, Edit and paste it in a single text file \n"+
                        "\n" + "\n"+
                        "GET FILES FROM PHONE\n"+
                        "\n" +
                        "Text file selected from the phone will be displayed here or edited text file from images will be displayed here, " +
                        "You can save the text file for future use or go to final Activity.  \n"+"\n" +"\n" +

                        "FINAL ACTIVITY.\n"+"\n" +
                        "* Select your desired paper for background.\n" +
                        "* Choose your favourite handwriting.\n" +
                        "* Adjust the text size. \n" +
                        "* Choose Ink color. \n"+
                        "* Import and add hand drawn diagram from gallery in\n" +
                        "  selected ink color at the cursor position.\n"+
                        "* Scroll and Capture the images of final text.\n"+
                        "* Save or Cancel the captured image.\n"+
                        "* Finally Export as Pdf File.\n"
                );
                toolbar44.startAnimation(fadein);
                toolbar44.setVisibility(View.VISIBLE);

                imageButton1.setVisibility(View.VISIBLE);
                imageButton2.setVisibility(View.VISIBLE);
                imageButton3.setVisibility(View.VISIBLE);
                imageButton4.setVisibility(View.VISIBLE);
                captureButton.setVisibility(View.VISIBLE);

                arrow1.startAnimation(animation);
                arrow1.setVisibility(View.VISIBLE);

                cardViewWalkthrough.setVisibility(View.VISIBLE);
                walkThrough_text.startAnimation(fadein);
                walkThrough_text.setVisibility(View.VISIBLE);

                imageButton2.setClickable(false);
                imageButton3.setClickable(false);
                imageButton4.setClickable(false);
                captureButton.setClickable(false);
            }
        }, 1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Dot1.startAnimation(fromRight);
                Dot1.setVisibility(View.VISIBLE);
                clickHere1.startAnimation(fromRight);
                clickHere1.setVisibility(View.VISIBLE);
                click1BG.startAnimation(fromRight);
                click1BG.setVisibility(View.VISIBLE);
            }
        }, 1250);


        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrow1.clearAnimation();
                arrow1.setVisibility(View.GONE);
                Dot1.setVisibility(View.GONE);
                clickHere1.setVisibility(View.GONE);
                click1BG.setVisibility(View.GONE);
                scrollView.setVisibility(View.VISIBLE);
                backgroundWallpaperClose.setVisibility(View.VISIBLE);

                scrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_RIGHT);
                    }
                }, 1000);

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        editText1.setBackground(getResources().getDrawable(R.drawable.paper_background_3));
                        scrollView.setVisibility(View.INVISIBLE);
                        backgroundWallpaperClose.setVisibility(View.INVISIBLE);

                        walkThrough_text.setText("Good, Now");
                        arrow2.startAnimation(animation);
                        arrow2.setVisibility(View.VISIBLE);

                        imageButton1.setEnabled(false);
                        imageButton2.setClickable(true);
                        imageButton3.setClickable(false);
                        imageButton4.setClickable(false);
                        captureButton.setClickable(false);
                    }
                }, 3000);

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dot2.startAnimation(fromLeft);
                        Dot2.setVisibility(View.VISIBLE);
                        clickHere2.startAnimation(fromLeft);
                        clickHere2.setVisibility(View.VISIBLE);
                    }
                }, 3250);
            }
        });







        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrow2.clearAnimation();
                arrow2.setVisibility(View.GONE);
                Dot2.setVisibility(View.GONE);
                clickHere2.setVisibility(View.GONE);
                        linearLayout.setVisibility(View.VISIBLE);
                        fontLayoutClose.setVisibility(View.VISIBLE);

                handWritingScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handWritingScrollView.fullScroll(ScrollView.FOCUS_RIGHT);
                    }
                }, 1000);

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        editText1.setTextSize(20);

                        Typeface type0 = ResourcesCompat.getFont(WalkThroughActivityInNext.this, R.font.handwriting_03);
                        editText1.setTypeface(type0);

                        linearLayout.setVisibility(View.INVISIBLE);
                        fontLayoutClose.setVisibility(View.INVISIBLE);

                        walkThrough_text.setText("Excellent, Now");
                        arrow4.startAnimation(animation);
                        arrow4.setVisibility(View.VISIBLE);

                        imageButton2.setEnabled(false);
                        imageButton3.setClickable(true);
                        imageButton4.setClickable(false);
                        captureButton.setClickable(false);
                    }
                }, 3000);

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dot3.startAnimation(fromRight);
                        Dot3.setVisibility(View.VISIBLE);
                        clickHere3.startAnimation(fromRight);
                        clickHere3.setVisibility(View.VISIBLE);
                        click3BG.startAnimation(fromRight);
                        click3BG.setVisibility(View.VISIBLE);
                    }
                }, 3250);
                }
        });


        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrow4.clearAnimation();
                arrow4.setVisibility(View.GONE);
                Dot3.setVisibility(View.GONE);
                clickHere3.setVisibility(View.GONE);
                click3BG.setVisibility(View.GONE);
                textColorScrollView.setVisibility(View.VISIBLE);
                colorClose.setVisibility(View.VISIBLE);

                textColorScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        textColorScrollView.fullScroll(ScrollView.FOCUS_RIGHT);
                    }
                }, 1000);

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        editText1.setTextColor(getResources().getColor(R.color.ColourBlue));
                        textColorScrollView.setVisibility(View.INVISIBLE);
                        colorClose.setVisibility(View.INVISIBLE);

                        walkThrough_text.setText("Perfect, Now");
                        arrow5.startAnimation(animation);
                        arrow5.setVisibility(View.VISIBLE);

                        imageButton3.setEnabled(false);
                        imageButton4.setClickable(true);
                        captureButton.setClickable(false);
                    }
                }, 3000);

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dot4.startAnimation(fromLeft);
                        Dot4.setVisibility(View.VISIBLE);
                        clickHere4.startAnimation(fromLeft);
                        clickHere4.setVisibility(View.VISIBLE);
                    }
                }, 3250);

            }
        });






        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrow5.clearAnimation();
                arrow5.setVisibility(View.GONE);
                Dot4.setVisibility(View.GONE);
                clickHere4.setVisibility(View.GONE);
                imageButton4.setEnabled(false);
                        linesLinearLayout.setVisibility(View.VISIBLE);

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        walkThrough_text.setText("Awesome, Now");
                        Dot7.startAnimation(fromLeft);
                        Dot7.setVisibility(View.VISIBLE);
                        clickHere7.startAnimation(fromLeft);
                        clickHere7.setVisibility(View.VISIBLE);
                        arrow7.startAnimation(animation);
                        arrow7.setVisibility(View.VISIBLE);
                    }
                }, 1000);

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dot7.setVisibility(View.GONE);
                        clickHere7.setVisibility(View.GONE);
                        arrow7.clearAnimation();
                        arrow7.setVisibility(View.GONE);
                    }
                }, 5000);

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        arrow8.startAnimation(animation);
                        arrow8.setVisibility(View.VISIBLE);
                        Dot8.startAnimation(fromRight);
                        Dot8.setVisibility(View.VISIBLE);
                        clickHere8.startAnimation(fromRight);
                        clickHere8.setVisibility(View.VISIBLE);
                        click8BG.startAnimation(fromRight);
                        click8BG.setVisibility(View.VISIBLE);

                    }
                }, 5250);

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dot8.setVisibility(View.GONE);
                        clickHere8.setVisibility(View.GONE);
                        click8BG.setVisibility(View.INVISIBLE);
                        arrow8.clearAnimation();
                        arrow8.setVisibility(View.GONE);
                    }
                }, 10000);

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        arrow9.startAnimation(animation);
                        arrow9.setVisibility(View.VISIBLE);
                        Dot9.startAnimation(fromLeft);
                        Dot9.setVisibility(View.VISIBLE);
                        clickHere9.startAnimation(fromLeft);
                        clickHere9.setVisibility(View.VISIBLE);
                    }
                }, 10250);

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dot9.setVisibility(View.GONE);
                        clickHere9.setVisibility(View.GONE);
                        arrow9.clearAnimation();
                        arrow9.setVisibility(View.GONE);
                    }
                }, 15000);

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        arrow10.startAnimation(animation);
                        arrow10.setVisibility(View.VISIBLE);
                        Dot10.startAnimation(fromRight);
                        Dot10.setVisibility(View.VISIBLE);
                        clickHere10.startAnimation(fromRight);
                        clickHere10.setVisibility(View.VISIBLE);
                        click10BG.startAnimation(fromRight);
                        click10BG.setVisibility(View.VISIBLE);
                    }
                }, 15250);

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dot10.setVisibility(View.GONE);
                        clickHere10.setVisibility(View.GONE);
                        click10BG.setVisibility(View.GONE);
                        arrow10.clearAnimation();
                        arrow10.setVisibility(View.GONE);
                    }
                }, 20000);

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        arrow11.startAnimation(animation);
                        arrow11.setVisibility(View.VISIBLE);
                        Dot11.startAnimation(fromLeft);
                        Dot11.setVisibility(View.VISIBLE);
                        clickHere11.startAnimation(fromLeft);
                        clickHere11.setVisibility(View.VISIBLE);
                    }
                }, 20250);

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dot11.setVisibility(View.GONE);
                        clickHere11.setVisibility(View.GONE);
                        arrow11.clearAnimation();
                        arrow11.setVisibility(View.GONE);
                        linesLinearLayout.setVisibility(View.INVISIBLE);

                    }
                }, 25250);

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //walkThrough_text.setText("You are ready to go");
                        arrow3.startAnimation(animation);
                        arrow3.setVisibility(View.VISIBLE);
                    }
                }, 25500);


                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        captureButton.setClickable(true);
                        Dot5.startAnimation(fromRight);
                        Dot5.setVisibility(View.VISIBLE);
                        clickHere5.startAnimation(fromRight);
                        clickHere5.setVisibility(View.VISIBLE);
                        click5BG.startAnimation(fromRight);
                        click5BG.setVisibility(View.VISIBLE);
                    }
                }, 25750);



            }
        });

        captureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                arrow3.clearAnimation();
                arrow3.setVisibility(View.GONE);
                cardViewWalkthrough.setVisibility(View.GONE);
                Dot5.setVisibility(View.GONE);
                clickHere5.setVisibility(View.GONE);
                click5BG.setVisibility(View.GONE);

                editText1.setCursorVisible(false);
                editText1.buildDrawingCache();
                saveBitmap = Bitmap.createBitmap(editText1.getDrawingCache());
                pickImage.setVisibility(View.VISIBLE);
                pickImage.setImageBitmap(saveBitmap);
                capturedAccept.setVisibility(View.VISIBLE);
                capturedDecline.setVisibility(View.VISIBLE);
                toolbarcaptured.setVisibility(View.VISIBLE);
                editText1.destroyDrawingCache();
                capturedImage = true;

                captureButton.setEnabled(false);

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dot6.startAnimation(fromLeft);
                        Dot6.setVisibility(View.VISIBLE);
                        clickHere6.startAnimation(fromLeft);
                        clickHere6.setVisibility(View.VISIBLE);
                    }
                }, 3000);

                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 8000);

            }
        });




    }

    public void walkThroughUI() {
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();

        viewUI(clickHere1,tv1,stv1,70,30);

        viewUI(clickHere2,tv2,stv2,70,30);

        viewUI(clickHere3,tv3,stv3,70,30);

        viewUI(clickHere4,tv4,stv4,70,30);

        viewUI(clickHere5,tv5,stv5,70,30);

        viewUI(clickHere6,tv6,stv6,70,30);

        viewUI(clickHere7,tv7,stv7,70,30);

        viewUI(clickHere8,tv8,stv8,70, 30);

        viewUI(clickHere9,tv9,stv9,70, 30);

        viewUI(clickHere10,tv10,stv10,70, 30);

        viewUI(clickHere11,tv11,stv11,70,30);

        CardView.MarginLayoutParams click11 = (CardView.MarginLayoutParams) click1BG.getLayoutParams();
        click11.width = (screenWidth * 70) / 100;
        click11.height = (screenWidth * 30) / 100;
        click1BG.setLayoutParams(click11);

        CardView.MarginLayoutParams click33 = (CardView.MarginLayoutParams) click3BG.getLayoutParams();
        click33.width = (screenWidth * 70) / 100;
        click33.height = (screenWidth * 30) / 100;
        click3BG.setLayoutParams(click33);

        CardView.MarginLayoutParams click55 = (CardView.MarginLayoutParams) click5BG.getLayoutParams();
        click55.width = (screenWidth * 70) / 100;
        click55.height = (screenWidth * 30) / 100;
        click5BG.setLayoutParams(click55);

        CardView.MarginLayoutParams click88 = (CardView.MarginLayoutParams) click8BG.getLayoutParams();
        click88.width = (screenWidth * 70) / 100;
        click88.height = (screenWidth * 30) / 100;
        click8BG.setLayoutParams(click88);

        CardView.MarginLayoutParams click1010 = (CardView.MarginLayoutParams) click10BG.getLayoutParams();
        click1010.width = (screenWidth * 70) / 100;
        click1010.height = (screenWidth * 30) / 100;
        click10BG.setLayoutParams(click1010);


    }

    private void viewUI(View view, TextView textView, TextView textView2, int width, int height){
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();

        CardView.MarginLayoutParams click = (CardView.MarginLayoutParams) view.getLayoutParams();
        click.width = (screenWidth * width) / 100;
        click.height = (screenWidth * height) / 100;
        view.setLayoutParams(click);
        textView.setTextSize((screenWidth*3)/100);
        textView2.setTextSize((screenWidth*2)/80);
    }

        private void homeUI() {
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();

        CardView.MarginLayoutParams cardParams = (CardView.MarginLayoutParams) cardViewWalkthrough.getLayoutParams();
        cardParams.width = (screenWidth * 94) / 100;
        cardParams.height = (screenWidth * 47) / 100;
        cardViewWalkthrough.setLayoutParams(cardParams);

    }

    @Override
    public void onBackPressed() {

    }


}