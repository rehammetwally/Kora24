package com.rehammetwally.kora24.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rehammetwally.kora24.data.APIHelper;
import com.rehammetwally.kora24.data.Api;
import com.rehammetwally.kora24.models.CompetitionMatch;
import com.rehammetwally.kora24.models.Message;
import com.rehammetwally.kora24.models.Team;
import com.rehammetwally.kora24.models.Tournaments;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteRepository {
    private Api service;
    private static final String TAG = "FavouriteRepository";

    public FavouriteRepository() {
        if (this.service == null)
            this.service = APIHelper.getApiService();
    }

    public MutableLiveData<List<Team>> showFavouriteTeams() {
        final MutableLiveData<List<Team>> data = new MutableLiveData<>();

        Call<List<Team>> call = service.showFavouriteTeams();
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "showFavouriteTeamsResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "showFavouriteTeamsResponse:error " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Log.e(TAG, "showFavouriteTeamsFailure: " + t.getMessage());
            }
        });
        return data;
    }
    public MutableLiveData<List<Team>> showUnFavouriteTeams() {
        final MutableLiveData<List<Team>> data = new MutableLiveData<>();

        Call<List<Team>> call = service.showUnFavouriteTeams();
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "showUnFavouriteTeamsResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "showUnFavouriteTeamsResponse:error " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Log.e(TAG, "showUnFavouriteTeamsFailure: " + t.getMessage());
            }
        });
        return data;
    }



    public MutableLiveData<List<Team>> showAllTeams() {
        final MutableLiveData<List<Team>> data = new MutableLiveData<>();

        Call<List<Team>> call = service.showAllTeams();
        call.enqueue(new Callback<List<Team>>() {
            @Override
            public void onResponse(Call<List<Team>> call, Response<List<Team>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "showAllTeamsResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "showAllTeamsResponse:error " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Log.e(TAG, "showAllTeamsFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<String> setFavourite(int team_id) {
        final MutableLiveData<String> data = new MutableLiveData<>();

        Call<String> call = service.setFavourite(team_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "setFavouriteResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "setFavouriteResponse:error " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "setFavouriteFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public MutableLiveData<String> setUnFavourite(int team_id) {
        final MutableLiveData<String> data = new MutableLiveData<>();

        Call<String> call = service.setUnFavourite(team_id);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "setUnFavouriteResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "setUnFavouriteResponse:error " + response.message());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "setUnFavouriteFailure: " + t.getMessage());
            }
        });
        return data;
    }
}
