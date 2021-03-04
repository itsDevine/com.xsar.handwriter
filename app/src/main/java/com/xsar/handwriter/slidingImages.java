package com.xsar.handwriter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;

public class slidingImages extends PagerAdapter {

    private Context context1;
    private ArrayList<Bitmap> bitmapArray;
    private int index1;

    slidingImages(Context context, ArrayList<Bitmap> bitmapArrayList, int index){
        context1 = context;
        bitmapArray = bitmapArrayList;
        index1 = index;
    }

    @Override
    public int getCount() {
        return bitmapArray.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(context1);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(bitmapArray.get(position));
        container.addView(imageView,index1);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ImageView) object);
    }
}
