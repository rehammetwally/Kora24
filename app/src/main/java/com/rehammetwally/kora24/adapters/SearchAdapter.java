package com.rehammetwally.kora24.adapters;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.FavoriteItemBinding;
import com.rehammetwally.kora24.databinding.SearchItemBinding;
import com.rehammetwally.kora24.models.SearchResult;
import com.rehammetwally.kora24.models.Team;
import com.rehammetwally.kora24.viewmodels.FavouritesViewModel;

public class SearchAdapter extends ListAdapter<SearchResult.Result, SearchAdapter.SearchViewHolder> {
    private Context context;

    public SearchAdapter(Context context) {
        super(diffCallback);
        this.context = context;
    }

    private static DiffUtil.ItemCallback<SearchResult.Result> diffCallback = new DiffUtil.ItemCallback<SearchResult.Result>() {
        @Override
        public boolean areItemsTheSame(@NonNull SearchResult.Result oldItem, @NonNull SearchResult.Result newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull SearchResult.Result oldItem, @NonNull SearchResult.Result newItem) {
            return oldItem.name.equals(newItem.name) &&
                    oldItem.logo.equals(newItem.logo) &&
                    oldItem.is_favorite == newItem.is_favorite &&
                    oldItem.id == newItem.id &&
                    oldItem.country_id == newItem.country_id;
        }
    };

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SearchItemBinding searchItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.search_item, parent, false);
        return new SearchViewHolder(searchItemBinding);
    }


    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, final int position) {
        final SearchResult.Result team = getItem(position);

        holder.searchItemBinding.setResult(team);

    }


    public class SearchViewHolder extends RecyclerView.ViewHolder {
        private SearchItemBinding searchItemBinding;

        public SearchViewHolder(@NonNull SearchItemBinding itemView) {
            super(itemView.getRoot());
            searchItemBinding = itemView;
        }
    }
}
