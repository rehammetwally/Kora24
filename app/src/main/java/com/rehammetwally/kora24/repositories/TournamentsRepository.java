package com.rehammetwally.kora24.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rehammetwally.kora24.data.APIHelper;
import com.rehammetwally.kora24.data.Api;
import com.rehammetwally.kora24.models.CompetitionMatch;
import com.rehammetwally.kora24.models.Country;
import com.rehammetwally.kora24.models.GamesList;
import com.rehammetwally.kora24.models.MatchList;
import com.rehammetwally.kora24.models.Message;
import com.rehammetwally.kora24.models.MessageMatch;
import com.rehammetwally.kora24.models.MessageSeasons;
import com.rehammetwally.kora24.models.Team;
import com.rehammetwally.kora24.models.TeamCompetition;
import com.rehammetwally.kora24.models.Tournaments;
import com.rehammetwally.kora24.models.UpdateResult;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TournamentsRepository {
    private Api service;
    private static final String TAG = "TournamentsRepository";

    public TournamentsRepository() {
        if (this.service == null)
            this.service = APIHelper.getApiService();
    }


    public LiveData<List<Tournaments>> showCompetitions() {
        final MutableLiveData<List<Tournaments>> data = new MutableLiveData<>();

        Call<List<Tournaments>> call = service.showCompetitions();
        call.enqueue(new Callback<List<Tournaments>>() {
            @Override
            public void onResponse(Call<List<Tournaments>> call, Response<List<Tournaments>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "showCompetitionsResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "showCompetitionsResponse:error " + response.message());
                }

                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<List<Tournaments>> call, Throwable t) {
                Log.e(TAG, "showCompetitionsFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<Tournaments> getCompetitionDetails(int competition_id) {
        final MutableLiveData<Tournaments> data = new MutableLiveData<>();

        Call<Tournaments> call = service.getCompetitionDetails(competition_id);
        call.enqueue(new Callback<Tournaments>() {
            @Override
            public void onResponse(Call<Tournaments> call, Response<Tournaments> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "getCompetitionDetailsResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "getCompetitionDetailsResponse:error " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Tournaments> call, Throwable t) {
                Log.e(TAG, "getCompetitionDetailsFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<CompetitionMatch> getCompetitionGames(int competition_id) {
        final MutableLiveData<CompetitionMatch> data = new MutableLiveData<>();

        Call<CompetitionMatch> call = service.getCompetitionGames(competition_id);
        call.enqueue(new Callback<CompetitionMatch>() {
            @Override
            public void onResponse(Call<CompetitionMatch> call, Response<CompetitionMatch> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: ");
                    Log.e(TAG, "getCompetitionGamesResponse: " + response.body());
                    data.setValue(response.body());
                    Log.e(TAG, "onResponse: " + response.message());
                } else {
                    Log.e(TAG, "getCompetitionGamesResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<CompetitionMatch> call, Throwable t) {
                Log.e(TAG, "getCompetitionGamesFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<TeamCompetition> showTeamCompetition(int team_id, int competition_id) {
        final MutableLiveData<TeamCompetition> data = new MutableLiveData<>();

        Call<TeamCompetition> call = service.showTeamCompetition(team_id, competition_id);
        call.enqueue(new Callback<TeamCompetition>() {
            @Override
            public void onResponse(Call<TeamCompetition> call, Response<TeamCompetition> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "onResponse: ");
                    Log.e(TAG, "showTeamCompetitionResponse: " + response.body());
                    data.setValue(response.body());
                    Log.e(TAG, "onResponse: " + response.message());
                } else {
                    Log.e(TAG, "showTeamCompetitionResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<TeamCompetition> call, Throwable t) {
                Log.e(TAG, "showTeamCompetitionFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<MessageMatch> getGameInfo(int game_id) {
        final MutableLiveData<MessageMatch> data = new MutableLiveData<>();

        Call<MessageMatch> call = service.getGameInfo(game_id);
        call.enqueue(new Callback<MessageMatch>() {
            @Override
            public void onResponse(Call<MessageMatch> call, Response<MessageMatch> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "getGameInfoResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "getGameInfoResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<MessageMatch> call, Throwable t) {
                Log.e(TAG, "getGameInfoFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<MatchList> showGamesDate() {
        final MutableLiveData<MatchList> data = new MutableLiveData<>();

        Call<MatchList> call = service.showGamesDate();
        call.enqueue(new Callback<MatchList>() {
            @Override
            public void onResponse(Call<MatchList> call, Response<MatchList> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "showGamesDateResponse: " + response);
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "showGamesDateResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<MatchList> call, Throwable t) {
                Log.e(TAG, "showGamesDateFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<MatchList> getGamesSchedule(String date) {
        final MutableLiveData<MatchList> data = new MutableLiveData<>();

        Call<MatchList> call = service.getGamesSchedule(date);
        call.enqueue(new Callback<MatchList>() {
            @Override
            public void onResponse(Call<MatchList> call, Response<MatchList> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "getGamesScheduleResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    MatchList matchList = new MatchList();
                    matchList.message = response.message();
                    data.setValue(matchList);
                    Log.e(TAG, "getGamesScheduleResponse:error " + response.message() + " === " + matchList.message);
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<MatchList> call, Throwable t) {
                Log.e(TAG, "getGamesScheduleFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<GamesList> getGamesByDate(String date) {
        final MutableLiveData<GamesList> data = new MutableLiveData<>();

        Call<GamesList> call = service.getGamesByDate(date);
        call.enqueue(new Callback<GamesList>() {
            @Override
            public void onResponse(Call<GamesList> call, Response<GamesList> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "getGamesByDateResponse: " + response);
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "getGamesByDateResponse:error " + response.message());
                    if (response.body() != null) {
                        if (response.body().games.size() > 0) {
                            data.setValue(response.body());
                        }
                    }
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<GamesList> call, Throwable t) {
                Log.e(TAG, "getGamesByDateFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<MatchList> showGames() {
        final MutableLiveData<MatchList> data = new MutableLiveData<>();

        Call<MatchList> call = service.showGames();
        call.enqueue(new Callback<MatchList>() {
            @Override
            public void onResponse(Call<MatchList> call, Response<MatchList> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "showGamesResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "showGamesResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<MatchList> call, Throwable t) {
                Log.e(TAG, "showGamesFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<MessageSeasons> showSeasons() {
        final MutableLiveData<MessageSeasons> data = new MutableLiveData<>();

        Call<MessageSeasons> call = service.showSeasons();
        call.enqueue(new Callback<MessageSeasons>() {
            @Override
            public void onResponse(Call<MessageSeasons> call, Response<MessageSeasons> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "showSeasonsResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "showSeasonsResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<MessageSeasons> call, Throwable t) {
                Log.e(TAG, "showSeasonsFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<Message> addCompetition(String competition, String photo) {
        final MutableLiveData<Message> data = new MutableLiveData<>();

        File file = new File(photo);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("c_logo", file.getName(), requestFile);
        Call<Message> call = service.addCompetition(competition, body);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "getCompetitionGamesResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "getCompetitionGamesResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e(TAG, "getCompetitionGamesFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<Message> addCountry(String country_name) {
        final MutableLiveData<Message> data = new MutableLiveData<>();
        Call<Message> call = service.addCountry(country_name);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "addCountryResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "addCountryResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e(TAG, "addCountryFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<Message> addCompetitionTeam(int team_id, int compitation_id) {
        final MutableLiveData<Message> data = new MutableLiveData<>();

        Call<Message> call = service.addCompetitionTeam(team_id, compitation_id);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "addCompetitionTeamResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "addCompetitionTeamResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e(TAG, "addCompetitionTeamFailure: " + t.getMessage());
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
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<List<Team>> call, Throwable t) {
                Log.e(TAG, "showAllTeamsFailure: " + t.getMessage());
            }
        });
        return data;
    }


    public LiveData<Message> addGame(int home_team_id, int away_team_id, String match_time,
                                          String match_date, String TV_channel, String commentor,
                                          String stage, String stadium, int season_id, int competition_id) {
        final MutableLiveData<Message> data = new MutableLiveData<>();

        Call<Message> call = service.addGame(home_team_id, away_team_id, match_time, match_date,
                TV_channel, commentor, stage, stadium, season_id, competition_id);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "addGameResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "addGameResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e(TAG, "addGameFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<UpdateResult> updateHomeResult(int gid, int home_team_goal) {
        final MutableLiveData<UpdateResult> data = new MutableLiveData<>();

        Call<UpdateResult> call = service.updateHomeResult(gid, home_team_goal);
        call.enqueue(new Callback<UpdateResult>() {
            @Override
            public void onResponse(Call<UpdateResult> call, Response<UpdateResult> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "updateHomeResultResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "updateHomeResultResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<UpdateResult> call, Throwable t) {
                Log.e(TAG, "updateHomeResultFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<UpdateResult> updateAwayResult(int gid, int away_team_goal) {
        final MutableLiveData<UpdateResult> data = new MutableLiveData<>();

        Call<UpdateResult> call = service.updateAwayResult(gid, away_team_goal);
        call.enqueue(new Callback<UpdateResult>() {
            @Override
            public void onResponse(Call<UpdateResult> call, Response<UpdateResult> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "updateAwayResultResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "updateAwayResultResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<UpdateResult> call, Throwable t) {
                Log.e(TAG, "updateAwayResultFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<Message> addSeason(String season) {
        final MutableLiveData<Message> data = new MutableLiveData<>();

        Call<Message> call = service.addSeason(season);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "addSeasonResponse: " + response.body().message);
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "addSeasonResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e(TAG, "addSeasonFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<Message> addTeam(String team, String t_logo, int country_id) {
        final MutableLiveData<Message> data = new MutableLiveData<>();

        File file = new File(t_logo);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("t_logo", file.getName(), requestFile);
        Call<Message> call = service.addTeam(team, body, country_id);
        call.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "addTeamResponse: " + response.body().message);
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "addTeamResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.e(TAG, "addTeamFailure: " + t.getMessage());
            }
        });
        return data;
    }

    public LiveData<List<Country>> showCountries() {
        final MutableLiveData<List<Country>> data = new MutableLiveData<>();


        Call<List<Country>> call = service.showCountries();
        call.enqueue(new Callback<List<Country>>() {
            @Override
            public void onResponse(Call<List<Country>> call, Response<List<Country>> response) {
                if (response.isSuccessful()) {
                    Log.e(TAG, "showCountriesResponse: " + response.body());
                    data.setValue(response.body());
                } else {
                    Log.e(TAG, "showCountriesResponse:error " + response.message());
                }
                Log.e(TAG, "onResponse: " + response);
            }

            @Override
            public void onFailure(Call<List<Country>> call, Throwable t) {
                Log.e(TAG, "showCountriesFailure: " + t.getMessage());
            }
        });
        return data;
    }
}
