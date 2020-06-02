package com.rehammetwally.kora24.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.FavoriteItemBinding;
import com.rehammetwally.kora24.models.Favorite;
import com.rehammetwally.kora24.models.Team;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.viewmodels.FavouritesViewModel;
import com.rehammetwally.kora24.views.FavoriteFragment;
import com.rehammetwally.kora24.views.MainActivity;

public class FavoritesAdapter extends ListAdapter<Team, FavoritesAdapter.FavoritesViewHolder> {
    private Context context;
    private Boolean isAll;
    private FavouritesViewModel favouritesViewModel;

    public FavoritesAdapter(Context context, Boolean isAll) {
        super(diffCallback);
        this.context = context;
        this.isAll = isAll;
        favouritesViewModel = ViewModelProviders.of((FragmentActivity) context).get(FavouritesViewModel.class);
    }

    private static DiffUtil.ItemCallback<Team> diffCallback = new DiffUtil.ItemCallback<Team>() {
        @Override
        public boolean areItemsTheSame(@NonNull Team oldItem, @NonNull Team newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Team oldItem, @NonNull Team newItem) {
            return oldItem.t_logo.equals(newItem.t_logo) && oldItem.t_name.equals(newItem.t_name) && oldItem.country_name.equals(newItem.country_name);
        }
    };

    @NonNull
    @Override
    public FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        FavoriteItemBinding favoriteItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.favorite_item, parent, false);
        return new FavoritesViewHolder(favoriteItemBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull FavoritesViewHolder holder, final int position) {
        final Team team = getItem(position);

        holder.favoriteItemBinding.setTeam(team);
        if (!isAll) {
            int tabIconColor = ContextCompat.getColor(context, R.color.colorFavorite);
            holder.favoriteItemBinding.favorite.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
        }

        holder.favoriteItemBinding.favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (isAll) {
                    favouritesViewModel.setFavourite(team.id).observe((FragmentActivity) context, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("updated successfully")) {
                                int tabIconColor = ContextCompat.getColor(context, R.color.colorFavorite);
                                holder.favoriteItemBinding.favorite.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                                notifyItemChanged(position);
//                                MyApplication.showMessageBottom(v, team.t_name + " " + context.getResources().getString(R.string.add_to_favorite));
//                                Intent intent = new Intent(context, MainActivity.class);
//                                intent.putExtra(FavoriteFragment.EXTRA_FAVORITE, true);
//                                context.startActivity(intent);
                            }
                        }
                    });
                } else {
                    favouritesViewModel.setUnFavourite(team.id).observe((FragmentActivity) context, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("updated successfully")) {

                                int tabIconColor = ContextCompat.getColor(context, R.color.unSelectedFavorite);
                                holder.favoriteItemBinding.favorite.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                            }
                        }
                    });
                }

            }
        });

    }


    public class FavoritesViewHolder extends RecyclerView.ViewHolder {
        private FavoriteItemBinding favoriteItemBinding;

        public FavoritesViewHolder(@NonNull FavoriteItemBinding itemView) {
            super(itemView.getRoot());
            favoriteItemBinding = itemView;
        }
    }
}
