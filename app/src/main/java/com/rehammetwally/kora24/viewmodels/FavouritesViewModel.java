package com.rehammetwally.kora24.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rehammetwally.kora24.models.CompetitionMatch;
import com.rehammetwally.kora24.models.Message;
import com.rehammetwally.kora24.models.Team;
import com.rehammetwally.kora24.models.Tournaments;
import com.rehammetwally.kora24.repositories.FavouriteRepository;
import com.rehammetwally.kora24.repositories.TournamentsRepository;

import java.util.List;

public class FavouritesViewModel extends ViewModel {
    private FavouriteRepository favouriteRepository;
    private static final String TAG = "FavouritesViewModel";


    public FavouritesViewModel() {
        favouriteRepository = new FavouriteRepository();
    }



    public MutableLiveData<List<Team>> showUnFavouriteTeams() {
        return favouriteRepository.showUnFavouriteTeams();
    }
    public MutableLiveData<List<Team>> showAllTeams() {
        return favouriteRepository.showAllTeams();
    }

    public MutableLiveData<List<Team>> showFavouriteTeams() {
        return favouriteRepository.showFavouriteTeams();
    }

    public MutableLiveData<String> setFavourite(int team_id) {
        return favouriteRepository.setFavourite(team_id);
    }

    public MutableLiveData<String> setUnFavourite(int team_id) {
        return favouriteRepository.setUnFavourite(team_id);
    }
}