package com.rehammetwally.kora24.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SearchResult {
    @SerializedName("result")
    public List<List<Result>> results;
    @SerializedName("message")
    public String message;




    public class Result{
        @SerializedName("id")
        public int id;
        @SerializedName("t_name")
        public String name;
        @SerializedName("t_logo")
        public String logo;
        @SerializedName("is_favorite")
        public int is_favorite;
        @SerializedName("country_id")
        public int country_id;
        @SerializedName("created_at")
        public String created_at;
        @SerializedName("updated_at")
        public String update_at;


        public String icon(){
            return "http://kora24life.tk/kora24/public/TeamLogo/"+logo;
        }
    }
}
