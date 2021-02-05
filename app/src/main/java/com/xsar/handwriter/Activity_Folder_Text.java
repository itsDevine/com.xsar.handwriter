package com.xsar.handwriter;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.MenuItem;
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
import android.widget.PopupMenu;
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

public class Activity_Folder_Text extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public ArrayList<String> filenames;
    public ArrayList<String> filepaths;
    public ArrayList<Long> filedate;
    public ArrayList<Long> fileSize;
    public ArrayList<File> filesfolder;
    public File renamefrom;
    public int renameSelected,deleteSelected;
    ListView text_files_list;
    SwipeRefreshLayout swipeRefreshLayout;

    LinearLayout renamelayoutText,deleteLayout;
    Button renameokText, filenamecancelText, deleteOk, deleteCancel;
    View renameBgText, deleteBgText;
    EditText fileNameText;
    boolean savingText = false;
    private Animation fadeIn, fadeOut, fadeIn2, fadeOut2, fromRight,toRight ;
    String fileName;
    String filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__folder__text);

        Toolbar toolbar = findViewById(R.id.toolbarText);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Saved Text Files");

        renamelayoutText = findViewById(R.id.renamelayoutText);
        deleteLayout = findViewById(R.id.deleteText);
        deleteOk = findViewById(R.id.deleteOk);
        deleteCancel = findViewById(R.id.deleteCancel);
        renameokText = findViewById(R.id.renameokText);
        filenamecancelText = findViewById(R.id.renamecancelText);
        renameBgText = findViewById(R.id.renameBgText);
        deleteBgText = findViewById(R.id.deleteBgText);
        fileNameText = findViewById(R.id.filenameText);
        text_files_list = (ListView) findViewById(R.id.text_files_list);

        fadeIn2 = AnimationUtils.loadAnimation(Activity_Folder_Text.this, R.anim.fadein_slow);
        fadeOut2 = AnimationUtils.loadAnimation(Activity_Folder_Text.this, R.anim.fadeout_slow);
        fadeIn = AnimationUtils.loadAnimation(Activity_Folder_Text.this, R.anim.fadein);
        fadeOut = AnimationUtils.loadAnimation(Activity_Folder_Text.this, R.anim.fadeout);
        fromRight = AnimationUtils.loadAnimation(Activity_Folder_Text.this, R.anim.enter_from_right);
        toRight = AnimationUtils.loadAnimation(Activity_Folder_Text.this, R.anim.exit_to_right);

        filenames = new ArrayList<String>();
        filepaths = new ArrayList<String>();
        filedate = new ArrayList<Long>();
        fileSize = new ArrayList<Long>();
        filesfolder = new ArrayList<File>();

        renameUI();

        filePath = Environment.getExternalStorageDirectory().getPath() + "/HandWriter/Text Files/";
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
                filesfolder.add(f);
            }
        }


        final customAdapter customAdap = new customAdapter();
        text_files_list.setAdapter(customAdap);
        text_files_list.setItemsCanFocus(false);

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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

                customAdapter customAdap3 = new customAdapter();
                text_files_list.setAdapter(customAdap3);
                text_files_list.setItemsCanFocus(false);
                swipeRefreshLayout.setRefreshing(false);
            }
        });


        renameokText.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                saveToTextFileText();
                renamelayoutText.startAnimation(fadeOut);
                renamelayoutText.setVisibility(View.INVISIBLE);
                renameBgText.startAnimation(fadeOut2);
                renameBgText.setVisibility(View.INVISIBLE);
                savingText = false;
                customAdap.notifyDataSetChanged();
            }
        });

        filenamecancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renamelayoutText.startAnimation(fadeOut);
                renamelayoutText.setVisibility(View.INVISIBLE);
                renamelayoutText.startAnimation(fadeOut2);
                renameBgText.setVisibility(View.INVISIBLE);
                savingText = false;
            }
        });

        renameBgText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                renamelayoutText.startAnimation(fadeOut);
                renameBgText.startAnimation(fadeOut2);
                renamelayoutText.setVisibility(View.INVISIBLE);
                renameBgText.setVisibility(View.INVISIBLE);
                savingText = false;
            }
        });

        deleteBgText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLayout.startAnimation(fadeOut);
                deleteLayout.setVisibility(View.INVISIBLE);
                deleteBgText.startAnimation(fadeOut2);
                deleteBgText.setVisibility(View.INVISIBLE);
            }
        });

        deleteOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFile();
                deleteLayout.startAnimation(fadeOut);
                deleteLayout.setVisibility(View.INVISIBLE);
                deleteBgText.startAnimation(fadeOut2);
                deleteBgText.setVisibility(View.INVISIBLE);
                customAdap.notifyDataSetChanged();
                Toast.makeText(Activity_Folder_Text.this,  "file deleted", Toast.LENGTH_SHORT).show();
            }
        });

        deleteCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteLayout.startAnimation(fadeOut);
                deleteLayout.setVisibility(View.INVISIBLE);
                deleteBgText.startAnimation(fadeOut2);
                deleteBgText.setVisibility(View.INVISIBLE);
            }
        });

    }
/*

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search_menu);
        SearchView view = (SearchView) item.getActionView();
        view.setQueryHint("Type here to search");
        view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {


                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

 */


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

            long size = 0;
            String unit;

            view = getLayoutInflater().inflate(R.layout.text_apps_layout, null);
            ConstraintLayout text_file_but = view.findViewById(R.id.text_file_but);
            TextView text_file_but1 = view.findViewById(R.id.text_file_but1);
            TextView text_file_details = view.findViewById(R.id.text_file_details);
            TextView text_file_size = view.findViewById(R.id.text_file_size);
            final View MenuOptionsBg = view.findViewById(R.id.MenuOptionsBgText);
            text_file_but1.setText(filenames.get(i));

            final ImageButton showMenuButton = view.findViewById(R.id.showMenu);
            final ImageButton menuClose = view.findViewById(R.id.menuClose);
            final ImageButton renameMenu = view.findViewById(R.id.renameButtonMenu);
            final ImageButton deleteMenu = view.findViewById(R.id.deleteButtonMenu);
            final ImageButton shareMenu = view.findViewById(R.id.shareButtonMenu);

            text_file_but.setTag(i);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss | ");
            text_file_details.setText(String.valueOf(sdf.format(filedate.get(i))));
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
            text_file_size.setText( size + unit);


/*
            for (Long k : fileSize)
                if (k<1024){
                    text_file_size.setText(fileSize.get(i) + " KB");
                } else {
                    text_file_size.setText(fileSize.get(i) + " MB");
                }
 */

            text_file_but.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sendFile = new Intent(Activity_Folder_Text.this, com.xsar.handwriter.activity_fab4.class);
                    sendFile.putExtra("FileReady", 4);
                    sendFile.putExtra("path", filepaths.get(i));
                    startActivity(sendFile);

                }
            });
/*
            text_file_but.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (actionMode != null) {
                        return false;
                    }
                    actionMode = startSupportActionMode(actionModeCallBack);
                    view.setSelected(true);
                    return true;
                }
            });

 */

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
                    renamelayoutText.startAnimation(fadeIn);
                    renameBgText.startAnimation(fadeIn2);
                    renamelayoutText.setVisibility(View.VISIBLE);
                    renameBgText.setVisibility(View.VISIBLE);
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
                    deleteLayout.startAnimation(fadeIn);
                    deleteBgText.startAnimation(fadeIn2);
                    deleteLayout.setVisibility(View.VISIBLE);
                    deleteSelected = i;
                    deleteBgText.setVisibility(View.VISIBLE);

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
                    sendIntent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.parse(filesfolder.get(i).getAbsolutePath()));
                    //sendIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+filePath + "/" + filenames.get(i)));
                    sendIntent.setType("text/*");

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



/*
        private ActionMode.Callback actionModeCallBack = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.top_menu, menu);
                mode.setTitle("Choose your option");
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.rename:
                        renameMethod();
                        return true;

                    case R.id.delete:
                        //text_files_list.remove();

                    default:
                        return false;

                }

            }
/*

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                actionMode = null;
            }
*/
/*
           public void bindView (final View view, final Context context, final Cursor cursor){
               int pos = cursor.getPosition();
               boolean selected = ((Activity_Folder_Text)context).text_files_list.isItemChecked(pos);
               if (!selected) {
                   view.setBackgroundResource(R.drawable.color_state_list);
               } else {
                   view.setBackgroundResource(R.drawable.color);
               }
           }

           */

    };



    private void renameUI(){
        Display display = getWindowManager().getDefaultDisplay();
        int screenWidth = display.getWidth();
        int screenHeiht = display.getHeight();

        LinearLayout.MarginLayoutParams renameparams = (LinearLayout.MarginLayoutParams) renamelayoutText.getLayoutParams();
        renameparams.width = (screenWidth*80)/100;
        renamelayoutText.setLayoutParams(renameparams);

        LinearLayout.MarginLayoutParams deleteparams = (LinearLayout.MarginLayoutParams) deleteLayout.getLayoutParams();
        deleteparams.width = (screenWidth*70)/100;
        deleteLayout.setLayoutParams(deleteparams);
    }


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
                renameMethod();
                break;

            case R.id.delete:


                break;


            case R.id.share:
                Intent intent = new Intent(Intent.ACTION_SEND);
                File fileShare = new File(filePath);
                intent.setType("plain/text");
                intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileShare));
                startActivity(Intent.createChooser(intent, "Share..."));
                break;

        }

        return true;
    }

    private void saveToTextFileText() {
        String renameas = fileNameText.getText().toString();
        if (renameas.length() > 0){
            File renameto = new File(filePath,renameas+".txt");
            renamefrom.renameTo(renameto);
            filenames.set(renameSelected,renameas + ".txt");
        }

    }

    private void deleteFile(){
        File toDelete = new File (filePath, filenames.get(deleteSelected));
        toDelete.delete();
        filenames.remove(deleteSelected);
        filepaths.remove(deleteSelected);
        filedate.remove(deleteSelected);
        fileSize.remove(deleteSelected);
    }


    private void renameMethod() {
        renamelayoutText.startAnimation(fadeIn);
        renamelayoutText.setVisibility(View.VISIBLE);
        renameBgText.startAnimation(fadeIn2);
        renameBgText.setVisibility(View.VISIBLE);
        savingText = true;
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        if (savingText) {
            renamelayoutText.startAnimation(fadeOut);
            renamelayoutText.setVisibility(View.INVISIBLE);
            renamelayoutText.startAnimation(fadeOut2);
            renameBgText.setVisibility(View.INVISIBLE);
            savingText = false;
        } else {
            finish();
        }
    }


}


