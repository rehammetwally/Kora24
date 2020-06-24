package com.rehammetwally.kora24.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rehammetwally.kora24.data.APIHelper;
import com.rehammetwally.kora24.data.Api;
import com.rehammetwally.kora24.models.News;
import com.rehammetwally.kora24.models.NewsReation;
import com.rehammetwally.kora24.models.SearchResult;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private Api service;
    private static final String TAG = "NewsRepository";

    public NewsRepository() {
        if (this.service == null)
            this.service = APIHelper.getApiService();
    }


    public LiveData<News> addNews(String title, String content, String date, String photo) {
        final MutableLiveData<News> data = new MutableLiveData<>();
        File file = new File(photo);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photo", file.getName(), requestFile);

        Call<News> call = service.addNews(title, content, date, body);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "onResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<SearchResult> search(String query) {
        MutableLiveData<SearchResult> data = new MutableLiveData<>();
        Call<SearchResult> call = service.search(query);
        call.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "onResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<News> addNewsForCompitation(String title, String content, String date, int competitionId, String photo) {
        final MutableLiveData<News> data = new MutableLiveData<>();
        File file = new File(photo);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("photo", file.getName(), requestFile);

        Call<News> call = service.addNewsForCompitation(title, content, date, competitionId, body);
        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "onResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
        return data;
    }


    public LiveData<List<News>> showGeneralNews() {
        final MutableLiveData<List<News>> data = new MutableLiveData<>();

        Call<List<News>> call = service.showGeneralNews();
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "showGeneralNewsonResponse: " + response.body());
                    data.postValue(response.body());
                } else {
                    Log.e(TAG, "showGeneralNewsonResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.e(TAG, "showGeneralNewsonFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<List<News>> showCompetitionNews(int competition_id) {
        final MutableLiveData<List<News>> data = new MutableLiveData<>();

        Call<List<News>> call = service.showCompetitionNews(competition_id);
        call.enqueue(new Callback<List<News>>() {
            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "showCompetitionNewsonResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "showCompetitionNewsonResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.e(TAG, "showCompetitionNewsonFailure: " + t.getMessage());
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

}
