package com.rehammetwally.kora24.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.NewsHeadItemBinding;
import com.rehammetwally.kora24.databinding.NewsItemBinding;
import com.rehammetwally.kora24.models.News;
import com.rehammetwally.kora24.models.NewsReation;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.utils.StringsUtils;
import com.rehammetwally.kora24.viewmodels.NewsViewModel;
import com.rehammetwally.kora24.views.NewsDetailsActivity;

public class NewsAdapter extends ListAdapter<News, RecyclerView.ViewHolder> {

    private Context context;
    private NewsViewModel newsViewModel;
    private static final String TAG = "NewsAdapter";
//    private ItemClickListener itemClickListener;
//
//    public void setItemClickListener(ItemClickListener itemClickListener) {
//        this.itemClickListener = itemClickListener;
//    }

    public NewsAdapter(Context context,NewsViewModel newsViewModel) {
        super(diffCallback);
        this.context = context;
        this.newsViewModel=newsViewModel;
    }

    private static DiffUtil.ItemCallback<News> diffCallback = new DiffUtil.ItemCallback<News>() {
        @Override
        public boolean areItemsTheSame(@NonNull News oldItem, @NonNull News newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull News oldItem, @NonNull News newItem) {
            return oldItem.id == newItem.id &&
                    oldItem.icon().equals(newItem.icon()) &&
                    oldItem.content.equals(newItem.content);
        }
    };

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 1) {
            NewsHeadItemBinding newsHeadItemBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.news_head_item, parent, false);
            return new NewsHeadViewHolder(newsHeadItemBinding);
        } else {
            // inflate your second item layout & return that viewHolder
            NewsItemBinding newsItemBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            R.layout.news_item, parent, false);
            return new NewsViewHolder(newsItemBinding);
        }

    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).isHead) return 1;
        else return 2;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final News news = getItem(position);
        switch (holder.getItemViewType()) {
            case 1:

                NewsHeadViewHolder newsHeadViewHolder = (NewsHeadViewHolder) holder;
                newsHeadViewHolder.newsHeadItemBinding.setNews(news);
                newsHeadViewHolder.newsHeadItemBinding.headCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showNewsDetails(news);
//                        ((Activity)context).finish();
                    }
                });
                newsHeadViewHolder.newsHeadItemBinding.shareHeadNews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.shareApp(context);
                    }
                });

                break;

            case 2:
                NewsViewHolder newsViewHolder = (NewsViewHolder) holder;
                newsViewHolder.newsItemBinding.setNews(news);

                if (position == 0) {

                    Log.e(TAG, "onBindViewHolder: " + news.title);
                    newsViewHolder.newsItemBinding.newsCard.setBackground(context.getResources().getDrawable(R.drawable.bg_first));
                }
                newsViewHolder.newsItemBinding.newsCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showNewsDetails(news);
//                        ((Activity)context).finish();
                    }
                });

                newsViewHolder.newsItemBinding.shareNews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyApplication.shareApp(context);
                    }
                });
//                newsViewModel.showNewsReation(news.id).observe((LifecycleOwner) context, new Observer<NewsReation>() {
//                    @Override
//                    public void onChanged(NewsReation newsReation) {
//                        newsViewHolder.newsItemBinding.lastNewsViewsText.setText(StringsUtils.toString(newsReation.views));
//                        newsViewHolder.newsItemBinding.lastNewsCommentsText.setText(StringsUtils.toString(newsReation.comments));
////                        newsViewHolder.newsItemBinding.li.setText(StringsUtils.toString(newsReation.comments));
//                        Log.e(TAG, "onChanged: " + newsReation.comments);
//                        Log.e(TAG, "onChanged: " + newsReation.likes);
//                        Log.e(TAG, "onChanged: " + newsReation.dislikes);
//                        Log.e(TAG, "onChanged: " + newsReation.views);
//                    }
//                });
                break;
        }


    }

    private void showNewsDetails(News news) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        Log.e(TAG, "onClick: " + news.title);
        intent.putExtra("DETAILS", news);
        context.startActivity(intent);
    }

    public class NewsHeadViewHolder extends RecyclerView.ViewHolder {
        private NewsHeadItemBinding newsHeadItemBinding;

        public NewsHeadViewHolder(@NonNull NewsHeadItemBinding itemView) {
            super(itemView.getRoot());
            newsHeadItemBinding = itemView;
        }


    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {
        private NewsItemBinding newsItemBinding;

        public NewsViewHolder(@NonNull NewsItemBinding itemView) {
            super(itemView.getRoot());
            newsItemBinding = itemView;


        }


    }
}
