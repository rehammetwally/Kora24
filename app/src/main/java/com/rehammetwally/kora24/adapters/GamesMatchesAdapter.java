package com.rehammetwally.kora24.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.GamesMatchesItemBinding;
import com.rehammetwally.kora24.interfaces.ItemClickListener;
import com.rehammetwally.kora24.models.GamesList;
import com.rehammetwally.kora24.views.FixturesDetailsActivity;

import java.io.Serializable;

public class GamesMatchesAdapter extends ListAdapter<GamesList.Games.Data, GamesMatchesAdapter.MatchesViewHolder> {

    private static ItemClickListener itemClickListener;
    private Context context;
    private static final String TAG = "GamesMatchesAdapter";

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public GamesMatchesAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    private static DiffUtil.ItemCallback<GamesList.Games.Data> diffCallback = new DiffUtil.ItemCallback<GamesList.Games.Data>() {
        @Override
        public boolean areItemsTheSame(@NonNull GamesList.Games.Data  oldItem, @NonNull GamesList.Games.Data  newItem) {
            return oldItem.competition_id == newItem.competition_id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull GamesList.Games.Data oldItem, @NonNull GamesList.Games.Data newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GamesMatchesItemBinding matchesItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.games_matches_item, parent, false);
        return new MatchesViewHolder(matchesItemBinding);


    }


    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
        final GamesList.Games.Data match = getItem(position);
        Log.e(TAG, "onBindViewHolder:c_logo "+match.s_title );
        holder.matchesItemBinding.setGame(match);
        holder.matchesItemBinding.matchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FixturesDetailsActivity.class);
                intent.putExtra("MATCH", match.id);
                context.startActivity(intent);
            }
        });


    }

    public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private GamesMatchesItemBinding matchesItemBinding;

        public MatchesViewHolder(@NonNull GamesMatchesItemBinding itemView) {
            super(itemView.getRoot());
            matchesItemBinding = itemView;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(getAdapterPosition(), view);
        }
    }


}
