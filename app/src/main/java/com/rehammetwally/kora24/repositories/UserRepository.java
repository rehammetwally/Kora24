package com.rehammetwally.kora24.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rehammetwally.kora24.data.APIHelper;
import com.rehammetwally.kora24.data.Api;
import com.rehammetwally.kora24.models.Comments;
import com.rehammetwally.kora24.models.MatchList;
import com.rehammetwally.kora24.models.Message;
import com.rehammetwally.kora24.models.NewsReation;
import com.rehammetwally.kora24.models.User;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private Api service;
    private static final String TAG = "UserRepository";

    public UserRepository() {
        if (this.service == null)
            this.service = APIHelper.getApiService();
    }


    public LiveData<User> login(String email, String password) {
        final MutableLiveData<User> data = new MutableLiveData<>();
        Call<User> call = service.login(email, password);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + response.body().data.email);
                    data.setValue(response.body());
                } else {
                    User user = new User();
                    user.data = null;
                    user.message = response.message();
                    data.setValue(user);
                    Log.e(TAG, "onResponse:error " + user.message);
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                User user = new User();
                user.data = null;
                user.message = t.getMessage();
                data.setValue(user);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<String> setLikeOrDislike(int var, int user_id, int news_id) {
        final MutableLiveData<String> data = new MutableLiveData<>();
        Call<String> call = service.setLikeOrDislike(var, user_id, news_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<String> removeLikeOrDislike(int user_id, int news_id) {
        final MutableLiveData<String> data = new MutableLiveData<>();
        Call<String> call = service.removeLikeOrDislike(user_id, news_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<String> setReplayLikeOrDislike(int var, int user_id, int comment_id) {
        final MutableLiveData<String> data = new MutableLiveData<>();
        Call<String> call = service.setReplayLikeOrDislike(var, user_id, comment_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<String> removeReplayLikeOrDislike(int user_id, int comment_id) {
        final MutableLiveData<String> data = new MutableLiveData<>();
        Call<String> call = service.removeReplayLikeOrDislike(user_id, comment_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<String> addNewsView(int news_id) {
        final MutableLiveData<String> data = new MutableLiveData<>();
        Call<String> call = service.addNewsView(news_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<NewsReation> showNewsReation(int news_id) {
        final MutableLiveData<NewsReation> data = new MutableLiveData<>();
        Call<NewsReation> call = service.showNewsReation(news_id);
        call.enqueue(new Callback<NewsReation>() {
            @Override
            public void onResponse(Call<NewsReation> call, Response<NewsReation> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<NewsReation> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<Message> addComment(String comment, int user_id, int news_id) {
        final MutableLiveData<Message> data = new MutableLiveData<>();
        Call<Message> call = service.addComment(comment, user_id, news_id);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
//                    User user=new User();
//                    user.data=null;
//                    user.message=response.message();
//                    data.setValue(user);
                    Log.e(TAG, "onResponse:error " + response.body().message);
                }
                Log.e(TAG, "onResponse:addComment " + response);
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
//                User user=new User();
//                user.data=null;
//                user.message=t.getMessage();
//                data.setValue(user);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<Message> addReply(String comment, int user_id, int parent_id, int news_id) {
        final MutableLiveData<Message> data = new MutableLiveData<>();
        Call<Message> call = service.addReply(comment, user_id, parent_id, news_id);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    data.postValue(response.body());
                } else {
//                    User user=new User();
//                    user.data=null;
//                    user.message=response.message();
//                    data.setValue(user);
                    Log.e(TAG, "onResponse:error " + response.body().message);
                }
                Log.e(TAG, "onResponse:addReply " + response);
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
//                User user=new User();
//                user.data=null;
//                user.message=t.getMessage();
//                data.setValue(user);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<Comments> showCommentsOfNews(int news_id) {
        final MutableLiveData<Comments> data = new MutableLiveData<>();
        Call<Comments> call = service.showCommentsOfNews(news_id);
        call.enqueue(new Callback<Comments>() {
            @Override
            public void onResponse(Call<Comments> call, Response<Comments> response) {
                if (response.isSuccessful()) {
                    data.setValue(response.body());
                } else {
//                    User user=new User();
//                    user.data=null;
//                    user.message=response.message();
//                    data.setValue(user);
                    Log.e(TAG, "onResponse:error " + response.body().message);
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<Comments> call, Throwable t) {
//                User user=new User();
//                user.data=null;
//                user.message=t.getMessage();
//                data.setValue(user);
                t.printStackTrace();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<User> register(String name, String email, String password, String photo) {
        final MutableLiveData<User> data = new MutableLiveData<>();
        Log.e(TAG, "register:photo " + photo);
        //pass it like this
        File file = new File(photo);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photo", file.getName(), requestFile);

        Call<User> call = service.register(name, email, password, body);
        Log.e(TAG, "register: " + body.body());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + response.body().data.email);
                    data.setValue(response.body());
                } else {
                    User user = new User();
                    user.message = response.message();
                    data.setValue(user);
                    Log.e(TAG, "onResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                User user = new User();
                user.message = t.getMessage();
                data.setValue(user);
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return data;
    }

}
