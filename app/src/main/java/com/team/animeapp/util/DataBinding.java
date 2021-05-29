package com.team.animeapp.util;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;

public class DataBinding {
    @BindingAdapter("app:imageUrl")
    public static void loadImage(ImageView view, String url) {
        Glide.with(view)
                .load(url)
                .into(view);
    }

}
