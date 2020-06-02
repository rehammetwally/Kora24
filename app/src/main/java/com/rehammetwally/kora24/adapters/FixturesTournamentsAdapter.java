package com.rehammetwally.kora24.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.FixturesTournamentsListItemBinding;
import com.rehammetwally.kora24.databinding.MatchesListItemBinding;
import com.rehammetwally.kora24.interfaces.ItemClickListener;
import com.rehammetwally.kora24.models.Match;

import java.util.List;

public class FixturesTournamentsAdapter extends RecyclerView.Adapter<FixturesTournamentsAdapter.FixturesTournamentsViewHolder> {

    private static ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private Context context;
    private List<Match> list;
    private static final String TAG = "MatchesListAdapter";

    public FixturesTournamentsAdapter(Context context, List<Match> list) {
        this.list = list;
        this.context = context;
        Log.e(TAG, "FixturesTournamentsAdapter: " + list.size());
    }


    @NonNull
    @Override
    public FixturesTournamentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MatchesListItemBinding matchesListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.matches_list_item, parent, false);
        return new FixturesTournamentsViewHolder(matchesListItemBinding);


    }


    @Override
    public void onBindViewHolder(@NonNull FixturesTournamentsViewHolder holder, int position) {
//        Log.e(TAG, "onBindViewHolder: "+ list.keySet().get(position));
        Match match = list.get(position);
//        List<Match> matchList =list.get(modelObject);
//        holder.matchesListItemBinding.setModel(modelObject);
//        Log.e(TAG, "onBindViewHolder: " + matchList.size());
//        Log.e(TAG, "onBindViewHolder: "+match.commentor );
        holder.matchesListItemBinding.matchesList.setHasFixedSize(true);
        holder.matchesListItemBinding.matchesList.setLayoutManager(new LinearLayoutManager(context));
        MatchesAdapter matchesAdapter = new MatchesAdapter(context);
        matchesAdapter.submitList(list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(holder.matchesListItemBinding.matchesList.getContext(),
                DividerItemDecoration.VERTICAL);
        holder.matchesListItemBinding.matchesList.addItemDecoration(dividerItemDecoration);
        holder.matchesListItemBinding.matchesList.setAdapter(matchesAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

//    @Override
//    public int getItemCount() {
//        return list.size();
//    }

    public class FixturesTournamentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MatchesListItemBinding matchesListItemBinding;

        public FixturesTournamentsViewHolder(@NonNull MatchesListItemBinding itemView) {
            super(itemView.getRoot());
            matchesListItemBinding = itemView;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(getAdapterPosition(), view);
        }
    }


}
