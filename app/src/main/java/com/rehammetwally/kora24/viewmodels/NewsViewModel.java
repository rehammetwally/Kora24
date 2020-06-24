package com.rehammetwally.kora24.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rehammetwally.kora24.models.News;
import com.rehammetwally.kora24.models.NewsReation;
import com.rehammetwally.kora24.models.SearchResult;
import com.rehammetwally.kora24.models.User;
import com.rehammetwally.kora24.repositories.NewsRepository;
import com.rehammetwally.kora24.repositories.UserRepository;

import java.util.List;

public class NewsViewModel extends ViewModel {
    private NewsRepository newsRepository;

    private static final String TAG = "NewsViewModel";


    public NewsViewModel() {
        newsRepository = new NewsRepository();
        Log.e(TAG, "NewsViewModel: ");
    }

    public LiveData<List<News>> showGeneralNews() {
        return newsRepository.showGeneralNews();
    }

    public LiveData<List<News>> showCompetitionNews(int competition_id) {
        return newsRepository.showCompetitionNews(competition_id);
    }

    public LiveData<News> addNews(String title, String content, String date, String photo) {
        return newsRepository.addNews(title, content, date, photo);
    }
    public LiveData<News> addNewsForCompitation(String title, String content, String date,int competionId, String photo) {
        return newsRepository.addNewsForCompitation(title, content, date,competionId, photo);
    }

    public LiveData<NewsReation> showNewsReation(int news_id)   {
        return newsRepository.showNewsReation(news_id);
    }
    public LiveData<SearchResult> search(String q)   {
        return newsRepository.search(q);
    }

}