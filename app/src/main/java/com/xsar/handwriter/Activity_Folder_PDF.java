package com.xsar.handwriter;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Activity_Folder_PDF extends AppCompatActivity{


    public ArrayList<String> filenames;
    public ArrayList<String> filepaths;
    public ArrayList<Long> filedate;
    public ArrayList<Long> fileSize;

    public File renamefrom;
    public int renameSelected,deleteSelected;
    SwipeRefreshLayout swipeRefreshLayoutPdf;

    LinearLayout renamelayoutPdf,deleteLayoutPdf;
    Button renameokPdf, filenamecancelPdf, deleteOk, deleteCancel;
    View renameBgPdf, deleteBgPdf;
    EditText fileNamePdf;
    boolean savingPdf = false;
    private Animation fadeIn, fadeOut, fadeIn2, fadeOut2, fromRight,toRight ;
    String fileName;
    String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__folder__pdf);

        Toolbar toolbar = findViewById(R.id.toolbarPDF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Saved Pdf Files");


        renamelayoutPdf = findViewById(R.id.renamelayoutPdf);
        deleteLayoutPdf = findViewById(R.id.deletePdf);
        deleteOk = findViewById(R.id.deleteOk);
        deleteCancel = findViewById(R.id.deleteCancel);
        renameokPdf = findViewById(R.id.renameokPdf);
        filenamecancelPdf = findViewById(R.id.renamecancelPdf);
        renameBgPdf = findViewById(R.id.renameBgPdf);
        deleteBgPdf = findViewById(R.id.deleteBgPdf);
        fileNamePdf = findViewById(R.id.filenamePdf);

        fadeIn2 = AnimationUtils.loadAnimation(Activity_Folder_PDF.this, R.anim.fadein_slow);
        fadeOut2 = AnimationUtils.loadAnimation(Activity_Folder_PDF.this, R.anim.fadeout_slow);
        fadeIn = AnimationUtils.loadAnimation(Activity_Folder_PDF.this, R.anim.fadein);
        fadeOut = AnimationUtils.loadAnimation(Activity_Folder_PDF.this, R.anim.fadeout);
        fromRight = AnimationUtils.loadAnimation(Activity_Folder_PDF.this, R.anim.enter_from_right);
        toRight = AnimationUtils.loadAnimation(Activity_Folder_PDF.this, R.anim.exit_to_right);

        filenames = new ArrayList<String>();
        filepaths = new ArrayList<String>();
        filedate = new ArrayList<Long>();
        fileSize = new ArrayList<Long>();

        renameUI();

        filePath = Environment.getExternalStorageDirectory().getPath() + "/HandWriter/Pdf Files/";
        File myFolder = new File(filePath);
        if (!myFolder.exists()) {
            myFolder.mkdirs();
        }
        for (File f : myFolder.listFiles()) {
            if (f.isFile()) {
                filenames.add(f.getName());
                filepaths.add(f.getAbsolutePath());
                filedate.add(f.lastModified());
                fileSize.add((f.length()/1024));
            }
        }
        final ListView pdf_files_list = (ListView) findViewById(R.id.pdf_files_list);
        final Activity_Folder_PDF.customAdapter customAdap = new Activity_Folder_PDF.customAdapter();
        pdf_files_list.setAdapter(customAdap);

        swipeRefreshLayoutPdf = findViewById(R.id.swipeRefreshPdf);
        swipeRefreshLayoutPdf.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                filenames.clear();
                filepaths.clear();
                filedate.clear();
                fileSize.clear();

                File myFolder = new File(filePath);
                if (!myFolder.exists()) {
                    myFolder.mkdirs();
                }

                for (File f : myFolder.listFiles()) {
                    if (f.isFile()) {
                        filenames.add(f.getName());
                        filepaths.add(f.getAbsolutePath());
                        filedate.add(f.lastModified());
                        fileSize.add(f.length());
                    }
                }

                Activity_Folder_PDF.customAdapter customAdap3 = new customAdapter();
                pdf_files_list.setAdapter(customAdap3);
                pdf_files_list.setItemsCanFocus(false);
                swipeRefreshLayoutPdf.setRefreshing(false);
            }
        });


        renameokPdf.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                saveToPdfFilePdf();
                renamelayoutPdf.startAnimation(fadeOut);
                renamelayoutPdf.setVisibility(View.INVISIBLE);
                renameBgPdf.startAnimation(fadeOut2);
                renameBgPdf.setVisibility(View.INVISIBLE);
                savingPdf = false;
                customAdap.notifyDataSetChanged();
            }
        });

        filenamecancelPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renamelayoutPdf.startAnimation(fadeOut);
                renamelayoutPdf.setVisibility(View.INVISIBLE);
                renamelayoutPdf.startAnimation(fadeOut2);
                renameBgPdf.setVisibility(View.INVISIBLE);
                savingPdf = false;
            }
        });

        renameBgPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renamelayoutPdf.startAnimation(fadeOut);
                renameBgPdf.startAnimation(fadeOut2);
                renamelayoutPdf.setVisibility(View.INVISIBLE);
                renameBgPdf.setVisibility(View.INVISIBLE);
                savingPdf = false;
            }
        });

        deleteBgPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLayoutPdf.startAnimation(fadeOut);
                deleteLayoutPdf.setVisibility(View.INVISIBLE);
                deleteBgPdf.startAnimation(fadeOut2);
                deleteBgPdf.setVisibility(View.INVISIBLE);
            }
        });

        deleteOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFile();
                deleteLayoutPdf.startAnimation(fadeOut);
                deleteLayoutPdf.setVisibility(View.INVISIBLE);
                deleteBgPdf.startAnimation(fadeOut2);
                deleteBgPdf.setVisibility(View.INVISIBLE);
                customAdap.notifyDataSetChanged();
                Toast.makeText(Activity_Folder_PDF.this,  "file deleted", Toast.LENGTH_SHORT).show();
            }
        });

        deleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLayoutPdf.startAnimation(fadeOut);
                deleteLayoutPdf.setVisibility(View.INVISIBLE);
                deleteBgPdf.startAnimation(fadeOut2);
                deleteBgPdf.setVisibility(View.INVISIBLE);
            }
        });


    }

    class customAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return filenames.size();
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

            view = getLayoutInflater().inflate(R.layout.pdf_apps_layout, null);
            ConstraintLayout pdf_file_but = view.findViewById(R.id.pdf_file_but);
            TextView pdf_file_but1 = view.findViewById(R.id.pdf_file_but1);
            pdf_file_but1.setText(filenames.get(i));

            TextView pdf_file_details = view.findViewById(R.id.pdf_file_details);
            TextView pdf_file_size = view.findViewById(R.id.pdf_file_size);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss | ");
            pdf_file_details.setText(String.valueOf(sdf.format(filedate.get(i))));

            long size = 0;
            String unit;
            size = fileSize.get(i);
            if(size > 1024){
                size = size/1024;
                if(size>1024){
                    size = size/1024;
                    unit = "MB";
                } else{
                    unit = "KB";
                }
            } else {
                unit = "B";
            }
            pdf_file_size.setText( size + unit);

            final ImageButton showMenuButton = view.findViewById(R.id.showMenu);
            final ImageButton menuClose = view.findViewById(R.id.menuClose);
            final ImageButton renameMenu = view.findViewById(R.id.renameButtonMenu);
            final ImageButton deleteMenu = view.findViewById(R.id.deleteButtonMenu);
            final ImageButton shareMenu = view.findViewById(R.id.shareButtonMenu);;
            final View MenuOptionsBg = view.findViewById(R.id.MenuOptionsBgText);


            pdf_file_but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(filepaths.get(i)), "application/pdf");
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Intent intent1 = Intent.createChooser(intent, "Open With");
                    try {
                        startActivity(intent1);
                    } catch(ActivityNotFoundException e){
                        Toast.makeText(Activity_Folder_PDF.this, "Oops, No application found", Toast.LENGTH_SHORT).show();
                    }
                }
            });




            showMenuButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MenuOptionsBg.setVisibility(View.VISIBLE);
                    showMenuButton.startAnimation(fadeOut);
                    showMenuButton.setVisibility(View.INVISIBLE);

                    menuClose.startAnimation(fadeIn2);
                    menuClose.setVisibility(View.VISIBLE);

                    renameMenu.startAnimation(fromRight);
                    renameMenu.setVisibility(View.VISIBLE);

                    deleteMenu.startAnimation(fromRight);
                    deleteMenu.setVisibility(View.VISIBLE);

                    shareMenu.startAnimation(fromRight);
                    shareMenu.setVisibility(View.VISIBLE);
                }
            });

            MenuOptionsBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MenuOptionsBg.setVisibility(View.INVISIBLE);
                    showMenuButton.startAnimation(fadeIn2);
                    showMenuButton.setVisibility(View.VISIBLE);

                    menuClose.startAnimation(fadeOut);
                    menuClose.setVisibility(View.INVISIBLE);

                    renameMenu.startAnimation(toRight);
                    renameMenu.setVisibility(View.INVISIBLE);

                    deleteMenu.startAnimation(toRight);
                    deleteMenu.setVisibility(View.INVISIBLE);

                    shareMenu.startAnimation(toRight);
                    shareMenu.setVisibility(View.INVISIBLE);
                }
            });

            menuClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MenuOptionsBg.setVisibility(View.INVISIBLE);
                    showMenuButton.startAnimation(fadeIn2);
                    showMenuButton.setVisibility(View.VISIBLE);

                    menuClose.startAnimation(fadeOut);
                    menuClose.setVisibility(View.INVISIBLE);

                    renameMenu.startAnimation(toRight);
                    renameMenu.setVisibility(View.INVISIBLE);

                    deleteMenu.startAnimation(toRight);
                    deleteMenu.setVisibility(View.INVISIBLE);

                    shareMenu.startAnimation(toRight);
                    shareMenu.setVisibility(View.INVISIBLE);

                }
            });

            renameMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    renamelayoutPdf.startAnimation(fadeIn);
                    renameBgPdf.startAnimation(fadeIn2);
                    renamelayoutPdf.setVisibility(View.VISIBLE);
                    renameBgPdf.setVisibility(View.VISIBLE);
                    renamefrom = new File(filePath,filenames.get(i));
                    renameSelected = i;

                    showMenuButton.startAnimation(fadeIn2);
                    showMenuButton.setVisibility(View.VISIBLE);

                    menuClose.startAnimation(fadeOut);
                    menuClose.setVisibility(View.INVISIBLE);

                    renameMenu.startAnimation(toRight);
                    renameMenu.setVisibility(View.INVISIBLE);

                    deleteMenu.startAnimation(toRight);
                    deleteMenu.setVisibility(View.INVISIBLE);

                    shareMenu.startAnimation(toRight);
                    shareMenu.setVisibility(View.INVISIBLE);

                }
            });

            deleteMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteLayoutPdf.startAnimation(fadeIn);
                    deleteBgPdf.startAnimation(fadeIn2);
                    deleteLayoutPdf.setVisibility(View.VISIBLE);
                    deleteSelected = i;
                    deleteBgPdf.setVisibility(View.VISIBLE);

                    showMenuButton.startAnimation(fadeIn2);
                    showMenuButton.setVisibility(View.VISIBLE);

                    menuClose.startAnimation(fadeOut);
                    menuClose.setVisibility(View.INVISIBLE);

                    renameMenu.startAnimation(toRight);
                    renameMenu.setVisibility(View.INVISIBLE);

                    deleteMenu.startAnimation(toRight);
                    deleteMenu.setVisibility(View.INVISIBLE);

                    shareMenu.startAnimation(toRight);
                    shareMenu.setVisibility(View.INVISIBLE);

                }
            });

            shareMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendIntent = new Intent();
                    sendIntent.setAction(Intent.ACTION_SEND);
                    sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(filePath + "/" + filenames.get(i)));
                    sendIntent.setType("pdf/*");

                    Intent shareIntent = Intent.createChooser(sendIntent, null);
                    startActivity(shareIntent);

                    showMenuButton.startAnimation(fadeIn2);
                    showMenuButton.setVisibility(View.VISIBLE);

                    menuClose.startAnimation(fadeOut);
                    menuClose.setVisibility(View.INVISIBLE);

                    renameMenu.startAnimation(toRight);
                    renameMenu.setVisibility(View.INVISIBLE);

                    deleteMenu.startAnimation(toRight);
                    deleteMenu.setVisibility(View.INVISIBLE);

                    shareMenu.startAnimation(toRight);
                    shareMenu.setVisibility(View.INVISIBLE);

                }
            });



            return view;
        }

    }





    private void saveToPdfFilePdf() {
        String renameas = fileNamePdf.getText().toString();
        if (renameas.length() > 0){
            File renameto = new File(filePath,renameas+".pdf");
            renamefrom.renameTo(renameto);
            filenames.set(renameSelected,renameas + ".pdf");
        }

    }

    private void renameUI(){
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeiht = display.getHeight();

        LinearLayout.MarginLayoutParams renameparams = (LinearLayout.MarginLayoutParams) renamelayoutPdf.getLayoutParams();
        renameparams.width = (screenWidth*80)/100;
        renamelayoutPdf.setLayoutParams(renameparams);

        LinearLayout.MarginLayoutParams deleteparams = (LinearLayout.MarginLayoutParams) deleteLayoutPdf.getLayoutParams();
        deleteparams.width = (screenWidth*70)/100;
        deleteLayoutPdf.setLayoutParams(deleteparams);
    }

    private void deleteFile(){
        File toDelete = new File (filePath, filenames.get(deleteSelected));
        toDelete.delete();
        filenames.remove(deleteSelected);
        filepaths.remove(deleteSelected);
        filedate.remove(deleteSelected);
        fileSize.remove(deleteSelected);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (savingPdf) {
            renamelayoutPdf.startAnimation(fadeOut);
            renamelayoutPdf.setVisibility(View.INVISIBLE);
            renamelayoutPdf.startAnimation(fadeOut2);
            renameBgPdf.setVisibility(View.INVISIBLE);
            savingPdf = false;
        } else {
            finish();
        }
    }



}
















    /*
    public void showMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.dropdown_options);
        popupMenu.show();

    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.rename:

                break;

            case R.id.delete:


                break;


            case R.id.share:
                finish();

                break;

        }

        return true;
    }
    }
*/
