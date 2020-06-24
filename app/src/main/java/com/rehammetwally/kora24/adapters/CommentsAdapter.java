package com.rehammetwally.kora24.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.CommentItemBinding;
import com.rehammetwally.kora24.interfaces.ItemClickListener;
import com.rehammetwally.kora24.models.Comments;
import com.rehammetwally.kora24.models.CommentsReation;
import com.rehammetwally.kora24.models.Message;
import com.rehammetwally.kora24.models.NewsReation;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.utils.StringsUtils;
import com.rehammetwally.kora24.viewmodels.UserViewModel;
import com.rehammetwally.kora24.views.MainActivity;
import com.rehammetwally.kora24.views.NewsDetailsActivity;

import java.util.ArrayList;
import java.util.List;

import static com.rehammetwally.kora24.views.NewsDetailsActivity.EXTRA_LOGIN;

public class CommentsAdapter extends ListAdapter<Comments.Comment, CommentsAdapter.CommentsViewHolder> {

    private static final String TAG = "CommentsAdapter";
    private static ItemClickListener itemClickListener;
    private Context context;
    private int user_id;
    private boolean isLike = false;
    private boolean isDisLike = false;
    private boolean isLikeBefore = false;
    private boolean isDisLikeBefore = false;
    private UserViewModel userViewModel;
    private ReplaiesAdapter replaiesAdapter;
    List<Comments.Comment.Replies> repliesList = new ArrayList<>();

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CommentsAdapter(Context context) {
        super(diffCallback);
        this.context = context;
        user_id = MyApplication.getPref().getInt("USERID");
        userViewModel = ViewModelProviders.of((FragmentActivity) context).get(UserViewModel.class);
    }

    private static DiffUtil.ItemCallback<Comments.Comment> diffCallback = new DiffUtil.ItemCallback<Comments.Comment>() {
        @Override
        public boolean areItemsTheSame(@NonNull Comments.Comment oldItem, @NonNull Comments.Comment newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Comments.Comment oldItem, @NonNull Comments.Comment newItem) {

            return oldItem.comment.equals(newItem.comment) &&
                    oldItem.likes == newItem.likes &&
                    oldItem.dislikes == newItem.dislikes &&
                    oldItem.id == newItem.id &&
                    oldItem.name.equals(newItem.name) &&
                    oldItem.icon().equals(newItem.icon());
        }
    };

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CommentItemBinding commentItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.comment_item, parent, false);
        return new CommentsViewHolder(commentItemBinding);


    }

    public void showDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        ViewGroup viewGroup = view.findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.custom_dialog, viewGroup, false);
        TextView title = dialogView.findViewById(R.id.dialog_title);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        title.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        TextView message = dialogView.findViewById(R.id.dialog_message);
        message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        message.setGravity(Gravity.CENTER_HORIZONTAL);
        message.setTextColor(context.getResources().getColor(R.color.colorPrimaryDark));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(80, 0, 80, 0);
        message.setLayoutParams(params);
        TextView positive = dialogView.findViewById(R.id.dialog_positive);
        TextView negative = dialogView.findViewById(R.id.dialog_negative);
        positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(EXTRA_LOGIN, true);
                context.startActivity(intent);
            }
        });

        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        negative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {
        Comments.Comment comments = getItem(position);
        holder.commentItemBinding.setComments(comments);
        holder.commentItemBinding.replaysRv.setHasFixedSize(true);
        holder.commentItemBinding.replaysRv.setLayoutManager(new LinearLayoutManager(context));
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(holder.commentItemBinding.replaysRv.getContext(),
//                DividerItemDecoration.VERTICAL);
//        holder.commentItemBinding.replaysRv.addItemDecoration(dividerItemDecoration);
        if (comments.replies.size() > 0) {
            for (int i = 0; i < comments.replies.size(); i++) {
                if (comments.replies.get(i).size() > 0) {
                    List<Comments.Comment.Replies> repliesList = new ArrayList<>();
                    for (int j = 0; j < comments.replies.get(i).size(); j++) {
                        Log.e(TAG, "replies: " + comments.replies.get(i).get(j).comment);
                        if (comments.replies.get(i).size() > 2) {
                            holder.commentItemBinding.moreReplaies.setVisibility(View.VISIBLE);
                            if (j < 2) {
                                repliesList.add(comments.replies.get(i).get(j));
                                replaiesAdapter = new ReplaiesAdapter(context);
                                replaiesAdapter.submitList(repliesList);
                                holder.commentItemBinding.replaysRv.setAdapter(replaiesAdapter);
                            }

                        } else {
                            holder.commentItemBinding.moreReplaies.setVisibility(View.GONE);
                            replaiesAdapter = new ReplaiesAdapter(context);
                            replaiesAdapter.submitList(comments.replies.get(i));
                            holder.commentItemBinding.replaysRv.setAdapter(replaiesAdapter);
                        }
                    }
                }
            }

        }
        holder.commentItemBinding.moreReplaies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.showCommentsOfNews(comments.news_id).observe((LifecycleOwner) context, new Observer<Comments>() {
                    @Override
                    public void onChanged(Comments comments) {
                        List<Comments.Comment.Replies> list = new ArrayList<>();
                        for (int i = 0; i < comments.comments.get(position).replies.size(); i++) {
                            list.addAll(comments.comments.get(position).replies.get(i));
                        }
                        replaiesAdapter = new ReplaiesAdapter(context);
                        replaiesAdapter.submitList(list);
                        replaiesAdapter.notifyDataSetChanged();
                        holder.commentItemBinding.replaysRv.setAdapter(replaiesAdapter);
                    }
                });
            }
        });
//        for (int i = 0; i < comments.replies.size(); i++) {
//            Log.e(TAG, "replays:first " + comments.replies.get(i).size());
//            for (int j = 0; j < comments.replies.get(i).size(); j++) {
//                Log.e(TAG, "replays: " + comments.replies.get(i).get(j).name + " ===> " + comments.replies.get(i).get(j).comment);
//                repliesList.add(comments.replies.get(i).get(j));
//            }
//        }
//        ReplaiesAdapter replaiesAdapter = new ReplaiesAdapter();
//        replaiesAdapter.submitList(repliesList);
//        holder.commentItemBinding.replaysRv.setAdapter(replaiesAdapter);
//        Log.e(TAG, "onBindViewHolder: " + repliesList.size());
//
//
//        if (comments.replies.get(0).size() >= 2) {
//            holder.commentItemBinding.moreReplaies.setVisibility(View.VISIBLE);
//            holder.commentItemBinding.moreReplaies.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    List<Comments.Comment.Replies> repliesList = new ArrayList<>();
//                    for (int i = 0; i < comments.replies.get(0).size(); i++) {
//                        repliesList.add(comments.replies.get(0).get(i));
//                    }
//                    Log.e(TAG, "onClick: " + repliesList.size());
//                    replaiesAdapter.submitList(repliesList);
//                    holder.commentItemBinding.replaysRv.setAdapter(replaiesAdapter);
//                    replaiesAdapter.notifyDataSetChanged();
////                    context.startActivity(new Intent(context, NewsDetailsActivity.class));
//                }
//            });
//        }
//        replaiesAdapter.submitList(repliesList);
////        activityNewsDetailsBinding.commentsList.setAdapter(commentsAdapter);
//        holder.commentItemBinding.replaysRv.setAdapter(replaiesAdapter);
        holder.commentItemBinding.thumbsUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLikeBefore = true;
                if (user_id == 0) {
                    showDialog(v);
                    return;
                }
                if (isDisLikeBefore) {
                    userViewModel.removeReplayLikeOrDislike(user_id, comments.id).observe((LifecycleOwner) context, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("added successfully")) {
                                int tabIconColor = ContextCompat.getColor(context, R.color.colorGrayDark);
                                holder.commentItemBinding.thumbsDown.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                                userViewModel.showCommentsReation(comments.id).observe((LifecycleOwner) context, new Observer<CommentsReation>() {
                                    @Override
                                    public void onChanged(CommentsReation commentsReation) {
                                        holder.commentItemBinding.commentDislike.setText(StringsUtils.toString(commentsReation.dislikes));
                                    }
                                });
                                isLike = false;
                            }
                        }
                    });
                }
                if (!isLike) {
                    userViewModel.setReplayLikeOrDislike(1, user_id, comments.id).observe((LifecycleOwner) context, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("added successfully")) {
                                int tabIconColor = ContextCompat.getColor(context, R.color.colorAccent);
                                holder.commentItemBinding.thumbsUp.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                                userViewModel.showNewsReation(comments.id).observe((LifecycleOwner) context, new Observer<NewsReation>() {
                                    @Override
                                    public void onChanged(NewsReation newsReation) {
                                        holder.commentItemBinding.commentLike.setText(StringsUtils.toString(newsReation.likes));
                                    }
                                });
                                isLike = true;
                            }
                        }
                    });
                    Log.e(TAG, "onClick:thumbsUp " + isLike);
                } else {
                    userViewModel.removeReplayLikeOrDislike(user_id, comments.id).observe((LifecycleOwner) context, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("added successfully")) {
                                int tabIconColor = ContextCompat.getColor(context, R.color.colorGrayDark);
                                holder.commentItemBinding.thumbsUp.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                                userViewModel.showCommentsReation(comments.id).observe((LifecycleOwner) context, new Observer<CommentsReation>() {
                                    @Override
                                    public void onChanged(CommentsReation commentsReation) {
                                        holder.commentItemBinding.commentLike.setText(StringsUtils.toString(commentsReation.likes));
                                    }
                                });
                                isLike = false;
                            }
                        }
                    });

                }
            }
        });
        holder.commentItemBinding.thumbsDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isDisLikeBefore = true;

                Log.e(TAG, "onClick:thumbsDown " + isLike);
                Log.e(TAG, "onClick:thumbsDown " + isDisLike);
                Log.e(TAG, "onClick:thumbsDown " + isLikeBefore);
                Log.e(TAG, "onClick:thumbsDown " + isDisLikeBefore);
                if (user_id == 0) {
                    showDialog(v);
                    return;
                }
                if (isLikeBefore) {
                    userViewModel.removeReplayLikeOrDislike(user_id, comments.id).observe((LifecycleOwner) context, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("added successfully")) {
                                int tabIconColor = ContextCompat.getColor(context, R.color.colorGrayDark);
                                holder.commentItemBinding.thumbsUp.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                                userViewModel.showCommentsReation(comments.id).observe((LifecycleOwner) context, new Observer<CommentsReation>() {
                                    @Override
                                    public void onChanged(CommentsReation commentsReation) {
                                        holder.commentItemBinding.commentLike.setText(StringsUtils.toString(commentsReation.likes));
                                    }
                                });
                                isLike = false;
                            }
                        }
                    });
                }
                if (!isDisLike) {
                    userViewModel.setReplayLikeOrDislike(2, user_id, comments.id).observe((LifecycleOwner) context, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("added successfully")) {
                                int tabIconColor = ContextCompat.getColor(context, R.color.colorAccent);
                                holder.commentItemBinding.thumbsDown.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                                activityNewsDetailsBinding.dislike.setText(StringsUtils.toString(news.dislikes_num + 1));
                                userViewModel.showCommentsReation(comments.id).observe((LifecycleOwner) context, new Observer<CommentsReation>() {
                                    @Override
                                    public void onChanged(CommentsReation commentsReation) {
                                        holder.commentItemBinding.commentDislike.setText(StringsUtils.toString(commentsReation.dislikes));
                                    }
                                });
                                isDisLike = true;
                            }
                        }
                    });
                } else {
                    userViewModel.removeReplayLikeOrDislike(user_id, comments.id).observe((LifecycleOwner) context, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("added successfully")) {
                                int tabIconColor = ContextCompat.getColor(context, R.color.colorGrayDark);
                                holder.commentItemBinding.thumbsDown.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                                userViewModel.showCommentsReation(comments.id).observe((LifecycleOwner) context, new Observer<CommentsReation>() {
                                    @Override
                                    public void onChanged(CommentsReation commentsReation) {
                                        holder.commentItemBinding.commentDislike.setText(StringsUtils.toString(commentsReation.dislikes));
                                    }
                                });
                                isDisLike = false;
                            }
                        }
                    });

                }
            }
        });
//        holder.commentItemBinding.thumbsUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (user_id == 0) {
//                    showDialog(v);
//                    return;
//                }
//                if (isDisLikeBefore) {
//                    userViewModel.removeReplayLikeOrDislike(user_id, comments.id).observe((LifecycleOwner) context, new Observer<String>() {
//                        @Override
//                        public void onChanged(String s) {
//                            if (s.equals("added successfully")) {
//                                int tabIconColor = ContextCompat.getColor(context, R.color.colorGrayDark);
//                                holder.commentItemBinding.thumbsDown.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                                userViewModel.showCommentsReation(comments.id).observe((LifecycleOwner) context, new Observer<CommentsReation>() {
//                                    @Override
//                                    public void onChanged(CommentsReation commentsReation) {
//                                        holder.commentItemBinding.commentDislike.setText(StringsUtils.toString(comments.dislikes));
//                                    }
//                                });
//                                isLike = false;
//                            }
//                        }
//                    });
//                }
//                if (!isLike) {
//                    userViewModel.setReplayLikeOrDislike(1, user_id, comments.id).observe((LifecycleOwner) context, new Observer<String>() {
//                        @Override
//                        public void onChanged(String s) {
//                            if (s.equals("added successfully")) {
//                                int tabIconColor = ContextCompat.getColor(context, R.color.colorAccent);
//                                holder.commentItemBinding.thumbsUp.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                                userViewModel.showCommentsReation(comments.id).observe((LifecycleOwner) context, new Observer<CommentsReation>() {
//                                    @Override
//                                    public void onChanged(CommentsReation commentsReation) {
//                                        holder.commentItemBinding.commentLike.setText(StringsUtils.toString(comments.likes));
//                                    }
//                                });
//                                isLike = true;
//                            }
//                        }
//                    });
//                    Log.e(TAG, "onClick:thumbsUp " + isLike);
//                } else {
//                    userViewModel.removeReplayLikeOrDislike(user_id, comments.id).observe((LifecycleOwner) context, new Observer<String>() {
//                        @Override
//                        public void onChanged(String s) {
//                            if (s.equals("added successfully")) {
//                                int tabIconColor = ContextCompat.getColor(context, R.color.colorGrayDark);
//                                holder.commentItemBinding.thumbsUp.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                                userViewModel.showCommentsReation(comments.id).observe((LifecycleOwner) context, new Observer<CommentsReation>() {
//                                    @Override
//                                    public void onChanged(CommentsReation commentsReation) {
//                                        holder.commentItemBinding.commentLike.setText(StringsUtils.toString(comments.likes));
//                                    }
//                                });
//                                isLike = false;
//                            }
//                        }
//                    });
//
//                }
//
////                if (user_id == 0) {
////                    showDialog(v);
////                    return;
////                }
////                if (!isLike) {
////                    userViewModel.setReplayLikeOrDislike(1, user_id, comments.id).observe((LifecycleOwner) context, new Observer<String>() {
////                        @Override
////                        public void onChanged(String s) {
////                            if (s.equals("added successfully")) {
////                                int tabIconColor = ContextCompat.getColor(context, R.color.colorAccent);
////                                holder.commentItemBinding.thumbsUp.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
////                                holder.commentItemBinding.commentLike.setText(StringsUtils.toString(comments.likes + 1));
////                                isLike = true;
////                            }
////                        }
////                    });
////                } else {
////                    userViewModel.removeReplayLikeOrDislike(user_id, comments.id).observe((LifecycleOwner) context, new Observer<String>() {
////                        @Override
////                        public void onChanged(String s) {
////                            if (s.equals("added successfully")) {
////                                int tabIconColor = ContextCompat.getColor(context, R.color.colorGrayDark);
////                                holder.commentItemBinding.thumbsUp.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
////                                if (comments.likes == 0) {
////                                    holder.commentItemBinding.commentLike.setText(StringsUtils.toString(comments.likes));
////                                } else {
////                                    holder.commentItemBinding.commentLike.setText(StringsUtils.toString(comments.likes - 1));
////                                }
////                                isLike = false;
////                            }
////                        }
////                    });
//
////                }
//            }
//        });
//        holder.commentItemBinding.thumbsDown.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                count++;
////                Log.e(TAG, "onClick:thumbsUp " + count);
//                if (user_id == 0) {
//                    showDialog(v);
//                    return;
//                }
//                if (isLikeBefore) {
//                    userViewModel.removeReplayLikeOrDislike(user_id, comments.id).observe((LifecycleOwner) context, new Observer<String>() {
//                        @Override
//                        public void onChanged(String s) {
//                            if (s.equals("added successfully")) {
//                                int tabIconColor = ContextCompat.getColor(context, R.color.colorGrayDark);
//                                holder.commentItemBinding.thumbsDown.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                                userViewModel.showCommentsReation(comments.id).observe((LifecycleOwner) context, new Observer<CommentsReation>() {
//                                    @Override
//                                    public void onChanged(CommentsReation commentsReation) {
//                                        holder.commentItemBinding.commentLike.setText(StringsUtils.toString(comments.likes));
//                                    }
//                                });
////                                if (comments.dislikes == 0) {
////                                    holder.commentItemBinding.commentDislike.setText(StringsUtils.toString(comments.dislikes));
////                                } else {
////                                    holder.commentItemBinding.commentDislike.setText(StringsUtils.toString(comments.dislikes - 1));
////                                }
//                                isLike = false;
//                            }
//                        }
//                    });
//                }
//                if (!isDisLike) {
//                    userViewModel.setReplayLikeOrDislike(2, user_id, comments.id).observe((LifecycleOwner) context, new Observer<String>() {
//                        @Override
//                        public void onChanged(String s) {
//                            if (s.equals("added successfully")) {
//                                int tabIconColor = ContextCompat.getColor(context, R.color.colorAccent);
//                                holder.commentItemBinding.thumbsDown.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                                userViewModel.showCommentsReation(comments.id).observe((LifecycleOwner) context, new Observer<CommentsReation>() {
//                                    @Override
//                                    public void onChanged(CommentsReation commentsReation) {
//                                        holder.commentItemBinding.commentDislike.setText(StringsUtils.toString(comments.dislikes));
//                                    }
//                                });
////                                holder.commentItemBinding.commentDislike.setText(StringsUtils.toString(comments.dislikes + 1));
//                                isDisLike = true;
//                            }
//                        }
//                    });
//                } else {
//                    userViewModel.removeReplayLikeOrDislike(user_id, comments.id).observe((LifecycleOwner) context, new Observer<String>() {
//                        @Override
//                        public void onChanged(String s) {
//                            if (s.equals("added successfully")) {
//                                int tabIconColor = ContextCompat.getColor(context, R.color.colorGrayDark);
//                                holder.commentItemBinding.thumbsDown.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                                userViewModel.showCommentsReation(comments.id).observe((LifecycleOwner) context, new Observer<CommentsReation>() {
//                                    @Override
//                                    public void onChanged(CommentsReation commentsReation) {
//                                        holder.commentItemBinding.commentDislike.setText(StringsUtils.toString(comments.dislikes));
//                                    }
//                                });
////                                if (comments.dislikes == 0) {
////                                    holder.commentItemBinding.commentDislike.setText(StringsUtils.toString(comments.dislikes));
////                                } else {
////                                    holder.commentItemBinding.commentDislike.setText(StringsUtils.toString(comments.dislikes - 1));
////                                }
//                                isDisLike = false;
//                            }
//                        }
//                    });
//
//                }
//            }
//        });
        holder.commentItemBinding.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.commentItemBinding.commentLayout.setVisibility(View.VISIBLE);
            }
        });
        holder.commentItemBinding.sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = holder.commentItemBinding.commentEditText.getText().toString();
                if (user_id == 0) {
                    showDialog(v);
                    return;
                }
                if (comment.isEmpty()) {
                    MyApplication.showMessageBottom(v, context.getResources().getString(R.string.type_reply) + " " + context.getResources().getString(R.string.error_message_empty));
                    return;
                }
                Log.e(TAG, "onClick: " + user_id);
                Log.e(TAG, "onClick: " + comment);
                Log.e(TAG, "onClick: " + comments.news_id);
                Log.e(TAG, "onClick: " + comments.user_id);
//                Log.e(TAG, "onClick: " + FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                userViewModel.addReply(comment, user_id, comments.id, comments.news_id).observe((LifecycleOwner) context, new Observer<Message>() {
                    @Override
                    public void onChanged(Message message) {
                        if (message.message.equals("Comment added successfully")) {
                            holder.commentItemBinding.commentEditText.setText("");
                            MyApplication.showMessage(v, context.getResources().getString(R.string.add_reply_success));
                            userViewModel.showCommentsOfNews(comments.news_id).observe((LifecycleOwner) context, new Observer<Comments>() {
                                @Override
                                public void onChanged(Comments comments) {
                                    List<Comments.Comment.Replies> list = new ArrayList<>();
                                    for (int i = 0; i < comments.comments.get(position).replies.size(); i++) {
                                        list.addAll(comments.comments.get(position).replies.get(i));
                                    }
                                    replaiesAdapter = new ReplaiesAdapter(context);
                                    replaiesAdapter.submitList(list);
                                    replaiesAdapter.notifyDataSetChanged();
                                    holder.commentItemBinding.replaysRv.setAdapter(replaiesAdapter);
                                }
                            });


                        }
                    }
                });

            }
        });


    }

    private List<Comments.Comment.Replies> setComments(int id) {
        repliesList = new ArrayList<>();
        userViewModel.showCommentsOfNews(id).observe((LifecycleOwner) context, new Observer<Comments>() {
            @Override
            public void onChanged(Comments comments) {
                if (comments != null) {
                    for (int i = 0; i < comments.comments.size(); i++) {
                        if (comments.comments.get(i).news_id == id) {
                            for (int j = 0; j < comments.comments.get(i).replies.size(); j++) {
                                repliesList.addAll(comments.comments.get(i).replies.get(j));
                            }
                        }
                    }
                }
            }
        });
        return repliesList;
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private CommentItemBinding commentItemBinding;

        public CommentsViewHolder(@NonNull CommentItemBinding itemView) {
            super(itemView.getRoot());
            commentItemBinding = itemView;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(getAdapterPosition(), view);
        }
    }

}
