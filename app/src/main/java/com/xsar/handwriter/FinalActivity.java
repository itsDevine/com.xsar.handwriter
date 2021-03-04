package com.xsar.handwriter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
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
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class FinalActivity extends AppCompatActivity {

    ArrayList<Bitmap> capturedImages;
    ArrayList<String> uris, uris2;
    GridView images;
    int selected, totalImages, saveas;
    private ImageView finalImageView,finalDiagramView;
    private Button finalDiagram, finalAddButton, finalDeleteButton, finalRetakeButton,
            finalRenameOk, finalRenameCancel, exportPdf, exportImages,
            diagramClose,diagramResize,diagramOk,originalDiagram,inkDiagram;
    private ImageButton finalAdd1,eraseLayoutClose;
    private Toolbar flToolbar,flToolbar1;
    private EditText finalFilename;
    private boolean imageOpen = false, exportPressed = false, renameLayout = false, pasting = false;
    LinearLayout finalExportButton, exportLayout, finalRenameLayout, eraseLayout,eraseDiagram,paintDiagram;
    View renameBg, exportBg;
    HorizontalScrollView inkColorScroll;
    Button inkBlack,inkGrey,inkBlue,inkDarkBlue,inkRed,inkDarkRed,paintButton,eraseButton;
    Bitmap coloredBitmap,inkBitmap,originalBitmap,changeabliBitmap;
    Animation fromBottom, toBottom, fadeIn2, fadeOut2, fadeIn, fadeOut;
    int diagramAction=0, brushOpacity = 10, brushSizeValue = 20,imageWidth,imageHeight;
    SeekBar brushHardness,brushSize;
    boolean finished = true, erase = true;
    AnimationDrawable anim;
    ImageView loading;
    View loadingBg;
    TextView loadingText;

    int nx,ny;
    private InterstitialAd mInterstitialAd;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);

        findViewbyId();
        homeUI();
        adshow();

        finalImageView.setOnTouchListener(new FinalActivity.OnSwipeTouchListener(this));
        finalImageView.setVisibility(View.INVISIBLE);

        finalAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMoreImages();
            }
        });

        finalDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delete();
            }
        });

        finalRetakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                retake();
            }
        });

        finalExportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                export();
            }
        });

        exportPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportAsPdf();
            }
        });

        exportImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportAsImages();
            }
        });

        finalRenameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        finalRenameCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renameCancel();
            }
        });

        finalRenameOk.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View view) {
                exportAsWhatever();
                renameOk();
            }
        });


        renameBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renameBg();
            }
        });

        exportBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exportBg();
            }
        });

        diagramResize.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction()==MotionEvent.ACTION_MOVE);{
                    /*ViewGroup.MarginLayoutParams resizeparams = new ViewGroup.MarginLayoutParams(diagramResize.getWidth(),diagramResize.getHeight());
                    //resizeparams.setMargins((int)(motionEvent.getRawX() - (diagramResize.getWidth()/2)),(int)(motionEvent.getRawY()-(diagramResize.getHeight()/2)),0,0);
                    diagramResize.setLayoutParams(resizeparams);*/
                    int ivPosX = (int) finalDiagramView.getX();
                    int ivPosY = (int) finalDiagramView.getY();
                    int limitx = ivPosX + 300;
                    int limity = ivPosY + 300;


                    if (motionEvent.getRawX() > limitx && motionEvent.getRawY() > limity && finalDiagramView.getY() > finalImageView.getY() && diagramResize.getY() < (flToolbar.getY()
                    )) {
                        diagramResize.setX((int) (motionEvent.getRawX() - (diagramResize.getWidth() / 2)));
                        diagramResize.setY((int) (motionEvent.getRawY() - (diagramResize.getHeight() )));
                        ViewGroup.LayoutParams ivParams = (ViewGroup.LayoutParams) finalDiagramView.getLayoutParams();
                        ivParams.width = (int) motionEvent.getRawX() - ivPosX + (diagramResize.getWidth() / 2);
                        ivParams.height = (int) motionEvent.getRawY() - ivPosY - (diagramResize.getHeight());
                        finalDiagramView.setLayoutParams(ivParams);

                    } else if (motionEvent.getRawX() > limitx && motionEvent.getRawY() < limity) {
                        diagramResize.setX((int) (motionEvent.getRawX() - (diagramResize.getWidth() / 2)));
                        ViewGroup.LayoutParams ivParams = (ViewGroup.LayoutParams) finalDiagramView.getLayoutParams();
                        ivParams.width = (int) motionEvent.getRawX() - ivPosX - (diagramResize.getWidth() / 2);
                        finalDiagramView.setLayoutParams(ivParams);
                    } else if (motionEvent.getRawX() < limitx && motionEvent.getRawY() > limity) {
                        diagramResize.setY((int) (motionEvent.getRawY() - (diagramResize.getHeight())));
                        ViewGroup.LayoutParams ivParams = (ViewGroup.LayoutParams) finalDiagramView.getLayoutParams();
                        ivParams.height = (int) motionEvent.getRawY() - ivPosY - (diagramResize.getHeight());
                        finalDiagramView.setLayoutParams(ivParams);
                    } else if(diagramResize.getY()>flToolbar.getY() && motionEvent.getRawY()<flToolbar.getY()){
                        diagramResize.setX((int) (motionEvent.getRawX() - (diagramResize.getWidth() / 2)));
                        diagramResize.setY((int) (motionEvent.getRawY() - (diagramResize.getHeight() )));
                        ViewGroup.LayoutParams ivParams = (ViewGroup.LayoutParams) finalDiagramView.getLayoutParams();
                        ivParams.width = (int) motionEvent.getRawX() - ivPosX + (diagramResize.getWidth() / 2);
                        ivParams.height = (int) motionEvent.getRawY() - ivPosY - (diagramResize.getHeight());
                        finalDiagramView.setLayoutParams(ivParams);
                    } else if(finalDiagramView.getY() < finalImageView.getY() && motionEvent.getRawY()<diagramResize.getY() && motionEvent.getRawY()>limity ){
                        diagramResize.setX((int) (motionEvent.getRawX() - (diagramResize.getWidth() / 2)));
                        diagramResize.setY((int) (motionEvent.getRawY() - (diagramResize.getHeight() )));
                        ViewGroup.LayoutParams ivParams = (ViewGroup.LayoutParams) finalDiagramView.getLayoutParams();
                        ivParams.width = (int) motionEvent.getRawX() - ivPosX + (diagramResize.getWidth() / 2);
                        ivParams.height = (int) motionEvent.getRawY() - ivPosY - (diagramResize.getHeight());
                        finalDiagramView.setLayoutParams(ivParams);
                    }

                }
                return true;
            }
        });

        finalDiagramView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {



                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    nx = (int)motionEvent.getX();
                    ny = (int)motionEvent.getY();
                }

                if(motionEvent.getAction() == MotionEvent.ACTION_MOVE) {


                    if(diagramAction==1){
                        int pointX1 = (int)(motionEvent.getX()-brushSizeValue);
                        int pointY1 = (int) (motionEvent.getY()-brushSizeValue);
                        int pointX2 = (int) (motionEvent.getX()+brushSizeValue);
                        int pointY2 = (int) (motionEvent.getY()+brushSizeValue);
                        if(erase){
                            if(finished){
                                for(int ed = pointX1; ed <= pointX2;ed++){
                                    for(int ed2 = pointY1; ed2 <= pointY2;ed2++){
                                        if(ed>=0 && ed<changeabliBitmap.getWidth() && ed2>=0 && ed2<changeabliBitmap.getHeight()) {
                                            int pixel = changeabliBitmap.getPixel(ed, ed2);
                                            int red = Color.red(pixel);
                                            int blue = Color.blue(pixel);
                                            int green = Color.green(pixel);
                                            int alpha = Color.alpha(pixel);
                                            if(brushOpacity == 100){
                                                changeabliBitmap.setPixel(ed, ed2, Color.argb(0, 0, 0, 0));
                                            } else {
                                                alpha-=brushOpacity;
                                                red-=(int)(red/brushOpacity);
                                                green-=(int)(green/brushOpacity);
                                                blue-=(int)(blue/brushOpacity);

                                                if(alpha<=0){
                                                    alpha=0;red=0; green=0; blue=0;
                                                }
                                                if(red<0){red=0;}
                                                if(green<0){green=0;}
                                                if(blue<0){blue=0;}

                                                changeabliBitmap.setPixel(ed, ed2, Color.argb(alpha, red, green, blue));
                                            }

                                        }

                                    }
                                }
                                finalDiagramView.setBackground(new BitmapDrawable(getResources(),changeabliBitmap));
                                finished = true;
                            }
                        } else {
                            if(finished){
                                for(int ed = pointX1; ed <= pointX2;ed++){
                                    for(int ed2 = pointY1; ed2 <= pointY2;ed2++){
                                        if(ed>=0 && ed<changeabliBitmap.getWidth() && ed2>=0 && ed2<changeabliBitmap.getHeight()) {
                                            int pixel = changeabliBitmap.getPixel(ed, ed2);
                                            int alpha = Color.alpha(pixel);
                                            int pixel2 = originalBitmap.getPixel(ed, ed2);
                                            int red2 = Color.red(pixel2);
                                            int blue2 = Color.blue(pixel2);
                                            int green2 = Color.green(pixel2);
                                            int alpha2 = Color.alpha(pixel2);
                                            alpha+=(int)(alpha2*brushOpacity/255);
                                            if(alpha >255){
                                                alpha = 255;
                                            }

                                            changeabliBitmap.setPixel(ed, ed2, Color.argb(alpha, red2, green2, blue2));
                                        }

                                    }
                                }
                                finalDiagramView.setBackground(new BitmapDrawable(getResources(),changeabliBitmap));
                                finished = true;
                            }
                        }
                    }else if (diagramAction == 2) {

                        int m1 =(int) motionEvent.getX();
                        int m2 = (int) motionEvent.getY();
                        int m3 = (int)finalDiagramView.getX();
                        int m4 = (int) finalDiagramView.getY();
                        int m5 = (int) finalImageView.getX();
                        int m6 = (int) finalImageView.getY();
                        int m7 = (int) finalDiagramView.getWidth();
                        int m8 = (int) finalDiagramView.getHeight();
                        int m9 = (int) finalImageView.getWidth();
                        int m10 = (int) finalImageView.getHeight();

                        if(m3 > m5 && m3+m7<m5+m9){
                            finalDiagramView.setX(m1- nx +m3 );

                        }
                        if (m4>m6 && m6+m10>m4+m8){
                            finalDiagramView.setY(m4 + m2 - ny);
                        }

                        if(m3 <= m5 && m1 > nx){
                            finalDiagramView.setX(m1- nx +m3 );

                        } else  if(m3+m7>=m5+m9 && nx>m1){
                            finalDiagramView.setX(m1- nx +m3 );
                        }


                        if (m4<=m6 && m2 > ny) {
                            finalDiagramView.setY(m4 + m2 - ny);
                        } else if (m4+m8 >= m6+m10 && m2<ny ){
                            finalDiagramView.setY(m4 + m2 - ny);
                        }

                        diagramClose.setX(finalDiagramView.getX());
                        diagramClose.setY(finalDiagramView.getY()-diagramClose.getHeight());
                        diagramOk.setX(finalDiagramView.getX()+finalDiagramView.getWidth()-diagramOk.getWidth());
                        diagramOk.setY(finalDiagramView.getY()-diagramOk.getHeight());
                        diagramResize.setX(finalDiagramView.getX()+finalDiagramView.getWidth()-diagramResize.getWidth());
                        diagramResize.setY(finalDiagramView.getY()+finalDiagramView.getHeight());
                    }
                }

                return true;
            }
        });

        diagramClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                diagramClose();
            }
        });

        finalAdd1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMoreImages();
            }
        });

        capturedImages = new ArrayList<Bitmap>();
        uris = new ArrayList<String>();
        uris = getIntent().getStringArrayListExtra("capturedImages");
        uris2 = uris;
        totalImages = uris.size();
        for (int i = 0; i < uris.size(); i++) {
            capturedImages.add(BitmapFactory.decodeFile(uris.get(i)));
        }

        customAdapter customAdap = new customAdapter();
        images.setAdapter(customAdap);

        finalDiagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //if (!capturedImage) {

                Intent pick_image_intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pick_image_intent, 120);
                //}
            }
        });

        originalDiagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                originalDiagram();
            }
        });

        inkDiagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inkColorScroll.setVisibility(View.VISIBLE);
            }
        });

        inkBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inkDiagram(0,0,0);
            }
        });

        inkGrey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inkDiagram(80,80,80);
            }
        });

        inkBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inkDiagram(0,68,170);
            }
        });

        inkDarkBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inkDiagram(22,38,119);
            }
        });

        inkRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inkDiagram(255, 34, 34);
            }
        });

        inkDarkRed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inkDiagram(153, 0, 0);
            }
        });

        diagramOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(diagramAction == 0){
                    diagramAction = 1;
                    originalDiagram.setVisibility(View.INVISIBLE);
                    inkDiagram.setVisibility(View.INVISIBLE);
                    inkColorScroll.setVisibility(View.INVISIBLE);
                    paintDiagram.setVisibility(View.VISIBLE);
                    eraseDiagram.setVisibility(View.VISIBLE);
                    eraseLayout.setVisibility(View.VISIBLE);
                    eraseLayoutClose.setVisibility(View.VISIBLE);
                } else if (diagramAction == 1){
                    diagramAction = 2;
                    paintDiagram.setVisibility(View.INVISIBLE);
                    eraseDiagram.setVisibility(View.INVISIBLE);
                    eraseLayout.setVisibility(View.INVISIBLE);
                    eraseLayoutClose.setVisibility(View.INVISIBLE);
                    diagramResize.setVisibility(View.VISIBLE);
                    flToolbar.setVisibility(View.INVISIBLE);
                } else if(diagramAction == 2){
                    finalDiagramView.setVisibility(View.INVISIBLE);
                    diagramResize.setVisibility(View.INVISIBLE);
                    diagramClose.setVisibility(View.INVISIBLE);
                    diagramOk.setVisibility(View.INVISIBLE);
                    loadingBg.setVisibility(View.VISIBLE);
                    loadingText.setVisibility(View.VISIBLE);
                    loading.setVisibility(View.VISIBLE);
                    anim.start();
                    pasting = true;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            diagramAction = 0;

                            Bitmap finalBitmap = Bitmap.createScaledBitmap(changeabliBitmap,finalDiagramView.getWidth(),finalDiagramView.getHeight(),false);
                            Bitmap newBitmap = Bitmap.createBitmap(capturedImages.get(selected-1).getWidth(),capturedImages.get(selected-1).getHeight(),Bitmap.Config.ARGB_8888);

                            int xstart = (int) (finalDiagramView.getX());
                            int ystart = (int) (finalDiagramView.getY()-finalImageView.getY());
                            for (int x = 0; x < newBitmap.getWidth(); x++) {
                                for (int y = 0; y < newBitmap.getHeight(); y++) {
                                    int pixel = capturedImages.get(selected-1).getPixel(x, y);
                                    int red = Color.red(pixel);
                                    int blue = Color.blue(pixel);
                                    int green = Color.green(pixel);
                                    if(x>=xstart && x<xstart + finalBitmap.getWidth()){
                                        if(y>=ystart && y<ystart+finalBitmap.getHeight()){
                                            int pixel2 = finalBitmap.getPixel(x-xstart, y-ystart);
                                            int red2 = Color.red(pixel2);
                                            int blue2 = Color.blue(pixel2);
                                            int green2 = Color.green(pixel2);
                                            int alpha = Color.alpha(pixel2);
                                            if(alpha == 255){
                                                newBitmap.setPixel(x, y, Color.argb(255, red2, green2, blue2));
                                            } else if (alpha==0){
                                                newBitmap.setPixel(x, y, Color.argb(255, red, green, blue));
                                            } else {
                                                int r = (int)(red+red2+alpha)/3;
                                                int g = (int)(green+green2+alpha)/3;
                                                int b = (int)(blue+blue2+alpha)/3;
                                                newBitmap.setPixel(x, y, Color.argb(255, r, g, b));
                                            }

                                        }else {
                                            newBitmap.setPixel(x, y, Color.argb(255, red, green, blue));
                                        }
                                    } else {
                                        newBitmap.setPixel(x, y, Color.argb(255, red, green, blue));
                                    }
                                }
                            }
                            capturedImages.set(selected-1,newBitmap);
                            finalImageView.setImageBitmap(newBitmap);
                            File file = new File(Uri.parse(uris2.get(selected-1)).getPath());
                            pasting = false;
                            try {
                                FileOutputStream fos = new FileOutputStream(file);
                                newBitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            anim.stop();
                            loadingBg.setVisibility(View.GONE);
                            loadingText.setVisibility(View.GONE);
                            loading.setVisibility(View.GONE);
                            finalAddButton.setVisibility(View.VISIBLE);
                            finalDeleteButton.setVisibility(View.VISIBLE);
                            finalRetakeButton.setVisibility(View.VISIBLE);
                            finalDiagram.setVisibility(View.VISIBLE);
                            flToolbar.setVisibility(View.VISIBLE);
                        }
                    }, 5000);

                }
            }
        });
        paintDiagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint();

            }
        });

        paintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint();
            }
        });

        eraseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erase();
            }
        });

        eraseDiagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                erase();
            }
        });

        eraseLayoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eraseLayout.setVisibility(View.INVISIBLE);
                eraseLayoutClose.setVisibility(View.INVISIBLE);
            }
        });

        brushHardness.setMin(10);
        brushHardness.setMax(100);
        brushSize.setMin(20);
        brushSize.setMax(100);

        brushHardness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brushOpacity = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        brushSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                brushSizeValue = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void originalDiagram() {
        finalDiagramView.setBackground(new BitmapDrawable(getResources(),coloredBitmap));
        originalBitmap = Bitmap.createScaledBitmap(coloredBitmap,coloredBitmap.getWidth(), coloredBitmap.getHeight(), false);
        changeabliBitmap = Bitmap.createScaledBitmap(coloredBitmap,coloredBitmap.getWidth(), coloredBitmap.getHeight(), false);
        inkColorScroll.setVisibility(View.INVISIBLE);
    }

    private void diagramClose() {
        diagramClose.setVisibility(View.INVISIBLE);
        finalDiagramView.setVisibility(View.INVISIBLE);
        diagramResize.setVisibility(View.INVISIBLE);
        diagramOk.setVisibility(View.INVISIBLE);
        originalDiagram.setVisibility(View.INVISIBLE);
        inkDiagram.setVisibility(View.INVISIBLE);
        finalDeleteButton.setVisibility(View.VISIBLE);
        finalAddButton.setVisibility(View.VISIBLE);
        finalRetakeButton.setVisibility(View.VISIBLE);
        finalDiagram.setVisibility(View.VISIBLE);
        paintDiagram.setVisibility(View.INVISIBLE);
        eraseDiagram.setVisibility(View.INVISIBLE);
        eraseLayout.setVisibility(View.INVISIBLE);
        flToolbar.setVisibility(View.VISIBLE);
        eraseLayoutClose.setVisibility(View.INVISIBLE);
        diagramAction = 0;
    }

    private void exportBg() {
        exportBg.startAnimation(fadeOut2);
        exportBg.setVisibility(View.INVISIBLE);
        exportLayout.startAnimation(toBottom);
        exportLayout.setVisibility(View.INVISIBLE);
        exportPressed = false;
    }

    private void renameBg() {
        finalRenameLayout.startAnimation(fadeOut);
        renameBg.startAnimation(fadeOut2);
        finalRenameLayout.setVisibility(View.INVISIBLE);
        renameBg.setVisibility(View.INVISIBLE);
        exportPressed = false;
    }

    private void renameOk() {
        finalRenameLayout.startAnimation(fadeOut);
        finalRenameLayout.setVisibility(View.INVISIBLE);
        renameBg.startAnimation(fadeOut2);
        renameBg.setVisibility(View.INVISIBLE);
        exportPressed = false;

        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }

    private void renameCancel() {
        saveas = 0;
        finalRenameLayout.startAnimation(fadeOut);
        finalRenameLayout.setVisibility(View.INVISIBLE);
        renameBg.startAnimation(fadeOut2);
        renameBg.setVisibility(View.INVISIBLE);
        exportPressed = false;
    }

    private void exportAsImages() {
        saveas = 2;
        exportBg.startAnimation(fadeOut2);
        exportBg.setVisibility(View.INVISIBLE);
        exportLayout.startAnimation(fadeOut);
        exportLayout.setVisibility(View.INVISIBLE);
        finalRenameLayout.startAnimation(fadeIn);
        finalRenameLayout.setVisibility(View.VISIBLE);
        renameBg.startAnimation(fadeIn2);
        renameBg.setVisibility(View.VISIBLE);
        renameLayout=true;
        exportPressed = false;
    }

    private void exportAsPdf() {
        saveas = 1;
        exportBg.startAnimation(fadeOut2);
        exportBg.setVisibility(View.INVISIBLE);
        exportLayout.startAnimation(fadeOut);
        exportLayout.setVisibility(View.INVISIBLE);
        finalRenameLayout.startAnimation(fadeIn);
        finalRenameLayout.setVisibility(View.VISIBLE);
        renameBg.startAnimation(fadeIn2);
        renameBg.setVisibility(View.VISIBLE);
        renameLayout=true;
        exportPressed = false;
    }

    private void export() {
        if (!exportPressed) {
            exportBg.startAnimation(fadeIn2);
            exportBg.setVisibility(View.VISIBLE);
            slideUp(exportLayout);
            exportPressed = true;
        } else {
            exportBg.startAnimation(fadeOut2);
            exportBg.setVisibility(View.INVISIBLE);
            slideDown(exportLayout);
            exportPressed = false;
        }
    }

    private void retake() {
        Intent cameraintent2 = new Intent();
        cameraintent2.putExtra("actionPerformed", 2);
        cameraintent2.putExtra("uris", uris);
        cameraintent2.putExtra("retake", selected);
        setResult(RESULT_OK, cameraintent2);
        finish();
    }

    private void delete() {
        totalImages--;
        capturedImages.remove(selected - 1);
        uris.remove(selected - 1);
        Toast.makeText(FinalActivity.this, "1 image deleted", Toast.LENGTH_SHORT).show();
        if (totalImages > 0) {
            if (selected <= totalImages) {
                finalImageView.setImageBitmap(capturedImages.get(selected - 1));
            } else {
                selected--;
                finalImageView.setImageBitmap(capturedImages.get(selected - 1));
            }
        } else {
            Intent cameraintent2 = new Intent();
            cameraintent2.putExtra("actionPerformed", 1);
            cameraintent2.putExtra("uris", uris);
            setResult(RESULT_OK, cameraintent2);
            finish();
        }
    }

    private void adshow() {
        MobileAds.initialize(this,
                "ca-app-pub-9818307260762254~1966791439");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-9818307260762254/9978316856");
        //mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    private void findViewbyId() {
        images = (GridView) findViewById(R.id.imagesGrid);
        flToolbar1 = (Toolbar) findViewById(R.id.finaltoolbar1);
        finalImageView = (ImageView) findViewById(R.id.finalImageView);
        finalAddButton = (Button) findViewById(R.id.finaladd);
        finalDeleteButton = (Button) findViewById(R.id.finaldelete);
        finalRetakeButton = (Button) findViewById(R.id.finalretake);
        finalExportButton = (LinearLayout) findViewById(R.id.finalExport);
        flToolbar = (Toolbar) findViewById(R.id.fltoolbar);
        finalAdd1 = (ImageButton) findViewById(R.id.finaladd1);
        exportImages = (Button) findViewById(R.id.exportImages);
        exportPdf = (Button) findViewById(R.id.exportPdf);
        exportLayout = (LinearLayout) findViewById(R.id.exportLayout);
        finalRenameLayout = (LinearLayout) findViewById(R.id.finalrenamelayout);
        finalFilename = (EditText) findViewById(R.id.finalfilename);
        finalRenameOk = (Button) findViewById(R.id.finalrenameok);
        finalRenameCancel = (Button) findViewById(R.id.finalrenamecancel);
        renameBg = findViewById(R.id.renameBackground2);
        exportBg = findViewById(R.id.exportBackground22);
        finalDiagramView = (ImageView) findViewById(R.id.finalDiagramView);
        diagramClose = (Button) findViewById(R.id.cancelDiagram);
        diagramResize = (Button) findViewById(R.id.finalDragButton);
        finalDiagram = (Button) findViewById(R.id.finaldiagram);
        diagramOk = (Button) findViewById(R.id.okDiagram);
        originalDiagram = (Button) findViewById(R.id.originalDiagram);
        inkDiagram = (Button) findViewById(R.id.inkDiagram);
        loading =(ImageView) findViewById(R.id.loadingImageView2);
        anim = (AnimationDrawable)loading.getDrawable();
        loadingBg = findViewById(R.id.loadingBG2);
        loadingText = findViewById(R.id.loadingTextView2);

        fromBottom = AnimationUtils.loadAnimation(FinalActivity.this, R.anim.from_bottom_original_size);
        toBottom = AnimationUtils.loadAnimation(FinalActivity.this, R.anim.to_bottom_original_size);
        fadeIn2 = AnimationUtils.loadAnimation(FinalActivity.this, R.anim.fadein_slow);
        fadeOut2 = AnimationUtils.loadAnimation(FinalActivity.this, R.anim.fadeout_slow);
        fadeIn = AnimationUtils.loadAnimation(FinalActivity.this, R.anim.fadein);
        fadeOut = AnimationUtils.loadAnimation(FinalActivity.this, R.anim.fadeout);

        inkColorScroll = (HorizontalScrollView) findViewById(R.id.inkColorScroll);
        inkBlack = (Button) findViewById(R.id.ink_Colour_Black);
        inkGrey = (Button) findViewById(R.id.ink_Colour_Grey);
        inkBlue = (Button) findViewById(R.id.ink_Color_Blue);
        inkDarkBlue = (Button) findViewById(R.id.ink_Colour_DarkBlue);
        inkRed = (Button) findViewById(R.id.ink_Colour_Red);
        inkDarkRed = (Button) findViewById(R.id.ink_Colour_DarkRed);

        eraseLayout = (LinearLayout) findViewById(R.id.eraseLayout);
        brushHardness = (SeekBar) findViewById(R.id.brushHardness);
        paintDiagram = (LinearLayout) findViewById(R.id.paintDiagram);
        eraseDiagram = (LinearLayout) findViewById(R.id.eraseDiagram);
        brushSize = (SeekBar) findViewById(R.id.brushSize);
        eraseLayoutClose = (ImageButton) findViewById(R.id.eraseLayoutClose);
        eraseButton = (Button) findViewById(R.id.eraseButton);
        paintButton = (Button) findViewById(R.id.paintButton);

        Toolbar toolbar = findViewById(R.id.finaltoolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("");
    }

    private void homeUI() {
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();

        ViewGroup.MarginLayoutParams loadingLayoutParams = (ViewGroup.MarginLayoutParams)loading.getLayoutParams();
        loadingLayoutParams.height = (screenWidth*40)/100;
        loadingLayoutParams.width = (screenWidth*40)/100;
        loading.setLayoutParams(loadingLayoutParams);

        CardView.MarginLayoutParams finalRenameLayoutParams = (CardView.MarginLayoutParams) finalRenameLayout.getLayoutParams();
        finalRenameLayoutParams.width = (screenWidth*80)/100;
        finalRenameLayout.setLayoutParams(finalRenameLayoutParams);
    }


    public void slideUp(View view){
        exportLayout.setVisibility(View.VISIBLE);
        TranslateAnimation Up = new TranslateAnimation(0,0,view.getHeight(),0);
        Up.setDuration(250);
        Up.setFillAfter(true);
        view.startAnimation(Up);
    }

    public void slideDown(View view){
        exportLayout.setVisibility(View.VISIBLE);
        TranslateAnimation Down = new TranslateAnimation(0,0,0,view.getHeight());
        Down.setDuration(500);
        Down.setFillAfter(true);
        view.startAnimation(Down);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void exportAsWhatever() {
        if (saveas == 1) {

            if (ActivityCompat.checkSelfPermission(FinalActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(FinalActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        1);
                return;
            }




            PdfDocument doc = new PdfDocument();
            for (int i = 0; i < capturedImages.size(); i++) {
                PdfDocument.PageInfo pi = new PdfDocument.PageInfo.Builder(capturedImages.get(i).getWidth(),
                        capturedImages.get(i).getHeight(), i).create();
                PdfDocument.Page newPage = doc.startPage(pi);
                Canvas canvas = newPage.getCanvas();
                Paint paint = new Paint();
                paint.setColor(Color.WHITE);
                canvas.drawPaint(paint);
                paint.setColor(Color.BLUE);
                canvas.drawBitmap(capturedImages.get(i), 0, 0, null);
                doc.finishPage(newPage);
            }

            String renamed = finalFilename.getText().toString();

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmm", Locale.getDefault()).format(System.currentTimeMillis());
            File path = Environment.getExternalStorageDirectory();
            File dir = new File(path + "/HandWriter/" + "/Pdf Files/");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            String fileName;
            if (!renamed.isEmpty()) {
                fileName = renamed + ".pdf";
            } else {
                fileName = "New Doc " + timeStamp + ".pdf";
            }
            File mFilePath = new File(dir, fileName);

            try {
                FileOutputStream fOS = new FileOutputStream(mFilePath);
                doc.writeTo(fOS);
                Toast.makeText(this, fileName + " is saved to " + dir, Toast.LENGTH_SHORT).show();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            doc.close();
        } else if (saveas == 2) {
            String folder = finalFilename.getText().toString();
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            if (folder.isEmpty()) {
                folder = timeStamp;
            }
            File path = Environment.getExternalStorageDirectory();
            File dir = new File(path + "/HandWriter/" + "/Images/" + folder);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            for (int i = 0; i < capturedImages.size(); i++) {
                try {
                    String imageFileName = folder + String.valueOf(i);
                    File storageDir = new File(Environment.getExternalStorageDirectory() + "/HandWriter/" + "/Images/" + folder);
                    File file = File.createTempFile(imageFileName, ".jpg", storageDir);
                    FileOutputStream fileOutputStream = new FileOutputStream(file);
                    capturedImages.get(i).compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Toast.makeText(this, "Images saved to HandWriter/Images/" + folder, Toast.LENGTH_SHORT).show();
        }
    }

    private void closeImage() {
        images.setVisibility(View.VISIBLE);
        finalExportButton.setVisibility(View.VISIBLE);
        finalAdd1.setVisibility(View.VISIBLE);
        finalImageView.setVisibility(View.INVISIBLE);
        finalAddButton.setVisibility(View.INVISIBLE);
        finalDeleteButton.setVisibility(View.INVISIBLE);
        finalRetakeButton.setVisibility(View.INVISIBLE);
        flToolbar.setVisibility(View.INVISIBLE);
        finalDiagram.setVisibility(View.INVISIBLE);
        customAdapter customAdap = new customAdapter();
        images.setAdapter(customAdap);
        imageOpen = false;
    }

    private void addMoreImages() {
        Intent cameraintent2 = new Intent();
        cameraintent2.putExtra("actionPerformed", 1);
        cameraintent2.putExtra("uris", uris);
        setResult(RESULT_OK, cameraintent2);
        finish();
    }


    class customAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return capturedImages.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {

            Display display = getWindowManager().getDefaultDisplay();
            int screenWidth = display.getWidth();

            view = getLayoutInflater().inflate(R.layout.activity_finimg, null);
            ImageButton imagesCaptured = (ImageButton) view.findViewById(R.id.imagesCaptured);
            Button imagesNo = (Button) view.findViewById(R.id.imagesNumberFinal);
            LinearLayout fLinearLayout = (LinearLayout) view.findViewById(R.id.flinearLayout);

            LinearLayout.MarginLayoutParams lparams = (LinearLayout.MarginLayoutParams) fLinearLayout.getLayoutParams();
            lparams.setMargins((screenWidth * 2) / 100, (screenWidth * 2) / 100, 0, 0);
            fLinearLayout.setLayoutParams(lparams);


            ViewGroup.MarginLayoutParams imagesParams = (ViewGroup.MarginLayoutParams) imagesCaptured.getLayoutParams();
            imagesParams.height = (screenWidth * 60) / 100;
            imagesParams.width = (screenWidth * 46) / 100;
            imagesCaptured.setLayoutParams(imagesParams);

            imagesNo.setText(String.valueOf(i + 1));
            imagesCaptured.setBackground(new BitmapDrawable(getResources(), capturedImages.get(i)));
            imagesCaptured.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (exportPressed == false) {
                        images.setVisibility(View.INVISIBLE);
                        finalExportButton.setVisibility(View.INVISIBLE);
                        finalAdd1.setVisibility(View.INVISIBLE);
                        finalImageView.setVisibility(View.VISIBLE);
                        finalAddButton.setVisibility(View.VISIBLE);
                        finalDeleteButton.setVisibility(View.VISIBLE);
                        finalRetakeButton.setVisibility(View.VISIBLE);
                        finalDiagram.setVisibility(View.VISIBLE);
                        flToolbar.setVisibility(View.VISIBLE);
                        finalImageView.setImageBitmap(capturedImages.get(i));
                        selected = i + 1;
                        imageOpen = true;
                    }
                }
            });
            return view;
        }
    }

    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context context) {
            gestureDetector = new GestureDetector(context, new FinalActivity.OnSwipeTouchListener.GestureListener());
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

    private void nextImageDisplay() {
        if (selected < totalImages) {
            finalImageView.setImageBitmap(capturedImages.get(selected));
            selected++;
        }
    }

    private void prevImageDisplay() {
        if (selected > 1) {
            selected--;
            finalImageView.setImageBitmap(capturedImages.get(selected - 1));
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (exportPressed) {
            exportBg.startAnimation(fadeOut2);
            exportBg.setVisibility(View.INVISIBLE);
            exportLayout.startAnimation(toBottom);
            exportLayout.setVisibility(View.INVISIBLE);
            finalRenameLayout.startAnimation(fadeOut);
            finalRenameLayout.setVisibility(View.INVISIBLE);
            renameBg.startAnimation(fadeOut2);
            renameBg.setVisibility(View.INVISIBLE);
            exportPressed = false;
        }else {
            if (imageOpen) {
                if(finalDiagramView.getVisibility() == View.VISIBLE){
                    Toast.makeText(this,"press cancel button on image top to discard diagram",Toast.LENGTH_SHORT).show();
                } else {
                    closeImage();
                }

            } else {
                Intent cameraintent2 = new Intent();
                cameraintent2.putExtra("actionPerformed", 3);
                cameraintent2.putExtra("uris", uris2);
                setResult(RESULT_OK, cameraintent2);
                finish();
            }

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 120 && resultCode == RESULT_OK) {
            Uri selectedImageUri = data.getData();



            try {
                InputStream inputStream = getContentResolver().openInputStream(selectedImageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                float iheight = bitmap.getHeight();
                float iwidth = bitmap.getWidth();

                imageWidth = (int)(finalImageView.getWidth()*0.7);
                imageHeight = (int) ((iheight/iwidth)*imageWidth);

                if(imageHeight > (finalImageView.getHeight()*0.7)){
                    imageHeight = (int) (finalImageView.getHeight()*0.8);
                    imageWidth = (int) ((iwidth/iheight)*imageHeight);
                }


                coloredBitmap = Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, false);
                inkBitmap = Bitmap.createBitmap(coloredBitmap.getWidth(), coloredBitmap.getHeight(), coloredBitmap.getConfig());
                originalBitmap = Bitmap.createBitmap(coloredBitmap.getWidth(), coloredBitmap.getHeight(), coloredBitmap.getConfig());
                finalDiagramView.setVisibility(View.VISIBLE);
                //finalDiagramView.setX(finalImageView.getWidth()/10);
                //finalDiagramView.setY((finalImageView.getHeight()-imageHeight)/2);
                diagramClose.setVisibility(View.VISIBLE);
                diagramOk.setVisibility(View.VISIBLE);
                finalDeleteButton.setVisibility(View.INVISIBLE);
                finalAddButton.setVisibility(View.INVISIBLE);
                finalRetakeButton.setVisibility(View.INVISIBLE);
                finalDiagram.setVisibility(View.INVISIBLE);
                originalDiagram.setVisibility(View.VISIBLE);
                inkDiagram.setVisibility(View.VISIBLE);

                finalDiagramView.setX((finalImageView.getWidth() - finalDiagramView.getWidth())/2);
                finalDiagramView.setY((finalImageView.getHeight()-finalDiagramView.getHeight())/2+finalImageView.getY());
                diagramClose.setX(finalDiagramView.getX());
                diagramClose.setY(finalDiagramView.getY()-diagramClose.getHeight());
                diagramOk.setX(finalDiagramView.getX()+finalDiagramView.getWidth()-diagramOk.getWidth());
                diagramOk.setY(finalDiagramView.getY()-diagramOk.getHeight());
                diagramResize.setX(finalDiagramView.getX()+finalDiagramView.getWidth()-diagramResize.getWidth());
                diagramResize.setY(finalDiagramView.getY()+finalDiagramView.getHeight());


                ViewGroup.LayoutParams ivp = (ViewGroup.LayoutParams) finalDiagramView.getLayoutParams();
                ivp.height = imageHeight;
                ivp.width = imageWidth;
                finalDiagramView.setLayoutParams(ivp);

                for (int x = 0; x < coloredBitmap.getWidth(); x++) {
                    for (int y = 0; y < coloredBitmap.getHeight(); y++) {
                        int pixel = coloredBitmap.getPixel(x, y);
                        int red = Color.red(pixel);
                        int blue = Color.blue(pixel);
                        int green = Color.green(pixel);
                        int alpha = Color.alpha(pixel);
                        originalBitmap.setPixel(x, y, Color.argb(255, red, green, blue));
                    }
                }
                changeabliBitmap = Bitmap.createScaledBitmap(originalBitmap,originalBitmap.getWidth(), originalBitmap.getHeight(), false);
                finalDiagramView.setBackground(new BitmapDrawable(getResources(),coloredBitmap));

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void erase(){
        if (erase == false) {
            eraseLayout.setVisibility(View.VISIBLE);
            eraseLayoutClose.setVisibility(View.VISIBLE);
            eraseDiagram.setBackgroundColor(getResources().getColor(R.color.lightGrey));
            paintDiagram.setBackgroundColor(Color.TRANSPARENT);
            erase = true;

        } else {
            if(eraseLayout.getVisibility()==View.VISIBLE){
                eraseLayout.setVisibility(View.INVISIBLE);
                eraseLayoutClose.setVisibility(View.INVISIBLE);
            } else {
                eraseLayout.setVisibility(View.VISIBLE);
                eraseLayoutClose.setVisibility(View.VISIBLE);
            }
        }
    }

    private void paint(){
        if (erase == true){
            eraseLayout.setVisibility(View.VISIBLE);
            eraseLayoutClose.setVisibility(View.VISIBLE);
            eraseDiagram.setBackgroundColor(Color.TRANSPARENT);
            paintDiagram.setBackgroundColor(getResources().getColor(R.color.lightGrey));
            erase = false;
        } else {
            if(eraseLayout.getVisibility()==View.VISIBLE){
                eraseLayout.setVisibility(View.INVISIBLE);
                eraseLayoutClose.setVisibility(View.INVISIBLE);
            } else {
                eraseLayout.setVisibility(View.VISIBLE);
                eraseLayoutClose.setVisibility(View.VISIBLE);
            }
        }
    }

    private void inkDiagram(int r, int g, int b){
        for (int x = 0; x < coloredBitmap.getWidth(); x++) {
            for (int y = 0; y < coloredBitmap.getHeight(); y++) {
                int pixel = coloredBitmap.getPixel(x, y);
                int red = Color.red(pixel);
                int blue = Color.blue(pixel);
                int green = Color.green(pixel);

                if (red > 100 && blue > 100 && green > 100) {
                    inkBitmap.setPixel(x, y, Color.argb(0, 0, 0, 0));
                } else {
                    inkBitmap.setPixel(x, y, Color.argb(255, r, g, b));
                }
            }
        }
        originalBitmap = Bitmap.createScaledBitmap(inkBitmap,coloredBitmap.getWidth(), coloredBitmap.getHeight(), false);
        changeabliBitmap = Bitmap.createScaledBitmap(inkBitmap,coloredBitmap.getWidth(), coloredBitmap.getHeight(), false);
        finalDiagramView.setBackground(new BitmapDrawable(getResources(),inkBitmap));
    }

}