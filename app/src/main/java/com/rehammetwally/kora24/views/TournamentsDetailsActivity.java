package com.rehammetwally.kora24.views;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;
import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.ViewPagerAdapter;
import com.rehammetwally.kora24.databinding.ActivityTournamentsDetailsBinding;
import com.rehammetwally.kora24.models.Tournaments;
import com.rehammetwally.kora24.utils.MyApplication;

import akndmr.github.io.colorprefutil.ColorPrefUtil;

public class TournamentsDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityTournamentsDetailsBinding binding;
    private ActionBarDrawerToggle mDrawerToggle;
    private Tournaments tournaments;
    private static final String TAG = "TournamentsDetailsActiv";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int themeSelected = MyApplication.getPref().getInt(MainActivity.THEME_SELECTED);
        ColorPrefUtil.changeThemeStyle(this, themeSelected);
        Log.e(TAG, "onCreate: " + themeSelected);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tournaments_details);
        tournaments = (Tournaments) getIntent().getExtras().get("TOURNAMENTS");

        Log.e(TAG, "onCreate: " + tournaments.title);
        Log.e(TAG, "onCreate:id " + tournaments.id);
        binding.setTournaments(tournaments);
        setupToolbar();
        setupAppBar();
        binding.shareTournaments.setOnClickListener(this);
        setupViewPager(binding.pager);
        binding.tabLayout.setupWithViewPager(binding.pager);

        for (int i = 0; i < binding.tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) binding.tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(16, 16, 16, 16);
            tab.requestLayout();
        }

    }

    private void setupAppBar() {
        binding.appBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
        binding.toolbarTitle.setVisibility(View.INVISIBLE);

    }

    private void showTitle() {
        binding.toolbarTitle.setVisibility(View.VISIBLE);
        String title = "";
        if (MyApplication.getPref().getString("TOURNAMENTS_TITLE") != null) {
            binding.toolbarTitle.setText(MyApplication.getPref().getString("TOURNAMENTS_TITLE"));
        } else {
            if (tournaments.title.length() <= 25) {
                title = tournaments.title;
            } else {
                title = tournaments.title.substring(0, 25).concat("...");
            }
            binding.toolbarTitle.setText(title);
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundle = new Bundle();
        LastNewsFragment lastNewsFragment = new LastNewsFragment();
        Log.e(TAG, "setupViewPager: " + MyApplication.getPref().getInt("TOURNAMENTS_ID"));
//        if (MyApplication.getPref().getInt("TOURNAMENTS_ID") !=0){
//            bundle.putInt("COMPITIONID",MyApplication.getPref().getInt("TOURNAMENTS_ID"));
//        }else {
        bundle.putInt("COMPITIONID", tournaments.id);
//        }
        lastNewsFragment.setArguments(bundle);
        FixturesTournamentsFragment fixturesTournamentsFragment = new FixturesTournamentsFragment();
        bundle.putInt("COMPITIONID", tournaments.id);
        fixturesTournamentsFragment.setArguments(bundle);
        adapter.addFragment(lastNewsFragment, getResources().getString(R.string.last_news));
        adapter.addFragment(fixturesTournamentsFragment, getResources().getString(R.string.fixtures));
        viewPager.setAdapter(adapter);
    }


    void setupToolbar() {
        setSupportActionBar(binding.toolbar);
        binding.drawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();
    }


    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_custom_drawer_icon);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TournamentsDetailsActivity.this, MainActivity.class));
            }
        });
        mDrawerToggle.syncState();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_tournaments:
                MyApplication.shareApp(this);
                break;
        }
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
}
