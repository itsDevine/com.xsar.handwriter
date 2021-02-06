package com.xsar.handwriter;

import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.smarteist.autoimageslider.SliderViewAdapter;
import com.xsar.handwriter.ui.home.HomeFragment;

public class ImageSliderAdapter extends SliderViewAdapter<SliderViewHolder> {

    HomeFragment context;
    int setTotalCount;
    String imageList, url;

    public ImageSliderAdapter(HomeFragment context, int setTotalCount) {
        this.context = context;
        this.setTotalCount=setTotalCount;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_layout, parent,false);
        return new SliderViewHolder(view);
    }

    /*
    @Override
    public void onBindViewHolder(final SliderViewHolder viewHolder, final int position) {
        FirebaseDatabase.getInstance().getReference("Image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                switch (position) {

                    case 0:
                        imageList = snapshot.child("1").child("image").getValue().toString();
                        url = snapshot.child("4").child("url").getValue().toString();
                        Glide.with(viewHolder.itemView).load(imageList).into(viewHolder.imageView);
                        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                context.startActivity(intent);
                            }
                        });
                        break;

                    case 1:
                        imageList = snapshot.child("2").child("image").getValue().toString();
                        url = snapshot.child("1").child("url").getValue().toString();
                        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                context.startActivity(intent);
                            }
                        });
                        Glide.with(viewHolder.itemView).load(imageList).into(viewHolder.imageView);
                        break;

                    case 2:
                        imageList = snapshot.child("3").child("image").getValue().toString();
                        url = snapshot.child("2").child("url").getValue().toString();
                        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                context.startActivity(intent);
                            }
                        });
                        Glide.with(viewHolder.itemView).load(imageList).into(viewHolder.imageView);
                        break;

                    case 3:
                        imageList = snapshot.child("4").child("image").getValue().toString();
                        url = snapshot.child("3").child("url").getValue().toString();
                        Glide.with(viewHolder.itemView).load(imageList).into(viewHolder.imageView);
                        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                context.startActivity(intent);
                            }
                        });
                        break;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

     */

    //l,f,nlkfd b,fdlbdflb,fbldfnlbmfd. .szknsgn

    @Override
    public void onBindViewHolder(final SliderViewHolder viewHolder, final int position) {
        FirebaseDatabase.getInstance().getReference("TestImage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                switch (position) {

                    case 0:
                        imageList = snapshot.child("1").child("image").getValue().toString();
                        url = snapshot.child("4").child("url").getValue().toString();
                        Glide.with(viewHolder.itemView).load(imageList).into(viewHolder.imageView);
                        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                context.startActivity(intent);
                            }
                        });
                        break;

                    case 1:
                        imageList = snapshot.child("2").child("image").getValue().toString();
                        url = snapshot.child("1").child("url").getValue().toString();
                        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                context.startActivity(intent);
                            }
                        });
                        Glide.with(viewHolder.itemView).load(imageList).into(viewHolder.imageView);
                        break;

                    case 2:
                        imageList = snapshot.child("3").child("image").getValue().toString();
                        url = snapshot.child("2").child("url").getValue().toString();
                        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                context.startActivity(intent);
                            }
                        });
                        Glide.with(viewHolder.itemView).load(imageList).into(viewHolder.imageView);
                        break;

                    case 3:
                        imageList = snapshot.child("4").child("image").getValue().toString();
                        url = snapshot.child("3").child("url").getValue().toString();
                        Glide.with(viewHolder.itemView).load(imageList).into(viewHolder.imageView);
                        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                                context.startActivity(intent);
                            }
                        });
                        break;
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public int getCount() {
        return setTotalCount;
    }
}

class SliderViewHolder extends SliderViewAdapter.ViewHolder {

    ImageView imageView;
    View itemView;


    public SliderViewHolder(View itemView) {
        super(itemView);
        this.itemView=itemView;
        imageView = itemView.findViewById(R.id.imageView);
/*
        Display display =
        ViewGroup.MarginLayoutParams imageParams = (ViewGroup.MarginLayoutParams) imageView.getLayoutParams();
        imageParams.height =


 */
    }
}