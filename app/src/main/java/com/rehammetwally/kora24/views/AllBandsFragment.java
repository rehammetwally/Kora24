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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.FavoritesAdapter;
import com.rehammetwally.kora24.databinding.FragmentAllBandsBinding;
import com.rehammetwally.kora24.models.Team;
import com.rehammetwally.kora24.viewmodels.FavouritesViewModel;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllBandsFragment extends Fragment {
    private FragmentAllBandsBinding binding;
    private FavouritesViewModel favouritesViewModel;

    public AllBandsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_bands, container, false);
        View view = binding.getRoot();
        favouritesViewModel = ViewModelProviders.of(getActivity()).get(FavouritesViewModel.class);
        binding.favoritesList.setHasFixedSize(true);
        binding.favoritesList.setLayoutManager(new LinearLayoutManager(getContext()));
        final FavoritesAdapter favoritesAdapter = new FavoritesAdapter(getContext(),true);
        favouritesViewModel.showAllTeams().observe(getActivity(), new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                favoritesAdapter.submitList(teams);
            }
        });
        binding.favoritesList.setAdapter(favoritesAdapter);
        favoritesAdapter.notifyDataSetChanged();
        return view;
    }




}
