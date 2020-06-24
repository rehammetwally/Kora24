package com.rehammetwally.kora24.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.FixturesTournamentsAdapter;
import com.rehammetwally.kora24.databinding.FragmentFixturesBinding;
import com.rehammetwally.kora24.databinding.FragmentFixturesTournamentsBinding;
import com.rehammetwally.kora24.models.CompetitionMatch;
import com.rehammetwally.kora24.models.Match;
import com.rehammetwally.kora24.viewmodels.TournamentsViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FixturesTournamentsFragment extends Fragment {
    private FragmentFixturesTournamentsBinding binding;
    private TournamentsViewModel tournamentsViewModel;
    private int competitionId = 0;
    private static final String TAG = "FixturesTournamentsFrag";

    public FixturesTournamentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fixtures_tournaments, container, false);
        View view = binding.getRoot();
        tournamentsViewModel = ViewModelProviders.of(getActivity()).get(TournamentsViewModel.class);
        competitionId = getArguments().getInt("COMPITIONID");
        Log.e(TAG, "competitionId: " + competitionId);
        binding.fixturesTournamentsList.setHasFixedSize(true);
        binding.fixturesTournamentsList.setLayoutManager(new LinearLayoutManager(getContext()));

        tournamentsViewModel.getCompetitionGames(competitionId).observe(getActivity(), new Observer<CompetitionMatch>() {
            @Override
            public void onChanged(CompetitionMatch competitionMatch) {
                Log.e(TAG, "getCompetitionGames: " + competitionMatch.match.size());
                if (competitionMatch.match.size() > 0) {
                    FixturesTournamentsAdapter fixturesTournamentsAdapter = new FixturesTournamentsAdapter(getContext(),competitionMatch.match);
                    binding.fixturesTournamentsList.setAdapter(fixturesTournamentsAdapter);
                }
            }
        });
        //


        return view;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
