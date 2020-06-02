package com.rehammetwally.kora24.databinding;

import android.widget.ImageView;


import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rehammetwally.kora24.R;

public class ImageResource {
    @BindingAdapter({ "avatar" })
    public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions())
                .load(imageURL)
                .placeholder(R.drawable.loading)
                .into(imageView);
    }
    @BindingAdapter({ "avatar" })
    public static void loadImage(ImageView imageView, int drawable) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions())
                .load(drawable)
                .placeholder(R.drawable.loading)
                .into(imageView);
    }
}
