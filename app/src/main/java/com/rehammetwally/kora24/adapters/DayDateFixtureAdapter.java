package com.rehammetwally.kora24.adapters;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.ClubLogoItemBinding;
import com.rehammetwally.kora24.databinding.DayDateItemRowBinding;
import com.rehammetwally.kora24.models.GamesList;
import com.rehammetwally.kora24.models.Match;
import com.rehammetwally.kora24.models.Team;
import com.rehammetwally.kora24.viewmodels.TournamentsViewModel;
import com.rehammetwally.kora24.views.MatchesDatesDialogFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DayDateFixtureAdapter extends RecyclerView.Adapter<DayDateFixtureAdapter.DayDateFixtureViewHolder> {
    private Context context;
    private static final String TAG = "DayDateFixtureAdapter";
    private List<Match> games;
    private TournamentsViewModel tournamentsViewModel;
    public DaysSpinnerAdapter daysSpinnerAdapter;
    public ArrayList<Match> days;
    public Spinner spinner;
    private String matchDate;
    private Calendar calendar;
    private int year, month, day, hour, minute;
    public DayDateFixtureAdapter(Context context, List<Match> games, TournamentsViewModel tournamentsViewModel) {
        this.context = context;
        this.games = games;
        this.tournamentsViewModel = tournamentsViewModel;
        days = new ArrayList<Match>(games);
        for (int i = 0; i < days.size(); i++) {
            Log.e(TAG, "DayDateFixtureAdapter: " + days.get(i).match_date);
        }
        daysSpinnerAdapter =
                new DaysSpinnerAdapter(context, days);
    }


    @NonNull
    @Override
    public DayDateFixtureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DayDateItemRowBinding dayDateItemRowBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.day_date_item_row, parent, false);
        return new DayDateFixtureViewHolder(dayDateItemRowBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull DayDateFixtureViewHolder holder, int position) {
        Match match = games.get(position);
        holder.dayDateItemRowBinding.setGame(match);
        myDateListener = new
                DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker arg0,
                                          int arg1, int arg2, int arg3) {
                        showDate(arg1, arg2 + 1, arg3,holder);
                    }
                };
        holder.dayDateItemRowBinding.daysLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick:showDialog "+match.match_date );
                showDialog(999,
                        Integer.parseInt(match.match_date.split("-")[0]),
                        Integer.parseInt(match.match_date.split("-")[1]),
                        Integer.parseInt(match.match_date.split("-")[2])).show();
//                MatchesDatesDialogFragment dialog = new MatchesDatesDialogFragment();
//                                    Bundle bundle=new Bundle();
//                                    bundle.putSerializable("DAYS",days);
//                                    dialog.setArguments(bundle);
//                                    dialog.show(((FragmentActivity)context).getSupportFragmentManager(), "Days");
            }
        });
        holder.dayDateItemRowBinding.fixturesMatchesList.setHasFixedSize(true);
        holder.dayDateItemRowBinding.fixturesMatchesList.setLayoutManager(new LinearLayoutManager(context));
        tournamentsViewModel.getGamesByDate(match.match_date).observe((LifecycleOwner) context, new Observer<GamesList>() {
            @Override
            public void onChanged(GamesList gamesList) {
                Log.e(TAG, "onChanged:gamesSize " + gamesList.games.size());

//                for (int i = 0; i < gamesList.games.size(); i++) {
//                    Log.e(TAG, "c_title: " + gamesList.games.get(i).tournaments.title);
//                }
                MatchesFixturesListAdapter matchesFixturesListAdapter = new MatchesFixturesListAdapter(context, gamesList.games);
                holder.dayDateItemRowBinding.fixturesMatchesList.setAdapter(matchesFixturesListAdapter);
                matchesFixturesListAdapter.notifyDataSetChanged();
                Log.e(TAG, "onChanged:gamesSizeAdapter " + matchesFixturesListAdapter.getItemCount());
            }
        });


    }

    public Dialog showDialog(int id,int year,int month,int day) {
        if (id == 999) {
            calendar = Calendar.getInstance();
            calendar.set(year, month-1, day);
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(context,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener ;

    private void showDate(int year, int month, int day,DayDateFixtureViewHolder holder) {
        String monthStr = String.valueOf(month);
        String dayStr = String.valueOf(day);
        if (month < 10) {
            monthStr = "0" + month;
        }
        if (day < 10) {
            dayStr = "0" + day;
        }
//        binding.gameDate.setVisibility(View.VISIBLE);
        matchDate = new StringBuilder().append(year).append("-")
                .append(monthStr).append("-").append(dayStr).toString();
        Log.e(TAG, "showDate: "+matchDate );
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = input.parse(matchDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat("EEEE", new Locale("ar"));
        String daystr = outFormat.format(date);

        SimpleDateFormat output = new SimpleDateFormat("dd MMM yyyy", new Locale("ar"));
        Calendar calendar = Calendar.getInstance();
        String weekDay = outFormat.format(calendar.getTime());
        String weekDate = output.format(calendar.getTime());
        String dateStr = output.format(date);

        Log.e(TAG, "day: " + dateStr);
        Log.e(TAG, "day: " + weekDate);
        if (weekDay.equals(daystr) && weekDate.equals(dateStr)) {
            daystr = "اليوم";
        }
        Log.e(TAG, "day: " + daystr);
        holder.dayDateItemRowBinding.day.setText(daystr);
//        date = null;
//        try {
//            date = input.parse(matchDate);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        output = new SimpleDateFormat("dd MMM yyyy", new Locale("ar"));
         dayStr = output.format(date);
        holder.dayDateItemRowBinding.date.setText(dayStr);
        tournamentsViewModel.getGamesByDate(matchDate).observe((LifecycleOwner) context, new Observer<GamesList>() {
            @Override
            public void onChanged(GamesList gamesList) {
                Log.e(TAG, "onChanged:gamesSize " + gamesList.games.size());

                for (int i = 0; i < gamesList.games.size(); i++) {
                    Log.e(TAG, "c_title: " + gamesList.games.get(i).c_title);
                }
                if (gamesList.games.size()>0) {
                    holder.dayDateItemRowBinding.noMatchesToday.setVisibility(View.GONE);
                    holder.dayDateItemRowBinding.fixturesMatchesList.setVisibility(View.VISIBLE);
                    MatchesFixturesListAdapter matchesFixturesListAdapter = new MatchesFixturesListAdapter(context, gamesList.games);
                    holder.dayDateItemRowBinding.fixturesMatchesList.setAdapter(matchesFixturesListAdapter);
                    matchesFixturesListAdapter.notifyDataSetChanged();
                    Log.e(TAG, "onChanged:gamesSizeAdapter " + matchesFixturesListAdapter.getItemCount());
                }else {
                    holder.dayDateItemRowBinding.noMatchesToday.setVisibility(View.VISIBLE);
                    holder.dayDateItemRowBinding.fixturesMatchesList.setVisibility(View.GONE);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return games.size();
    }


    public class DayDateFixtureViewHolder extends RecyclerView.ViewHolder {
        private DayDateItemRowBinding dayDateItemRowBinding;

        public DayDateFixtureViewHolder(@NonNull DayDateItemRowBinding itemView) {
            super(itemView.getRoot());
            dayDateItemRowBinding = itemView;
//            spinner=dayDateItemRowBinding.daysSpinner;
        }
    }
}
