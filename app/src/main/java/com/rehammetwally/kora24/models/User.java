package com.rehammetwally.kora24.models;

import android.util.Patterns;

import androidx.databinding.BaseObservable;

import com.google.gson.annotations.SerializedName;

public class User extends BaseObservable {
    @SerializedName("user")
    public UserData data;

    @SerializedName("message")
    public String message;

    public class UserData{
        @SerializedName("email")
        public String email;
        @SerializedName("name")
        public String name;
        @SerializedName("photo")
        public String photo;
        @SerializedName("remember_token")
        public String remember_token;
        @SerializedName("user_type")
        public int user_type;
        @SerializedName("id")
        public int id;
        @SerializedName("updated_at")
        public String updated_at;
        @SerializedName("created_at")
        public String created_at;

    }




//
//    public boolean isEmailValid() {
//        return Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches();
//    }
//
//
//    public boolean isPasswordLength() {
//        return getPassword().length() >= 6;
//    }
}
