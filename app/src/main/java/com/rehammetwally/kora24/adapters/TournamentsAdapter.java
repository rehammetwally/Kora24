package com.rehammetwally.kora24.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.FavoriteItemBinding;
import com.rehammetwally.kora24.databinding.TournamentsItemBinding;
import com.rehammetwally.kora24.models.Favorite;
import com.rehammetwally.kora24.models.Tournaments;
import com.rehammetwally.kora24.views.TournamentsDetailsActivity;

public class TournamentsAdapter extends ListAdapter<Tournaments, TournamentsAdapter.TournamentsViewHolder> {
    private Context context;

    public TournamentsAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    private static DiffUtil.ItemCallback<Tournaments> diffCallback = new DiffUtil.ItemCallback<Tournaments>() {
        @Override
        public boolean areItemsTheSame(@NonNull Tournaments oldItem, @NonNull Tournaments newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Tournaments oldItem, @NonNull Tournaments newItem) {
            return  oldItem.id == newItem.id &&
                    oldItem.logo.equals(newItem.logo) &&
                    oldItem.title.equals(newItem.title);
        }
    };

    @NonNull
    @Override
    public TournamentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TournamentsItemBinding tournamentsItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.tournaments_item, parent, false);
        return new TournamentsViewHolder(tournamentsItemBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull TournamentsViewHolder holder, int position) {
        final Tournaments tournaments = getItem(position);

        holder.tournamentsItemBinding.setTournaments(tournaments);
        holder.tournamentsItemBinding.tournamentsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, TournamentsDetailsActivity.class);
                intent.putExtra("TOURNAMENTS",tournaments);
                context.startActivity(intent);
            }
        });
    }


    public class TournamentsViewHolder extends RecyclerView.ViewHolder {
        private TournamentsItemBinding tournamentsItemBinding;

        public TournamentsViewHolder(@NonNull TournamentsItemBinding itemView) {
            super(itemView.getRoot());
            tournamentsItemBinding = itemView;
        }
    }
}
