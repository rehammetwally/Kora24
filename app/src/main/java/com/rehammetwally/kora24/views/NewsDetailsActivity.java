package com.rehammetwally.kora24.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.appbar.AppBarLayout;
import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.CommentsAdapter;
import com.rehammetwally.kora24.databinding.ActivityNewsDetailsBinding;
import com.rehammetwally.kora24.models.Comments;
import com.rehammetwally.kora24.models.Message;
import com.rehammetwally.kora24.models.News;
import com.rehammetwally.kora24.models.NewsReation;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.utils.StringsUtils;
import com.rehammetwally.kora24.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.List;

import akndmr.github.io.colorprefutil.ColorPrefUtil;

public class NewsDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private News news;
    private ActionBarDrawerToggle mDrawerToggle;
    private ActivityNewsDetailsBinding activityNewsDetailsBinding;
    public static final String EXTRA_LOGIN = "EXTRA_LOGIN";
    private static final String TAG = "NewsDetailsActivity";
    private UserViewModel userViewModel;
    private List<Comments.Comment> commentList;
    private int user_id;
    private int count = 0;
    private int views = 0;
    private boolean isLike = false;
    private boolean isDisLike = false;
    private boolean isLikeBefore = false;
    private boolean isDisLikeBefore = false;
    private CommentsAdapter commentsAdapter;
    int themeSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        themeSelected = MyApplication.getPref().getInt(MainActivity.THEME_SELECTED);
        ColorPrefUtil.changeThemeStyle(this, themeSelected);
        Log.e(TAG, "onCreate: " + themeSelected);
        activityNewsDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_news_details);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        activityNewsDetailsBinding.commentsList.setHasFixedSize(true);
        activityNewsDetailsBinding.commentsList.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(activityNewsDetailsBinding.commentsList.getContext(),
                DividerItemDecoration.VERTICAL);
        activityNewsDetailsBinding.commentsList.addItemDecoration(dividerItemDecoration);
        setupToolbar();
        setupAppBar();
        user_id = MyApplication.getPref().getInt("USERID");
        Log.e(TAG, "onCreate:user_id " + user_id);
        if (getIntent() != null) {
            news = (News) getIntent().getExtras().get("DETAILS");
            Log.e(TAG, "onCreate: " + news.title);
            Log.e(TAG, "onCreate: " + news.views);
            activityNewsDetailsBinding.setNews(news);
            Log.e(TAG, "Icon: " + news.icon());
        }
        views++;
        Log.e(TAG, "onCreate: " + views);
        userViewModel.addNewsView(news.id).observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s.equals("added successfully")) {
                    Log.e(TAG, "addNewsView successfully ");
//                    activityNewsDetailsBinding.lastNewsViewsText.setText(StringsUtils.toString(views));
                }
            }
        });
        userViewModel.showNewsReation(news.id).observe(this, new Observer<NewsReation>() {
            @Override
            public void onChanged(NewsReation newsReation) {
                Log.e(TAG, "onChanged: " + newsReation.comments);
                Log.e(TAG, "onChanged: " + newsReation.likes);
                Log.e(TAG, "onChanged: " + newsReation.dislikes);
                Log.e(TAG, "onChanged: " + newsReation.views);
                activityNewsDetailsBinding.like.setText(StringsUtils.toString(newsReation.likes));
                activityNewsDetailsBinding.dislike.setText(StringsUtils.toString(newsReation.dislikes));
                activityNewsDetailsBinding.lastNewsViewsText.setText(StringsUtils.toString(newsReation.views));
            }
        });
        Log.e(TAG, "onCreate: " + news.title);
        setComments();

        activityNewsDetailsBinding.fab.setOnClickListener(this);
        activityNewsDetailsBinding.shareNews.setOnClickListener(this);
        activityNewsDetailsBinding.shareNewsLikeLayout.setOnClickListener(this);
        activityNewsDetailsBinding.showAllComments.setOnClickListener(this);
        activityNewsDetailsBinding.sendComment.setOnClickListener(this);
//        if (!isDisLikeBefore) {
        activityNewsDetailsBinding.thumbsUp.setOnClickListener(this);
//        }
//        if (!isLikeBefore) {
        activityNewsDetailsBinding.thumbsDown.setOnClickListener(this);
//        }
        activityNewsDetailsBinding.commentEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "commentEditText: ");
                activityNewsDetailsBinding.commentsList.setMinimumHeight(500);
            }
        });
        activityNewsDetailsBinding.commentEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                    case EditorInfo.IME_ACTION_NEXT:
                    case EditorInfo.IME_ACTION_PREVIOUS:
                        Log.e(TAG, "commentEditText: ");
                        activityNewsDetailsBinding.commentsList.setMinimumHeight(500);
                        return true;
                }
                return false;
            }
        });
    }

    public void setComments() {
        commentsAdapter = new CommentsAdapter(this);
        userViewModel.showCommentsOfNews(news.id).observe(this, new Observer<Comments>() {
            @Override
            public void onChanged(Comments comments) {
                if (comments.message.equals("Returned Successfully")) {
                    if (comments.comments.size() > 0) {
                        List<Comments.Comment> list = new ArrayList<>();
                        if (comments.comments.size() > 2) {
                            activityNewsDetailsBinding.showAllComments.setVisibility(View.VISIBLE);
                            for (int i = 0; i < comments.comments.size(); i++) {
                                if (i < 2) {
                                    list.add(comments.comments.get(i));
                                }
                            }
                            commentsAdapter.submitList(list);
                            activityNewsDetailsBinding.commentsList.setAdapter(commentsAdapter);
                        } else {
                            activityNewsDetailsBinding.showAllComments.setVisibility(View.GONE);
                            commentsAdapter.submitList(comments.comments);
                            activityNewsDetailsBinding.commentsList.setAdapter(commentsAdapter);
                        }
//                        for (int i = 0; i < comments.comments.size(); i++) {
//                            if (i < 2) {
//                                list.addAll(comments.comments);
//
//                                commentsAdapter.submitList(list);
//                                commentsAdapter.notifyDataSetChanged();
//                                activityNewsDetailsBinding.commentsList.setAdapter(commentsAdapter);
//                            }else {
//                                commentsAdapter.submitList(comments.comments);
//                                commentsAdapter.notifyDataSetChanged();
//                                activityNewsDetailsBinding.commentsList.setAdapter(commentsAdapter);
//                            }
//                        }
//                        if (i< 2) {
//                            list.addAll(comments.comments);
//
//                            commentsAdapter.submitList(list);
//                            commentsAdapter.notifyDataSetChanged();
//                            activityNewsDetailsBinding.commentsList.setAdapter(commentsAdapter);
//                        }else {

//                        }
                    }
                }
            }
        });
//        userViewModel.showCommentsOfNews(news.id).observe(this, new Observer<Comments>() {
//            @Override
//            public void onChanged(Comments comments) {
//                if (comments.message.equals("Returned Successfully")) {
//                    if (comments.comments.size() > 0) {
//                        activityNewsDetailsBinding.showAllComments.setVisibility(View.GONE);
//                        commentList = new ArrayList<>();
//                        for (int i = 0; i < comments.comments.size(); i++) {
//                            if (i < 2) {
//                                Log.e(TAG, "comments<3: " + i);
//                                commentList.add(comments.comments.get(i));
//                            }
//                        }
//                        Log.e(TAG, "commentList: " + commentList.size());
//                        commentsAdapter.submitList(commentList);
//                        activityNewsDetailsBinding.commentsList.setAdapter(commentsAdapter);
//                        Log.e(TAG, "setComments: " + commentsAdapter.getCurrentList().size());
//                        if (comments.comments.size() >= 4) {
//                            activityNewsDetailsBinding.showAllComments.setVisibility(View.VISIBLE);
//                            activityNewsDetailsBinding.showAllComments.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    if (comments.comments.size() > 0) {
//                                        commentsAdapter.submitList(comments.comments);
//                                        activityNewsDetailsBinding.commentsList.setAdapter(commentsAdapter);
//                                    }
////
//                                }
//                            });
//                        }
//
//                        commentsAdapter.submitList(commentList);
//                        activityNewsDetailsBinding.commentsList.setAdapter(commentsAdapter);
//                    }
//                }
//            }
//        });
//        commentsAdapter.notifyDataSetChanged();
//        Log.e(TAG, "setComments:size " + commentsAdapter.getCurrentList().size());
    }


    private void setupAppBar() {
        activityNewsDetailsBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    showTitle();
                } else if (isShow) {
                    isShow = false;
                    hideTitle();
                }
            }
        });
    }

    private void hideTitle() {
        activityNewsDetailsBinding.toolbarTitle.setVisibility(View.INVISIBLE);

    }

    private void showTitle() {
        activityNewsDetailsBinding.toolbarTitle.setVisibility(View.VISIBLE);
        String title = "";
        if (news.title.length() <= 25) {
            title = news.title;
        } else {
            title = news.title.substring(0, 25).concat("...");
        }
        activityNewsDetailsBinding.toolbarTitle.setText(title);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    void setupToolbar() {
        setSupportActionBar(activityNewsDetailsBinding.toolbar);
        activityNewsDetailsBinding.drawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();
    }


    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, activityNewsDetailsBinding.drawerLayout, activityNewsDetailsBinding.toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_custom_drawer_icon);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewsDetailsActivity.this, MainActivity.class));
            }
        });
        mDrawerToggle.syncState();
    }

    boolean isDown = false;

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.fab:
                activityNewsDetailsBinding.appBar.setExpanded(false);
                activityNewsDetailsBinding.detailsScrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        if (!isDown) {
                            isDown = true;
                            activityNewsDetailsBinding.fab.setImageResource(R.drawable.ic_arrow_up);
                            activityNewsDetailsBinding.detailsScrollView.fullScroll(ScrollView.FOCUS_DOWN);
                        } else {
                            isDown = false;
//                            activityNewsDetailsBinding.appBar..fullScroll(ScrollView.FOCUS_DOWN);
                            activityNewsDetailsBinding.fab.setImageResource(R.drawable.ic_arrow_down);
                            activityNewsDetailsBinding.appBar.setExpanded(true);
                            activityNewsDetailsBinding.detailsScrollView.fullScroll(ScrollView.FOCUS_UP);
                        }
                        Log.e(TAG, "isDown: " + isDown);
                    }
                });
                break;
            case R.id.shareNews:
            case R.id.shareNewsLikeLayout:
                MyApplication.shareApp(this);
                break;
            case R.id.show_all_comments:
                showAllComments();
                break;

            case R.id.send_comment:
                String comment = activityNewsDetailsBinding.commentEditText.getText().toString();
                if (user_id == 0) {
                    showDialog(v);
                    return;
                }
                if (comment.isEmpty()) {
                    activityNewsDetailsBinding.appBar.setExpanded(true);
                    activityNewsDetailsBinding.detailsScrollView.fullScroll(ScrollView.FOCUS_UP);

                    MyApplication.showMessage(v, getResources().getString(R.string.type_comment) + " " + getResources().getString(R.string.error_message_empty));
                    return;
                }
                Log.e(TAG, "onClick: " + user_id);
                Log.e(TAG, "onClick: " + comment);
                Log.e(TAG, "onClick: " + news.id);
//                Log.e(TAG, "onClick: " + FirebaseAuth.getInstance().getCurrentUser().getUid().toString());
                userViewModel.addComment(comment, user_id, news.id).observe(this, new Observer<Message>() {
                    @Override
                    public void onChanged(Message message) {
                        if (message.message.equals("Comment added successfully")) {
                            activityNewsDetailsBinding.commentEditText.setText("");
                            MyApplication.showMessageBottom(v, getResources().getString(R.string.add_comment_success));
//                            commentsAdapter.notifyDataSetChanged();
//                            setComments();
//                            reloadComments();
                            showAllComments();
                        }
                    }
                });

                break;
            case R.id.thumbsUp:
                isLikeBefore = true;
                if (user_id == 0) {
                    showDialog(v);
                    return;
                }
                if (isDisLikeBefore) {
                    userViewModel.removeLikeOrDislike(user_id, news.id).observe(this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("added successfully")) {
                                int tabIconColor = ContextCompat.getColor(NewsDetailsActivity.this, R.color.colorGrayDark);
                                activityNewsDetailsBinding.thumbsDown.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                                userViewModel.showNewsReation(news.id).observe(NewsDetailsActivity.this, new Observer<NewsReation>() {
                                    @Override
                                    public void onChanged(NewsReation newsReation) {
                                        activityNewsDetailsBinding.dislike.setText(StringsUtils.toString(newsReation.dislikes));
                                    }
                                });
                                isLike = false;
                            }
                        }
                    });
                }
                if (!isLike) {
                    userViewModel.setLikeOrDislike(1, user_id, news.id).observe(this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("added successfully")) {
                                int tabIconColor = ContextCompat.getColor(NewsDetailsActivity.this, R.color.colorAccent);
                                activityNewsDetailsBinding.thumbsUp.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                                userViewModel.showNewsReation(news.id).observe(NewsDetailsActivity.this, new Observer<NewsReation>() {
                                    @Override
                                    public void onChanged(NewsReation newsReation) {
                                        activityNewsDetailsBinding.like.setText(StringsUtils.toString(newsReation.likes));
                                    }
                                });
                                isLike = true;
                            }
                        }
                    });
                    Log.e(TAG, "onClick:thumbsUp " + isLike);
                } else {
                    userViewModel.removeLikeOrDislike(user_id, news.id).observe(this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("added successfully")) {
                                int tabIconColor = ContextCompat.getColor(NewsDetailsActivity.this, R.color.colorGrayDark);
                                activityNewsDetailsBinding.thumbsUp.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                                userViewModel.showNewsReation(news.id).observe(NewsDetailsActivity.this, new Observer<NewsReation>() {
                                    @Override
                                    public void onChanged(NewsReation newsReation) {
                                        activityNewsDetailsBinding.like.setText(StringsUtils.toString(newsReation.likes));
                                    }
                                });
                                isLike = false;
                            }
                        }
                    });

                }

                break;
            case R.id.thumbsDown:
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
                    userViewModel.removeLikeOrDislike(user_id, news.id).observe(this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("added successfully")) {
                                int tabIconColor = ContextCompat.getColor(NewsDetailsActivity.this, R.color.colorGrayDark);
                                activityNewsDetailsBinding.thumbsUp.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                                userViewModel.showNewsReation(news.id).observe(NewsDetailsActivity.this, new Observer<NewsReation>() {
                                    @Override
                                    public void onChanged(NewsReation newsReation) {
                                        activityNewsDetailsBinding.like.setText(StringsUtils.toString(newsReation.likes));
                                    }
                                });
                                isLike = false;
                            }
                        }
                    });
                }
                if (!isDisLike) {
                    userViewModel.setLikeOrDislike(2, user_id, news.id).observe(this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("added successfully")) {
                                int tabIconColor = ContextCompat.getColor(NewsDetailsActivity.this, R.color.colorAccent);
                                activityNewsDetailsBinding.thumbsDown.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                                activityNewsDetailsBinding.dislike.setText(StringsUtils.toString(news.dislikes_num + 1));
                                userViewModel.showNewsReation(news.id).observe(NewsDetailsActivity.this, new Observer<NewsReation>() {
                                    @Override
                                    public void onChanged(NewsReation newsReation) {
                                        activityNewsDetailsBinding.dislike.setText(StringsUtils.toString(newsReation.dislikes));
                                    }
                                });
                                isDisLike = true;
                            }
                        }
                    });
                } else {
                    userViewModel.removeLikeOrDislike(user_id, news.id).observe(this, new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            if (s.equals("added successfully")) {
                                int tabIconColor = ContextCompat.getColor(NewsDetailsActivity.this, R.color.colorGrayDark);
                                activityNewsDetailsBinding.thumbsDown.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                                userViewModel.showNewsReation(news.id).observe(NewsDetailsActivity.this, new Observer<NewsReation>() {
                                    @Override
                                    public void onChanged(NewsReation newsReation) {
                                        activityNewsDetailsBinding.dislike.setText(StringsUtils.toString(newsReation.dislikes));
                                    }
                                });
                                isDisLike = false;
                            }
                        }
                    });

                }
                break;
        }

//        if (v.getId() == R.id.thumbsUp) {
//            isLikeBefore = true;
//            Log.e(TAG, "onClick:thumbsUp " + isLike);
//            if (user_id == 0) {
//                showDialog(v);
//                return;
//            }
//            if (!isLike) {
//                userViewModel.setLikeOrDislike(1, user_id, news.id).observe(this, new Observer<String>() {
//                    @Override
//                    public void onChanged(String s) {
//                        if (s.equals("added successfully")) {
//                            int tabIconColor = ContextCompat.getColor(NewsDetailsActivity.this, R.color.colorAccent);
//                            activityNewsDetailsBinding.thumbsUp.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                            userViewModel.showNewsReation(news.id).observe(NewsDetailsActivity.this, new Observer<NewsReation>() {
//                                @Override
//                                public void onChanged(NewsReation newsReation) {
//                                    activityNewsDetailsBinding.like.setText(StringsUtils.toString(newsReation.likes));
//                                }
//                            });
//                            isLike = true;
//                        }
//                    }
//                });
//                Log.e(TAG, "onClick:thumbsUp " + isLike);
//            } else {
//                userViewModel.removeLikeOrDislike(user_id, news.id).observe(this, new Observer<String>() {
//                    @Override
//                    public void onChanged(String s) {
//                        if (s.equals("added successfully")) {
//                            int tabIconColor = ContextCompat.getColor(NewsDetailsActivity.this, R.color.colorGrayDark);
//                            activityNewsDetailsBinding.thumbsUp.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                            userViewModel.showNewsReation(news.id).observe(NewsDetailsActivity.this, new Observer<NewsReation>() {
//                                @Override
//                                public void onChanged(NewsReation newsReation) {
//                                    activityNewsDetailsBinding.like.setText(StringsUtils.toString(newsReation.likes));
//                                }
//                            });
//                            isLike = false;
//                        }
//                    }
//                });
//
//            }
//        } else if (v.getId() == R.id.thumbsDown) {
//            isDisLikeBefore = true;
//            Log.e(TAG, "onClick:thumbsUp " + count);
//            if (user_id == 0) {
//                showDialog(v);
//                return;
//            }
//            if (!isLike) {
//                userViewModel.setLikeOrDislike(2, user_id, news.id).observe(this, new Observer<String>() {
//                    @Override
//                    public void onChanged(String s) {
//                        if (s.equals("added successfully")) {
//                            int tabIconColor = ContextCompat.getColor(NewsDetailsActivity.this, R.color.colorAccent);
//                            activityNewsDetailsBinding.thumbsDown.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
////                                activityNewsDetailsBinding.dislike.setText(StringsUtils.toString(news.dislikes_num + 1));
//                            userViewModel.showNewsReation(news.id).observe(NewsDetailsActivity.this, new Observer<NewsReation>() {
//                                @Override
//                                public void onChanged(NewsReation newsReation) {
//                                    activityNewsDetailsBinding.dislike.setText(StringsUtils.toString(newsReation.dislikes));
//                                }
//                            });
//                            isLike = true;
//                        }
//                    }
//                });
//            } else {
//                userViewModel.removeLikeOrDislike(user_id, news.id).observe(this, new Observer<String>() {
//                    @Override
//                    public void onChanged(String s) {
//                        if (s.equals("added successfully")) {
//                            int tabIconColor = ContextCompat.getColor(NewsDetailsActivity.this, R.color.colorGrayDark);
//                            activityNewsDetailsBinding.thumbsDown.setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
//                            userViewModel.showNewsReation(news.id).observe(NewsDetailsActivity.this, new Observer<NewsReation>() {
//                                @Override
//                                public void onChanged(NewsReation newsReation) {
//                                    activityNewsDetailsBinding.dislike.setText(StringsUtils.toString(newsReation.dislikes));
//                                }
//                            });
//                            isLike = false;
//                        }
//                    }
//                });
//
//            }
//        }
    }

    private void showAllComments() {
        commentsAdapter = new CommentsAdapter(this);
        userViewModel.showCommentsOfNews(news.id).observe(this, new Observer<Comments>() {
            @Override
            public void onChanged(Comments comments) {
                commentsAdapter.submitList(comments.comments);
                commentsAdapter.notifyDataSetChanged();
                activityNewsDetailsBinding.commentsList.setAdapter(commentsAdapter);
            }
        });
    }

    public void showDialog(View view) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this, themeSelected));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(view.getContext()).inflate(R.layout.custom_dialog, viewGroup, false);
        TextView title = dialogView.findViewById(R.id.dialog_title);
        title.setGravity(Gravity.CENTER_HORIZONTAL);
        title.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        TextView message = dialogView.findViewById(R.id.dialog_message);
        message.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        message.setGravity(Gravity.CENTER_HORIZONTAL);
        message.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
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
                Intent intent = new Intent(NewsDetailsActivity.this, MainActivity.class);
                intent.putExtra(EXTRA_LOGIN, true);
                startActivity(intent);
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
//        AlertDialog dialog = new AlertDialog.Builder(this,R.style.RightJustifyTheme)
//                .setTitle("اضافة تعليق جديد")
//                .setMessage("عزيزى المستخدم يجب عليك تسجيل الدخول لكى تتمكن من اضافة تعليق")
//                .setPositiveButton("تسجيل دخول", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int which) {
////                        startActivity(new Intent(BaseActivity.this, LoginActivity.class));
//                    }
//                }).setNegativeButton("الغاء", null)
//                .show();
//        LinearLayout diagLayout = new LinearLayout(this);
//        diagLayout.setOrientation(LinearLayout.VERTICAL);
//        TextView title = new TextView(this);
//        title.setTypeface(ResourcesCompat.getFont(this, R.font.bukra));
//        WindowManager.LayoutParams params=new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//
//        title.setGravity(Gravity.CENTER);
//        title.setPadding(20, 20, 20, 20);
//        diagLayout.addView(title);
//        dialog.setView(diagLayout);
//        dialog.setCustomTitle(title);
//        TextView textView = dialog.findViewById(android.R.id.message);
//        Button button1 = dialog.getWindow().findViewById(android.R.id.button1);
//        Button button2 = dialog.getWindow().findViewById(android.R.id.button2);
//        textView.setLineSpacing(2f,2f);
////        title.setTextSize(13f);
//        textView.setTextSize(13f);
////        title.setTypeface(ResourcesCompat.getFont(this, R.font.bukra));
//        textView.setTypeface(ResourcesCompat.getFont(this, R.font.bukra));
////        button1.setTypeface(ResourcesCompat.getFont(this, R.font.bukra));
//        button1.setTextColor(getResources().getColor(R.color.colorAccent));
//        button2.setTextColor(getResources().getColor(R.color.colorAccent));
//        button2.setTypeface(ResourcesCompat.getFont(this, R.font.bukra));
    }

}
