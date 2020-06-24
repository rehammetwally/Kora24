package com.rehammetwally.kora24.views;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import com.google.android.material.appbar.AppBarLayout;
import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.ActivityFixturesDetailsBinding;
import com.rehammetwally.kora24.models.MessageMatch;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.viewmodels.TournamentsViewModel;

import akndmr.github.io.colorprefutil.ColorPrefUtil;

public class FixturesDetailsActivity extends AppCompatActivity {
    private ActivityFixturesDetailsBinding activityFixturesDetailsBinding;
    private ActionBarDrawerToggle mDrawerToggle;
    private TournamentsViewModel tournamentsViewModel;
    private int game_id;
    private static final String TAG = "FixturesDetailsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int themeSelected = MyApplication.getPref().getInt(MainActivity.THEME_SELECTED);
        ColorPrefUtil.changeThemeStyle(this, themeSelected);
        Log.e(TAG, "onCreate: " + themeSelected);
        activityFixturesDetailsBinding = DataBindingUtil.setContentView(this, R.layout.activity_fixtures_details);
        tournamentsViewModel = ViewModelProviders.of(this).get(TournamentsViewModel.class);
        game_id = getIntent().getExtras().getInt("MATCH");
        Log.e(TAG, "onCreate: "+game_id );
        tournamentsViewModel.getGameInfo(game_id).observe(this, new Observer<MessageMatch>() {
            @Override
            public void onChanged(MessageMatch messageMatch) {
                Log.e(TAG, "onChanged: "+messageMatch.match.commentor );
                activityFixturesDetailsBinding.setMatch(messageMatch.match);
                setupAppBar(messageMatch);
            }
        });
        setupToolbar();

    }



    private void setupAppBar(MessageMatch messageMatch) {
        activityFixturesDetailsBinding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    showTitle(messageMatch.match.hometeam.t_name+" - "+messageMatch.match.awayteam.t_name);
                } else if (isShow) {
                    isShow = false;
                    hideTitle();
                }
            }
        });
    }

    private void hideTitle() {
        activityFixturesDetailsBinding.toolbarTitle.setVisibility(View.INVISIBLE);

    }

    private void showTitle(String title) {
        activityFixturesDetailsBinding.toolbarTitle.setVisibility(View.VISIBLE);
        activityFixturesDetailsBinding.toolbarTitle.setText(title);
    }
    void setupToolbar() {
        setSupportActionBar(activityFixturesDetailsBinding.toolbar);
        activityFixturesDetailsBinding.drawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();
    }


    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, activityFixturesDetailsBinding.drawerLayout, activityFixturesDetailsBinding.toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_custom_drawer_icon);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FixturesDetailsActivity.this, MainActivity.class));
                finish();
            }
        });
        mDrawerToggle.syncState();
    }


}
