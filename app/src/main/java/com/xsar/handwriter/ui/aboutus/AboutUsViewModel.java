package com.xsar.handwriter.ui.aboutus;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AboutUsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AboutUsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("XSAR\n" +
                "First of all XSAR is pronounced as 'zar', \n" +
                "is a new company formed by a bunch of youngsters who does not like the flaws of the society and are trying to make a difference.\n" +
                "\n" +
                        "\n" +
                "WHO IS US?\n" +
                "\n" +
                "We wanted to make our lives easier and we thought why just us and not everyone else. We wish for others what we wish for ourselves. We love to see you here being a part of our mission.\n" +
                "\n" + "\n" +
                "HOW WE LIKE TO PROCEED?\n" +
                "\n" +
                "The main Motto of our company is to solve the problems. World is full of problems and are waiting to be solved. We like to be a part of solution the world needs.\n" +
                "\n" + "\n" +
                "ABOUT YOU?\n" +
                "\n" +
                "We like you to be our audience in all our missions and solutions. If you are reading this, know that you are awesome, because very few people open 'ABOUT US' page in an app.\n" +
                "\n" + "\n" +
                "To Support us And for Updates follow us on Instagram.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}