package com.rehammetwally.kora24.views;

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

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.CustomSpinnerAdapter;
import com.rehammetwally.kora24.databinding.FragmentUpdateGameResultBinding;
import com.rehammetwally.kora24.models.CompetitionMatch;
import com.rehammetwally.kora24.models.MatchList;
import com.rehammetwally.kora24.models.MessageMatch;
import com.rehammetwally.kora24.models.Team;
import com.rehammetwally.kora24.models.Tournaments;
import com.rehammetwally.kora24.models.UpdateResult;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.viewmodels.TournamentsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateGameResultFragment extends Fragment implements View.OnClickListener {
    private FragmentUpdateGameResultBinding binding;
    private CustomSpinnerAdapter adapter;
    private TournamentsViewModel tournamentsViewModel;
    private int gameId;
    private String homeResult, awayResult;
    private static final String TAG = "UpdateGameResultFrg";

    public UpdateGameResultFragment() {
        // Required empty public constructor
    }

    public boolean onBackPressed() {
        return true;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_game_result, container, false);
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
                binding.compitationSpinner.setAdapter(adapter);
            }
        });
        binding.compitationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemSelected: "+parent.getItemIdAtPosition(position) );
                Log.e(TAG, "onItemSelected: "+id );
                int compitationId = (int) binding.compitationSpinner.getSelectedItemId();
                Log.e(TAG, "onItemSelected: "+compitationId );
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        int compitationId = (int) binding.compitationSpinner.getSelectedItemId();
        Log.e(TAG, "onItemSelected: "+compitationId );
        tournamentsViewModel.showGames().observe(getActivity(), new Observer<MatchList>() {
            @Override
            public void onChanged(MatchList matchList) {
                for (int i = 0; i < matchList.match.size(); i++) {

                    tournamentsViewModel.getCompetitionGames(matchList.match.get(i).competition_id).observe(getActivity(), new Observer<CompetitionMatch>() {
                        @Override
                        public void onChanged(CompetitionMatch competitionMatch) {
                            Log.e(TAG, "getCompetitionGames: " + competitionMatch.match.size());
                            adapter = new CustomSpinnerAdapter(getContext(), competitionMatch.match);
                            binding.gamesSpinner.setAdapter(adapter);
//                        fixturesTournamentsAdapter.submitList(competitionMatch.match);
                        }
                    });
                }
//
            }
        });
//        tournamentsViewModel.showGames().observe(getActivity(), new Observer<List<Team>>() {
//            @Override
//            public void onChanged(List<Team> teams) {
//                List<Team> teamsList = new ArrayList<>();
//                teamsList.addAll(teams);
//                Team firstTeam = new Team();
//                firstTeam.t_name = getResources().getString(R.string.home_team);
//                firstTeam.id = 0;
//                teamsList.add(0, firstTeam);
////                adapter = new CustomSpinnerAdapter(getContext(), teamsList);
////                binding.homeTeamSpinner.setAdapter(adapter);
//                teamsList = new ArrayList<>();
//                teamsList.addAll(teams);
//                Team secondTeam = new Team();
//                secondTeam.t_name = getResources().getString(R.string.away_team);
//                secondTeam.id = 0;
//                teamsList.add(0, secondTeam);
//                adapter = new CustomSpinnerAdapter(getContext(), teamsList);
////                binding.awayTeamSpinner.setAdapter(adapter);
//            }
//        });
        binding.updateResultButton.setOnClickListener(this);
//        binding.updateHomeTeamButton.setOnClickListener(this);
        return view;

    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.update_result_button:
                gameId = (int) binding.gamesSpinner.getSelectedItemId();
                homeResult = binding.homeTeamEdittext.getText().toString();
                awayResult = binding.awayTeamEdittext.getText().toString();
                if (gameId == 0) {
                    MyApplication.showMessageBottom(v, getResources().getString(R.string.error_select) + " " + getResources().getString(R.string.game));
                    return;
                }
                if (homeResult.isEmpty() && awayResult.isEmpty()) {
                    if (homeResult.isEmpty()) {
                        MyApplication.showMessageBottom(v, getResources().getString(R.string.home_team_result) + getResources().getString(R.string.error_message_empty));
                        return;
                    }
                    if (awayResult.isEmpty()) {
                        MyApplication.showMessageBottom(v, getResources().getString(R.string.away_team_result) + getResources().getString(R.string.error_message_empty));
                        return;
                    }

                }
                if (!homeResult.isEmpty() && awayResult.isEmpty()) {
                    tournamentsViewModel.updateHomeResult(gameId, Integer.parseInt(homeResult)).observe(getActivity(), new Observer<UpdateResult>() {
                        @Override
                        public void onChanged(UpdateResult updateResult) {
                            if (updateResult.match.equals("Updated Successfully")) {
                                MyApplication.showMessageBottom(v, getString(R.string.update_result_success));
                            }
                        }
                    });
                }
                if (homeResult.isEmpty() && !awayResult.isEmpty()) {
                    tournamentsViewModel.updateAwayResult(gameId, Integer.parseInt(awayResult)).observe(getActivity(), new Observer<UpdateResult>() {
                        @Override
                        public void onChanged(UpdateResult updateResult) {
                            if (updateResult.match.equals("Updated Successfully")) {
                                MyApplication.showMessageBottom(v, getString(R.string.update_result_success));
                            }
                        }
                    });
                }
                break;
        }
    }
}
