package com.rehammetwally.kora24.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
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
import com.rehammetwally.kora24.databinding.MatchesItemBinding;
import com.rehammetwally.kora24.interfaces.ItemClickListener;
import com.rehammetwally.kora24.models.CompetitionMatch;
import com.rehammetwally.kora24.models.Match;
import com.rehammetwally.kora24.views.FixturesDetailsActivity;

import java.io.Serializable;

public class MatchesAdapter extends ListAdapter<Match, MatchesAdapter.MatchesViewHolder> {

    private static ItemClickListener itemClickListener;
    private Context context;

    private static final String TAG = "MatchesAdapter";

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public MatchesAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    private static DiffUtil.ItemCallback<Match> diffCallback = new DiffUtil.ItemCallback<Match>() {
        @Override
        public boolean areItemsTheSame(@NonNull Match oldItem, @NonNull Match newItem) {
            return oldItem.competition_id == newItem.competition_id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Match oldItem, @NonNull Match newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MatchesItemBinding matchesItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.matches_item, parent, false);
        return new MatchesViewHolder(matchesItemBinding);


    }


    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
        final Match match = getItem(position);
        Log.e(TAG, "onBindViewHolder: " + match.match_date);
        holder.matchesItemBinding.setMatch(match);
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
        private MatchesItemBinding matchesItemBinding;

        public MatchesViewHolder(@NonNull MatchesItemBinding itemView) {
            super(itemView.getRoot());
            matchesItemBinding = itemView;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(getAdapterPosition(), view);
        }
    }


}
