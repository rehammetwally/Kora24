package com.rehammetwally.kora24.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DayDateFixtureAdapter extends RecyclerView.Adapter<DayDateFixtureAdapter.DayDateFixtureViewHolder> {
    private Context context;
    private static final String TAG = "DayDateFixtureAdapter";
    private List<Match> games;
    private TournamentsViewModel tournamentsViewModel;
    public DaysSpinnerAdapter daysSpinnerAdapter;
    public ArrayList<Match> days;
    public Spinner spinner;

    public DayDateFixtureAdapter(Context context, List<Match> games, TournamentsViewModel tournamentsViewModel) {
        this.context = context;
        this.games = games;
        this.tournamentsViewModel = tournamentsViewModel;
        days = new ArrayList<Match>(games);
        for (int i = 0; i < days.size(); i++) {

            Log.e(TAG, "DayDateFixtureAdapter: " + days.get(i).toString());
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
        holder.dayDateItemRowBinding.daysSpinner.setAdapter(daysSpinnerAdapter);
        holder.dayDateItemRowBinding.daysSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Match match1 = (Match) holder.dayDateItemRowBinding.daysSpinner.getSelectedItem();

                Log.e(TAG, "onItemSelected: " + match1.match_date);
                tournamentsViewModel.getGamesByDate(match1.match_date).observe((LifecycleOwner) context, new Observer<GamesList>() {
                    @Override
                    public void onChanged(GamesList gamesList) {
                        Log.e(TAG, "onChanged:gamesSize " + gamesList.games.size());

                        for (int i = 0; i < gamesList.games.size(); i++) {
                            Log.e(TAG, "c_title: " + gamesList.games.get(i).c_title);
                        }
                        MatchesFixturesListAdapter matchesFixturesListAdapter = new MatchesFixturesListAdapter(context, gamesList.games);
                        holder.dayDateItemRowBinding.fixturesMatchesList.setAdapter(matchesFixturesListAdapter);
                        matchesFixturesListAdapter.notifyDataSetChanged();
                        Log.e(TAG, "onChanged:gamesSizeAdapter " + matchesFixturesListAdapter.getItemCount());
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        holder.dayDateItemRowBinding.fixturesMatchesList.setHasFixedSize(true);
        holder.dayDateItemRowBinding.fixturesMatchesList.setLayoutManager(new LinearLayoutManager(context));
        tournamentsViewModel.getGamesByDate(match.match_date).observe((LifecycleOwner) context, new Observer<GamesList>() {
            @Override
            public void onChanged(GamesList gamesList) {
                Log.e(TAG, "onChanged:gamesSize " + gamesList.games.size());

                for (int i = 0; i < gamesList.games.size(); i++) {
                    Log.e(TAG, "c_title: " + gamesList.games.get(i).c_title);
                }
                MatchesFixturesListAdapter matchesFixturesListAdapter = new MatchesFixturesListAdapter(context, gamesList.games);
                holder.dayDateItemRowBinding.fixturesMatchesList.setAdapter(matchesFixturesListAdapter);
                matchesFixturesListAdapter.notifyDataSetChanged();
                Log.e(TAG, "onChanged:gamesSizeAdapter " + matchesFixturesListAdapter.getItemCount());
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
            spinner=dayDateItemRowBinding.daysSpinner;
        }
    }
}
