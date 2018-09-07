package com.evgeniy.test.behancer.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class CustomBindingAdapter {

    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String urlImage){
        Picasso.with(imageView.getContext())
                .load(urlImage)
                .into(imageView);
    }


}
