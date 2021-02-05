package com.xsar.handwriter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class WalkThroughSliderAdapter extends PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public WalkThroughSliderAdapter(Context context ) {
        this.context = context;

    }

    public  int[] slide_images = {
      R.drawable.car, R.drawable.car1, R.drawable.car2, R.drawable.car3

    };

    public String[] slide_heading = {
      "EAT" , "SLEEP" , "REPEAT"

    };

    public String[] slide_description = {
            "dfsfsdfbfbdffb gfdgs sfgfds dffdgf gf" ,
            "xbdfbfdbfdsbfbfs fds gfdgfdf  sff fg fdsg fgf" ,
            "grgr rgrgfdsg gfgdf fd d fgf ffgsd s gfdsg fds gdf fdg"
    };

    @Override
    public int getCount() {
        return slide_heading.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.walkthrough_slider_layout, container,  false);

        ImageView sliderImages = view.findViewById(R.id.sliderimageView);
        TextView textViewHeading = view.findViewById(R.id.headingtextView);
        TextView textViewDescription = view.findViewById(R.id.descriptiontextView);

        sliderImages.setImageResource(slide_images[position]);
        textViewHeading.setText(slide_heading[position]);
        textViewDescription.setText(slide_description[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
