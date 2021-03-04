package com.xsar.handwriter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton mfab1, mfab2, mfab3, mfab4, mfab5;
    View fab_button;
    TextView mfabtext2, mfabtext3, mfabtext4, mfabtext5;

    private boolean isOpen = false;
    private Animation fromBottom, toBottom, rotateOpen, rotateClose, fromBottomOG, fadeOut, fadeOutSlow, fromRight, fadein, fadeinSlow;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;

    private AppBarConfiguration mAppBarConfiguration;

    AppUpdateManager appUpdateManager;
    private int REQUEST_CODE = 420;

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolBar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolBar);

        mfab1 = findViewById(R.id.floatingActionButton1);
        mfab2 = findViewById(R.id.floatingActionButton2);
        mfab3 = findViewById(R.id.floatingActionButton3);
        mfab4 = findViewById(R.id.floatingActionButton4);
        mfab5 = findViewById(R.id.floatingActionButton5);

        fab_button = findViewById(R.id.fab_button);

        mfabtext2 = findViewById(R.id.fab_text2);
        mfabtext3 = findViewById(R.id.fab_text3);
        mfabtext4 = findViewById(R.id.fab_text4);
        mfabtext5 = findViewById(R.id.fab_text5);

        fromBottom = AnimationUtils.loadAnimation(MainActivity.this, R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(MainActivity.this, R.anim.to_bottom_anim);

        fromBottomOG = AnimationUtils.loadAnimation(MainActivity.this, R.anim.from_bottom_original_size);
        fadein = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadein);
        fadeinSlow = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadein_slow);
        fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadeout);
        fadeOutSlow = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fadeout_slow);

        fromRight = AnimationUtils.loadAnimation(MainActivity.this, R.anim.enter_from_right);

        rotateOpen = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate_close_anim);

        deleteTempFiles();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();
        mActionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
        toolBar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.tutorials, R.id.rate_us, R.id.contact_us, R.id.feedback, R.id.about_us, R.id.follow)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        appUpdateManager = AppUpdateManagerFactory.create(this);
        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if ((result.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) &&
                        result.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE,
                                MainActivity.this, REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        boolean isFirstRun = getSharedPreferences("PREFERENCE", MODE_PRIVATE).getBoolean("isFirstRun", true);
        if (isFirstRun) {

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isOpen) {
                        onAddButtonClicked();
                    }
                    Intent intent = new Intent(MainActivity.this, WalkThroughActivity.class);
                    startActivity(intent);
                }
            }, 1000);

        }
        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit().putBoolean("isFirstRun", false).apply();


        mfab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });


        mfab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{
                                    Manifest.permission.CAMERA},
                            150);
                } else {
                    onAddButtonClicked();
                    /*Intent cameraIntent = new Intent(MainActivity.this, NewCustomCamera.class);
                    cameraIntent.putExtra("fromGallery", false);
                    startActivity(cameraIntent);*/
                    Intent camIntent = new Intent(MainActivity.this, NewCustomCamera.class);
                    camIntent.putExtra("fromActivity", 1);
                    startActivity(camIntent);
                }

            }
        });


        mfab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v3) {
                onAddButtonClicked();
                Intent intent3 = new Intent(MainActivity.this, activity_fab4.class);
                intent3.putExtra("FileReady", 1);
                startActivity(intent3);
            }
        });

        mfab4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v4) {
                onAddButtonClicked();
                Intent intent4 = new Intent(MainActivity.this, activity_fab4.class);
                intent4.putExtra("FileReady", 2);
                startActivity(intent4);
            }
        });

        mfab5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v4) {
                onAddButtonClicked();
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            200);
                } else {
                    OpenGallery();
                }
            }
        });

        fab_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked();
            }
        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.rate_us:

                    case R.id.feedback: {
                        Intent rateIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.xsar.handwriter"));
                        startActivity(rateIntent);
                        break;
                    }

                    case R.id.video_tutorials: {
                        Intent video = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/tv/CE2AV93DjKL/?igshid=19q06xsz3asa1"));
                        startActivity(video);
                        break;
                    }

                    case R.id.contact_us: {
                        Intent contactIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:xsar.incorporated@gmail.com"));
                        startActivity(contactIntent);
                        break;
                    }

                    case R.id.follow: {
                        Intent followtIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/xsar.inc/"));
                        startActivity(followtIntent);
                        break;
                    }

                }
                NavigationUI.onNavDestinationSelected(item, navController);
                mDrawerLayout.closeDrawer(GravityCompat.START);

                return true;
            }
        });


    }







    @Override
    protected void onResume() {

        super.onResume();

        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo result) {
                if (result.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(result, AppUpdateType.IMMEDIATE, MainActivity.this, REQUEST_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        } else {
            if (item.getItemId() == R.id.share) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "HandWriter");
                    intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id="
                            + getApplicationContext().getPackageName());
                    startActivity(Intent.createChooser(intent, "Share-Via"));
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Error Sharing", Toast.LENGTH_SHORT).show();
                }
            } else if (item.getItemId() == R.id.help_menu) {
                Intent intent = new Intent(MainActivity.this, WalkThroughActivity.class);
                startActivity(intent);
            }
        }
        return true;
    }

    private void OpenGallery() {
        Intent galleryIntent = new Intent(MainActivity.this, image_editor.class);
        galleryIntent.putExtra("fromGallery", true);
        startActivity(galleryIntent);
    }


    @Override
    public void onBackPressed() {
        if (this.mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.mDrawerLayout.closeDrawer(GravityCompat.START);
        } else if (isOpen) {
            onAddButtonClicked();
        } else {
            AlertDialog.Builder alertDialouge = new AlertDialog.Builder(this);
            alertDialouge.setTitle("Confirm Exit...!!");
            alertDialouge.setMessage("Are you sure you want to exit..?");
            alertDialouge.setCancelable(false);
            alertDialouge.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });

            alertDialouge.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    Toast.makeText(MainActivity.this, "Press Yes to exit", Toast.LENGTH_SHORT).show();
                }
            });

            AlertDialog alertDialog = alertDialouge.create();
            alertDialog.show();
        }
    }

    private void onAddButtonClicked() {
        setVisibility(isOpen);
        setAnimation(isOpen);
        setClickable(isOpen);
        isOpen = !isOpen;
    }

    private void setVisibility(boolean isOpen) {
        if (!isOpen) {
            mfab2.setVisibility(View.VISIBLE);
            mfab3.setVisibility(View.VISIBLE);
            mfab4.setVisibility(View.VISIBLE);
            mfab5.setVisibility(View.VISIBLE);
            fab_button.setVisibility(View.VISIBLE);

            mfabtext2.setVisibility(View.VISIBLE);
            mfabtext3.setVisibility(View.VISIBLE);
            mfabtext4.setVisibility(View.VISIBLE);
            mfabtext5.setVisibility(View.VISIBLE);

        } else {
            mfab2.setVisibility(View.INVISIBLE);
            mfab3.setVisibility(View.INVISIBLE);
            mfab4.setVisibility(View.INVISIBLE);
            mfab5.setVisibility(View.INVISIBLE);
            fab_button.setVisibility(View.GONE);

            mfabtext2.setVisibility(View.INVISIBLE);
            mfabtext3.setVisibility(View.INVISIBLE);
            mfabtext4.setVisibility(View.INVISIBLE);
            mfabtext5.setVisibility(View.VISIBLE);
        }
    }

    private void setAnimation(boolean isOpen) {

        if (!isOpen) {
            mfab1.startAnimation(rotateOpen);
            mfab2.startAnimation(fromBottom);
            mfab3.startAnimation(fromBottom);
            mfab4.startAnimation(fromBottom);
            mfab5.startAnimation(fromBottom);

            mfabtext2.startAnimation(fromBottom);
            mfabtext3.startAnimation(fromBottom);
            mfabtext4.startAnimation(fromBottom);
            mfabtext5.startAnimation(fromBottom);

        } else {
            mfab1.startAnimation(rotateClose);
            mfab2.startAnimation(toBottom);
            mfab3.startAnimation(toBottom);
            mfab4.startAnimation(toBottom);
            mfab5.startAnimation(toBottom);


            mfabtext2.startAnimation(toBottom);
            mfabtext3.startAnimation(toBottom);
            mfabtext4.startAnimation(toBottom);
            mfabtext5.startAnimation(toBottom);
        }
    }

    private void setClickable(boolean isOpen) {
        if (!isOpen) {
            mfab2.setClickable(true);
            mfab3.setClickable(true);
            mfab4.setClickable(true);
            mfab5.setClickable(true);
        } else {
            mfab2.setClickable(false);
            mfab3.setClickable(false);
            mfab4.setClickable(false);
            mfab5.setClickable(false);
        }
    }

    private void deleteTempFiles() {
        File file = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        String[] myfiles;
        myfiles = file.list();
        for (int i = 0; i < myfiles.length; i++) {
            File myfile = new File(file, myfiles[i]);
            if (myfile.getName().startsWith("Handwriter_")) {
                myfile.delete();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        switch (requestCode) {

            case 150: {
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    onAddButtonClicked();
                    Intent cameraIntent = new Intent(MainActivity.this, image_editor.class);
                    cameraIntent.putExtra("fromGallery", false);
                    startActivity(cameraIntent);
                } else {
                    Toast.makeText(this, "camera permission required to take pictures", Toast.LENGTH_SHORT).show();
                }

            }

            case 200: {
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    OpenGallery();
                } else {
                    Toast.makeText(this, "storage permission required to access images", Toast.LENGTH_SHORT).show();
                }


            }


        }

    }


}