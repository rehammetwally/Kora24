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
import com.rehammetwally.kora24.databinding.MatchesItemBinding;
import com.rehammetwally.kora24.databinding.MatchesListItemBinding;
import com.rehammetwally.kora24.interfaces.ItemClickListener;
import com.rehammetwally.kora24.models.GamesList;
import com.rehammetwally.kora24.models.Match;
import com.rehammetwally.kora24.models.ModelObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MatchesListAdapter extends RecyclerView.Adapter<MatchesListAdapter.MatchesViewHolder> {

    private static ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    private Context context;
    private  List<GamesList.Games> list;
    private List<ModelObject> modelObjects=new ArrayList<>();
    private static final String TAG = "MatchesListAdapter";

    public MatchesListAdapter(Context context,List<GamesList.Games> list) {
//        super(diffCallback);
        this.context = context;
        this.list = list;
//        stringList=list.get()
        Log.e(TAG, "MatchesListAdapter: "+list.size() );
//        for (int i = 0; i < list.size(); i++) {
//            modelObjects.addAll(list.keySet());
//            Log.e(TAG, "MatchesListAdapter: "+list.get(i). );
//        }
    }

//    private static DiffUtil.ItemCallback<List<Match>> diffCallback = new DiffUtil.ItemCallback<List<Match>>() {
//        @Override
//        public boolean areItemsTheSame(@NonNull List<Match> oldItem, @NonNull List<Match> newItem) {
//            return oldItem.size() == newItem.size();
//        }
//
//        @Override
//        public boolean areContentsTheSame(@NonNull List<Match> oldItem, @NonNull List<Match> newItem) {
//            return oldItem.equals(newItem);
//        }
//    };

    @NonNull
    @Override
    public MatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MatchesListItemBinding matchesListItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.matches_list_item, parent, false);
        return new MatchesViewHolder(matchesListItemBinding);


    }


    @Override
    public void onBindViewHolder(@NonNull MatchesViewHolder holder, int position) {
//        Log.e(TAG, "onBindViewHolder: "+ list.keySet().get(position));
        GamesList.Games games=list.get(position);
//        List<Match> matchList =list.get(modelObject);
        holder.matchesListItemBinding.setModel(games);
//        Log.e(TAG, "onBindViewHolder: " + matchList.size());
        holder.matchesListItemBinding.matchesList.setHasFixedSize(true);
        holder.matchesListItemBinding.matchesList.setLayoutManager(new LinearLayoutManager(context));
        MatchesAdapter matchesAdapter = new MatchesAdapter(context);
//        matchesAdapter.submitList(list);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(holder.matchesListItemBinding.matchesList.getContext(),
                DividerItemDecoration.VERTICAL);
        holder.matchesListItemBinding.matchesList.addItemDecoration(dividerItemDecoration);
        holder.matchesListItemBinding.matchesList.setAdapter(matchesAdapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private MatchesListItemBinding matchesListItemBinding;

        public MatchesViewHolder(@NonNull MatchesListItemBinding itemView) {
            super(itemView.getRoot());
            matchesListItemBinding = itemView;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(getAdapterPosition(), view);
        }
    }


}
