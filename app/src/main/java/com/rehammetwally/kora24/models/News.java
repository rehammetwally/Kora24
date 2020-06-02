package com.rehammetwally.kora24.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.annotations.SerializedName;
import com.rehammetwally.kora24.R;

import java.io.Serializable;
import java.util.List;

public class News implements Serializable {
    @SerializedName("id")
    public int id;
    @SerializedName("compitation_id")
    public int compitation_id;
    @SerializedName("photo")
    public String photo;
    @SerializedName("content")
    public String content;
    @SerializedName("title")
    public String title;
    @SerializedName("news_date")
    public String news_date;
    public int comments;
    @SerializedName("views")
    public int views;
    @SerializedName("dislikes_num")
    public int dislikes_num;
    @SerializedName("likes_num")
    public int likes_num;
    public boolean isHead;
    @SerializedName("updated_at")
    public String updated_at;
    @SerializedName("created_at")
    public String created_at;


    public int getImageResource() {
        if (views < 1000) {//blue
            return R.drawable.less_views;
        } else if (views > 1000 && views < 10000) {//orange
            return R.drawable.mid_views;
        } else if (views > 10000) {//red
            return R.drawable.most_views;
        } else {
            return R.drawable.less_views;
        }
    }

    //    http://kora24.atwebpages.com/public/Img
    public String icon() {

        return "http://kora24life.tk/kora24/public/Img/" + photo;
    }

}
