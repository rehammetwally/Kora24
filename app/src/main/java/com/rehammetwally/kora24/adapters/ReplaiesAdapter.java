package com.rehammetwally.kora24.adapters;

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
import com.rehammetwally.kora24.databinding.CommentItemBinding;
import com.rehammetwally.kora24.databinding.ReplayItemBinding;
import com.rehammetwally.kora24.interfaces.ItemClickListener;
import com.rehammetwally.kora24.models.Comments;

public class ReplaiesAdapter extends ListAdapter<Comments.Comment
        .Replies, ReplaiesAdapter.CommentsViewHolder> {

    private static final String TAG = "CommentsAdapter";
    private static ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ReplaiesAdapter() {
        super(diffCallback);
    }

    private static DiffUtil.ItemCallback<Comments.Comment.Replies> diffCallback = new DiffUtil.ItemCallback<Comments.Comment.Replies>() {
        @Override
        public boolean areItemsTheSame(@NonNull Comments.Comment.Replies oldItem, @NonNull Comments.Comment.Replies newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Comments.Comment.Replies oldItem, @NonNull Comments.Comment.Replies newItem) {

            return oldItem.comment.equals(newItem.comment) &&
                    oldItem.id == newItem.id &&
                    oldItem.name.equals(newItem.name) &&
                    oldItem.icon().equals(newItem.icon()) ;
        }
    };

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ReplayItemBinding commentItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.replay_item, parent, false);
        return new CommentsViewHolder(commentItemBinding);


    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        Comments.Comment.Replies comments = getItem(position);
        Log.e(TAG, "onBindViewHolder: "+comments.name );
        holder.commentItemBinding.setReplay(comments);
        holder.commentItemBinding.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ReplayItemBinding commentItemBinding;

        public CommentsViewHolder(@NonNull ReplayItemBinding itemView) {
            super(itemView.getRoot());
            commentItemBinding = itemView;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(getAdapterPosition(), view);
        }
    }


}
