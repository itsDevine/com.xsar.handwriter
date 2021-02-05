package com.xsar.handwriter.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderView;
import com.xsar.handwriter.Activity_Folder_PDF;
import com.xsar.handwriter.Activity_Folder_Text;
import com.xsar.handwriter.ImageSliderAdapter;
import com.xsar.handwriter.R;

import java.io.File;

public class HomeFragment extends Fragment {

    LinearLayout textFiles, pdfFiles, textFilesShadow, pdfFilesshadow;
            //scannedFiles, scannedFilesShadow;
    CardView cardview;
    SliderView sliderView;
    TextView getTextFilesBadge, getPdfFilesBadge, getScannedFilesBadge;
    int TotalCount;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        final View viewHome = inflater.inflate(R.layout.fragment_home, container, false);

        textFiles = viewHome.findViewById(R.id.text_button);
        pdfFiles = viewHome.findViewById(R.id.pdf_button);
        //scannedFiles = viewHome.findViewById(R.id.scanned_button);

        textFilesShadow = viewHome.findViewById(R.id.text_button_shadow);
        pdfFilesshadow = viewHome.findViewById(R.id.pdf_button_shadow);
        //scannedFilesShadow = viewHome.findViewById(R.id.scanned_button_shadow);

        getTextFilesBadge = viewHome.findViewById(R.id.getTextFilesBadge);
        getPdfFilesBadge = viewHome.findViewById(R.id.getPdfFilesBadge);
        getScannedFilesBadge = viewHome.findViewById(R.id.getScanedFilesBadge);

        cardview = viewHome.findViewById(R.id.cardView2);

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            String filePath = Environment.getExternalStorageDirectory().getPath() + "/HandWriter/Text Files/";
            File myFolder = new File(filePath);
            File[] files = myFolder.listFiles();
            int numOfFiles = files.length;
            getTextFilesBadge.setText(String.valueOf(numOfFiles));

            String filePath1 = Environment.getExternalStorageDirectory().getPath() + "/HandWriter/Pdf Files/";
            File myFolder1 = new File(filePath1);
            File[] files1 = myFolder1.listFiles();
            int numOfFiles1 = files1.length;
            getPdfFilesBadge.setText(String.valueOf(numOfFiles1));
        } else {
            getTextFilesBadge.setVisibility(View.INVISIBLE);
            getPdfFilesBadge.setVisibility(View.INVISIBLE);
        }


        homeUI();

        textFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            100);
                    return;
                }
                getTextFiles();
            }
        });

        pdfFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            200);
                    return;
                }
                getPdfFiles();
            }
        });

/*
        scannedFiles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Activity_Folder_Scanned.class);
                startActivity(intent);
            }
        });
 */



        sliderView = viewHome.findViewById(R.id.imageViewSlider);
        sliderView.startAutoCycle();
        FirebaseDatabase.getInstance().getReference("ImagesList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Long counts = snapshot.getChildrenCount();
                TotalCount = counts.intValue();
                sliderView.setSliderAdapter(new ImageSliderAdapter(HomeFragment.this, TotalCount));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent video = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/tv/CE2AV93DjKL/?igshid=19q06xsz3asa1"));
                startActivity(video);
            }
        });

        return viewHome;
    }

    private void getTextFiles() {
        Intent intent = new Intent(getActivity(), Activity_Folder_Text.class);
        startActivity(intent);
    }

    private void getPdfFiles() {
        Intent intent = new Intent(getActivity(), Activity_Folder_PDF.class);
        startActivity(intent);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        switch (requestCode) {

            case 100: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission Granted!", Toast.LENGTH_SHORT).show();
                    getTextFiles();
                } else {
                    Toast.makeText(getActivity(), "Storage permission is required to access files", Toast.LENGTH_SHORT).show();
                }
                break;
            }

            case 200: {

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Permission Granted!", Toast.LENGTH_SHORT).show();
                    getPdfFiles();
                } else {
                    Toast.makeText(getActivity(), "Storage permission is required to access files", Toast.LENGTH_SHORT).show();
                }
                break;
            }


        }

    }

    private void homeUI(){
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeight = display.getHeight();

        CardView.MarginLayoutParams cardParams = ( CardView.MarginLayoutParams) cardview.getLayoutParams();
        cardParams.width = (screenWidth * 94)/100;
        cardParams.height = (screenWidth * 47)/100;
        cardview.setLayoutParams(cardParams);

        ViewGroup.MarginLayoutParams textFilesParams = (ViewGroup.MarginLayoutParams) textFiles.getLayoutParams();
        textFilesParams.width = (screenWidth * 80)/100;
        textFilesParams.height = (screenWidth *18)/100;
        //textFilesParams.setMargins(0,(screenWidth*10)/100,0,0);
        textFiles.setLayoutParams(textFilesParams);

        ViewGroup.MarginLayoutParams pdfFilesParams = (ViewGroup.MarginLayoutParams) pdfFiles.getLayoutParams();
        pdfFilesParams.width = (screenWidth*80)/100;
        pdfFilesParams.height = (screenWidth *18)/100;
        //pdfFilesParams.setMargins(0,(screenWidth*10)/100,0,0);
        pdfFiles.setLayoutParams(pdfFilesParams);
 /*
        ViewGroup.MarginLayoutParams scannedFilesParams = (ViewGroup.MarginLayoutParams) scannedFiles.getLayoutParams();
        scannedFilesParams.width = (screenWidth*80)/100;
        scannedFilesParams.height = (screenWidth *18)/100;
        //scannedFilesParams.setMargins(0,(screenWidth*10)/100,0,0);
        scannedFiles.setLayoutParams(scannedFilesParams);

  */

        ViewGroup.MarginLayoutParams textFilesShadowParams = (ViewGroup.MarginLayoutParams) textFilesShadow.getLayoutParams();
        textFilesShadowParams.width = (screenWidth * 80)/100;
        textFilesShadowParams.height = (screenWidth *18)/100;
        //textFilesShadowParams.setMargins(0,(screenWidth*10)/100,0,0);
        textFilesShadow.setLayoutParams(textFilesShadowParams);

        ViewGroup.MarginLayoutParams pdfFilesShadowParams = (ViewGroup.MarginLayoutParams) pdfFilesshadow.getLayoutParams();
        pdfFilesShadowParams.width = (screenWidth*80)/100;
        pdfFilesShadowParams.height = (screenWidth *18)/100;
        //pdfFilesShadowParams.setMargins(0,(screenWidth*10)/100,0,0);
        pdfFilesshadow.setLayoutParams(pdfFilesShadowParams);
/*
        ViewGroup.MarginLayoutParams scannedFilesShadowParams = (ViewGroup.MarginLayoutParams) scannedFilesShadow.getLayoutParams();
        scannedFilesShadowParams.width = (screenWidth*80)/100;
        scannedFilesShadowParams.height = (screenWidth *18)/100;
        //scannedFilesShadowParams.setMargins(0,(screenWidth*10)/100,0,0);
        scannedFilesShadow.setLayoutParams(scannedFilesShadowParams);
 */

    }

}






/*
            <LinearLayout
                android:id="@+id/scanned_button_shadow"
                android:layout_width="match_parent"
                android:layout_height="81dp"
                android:layout_marginLeft="40dp"
                android:layout_marginTop="42dp"
                android:layout_marginRight="38dp"
                android:background="@drawable/main_button_bg_grey"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/pdf_button" />

            <LinearLayout
                android:id="@+id/scanned_button"
                android:layout_width="match_parent"
                android:layout_height="80dp"
                android:layout_marginLeft="40dp"
                android:layout_marginTop="40dp"
                android:layout_marginRight="40dp"
                android:background="@drawable/main_button_bg"
                app:layout_constraintEnd_toEndOf="parent"
                app:layout_constraintStart_toStartOf="parent"
                app:layout_constraintTop_toBottomOf="@+id/pdf_button">

                <ImageView
                    android:layout_width="60dp"
                    android:layout_height="60dp"
                    android:layout_alignParentStart="true"
                    android:layout_alignParentLeft="true"
                    android:layout_alignParentTop="true"
                    android:layout_alignParentBottom="true"
                    android:layout_marginLeft="20dp"
                    android:layout_marginTop="10dp"
                    android:layout_marginBottom="10dp"
                    android:src="@drawable/folder_icon_3" />

                <LinearLayout
                    android:layout_width="wrap_content"
                    android:layout_height="80dp"
                    android:layout_marginLeft="20dp"
                    android:gravity="center"
                    android:orientation="vertical">


                    <TextView
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:fontFamily="@font/raleway_semibold"
                        android:gravity="start"
                        android:text="Scanned Files"
                        android:textColor="@color/black"
                        android:textSize="25dp" />


                    <LinearLayout
                        android:layout_width="wrap_content"
                        android:layout_height="wrap_content"
                        android:orientation="horizontal"
                        android:layout_gravity="start">

                        <TextView
                            android:id="@+id/getScanedFilesBadge"
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:layout_gravity="center"
                            android:layout_marginRight="5dp"
                            android:gravity="center"
                            android:text="0"
                            android:textColor="@color/darkGrey"
                            app:layout_constraintEnd_toEndOf="parent"
                            app:layout_constraintTop_toTopOf="parent" />

                        <TextView
                            android:layout_width="wrap_content"
                            android:layout_height="wrap_content"
                            android:layout_gravity="start"
                            android:fontFamily="@font/raleway_semibold"
                            android:text="Scanned fies"
                            android:textColor="@color/darkGrey" />
                    </LinearLayout>


                </LinearLayout>
            </LinearLayout>

 */








