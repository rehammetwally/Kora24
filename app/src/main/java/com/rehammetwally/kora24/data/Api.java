package com.rehammetwally.kora24.data;

import com.rehammetwally.kora24.models.Comments;
import com.rehammetwally.kora24.models.CommentsReation;
import com.rehammetwally.kora24.models.CompetitionMatch;
import com.rehammetwally.kora24.models.Country;
import com.rehammetwally.kora24.models.GamesList;
import com.rehammetwally.kora24.models.MatchList;
import com.rehammetwally.kora24.models.Message;
import com.rehammetwally.kora24.models.MessageMatch;
import com.rehammetwally.kora24.models.MessageSeasons;
import com.rehammetwally.kora24.models.News;
import com.rehammetwally.kora24.models.NewsReation;
import com.rehammetwally.kora24.models.SearchResult;
import com.rehammetwally.kora24.models.Team;
import com.rehammetwally.kora24.models.TeamCompetition;
import com.rehammetwally.kora24.models.Tournaments;
import com.rehammetwally.kora24.models.UpdateResult;
import com.rehammetwally.kora24.models.User;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @Multipart
    @POST("register")
    Call<User> register(@Query(value = "name", encoded = true) String name,
                        @Query(value = "email", encoded = true) String email,
                        @Query("password") String password,
                        @Part MultipartBody.Part photo);

    @POST("register")
    Call<User> register(@Query(value = "name", encoded = true) String name,
                        @Query(value = "email", encoded = true) String email,
                        @Query("password") String password);

    @POST("addcomment")
    Call<Message> addComment(@Query("comment") String comment, @Query("user_id") int user_id, @Query("news_id") int news_id);


    @POST("addcomment")
    Call<Message> addReply(@Query("comment") String comment, @Query("user_id") int user_id, @Query("parent_id") int parent_id, @Query("news_id") int news_id);

    @POST("addnewsreation")
    Call<String> setLikeOrDislike(@Query("var") int var, @Query("user_id") int user_id, @Query("news_id") int news_id);

    @POST("addcommentreation")
    Call<String> setReplayLikeOrDislike(@Query("var") int var, @Query("user_id") int user_id, @Query("comment_id") int comment);

    @POST("removenewsreation")
    Call<String> removeLikeOrDislike(@Query("user_id") int user_id, @Query("news_id") int news_id);

    @POST("removecommentreation")
    Call<String> removeReplayLikeOrDislike(@Query("user_id") int user_id, @Query("comment_id") int comment_id);

    @POST("addnewview")
    Call<String> addNewsView(@Query("news_id") int news_id);

    @GET("shownewsreation/{news_id}")
    Call<NewsReation> showNewsReation(@Path("news_id") int news_id);

    @GET("getcommentreationcount/{comment_id}")
    Call<CommentsReation> showCommentsReation(@Path("comment_id") int comment_id);

    @POST("login")
    Call<User> login(@Query(value = "email", encoded = true) String email, @Query("password") String password);

    @Multipart
    @POST("addnews")
    Call<News> addNews(@Query("title") String title, @Query("content") String content,
                       @Query("news_date") String news_date,
                       @Part MultipartBody.Part photo);

    @Multipart
    @POST("addnews")
    Call<News> addNewsForCompitation(@Query("title") String title, @Query("content") String content,
                                     @Query("news_date") String news_date,
                                     @Query("compitation_id") int compition_id,
                                     @Part MultipartBody.Part photo);


    @GET("showgeneralnews")
    Call<List<News>> showGeneralNews();

    @GET("showcompetitionnews/{compitation_id}")
    Call<List<News>> showCompetitionNews(@Path("compitation_id") int competition_id);

    @Multipart
    @POST("addcompetition")
    Call<Message> addCompetition(@Query("competition") String competition, @Part MultipartBody.Part c_logo);

    @POST("addcompetionteam")
    Call<Message> addCompetitionTeam(@Query("tid") int tid, @Query("cid") int cid);

    @GET("showcompetitions")
    Call<List<Tournaments>> showCompetitions();

    @GET("showcompetition/{competition_id}")
    Call<Tournaments> getCompetitionDetails(@Query("competition_id") int competition_id);

    @GET("showcountries")
    Call<List<Country>> showCountries();

    @GET("showcountryteams/{country_id}")
    Call<News> showCountryTeams(@Query("country_id") int country_id);

    @POST("addcountry")
    Call<Message> addCountry(@Query("country_name") String country_name);

    @POST("newgoal")
    Call<News> addNewGoal(@Query("gid") int gid, @Query("team") int team);

    @GET("competitiongames/{cid}")
    Call<CompetitionMatch> getCompetitionGames(@Path("cid") int id);


    @GET("gameinfo/{game_id}")
    Call<MessageMatch> getGameInfo(@Path("game_id") int game_id);

    @POST("addgame")
    Call<Message> addGame(
            @Query("home_team_id") int home_team_id,
            @Query("away_team_id") int away_team_id,
            @Query("match_time") String match_time,
            @Query("match_date") String match_date,
            @Query("TV_channel") String TV_channel,
            @Query("commentor") String commentor,
            @Query("stage") String stage,
            @Query("stadium") String stadium,
            @Query("season_id") int season_id,
            @Query("competition_id") int competition_id
    );

    @GET("gamesschedule/{term}")
    Call<MatchList> getGamesSchedule(@Path("term") String date);

    @POST("search")
    Call<SearchResult> search(@Query("term") String term);

    @GET("gamesbydate/{term}")
    Call<GamesList> getGamesByDate(@Path("term") String date);

    @GET("showteamcompetition/{team_id}/{competition_id}")
    Call<TeamCompetition> showTeamCompetition(@Query("team_id") int team_id, @Query("competition_id") int competition_id);

    @GET("favouriteteams")
    Call<List<Team>> showFavouriteTeams();

    @GET("showallteams")
    Call<List<Team>> showAllTeams();

    @GET("unfavouriteteams")
    Call<List<Team>> showUnFavouriteTeams();

    @GET("favourite/{team_id}")
    Call<String> setFavourite(@Path("team_id") int team_id);

    @GET("unfavourite/{team_id}")
    Call<String> setUnFavourite(@Path("team_id") int team_id);

    @GET("showteam/{team_id}")
    Call<Team> showTeam(@Path("team_id") int team_id);

    @GET("showseasons")
    Call<MessageSeasons> showSeasons();

    @POST("addseason")
    Call<Message> addSeason(@Query("season") String season);

    @Multipart
    @POST("addteam")
    Call<Message> addTeam(@Query("team") String team, @Part MultipartBody.Part t_logo, @Query("country_id") int country_id);

    @POST("updateresult")
    Call<UpdateResult> updateHomeResult(@Query("gid") int gid, @Query("home_team_goal") int home_team_goal);

    @POST("updateresult")
    Call<UpdateResult> updateAwayResult(@Query("gid") int gid, @Query("away_team_goal") int away_team_goal);

    @GET("showgames")
    Call<MatchList> showGames();

    @GET("showgamesdate")
    Call<MatchList> showGamesDate();


    @GET("showcomments/{nid}")
    Call<Comments> showCommentsOfNews(@Path("nid") int nid);

    @GET("showcomment/{nid}/{id}")
    Call<Comments.Comment.Replies> showRepliesOfComment(@Query("nid") int nid, @Query("id") int id);
}
