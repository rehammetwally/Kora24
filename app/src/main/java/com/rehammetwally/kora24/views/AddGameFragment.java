package com.rehammetwally.kora24.views;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.CompetitionsSpinnerAdapter;
import com.rehammetwally.kora24.adapters.CustomSpinnerAdapter;
import com.rehammetwally.kora24.databinding.CustomSpinnerItemsBinding;
import com.rehammetwally.kora24.databinding.FragmentAddGameBinding;
import com.rehammetwally.kora24.models.CompetitionMatch;
import com.rehammetwally.kora24.models.Message;
import com.rehammetwally.kora24.models.MessageMatch;
import com.rehammetwally.kora24.models.MessageSeasons;
import com.rehammetwally.kora24.models.Season;
import com.rehammetwally.kora24.models.Team;
import com.rehammetwally.kora24.models.Tournaments;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.utils.StringsUtils;
import com.rehammetwally.kora24.viewmodels.TournamentsViewModel;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddGameFragment extends Fragment implements View.OnClickListener {
    private FragmentAddGameBinding binding;
    private TournamentsViewModel tournamentsViewModel;
    private static final String TAG = "AddGameFragment";
    private CustomSpinnerAdapter adapter;
    private Calendar calendar;
    private int year, month, day, hour, minute;
    private int seasonId, compitationId, homeTeamId, awayTeamId;
    private String matchTime, matchDate, tvChannel, commentor, stage, stadium;
    private TimePickerDialog timePickerDialog;

    public AddGameFragment() {
        // Required empty public constructor
    }
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_game, container, false);
        View view = binding.getRoot();
        tournamentsViewModel = ViewModelProviders.of(getActivity()).get(TournamentsViewModel.class);
        tournamentsViewModel.showCompetitions().observe(getActivity(), new Observer<List<Tournaments>>() {
            @Override
            public void onChanged(List<Tournaments> tournaments) {

                Tournaments tournament = new Tournaments();
                tournament.title = getResources().getString(R.string.add_compitation_title);
                tournament.id = 0;
                tournaments.add(0, tournament);
                adapter = new CustomSpinnerAdapter(getContext(), tournaments);
                binding.compitationTeamSpinner.setAdapter(adapter);
            }
        });
        tournamentsViewModel.showAllTeams().observe(getActivity(), new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                List<Team> teamsList = new ArrayList<>();
                teamsList.addAll(teams);
                Team firstTeam = new Team();
                firstTeam.t_name = getResources().getString(R.string.home_team);
                firstTeam.id = 0;
                teamsList.add(0, firstTeam);
                adapter = new CustomSpinnerAdapter(getContext(), teamsList);
                binding.homeTeamSpinner.setAdapter(adapter);
                teamsList = new ArrayList<>();
                teamsList.addAll(teams);
                Team secondTeam = new Team();
                secondTeam.t_name = getResources().getString(R.string.away_team);
                secondTeam.id = 0;
                teamsList.add(0, secondTeam);
                adapter = new CustomSpinnerAdapter(getContext(), teamsList);
                binding.awayTeamSpinner.setAdapter(adapter);
            }
        });
        binding.selectGameDate.setOnClickListener(this);
        binding.selectGameTime.setOnClickListener(this);
        binding.addGameButton.setOnClickListener(this);
        tournamentsViewModel.showSeasons().observe(getActivity(), new Observer<MessageSeasons>() {
            @Override
            public void onChanged(MessageSeasons messageSeasons) {
                List<Season> seasons = messageSeasons.season;
                Season season = new Season();
                season.s_title = getResources().getString(R.string.add_game_season);
                season.id = 0;
                seasons.add(0, season);
                adapter = new CustomSpinnerAdapter(getContext(), seasons);
                binding.seasonSpinner.setAdapter(adapter);
                Log.e(TAG, "onChanged: " + messageSeasons.season.size());
            }
        });

//        binding.compitationTeamSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                competitionId = (int) parent.getItemIdAtPosition(position);
//                Log.e(TAG, "onItemSelected: " + parent.getItemAtPosition(position).toString());
//                Log.e(TAG, "onItemSelected: " + competitionId);
//                tournamentsViewModel.getCompetitionGames(competitionId).observe(getActivity(), new Observer<CompetitionMatch>() {
//                    @Override
//                    public void onChanged(CompetitionMatch competitionMatch) {
//                        Log.e(TAG, "onChanged: " + competitionMatch.match.size());
//                        adapter = new CustomSpinnerAdapter(getContext(), competitionMatch.match);
//                        binding.homeTeamSpinner.setAdapter(adapter);
//                        binding.awayTeamSpinner.setAdapter(adapter);
//                    }
//                });
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });


        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.select_game_date:
                binding.progressBar.setVisibility(View.GONE);
                Log.e(TAG, "onClick:select ");
                showDialog(999).show();
                break;
            case R.id.select_game_time:
                binding.progressBar.setVisibility(View.GONE);
                calendar = Calendar.getInstance();
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
                // Launch Time Picker Dialog
                timePickerDialog = new TimePickerDialog(getContext(),
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                binding.gameTime.setVisibility(View.VISIBLE);
                                matchTime = StringsUtils.setTime(hourOfDay , minute);
                                TimeZone timeZone=TimeZone.getDefault();
                                String time=timeZone.getDisplayName(false, TimeZone.SHORT);
                                Log.e(TAG, "onTimeSet: "+ time);
                                binding.gameTime.setText(StringsUtils.updateTime(matchTime, "مساءا","صباحا") + " ("+time+")");
                            }
                        }, hour, minute, false);
                timePickerDialog.show();

                break;
            case R.id.add_game_button:
                seasonId = (int) binding.seasonSpinner.getSelectedItemId();
                compitationId = (int) binding.compitationTeamSpinner.getSelectedItemId();
                homeTeamId = (int) binding.homeTeamSpinner.getSelectedItemId();
                homeTeamId = (int) binding.homeTeamSpinner.getSelectedItemId();
                awayTeamId = (int) binding.awayTeamSpinner.getSelectedItemId();
//                int hour = timePickerDialog..getHour();
//                int minute = timePickerDialog.getMinute();
//                matchTime = binding.gameTime.getText().toString();
//                matchDate = binding.gameDate.getText().toString();
                tvChannel = binding.tvChannelEdittext.getText().toString();
                commentor = binding.commentorEdittext.getText().toString();
                stage = binding.stageEdittext.getText().toString();
                stadium = binding.stadiumEdittext.getText().toString();
                if (seasonId == 0) {
                    MyApplication.showMessageBottom(v, getResources().getString(R.string.error_select) + " " + getResources().getString(R.string.add_game_stage));
                    return;
                }
                if (compitationId == 0) {
                    MyApplication.showMessageBottom(v, getResources().getString(R.string.error_select) + " " + getResources().getString(R.string.add_compitation_title));
                    return;
                }
                if (homeTeamId == 0) {
                    MyApplication.showMessageBottom(v, getResources().getString(R.string.error_select) + " " + getResources().getString(R.string.home_team));
                    return;
                }
                if (awayTeamId == 0) {
                    MyApplication.showMessageBottom(v, getResources().getString(R.string.error_select) + " " + getResources().getString(R.string.away_team));
                    return;
                }
                if (matchTime == null) {
                    MyApplication.showMessageBottom(v, getResources().getString(R.string.add_game_time) + getResources().getString(R.string.error_message_empty));
                    return;
                }
                if (matchDate == null) {
                    MyApplication.showMessageBottom(v, getResources().getString(R.string.add_game_date) + getResources().getString(R.string.error_message_empty));
                    return;
                }
                if (tvChannel.isEmpty()) {
                    MyApplication.showMessageBottom(v, getResources().getString(R.string.add_game_tv_channel) + getResources().getString(R.string.error_message_empty));
                    return;
                }
                if (commentor.isEmpty()) {
                    MyApplication.showMessageBottom(v, getResources().getString(R.string.add_game_commentor) + getResources().getString(R.string.error_message_empty));
                    return;
                }
                if (stage.isEmpty()) {
                    MyApplication.showMessageBottom(v, getResources().getString(R.string.add_game_stage) + getResources().getString(R.string.error_message_empty));
                    return;
                }
                if (stadium.isEmpty()) {
                    MyApplication.showMessageBottom(v, getResources().getString(R.string.add_game_stadium) + getResources().getString(R.string.error_message_empty));
                    return;
                }
                Log.e(TAG, "onClick:seasonId " + seasonId);
                Log.e(TAG, "onClick:compitationId " + compitationId);
                Log.e(TAG, "onClick: " + homeTeamId);
                Log.e(TAG, "onClick: " + awayTeamId);
                Log.e(TAG, "onClick: " + matchTime);
                Log.e(TAG, "onClick: " + matchDate);
                Log.e(TAG, "onClick: " + tvChannel);
                Log.e(TAG, "onClick: " + commentor);
                Log.e(TAG, "onClick: " + stage);
                Log.e(TAG, "onClick: " + stadium);
                Log.e(TAG, "onClick: " + seasonId);
                Log.e(TAG, "onClick: " + compitationId);

                tournamentsViewModel.addGame(homeTeamId, awayTeamId, matchTime, matchDate, tvChannel, commentor, stage, stadium, seasonId, compitationId).observe(getActivity(), new Observer<Message>() {
                    @Override
                    public void onChanged(Message message) {
//                        Log.e(TAG, "onChanged: " + message.match);
                        if (message.message.equals("Game added successfully")){
                            MyApplication.showMessageBottom(v, getResources().getString(R.string.add_game_success));
                            binding.awayTeamSpinner.setSelection(0);
                            binding.homeTeamSpinner.setSelection(0);
                            binding.compitationTeamSpinner.setSelection(0);
                            binding.seasonSpinner.setSelection(0);
                            binding.stadiumEdittext.setText("");
                            binding.stageEdittext.setText("");
                            binding.commentorEdittext.setText("");
                            binding.tvChannelEdittext.setText("");
                            binding.gameTime.setVisibility(View.GONE);
                            binding.gameDate.setVisibility(View.GONE);
                        }
                    }
                });
                break;
        }
    }


    public Dialog showDialog(int id) {
        if (id == 999) {
            calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getContext(),
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2 + 1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        String monthStr = String.valueOf(month);
        String dayStr = String.valueOf(day);
        if (month < 10) {
            monthStr = "0" + month;
        }
        if (day < 10) {
            dayStr = "0" + day;
        }
        binding.gameDate.setVisibility(View.VISIBLE);
        matchDate = new StringBuilder().append(year).append("-")
                .append(monthStr).append("-").append(dayStr).toString();
        Log.e(TAG, "showDate: "+matchDate );
        SimpleDateFormat inFormat = new SimpleDateFormat("dd-MM-yyyy", new Locale("ar"));
        Date date = null;
        try {
            date = inFormat.parse(matchDate);
//            matchDate = date.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE", new Locale("ar"));
        String goal = outFormat.format(date);
        Log.e(TAG, "showDate: " + goal);
        binding.gameDate.setText(goal + " ( " + matchDate + " ) ");
    }

}
