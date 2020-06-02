package com.rehammetwally.kora24.views;

import android.database.DatabaseUtils;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.FavoritesAdapter;
import com.rehammetwally.kora24.databinding.FragmentYourFavoriteBinding;
import com.rehammetwally.kora24.models.Favorite;
import com.rehammetwally.kora24.models.Team;
import com.rehammetwally.kora24.viewmodels.FavouritesViewModel;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class YourFavoriteFragment extends Fragment {
    private FragmentYourFavoriteBinding binding;
    private FavouritesViewModel favouritesViewModel;

    public YourFavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_your_favorite, container, false);
        View view = binding.getRoot();
        favouritesViewModel = ViewModelProviders.of(getActivity()).get(FavouritesViewModel.class);
        binding.favoritesList.setHasFixedSize(true);
        binding.favoritesList.setLayoutManager(new LinearLayoutManager(getContext()));
        final FavoritesAdapter favoritesAdapter = new FavoritesAdapter(getContext(), false);
        favouritesViewModel.showFavouriteTeams().observe(getActivity(), new Observer<List<Team>>() {
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
