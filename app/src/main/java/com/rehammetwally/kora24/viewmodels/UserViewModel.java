package com.rehammetwally.kora24.viewmodels;

import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rehammetwally.kora24.models.Comments;
import com.rehammetwally.kora24.models.CommentsReation;
import com.rehammetwally.kora24.models.MatchList;
import com.rehammetwally.kora24.models.Message;
import com.rehammetwally.kora24.models.NewsReation;
import com.rehammetwally.kora24.models.User;
import com.rehammetwally.kora24.repositories.UserRepository;

public class UserViewModel extends ViewModel {
    private UserRepository userRepository;
    private static final String TAG = "UserViewModel";

    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    private MutableLiveData<User> userMutableLiveData;

    public UserViewModel() {
        userRepository = new UserRepository();
        Log.e(TAG, "UserViewModel: ");
    }

    public MutableLiveData<User> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }


    public LiveData<User> register(String name, String email, String password, String photo) {
        Log.e(TAG, "register: " + name);
        Log.e(TAG, "register: " + email);
        Log.e(TAG, "register: " + password);
        Log.e(TAG, "register: " + photo);
        return userRepository.register(name, email, password, photo);
    }

    public LiveData<Message> addComment(String comment, int user_id, int news_id) {
        return userRepository.addComment(comment, user_id, news_id);
    }

    public LiveData<Message> addReply(String comment, int user_id, int parent_id, int news_id) {
        return userRepository.addReply(comment, user_id, parent_id, news_id);
    }

    public LiveData<String> setLikeOrDislike(int var, int user_id, int news_id) {
        return userRepository.setLikeOrDislike(var, user_id, news_id);
    }

    public LiveData<String> removeLikeOrDislike(int user_id, int news_id) {
        return userRepository.removeLikeOrDislike(user_id, news_id);
    }

    public LiveData<String> setReplayLikeOrDislike(int var, int user_id, int comment_id) {
        return userRepository.setReplayLikeOrDislike(var, user_id, comment_id);
    }

    public LiveData<String> removeReplayLikeOrDislike(int user_id, int comment_id) {
        return userRepository.removeReplayLikeOrDislike(user_id, comment_id);
    }

    public LiveData<String> addNewsView(int news_id) {
        return userRepository.addNewsView(news_id);
    }

    public LiveData<NewsReation> showNewsReation(int news_id) {
        return userRepository.showNewsReation(news_id);
    }

    public LiveData<CommentsReation> showCommentsReation(int comment_id) {
        return userRepository.showCommentsReation(comment_id);
    }

    public LiveData<Comments> showCommentsOfNews(int nid) {
        return userRepository.showCommentsOfNews(nid);
    }


    public LiveData<User> login(String email, String password) {
        Log.e(TAG, "login: " + email);
        Log.e(TAG, "login: " + password);
        return userRepository.login(email, password);
    }

}