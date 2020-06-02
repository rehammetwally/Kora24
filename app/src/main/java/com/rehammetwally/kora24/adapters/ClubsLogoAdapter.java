package com.rehammetwally.kora24.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.ClubLogoItemBinding;
import com.rehammetwally.kora24.models.Team;

public class ClubsLogoAdapter extends ListAdapter<Team, ClubsLogoAdapter.ClubsLogoViewHolder> {
    private Context context;

    public ClubsLogoAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    private static DiffUtil.ItemCallback<Team> diffCallback = new DiffUtil.ItemCallback<Team>() {
        @Override
        public boolean areItemsTheSame(@NonNull Team oldItem, @NonNull Team newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Team oldItem, @NonNull Team newItem) {
            return oldItem.t_name.equals(newItem.t_name) && oldItem.t_logo.equals(newItem.t_logo) && oldItem.id == newItem.id;
        }
    };

    @NonNull
    @Override
    public ClubsLogoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ClubLogoItemBinding clubLogoItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.club_logo_item, parent, false);
        return new ClubsLogoViewHolder(clubLogoItemBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull ClubsLogoViewHolder holder, int position) {
        Team clubLogo = getItem(position);

        holder.clubLogoItemBinding.setClubLogo(clubLogo);
    }


    public class ClubsLogoViewHolder extends RecyclerView.ViewHolder {
        private ClubLogoItemBinding clubLogoItemBinding;

        public ClubsLogoViewHolder(@NonNull ClubLogoItemBinding itemView) {
            super(itemView.getRoot());
            clubLogoItemBinding = itemView;
        }
    }
}
