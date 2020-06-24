package com.rehammetwally.kora24.views;

import android.content.Context;
import android.database.DatabaseUtils;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    private static final String TAG = "YourFavoriteFragment";
    public static TextView emptyList;

    public YourFavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        loadFavorite();
        Log.e(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        loadFavorite();
        Log.e(TAG, "onResume: ");
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e(TAG, "onAttach: ");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_your_favorite, container, false);
        View view = binding.getRoot();
        Log.e(TAG, "onCreateView: ");
        favouritesViewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(FavouritesViewModel.class);
        binding.favoritesList.setHasFixedSize(true);
        binding.favoritesList.setLayoutManager(new LinearLayoutManager(getContext()));
        emptyList = binding.emptyList;
        loadFavorite();


        return view;
    }

    public void loadFavorite() {

        favouritesViewModel.showFavouriteTeams().observe((LifecycleOwner) getContext(), new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                if (teams.size() > 0) {
                    binding.emptyList.setVisibility(View.GONE);
                    FavoritesAdapter favoritesAdapter = new FavoritesAdapter(getContext(), false);
                    favoritesAdapter.submitList(teams);
                    binding.favoritesList.setAdapter(favoritesAdapter);
                    favoritesAdapter.notifyDataSetChanged();
                } else {
                    binding.emptyList.setVisibility(View.VISIBLE);
                    binding.favoritesList.setVisibility(View.GONE);
                }
            }
        });
    }
}
