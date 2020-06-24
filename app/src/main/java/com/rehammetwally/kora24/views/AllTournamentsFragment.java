package com.rehammetwally.kora24.views;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.TournamentsAdapter;
import com.rehammetwally.kora24.databinding.FragmentAllTournamentsBinding;
import com.rehammetwally.kora24.models.Tournaments;
import com.rehammetwally.kora24.viewmodels.TournamentsViewModel;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllTournamentsFragment extends Fragment {
    public static final String EXTRA_TOURNAMENTS ="EXTRA_TOURNAMENTS" ;
    private FragmentAllTournamentsBinding binding;
    private TournamentsViewModel tournamentsViewModel;
    private TournamentsAdapter tournamentsAdapter;
    private static final String TAG = "AllTournamentsFragment";

    public AllTournamentsFragment() {
        // Required empty public constructor
    }
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_all_tournaments, container, false);
        tournamentsViewModel= ViewModelProviders.of(getActivity()).get(TournamentsViewModel.class);
        View view=binding.getRoot();
        binding.tournamentsList.setHasFixedSize(true);
        binding.tournamentsList.setLayoutManager(new LinearLayoutManager(getContext()));
        tournamentsAdapter = new TournamentsAdapter(getContext());
        tournamentsViewModel.showCompetitions().observe(getActivity(), new Observer<List<Tournaments>>() {
            @Override
            public void onChanged(List<Tournaments> tournaments) {
                Log.e(TAG, "onChanged: "+tournaments.size());
                tournamentsAdapter.submitList(tournaments);
            }
        });
        binding.tournamentsList.setAdapter(tournamentsAdapter);

        return view;
    }
}
