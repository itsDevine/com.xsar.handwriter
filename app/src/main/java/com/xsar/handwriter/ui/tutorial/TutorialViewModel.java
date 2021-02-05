package com.xsar.handwriter.ui.tutorial;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TutorialViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TutorialViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("AN OVERVIEW ON HOW TO USE...\n" +
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
    }

    public LiveData<String> getText() {
        return mText;
    }
}