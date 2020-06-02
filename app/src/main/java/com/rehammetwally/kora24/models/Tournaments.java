package com.rehammetwally.kora24.models;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Tournaments implements Serializable {
    @SerializedName("id")
    public int id;
    @SerializedName("c_logo")
    public String logo;
    @SerializedName("c_title")
    public String title;
    @SerializedName("created_at")
    public String created_at;
    @SerializedName("updated_at")
    public String updated_at;
    private static final String TAG = "Tournaments";

    public String icon() {
        if (logo != null) {
            Log.e(TAG, "icon: " + "http://kora24life.tk/kora24/public/CompetitionLogo/" + logo);
            return "http://kora24life.tk/kora24/public/CompetitionLogo/" + logo;
        }
        return null;
    }


    public int hashCode() {
        return id;
    }

    public String toString() {
        return title;
    }

}
