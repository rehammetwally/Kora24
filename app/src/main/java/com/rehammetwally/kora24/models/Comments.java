
package com.rehammetwally.kora24.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

//
//
//import com.google.gson.annotations.SerializedName;
//
//import java.util.List;
//
public class Comments {
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

        ////
        public class Replies {
            //            public List<Reply> replyList;
//
//            public class Reply {
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

                return "http://kora24life.tk/kora24/public/User/" + photo;
            }
        }


        //
        public String icon() {

            return "http://kora24life.tk/kora24/public/User/" + photo;
        }

    }
}
//
////    public int id;
////    public int image;
////    public String commentPerson;
////    public String commentTime;
////    public String commentText;
////    public int likes;
////    public int dislikes;
////
////    public Comments(int image, String commentPerson, String commentTime, String commentText, int likes, int dislikes) {
////        this.image = image;
////        this.commentPerson = commentPerson;
////        this.commentTime = commentTime;
////        this.commentText = commentText;
////        this.likes = likes;
////        this.dislikes = dislikes;
////    }
////}
//import java.util.HashMap;
//import java.util.Map;
//
//
//        public class Comment {
//
//            @JsonProperty("id")
//            private Integer id;
//            @JsonProperty("comment")
//            private String comment;
//            @JsonProperty("parent_id")
//            private Object parentId;
//            @JsonProperty("user_id")
//            private Integer userId;
//            @JsonProperty("news_id")
//            private Integer newsId;
//            @JsonProperty("name")
//            private String name;
//            @JsonProperty("photo")
//            private String photo;
//            @JsonProperty("replies")
//            private List<List<Reply>> replies = null;
//            @JsonProperty("likes")
//            private Integer likes;
//            @JsonProperty("dislikes")
//            private Integer dislikes;
//            @JsonIgnore
//            private Map<String, Object> additionalProperties = new HashMap<String, Object>();
//
//            @JsonProperty("id")
//            public Integer getId() {
//                return id;
//            }
//
//            @JsonProperty("id")
//            public void setId(Integer id) {
//                this.id = id;
//            }
//
//            @JsonProperty("comment")
//            public String getComment() {
//                return comment;
//            }
//
//            @JsonProperty("comment")
//            public void setComment(String comment) {
//                this.comment = comment;
//            }
//
//            @JsonProperty("parent_id")
//            public Object getParentId() {
//                return parentId;
//            }
//
//            @JsonProperty("parent_id")
//            public void setParentId(Object parentId) {
//                this.parentId = parentId;
//            }
//
//            @JsonProperty("user_id")
//            public Integer getUserId() {
//                return userId;
//            }
//
//            @JsonProperty("user_id")
//            public void setUserId(Integer userId) {
//                this.userId = userId;
//            }
//
//            @JsonProperty("news_id")
//            public Integer getNewsId() {
//                return newsId;
//            }
//
//            @JsonProperty("news_id")
//            public void setNewsId(Integer newsId) {
//                this.newsId = newsId;
//            }
//
//            @JsonProperty("name")
//            public String getName() {
//                return name;
//            }
//
//            @JsonProperty("name")
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            @JsonProperty("photo")
//            public String getPhoto() {
//                return photo;
//            }
//
//            @JsonProperty("photo")
//            public void setPhoto(String photo) {
//                this.photo = photo;
//            }
//
//            @JsonProperty("replies")
//            public List<List<Reply>> getReplies() {
//                return replies;
//            }
//
//            @JsonProperty("replies")
//            public void setReplies(List<List<Reply>> replies) {
//                this.replies = replies;
//            }
//
//            @JsonProperty("likes")
//            public Integer getLikes() {
//                return likes;
//            }
//
//            @JsonProperty("likes")
//            public void setLikes(Integer likes) {
//                this.likes = likes;
//            }
//
//            @JsonProperty("dislikes")
//            public Integer getDislikes() {
//                return dislikes;
//            }
//
//            @JsonProperty("dislikes")
//            public void setDislikes(Integer dislikes) {
//                this.dislikes = dislikes;
//            }
//
//            @JsonAnyGetter
//            public Map<String, Object> getAdditionalProperties() {
//                return this.additionalProperties;
//            }
//
//            @JsonAnySetter
//            public void setAdditionalProperty(String name, Object value) {
//                this.additionalProperties.put(name, value);
//            }
//
//        }
//-----------------------------------com.example.Example.java-----------------------------------
//
//                package com.example;
//
//        import java.util.HashMap;
//        import java.util.List;
//        import java.util.Map;
//        import com.fasterxml.jackson.annotation.JsonAnyGetter;
//        import com.fasterxml.jackson.annotation.JsonAnySetter;
//        import com.fasterxml.jackson.annotation.JsonIgnore;
//        import com.fasterxml.jackson.annotation.JsonInclude;
//        import com.fasterxml.jackson.annotation.JsonProperty;
//        import com.fasterxml.jackson.annotation.JsonPropertyOrder;
//
//        @JsonInclude(JsonInclude.Include.NON_NULL)
//        @JsonPropertyOrder({
//                "comments",
//                "message"
//        })
//        public class Example {
//
//            @JsonProperty("comments")
//            private List<Comment> comments = null;
//            @JsonProperty("message")
//            private String message;
//            @JsonIgnore
//            private Map<String, Object> additionalProperties = new HashMap<String, Object>();
//
//            @JsonProperty("comments")
//            public List<Comment> getComments() {
//                return comments;
//            }
//
//            @JsonProperty("comments")
//            public void setComments(List<Comment> comments) {
//                this.comments = comments;
//            }
//
//            @JsonProperty("message")
//            public String getMessage() {
//                return message;
//            }
//
//            @JsonProperty("message")
//            public void setMessage(String message) {
//                this.message = message;
//            }
//
//            @JsonAnyGetter
//            public Map<String, Object> getAdditionalProperties() {
//                return this.additionalProperties;
//            }
//
//            @JsonAnySetter
//            public void setAdditionalProperty(String name, Object value) {
//                this.additionalProperties.put(name, value);
//            }
//
//        }
//
//
//        @JsonInclude(JsonInclude.Include.NON_NULL)
//        @JsonPropertyOrder({
//                "id",
//                "comment",
//                "parent_id",
//                "user_id",
//                "news_id",
//                "name",
//                "photo"
//        })
//        public class Reply {
//
//            @JsonProperty("id")
//            private Integer id;
//            @JsonProperty("comment")
//            private String comment;
//            @JsonProperty("parent_id")
//            private Integer parentId;
//            @JsonProperty("user_id")
//            private Integer userId;
//            @JsonProperty("news_id")
//            private Integer newsId;
//            @JsonProperty("name")
//            private String name;
//            @JsonProperty("photo")
//            private Object photo;
//            @JsonIgnore
//            private Map<String, Object> additionalProperties = new HashMap<String, Object>();
//
//            @JsonProperty("id")
//            public Integer getId() {
//                return id;
//            }
//
//            @JsonProperty("id")
//            public void setId(Integer id) {
//                this.id = id;
//            }
//
//            @JsonProperty("comment")
//            public String getComment() {
//                return comment;
//            }
//
//            @JsonProperty("comment")
//            public void setComment(String comment) {
//                this.comment = comment;
//            }
//
//            @JsonProperty("parent_id")
//            public Integer getParentId() {
//                return parentId;
//            }
//
//            @JsonProperty("parent_id")
//            public void setParentId(Integer parentId) {
//                this.parentId = parentId;
//            }
//
//            @JsonProperty("user_id")
//            public Integer getUserId() {
//                return userId;
//            }
//
//            @JsonProperty("user_id")
//            public void setUserId(Integer userId) {
//                this.userId = userId;
//            }
//
//            @SerializedName("news_id")
//            public Integer getNewsId() {
//                return newsId;
//            }
//
//            @JsonProperty("news_id")
//            public void setNewsId(Integer newsId) {
//                this.newsId = newsId;
//            }
//
//            @JsonProperty("name")
//            public String getName() {
//                return name;
//            }
//
//            @SerializedName("name")
//            public void setName(String name) {
//                this.name = name;
//            }
//
//            @SerializedName("photo")
//            public Object getPhoto() {
//                return photo;
//            }
//
//            @SerializedName("photo")
//            public void setPhoto(Object photo) {
//                this.photo = photo;
//            }
//
//            @JsonAnyGetter
//            public Map<String, Object> getAdditionalProperties() {
//                return this.additionalProperties;
//            }
//
//            @JsonAnySetter
//            public void setAdditionalProperty(String name, Object value) {
//                this.additionalProperties.put(name, value);
//            }
//
//        }