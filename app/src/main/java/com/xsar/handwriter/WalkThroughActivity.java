package com.xsar.handwriter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class WalkThroughActivity extends AppCompatActivity {


    FloatingActionButton Dfab1, Dfab2, Dfab3, Dfab4, Dfab5;
    View view;
    Button skipButt;
    TextView mfabtext2, mfabtext3, mfabtext4, mfabtext5,
            clickHere, toOpenMenu;
    ImageView arrow1, arrow2, arrow3, arrow4, arrow5;
    VideoView video;
    CardView cardViewWalkthrough;
    Button getStarted;
    private Animation fromBottom, rotateOpen, fromRight, fadein, fromLeft;
    TextView tv1,tv2,tv3,tv4,tv5;
    TextView stv1,stv2,stv3,stv4,stv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk_through);

        Toolbar toolBar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolBar);
        toolBar.setTitleTextColor(Color.WHITE);
        toolBar.setNavigationIcon(R.drawable.menu_icon);

        fromBottom = AnimationUtils.loadAnimation(WalkThroughActivity.this, R.anim.from_bottom_anim);
        fadein = AnimationUtils.loadAnimation(WalkThroughActivity.this, R.anim.fadein);
        fromRight = AnimationUtils.loadAnimation(WalkThroughActivity.this, R.anim.enter_from_right);
        fromLeft = AnimationUtils.loadAnimation(WalkThroughActivity.this, R.anim.enter_from_left);
        rotateOpen = AnimationUtils.loadAnimation(WalkThroughActivity.this, R.anim.rotate_open_anim);

        final TranslateAnimation animation = new TranslateAnimation(0.0f, 20.0f, 0.0f, 0.0f);
        animation.setDuration(1000);
        animation.setRepeatCount(Animation.INFINITE);

        video = findViewById(R.id.videoView);
        view = findViewById(R.id.walkThrough);
        getStarted = findViewById(R.id.walkThrough_text);
        clickHere = findViewById(R.id.click_here);
        toOpenMenu = findViewById(R.id.to_Open_Menu);
        cardViewWalkthrough = findViewById(R.id.cardViewWalkthrough);
        skipButt = findViewById(R.id.skipButton);

        arrow1 = findViewById(R.id.indicator_arrow1);
        arrow2 = findViewById(R.id.indicator_arrow2);
        arrow3 = findViewById(R.id.indicator_arrow3);
        arrow4 = findViewById(R.id.indicator_arrow4);
        arrow5 = findViewById(R.id.indicator_arrow5);

        tv1 = (TextView) findViewById(R.id.click_here);
        tv2 = (TextView) findViewById(R.id.click_here2);
        tv3 = (TextView) findViewById(R.id.click_here3);
        tv4 = (TextView) findViewById(R.id.click_here4);
        tv5 = (TextView) findViewById(R.id.click_here5);

        stv1 = (TextView) findViewById(R.id.to_Open_Menu);
        stv2 = (TextView) findViewById(R.id.to_Open_Menu2);
        stv3 = (TextView) findViewById(R.id.to_Open_Menu3);
        stv4 = (TextView) findViewById(R.id.to_Open_Menu4);
        stv5 = (TextView) findViewById(R.id.to_Open_Menu5);

        mfabtext2 = findViewById(R.id.fab_text2);
        mfabtext3 = findViewById(R.id.fab_text3);
        mfabtext4 = findViewById(R.id.fab_text4);
        mfabtext5 = findViewById(R.id.fab_text5);

        Dfab1 = findViewById(R.id.DummyfloatingActionButton1);
        Dfab2 = findViewById(R.id.DummyfloatingActionButton2);
        Dfab3 = findViewById(R.id.DummyfloatingActionButton3);
        Dfab4 = findViewById(R.id.DummyfloatingActionButton4);
        Dfab5 = findViewById(R.id.DummyfloatingActionButton5);

        homeUI();

        Dfab1.setVisibility(View.INVISIBLE);
        view.setVisibility(View.VISIBLE);

        skipButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                getStarted.startAnimation(fadein);
                getStarted.setVisibility(View.VISIBLE);

                cardViewWalkthrough.setVisibility(View.VISIBLE);

            }
        }, 1000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                view.setBackgroundColor(Color.parseColor("#ccffffff"));
                Dfab1.setVisibility(View.VISIBLE);

                arrow1.startAnimation(animation);
                arrow1.setVisibility(View.VISIBLE);
                skipButt.setVisibility(View.VISIBLE);

                getStarted.setText(R.string.main_menu);

            }
        }, 2500);


        Dfab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStarted.setText(R.string.open_camera);

                DummyonAddButtonClicked();
                arrow1.clearAnimation();
                arrow1.setVisibility(View.GONE);

                arrow2.startAnimation(animation);
                arrow2.setVisibility(View.VISIBLE);

                Dfab1.setEnabled(false);
                Dfab2.setClickable(false);
                Dfab3.setClickable(false);
                Dfab4.setClickable(false);
                Dfab5.setClickable(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 250);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dfab2.setClickable(true);
                    }
                }, 500);
            }
        });

        Dfab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStarted.setText(R.string.open_gallery);

                arrow2.clearAnimation();
                arrow2.setVisibility(View.GONE);

                arrow3.startAnimation(animation);
                arrow3.setVisibility(View.VISIBLE);

                Dfab2.setEnabled(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 250);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dfab3.setClickable(true);
                    }
                }, 500);


            }
        });

        Dfab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStarted.setText(R.string.open_storage);

                arrow3.clearAnimation();
                arrow3.setVisibility(View.GONE);

                arrow4.startAnimation(animation);
                arrow4.setVisibility(View.VISIBLE);

                Dfab3.setEnabled(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 250);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dfab4.setClickable(true);
                    }
                }, 500);
            }
        });

        Dfab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStarted.setText(R.string.create_file);

                arrow4.clearAnimation();
                arrow4.setVisibility(View.GONE);

                arrow5.startAnimation(animation);
                arrow5.setVisibility(View.VISIBLE);

                Dfab4.setEnabled(false);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 250);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Dfab5.setClickable(true);
                    }
                }, 500);
            }
        });

        Dfab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStarted.setText("Excellent");

                arrow5.clearAnimation();
                arrow5.setVisibility(View.GONE);

                Dfab5.setEnabled(false);


                Handler handler2 = new Handler();
                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getStarted.setText("You are ready to go...");

                        video.setVisibility(View.VISIBLE);
                        String path = "android.resource://" + getPackageName() + "/" + R.raw.success_icon;
                        video.setVideoURI(Uri.parse(path));
                        video.setZOrderOnTop(true);
                        video.start();

                        mfabtext2.setVisibility(View.VISIBLE);
                        mfabtext3.setVisibility(View.VISIBLE);
                        mfabtext4.setVisibility(View.VISIBLE);
                        mfabtext5.setVisibility(View.VISIBLE);
                    }
                }, 1000);

                handler2.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 5000);

            }
        });


    }



    private void viewUI(TextView textView, TextView textView2){
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();

        textView.setTextSize((screenWidth*3)/100);
        textView2.setTextSize((screenWidth*2)/80);
    }


    private void DummyonAddButtonClicked() {
        Dfab2.setVisibility(View.VISIBLE);
        Dfab3.setVisibility(View.VISIBLE);
        Dfab4.setVisibility(View.VISIBLE);
        Dfab5.setVisibility(View.VISIBLE);

        Dfab1.startAnimation(rotateOpen);
        Dfab2.startAnimation(fromBottom);
        Dfab3.startAnimation(fromBottom);
        Dfab4.startAnimation(fromBottom);
        Dfab5.startAnimation(fromBottom);

        Dfab2.setClickable(true);
        Dfab3.setClickable(true);
        Dfab4.setClickable(true);
        Dfab5.setClickable(true);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public void onBackPressed() {

    }
}



