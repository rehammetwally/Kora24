package com.rehammetwally.kora24.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.rehammetwally.kora24.models.CompetitionMatch;
import com.rehammetwally.kora24.models.Country;
import com.rehammetwally.kora24.models.GamesList;
import com.rehammetwally.kora24.models.MatchList;
import com.rehammetwally.kora24.models.Message;
import com.rehammetwally.kora24.models.MessageMatch;
import com.rehammetwally.kora24.models.MessageSeasons;
import com.rehammetwally.kora24.models.Team;
import com.rehammetwally.kora24.models.Tournaments;
import com.rehammetwally.kora24.models.UpdateResult;
import com.rehammetwally.kora24.repositories.TournamentsRepository;

import java.util.List;

public class TournamentsViewModel extends ViewModel {
    private TournamentsRepository tournamentsRepository;
    private static final String TAG = "TournamentsViewModel";


    public TournamentsViewModel() {
        tournamentsRepository = new TournamentsRepository();
    }

    public LiveData<List<Tournaments>> showCompetitions() {
        return tournamentsRepository.showCompetitions();
    }

    public LiveData<Tournaments> getCompetitionDetails(int competition_id) {
        return tournamentsRepository.getCompetitionDetails(competition_id);
    }

    public LiveData<CompetitionMatch> getCompetitionGames(int competition_id) {
        return tournamentsRepository.getCompetitionGames(competition_id);
    }

    public LiveData<Message> addCompetition(String competition, String photo) {
        return tournamentsRepository.addCompetition(competition, photo);
    }
    public LiveData<Message> addCountry(String country_name){
        return tournamentsRepository.addCountry(country_name);
    }

    public LiveData<Message> addCompetitionTeam(int team_id, int compitation_id) {
        return tournamentsRepository.addCompetitionTeam(team_id, compitation_id);
    }

    public LiveData<MessageMatch> getGameInfo(int game_id) {
        return tournamentsRepository.getGameInfo(game_id);
    }

    public LiveData<MessageSeasons> showSeasons() {
        return tournamentsRepository.showSeasons();
    }

    public MutableLiveData<List<Team>> showAllTeams() {
        return tournamentsRepository.showAllTeams();
    }

    public LiveData<Message> addGame(int home_team_id, int away_team_id, String match_time,
                                          String match_date, String TV_channel, String commentor,
                                          String stage, String stadium, int season_id, int competition_id) {
        return tournamentsRepository.addGame(home_team_id, away_team_id, match_time, match_date, TV_channel, commentor, stage, stadium, season_id, competition_id);
    }

    public LiveData<MatchList> showGames() {
        return tournamentsRepository.showGames();
    }

    public LiveData<UpdateResult> updateAwayResult(int gid, int away_team_goal) {
        return tournamentsRepository.updateAwayResult(gid, away_team_goal);
    }

    public LiveData<UpdateResult> updateHomeResult(int gid, int home_team_goal) {
        return tournamentsRepository.updateHomeResult(gid, home_team_goal);
    }

    public LiveData<Message> addSeason(String season) {
        return tournamentsRepository.addSeason(season);
    }

    public LiveData<Message> addTeam(String team, String t_logo, int country_id) {
        return tournamentsRepository.addTeam(team, t_logo, country_id);
    }

    public LiveData<MatchList> showGamesDate() {
        return tournamentsRepository.showGamesDate();
    }

    public LiveData<MatchList> getGamesSchedule(String date) {
        return tournamentsRepository.getGamesSchedule(date);
    }
    public LiveData<GamesList> getGamesByDate(String date) {
        return tournamentsRepository.getGamesByDate(date);
    }

    public LiveData<List<Country>> showCountries() {
        return tournamentsRepository.showCountries();
    }


}