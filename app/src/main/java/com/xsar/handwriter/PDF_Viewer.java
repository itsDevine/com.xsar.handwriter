package com.xsar.handwriter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.ScrollBar;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

import java.io.File;

public class PDF_Viewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        PDFView view = findViewById(R.id.pdfViewer);
        ScrollBar scroll = findViewById(R.id.pdfScrollbar);
        view.setScrollBar(scroll);
        scroll.setHorizontal(true);
        view.useBestQuality(true);

        Intent intent = this.getIntent();
        String path = intent.getExtras().getString("PATH");

        File file = new File(path);

        if (file.canRead()){
            view.fromFile(file).defaultPage(1).onLoad(new OnLoadCompleteListener() {
                @Override
                public void loadComplete(int nbPages) {
                    Toast.makeText(PDF_Viewer.this, String.valueOf(nbPages), Toast.LENGTH_SHORT).show();
                }
            }).load();
        }
    }
}