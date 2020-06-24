
package com.rehammetwally.kora24.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Comments {
    private static final String TAG = "Comments";
    @SerializedName("comments")
    public List<Comment> comments;
    @SerializedName("message")
    public String message;

    public class Comment {


        @SerializedName("id")
        public int id;
        @SerializedName("comment")
        public String comment;
        @SerializedName("likes")
        public int likes;
        @SerializedName("dislikes")
        public int dislikes;
        @SerializedName("parent_id")
        public Integer parent_id;
        @SerializedName("user_id")
        public int user_id;
        @SerializedName("news_id")
        public int news_id;
        @SerializedName("name")
        public String name;
        @SerializedName("photo")
        public String photo;
        @SerializedName("comment_date")
        public String comment_date;
        @SerializedName("replies")
        public List<List<Replies>> replies;


        public class Replies {
            @SerializedName("id")
            public int id;
            @SerializedName("comment")
            public String comment;
            @SerializedName("parent_id")
            public int parent_id;
            @SerializedName("user_id")
            public int user_id;
            @SerializedName("news_id")
            public int news_id;
            @SerializedName("name")
            public String name;
            @SerializedName("photo")
            public String photo;
            @SerializedName("likes")
            public int likes;
            @SerializedName("comment_date")
            public String comment_date;
            @SerializedName("dislikes")
            public int dislikes;

            public String icon() {
                if (photo == null) {
                    return name;
                } else {
                    return "http://kora24life.tk/kora24/public/User/" + photo;
                }
            }
        }


        //
        public String icon() {
            if (photo == null) {
                return name;
            } else {
                return "http://kora24life.tk/kora24/public/User/" + photo;
            }
        }

    }
}