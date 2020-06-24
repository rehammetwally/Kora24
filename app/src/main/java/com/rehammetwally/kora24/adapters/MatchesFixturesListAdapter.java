package com.rehammetwally.kora24.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.GamesMatchesItemBinding;
import com.rehammetwally.kora24.databinding.MatchesFixtureListItemBinding;
import com.rehammetwally.kora24.databinding.MatchesListItemBinding;
import com.rehammetwally.kora24.interfaces.ItemClickListener;
import com.rehammetwally.kora24.models.GamesList;
import com.rehammetwally.kora24.models.Match;
import com.rehammetwally.kora24.models.ModelObject;
import com.rehammetwally.kora24.models.Tournaments;
import com.rehammetwally.kora24.views.FixturesDetailsActivity;
import com.rehammetwally.kora24.views.TournamentsDetailsActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatchesFixturesListAdapter extends RecyclerView.Adapter<MatchesFixturesListAdapter.MatchesViewHolder> {

    private static ItemClickListener itemClickListener;




    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private Context context;
    private  List<GamesList.Games> list;
    private static final String TAG = "MatchesListAdapter";

    public MatchesFixturesListAdapter(Context context, List<GamesList.Games> list) {
        this.context = context;
        this.list = list;
        Log.e(TAG, "MatchesListAdapter: "+list.size() );
        for (int i = 0; i < list.size(); i++) {
            Log.e(TAG, "MatchesFixturesListAdapter: "+list.get(i).c_title );
        }
    }


    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MatchesFixtureListItemBinding matchesListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.matches_fixture_list_item, parent, false);
        return new MatchesViewHolder(matchesListItemBinding);


    }


    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
//        holder.matchesListItemBinding.setGame(list.get(position));
//        holder.matchesListItemBinding.matchItem.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, FixturesDetailsActivity.class);
//                intent.putExtra("MATCH", (Serializable) match);
//                context.startActivity(intent);
//            }
//        });
//        Log.e(TAG, "onBindViewHolder: "+ list.keySet().get(position));
        GamesList.Games games=list.get(position);
//        List<Match> matchList =list.get(modelObject);
        Log.e(TAG, "onBindViewHolder:c_title "+list.get(position).c_title );
        holder.matchesListItemBinding.setGames(list.get(position));
////        Log.e(TAG, "onBindViewHolder: " + matchList.size());
        holder.matchesListItemBinding.fixturesMatchesListItems.setHasFixedSize(true);
        holder.matchesListItemBinding.fixturesMatchesListItems.setLayoutManager(new LinearLayoutManager(context));
        Log.e(TAG, "onBindViewHolder:c_logo "+list.get(position).games.size() );
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(holder.matchesListItemBinding.fixturesMatchesListItems.getContext(),
                DividerItemDecoration.VERTICAL);
        holder.matchesListItemBinding.fixturesMatchesListItems.addItemDecoration(dividerItemDecoration);
        GamesMatchesAdapter matchesAdapter = new GamesMatchesAdapter(context);
        matchesAdapter.submitList(list.get(position).games);
        holder.matchesListItemBinding.fixturesMatchesListItems.setAdapter(matchesAdapter);
        holder.matchesListItemBinding.tournamentsCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, TournamentsDetailsActivity.class);
                Tournaments tournaments=new Tournaments();
                tournaments.id=games.id;
                tournaments.created_at=games.created_at;
                tournaments.updated_at=games.updated_at;
                tournaments.logo=games.c_logo;
                tournaments.title=games.c_title;
                intent.putExtra("TOURNAMENTS",tournaments);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MatchesFixtureListItemBinding matchesListItemBinding;

        public MatchesViewHolder(@NonNull MatchesFixtureListItemBinding itemView) {
            super(itemView.getRoot());
            matchesListItemBinding = itemView;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(getAdapterPosition(), view);
        }
    }


}
