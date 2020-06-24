package com.rehammetwally.kora24.views;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.DrawerItemCustomAdapter;
import com.rehammetwally.kora24.databinding.ActivityMainBinding;
import com.rehammetwally.kora24.models.NavModel;
import com.rehammetwally.kora24.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import akndmr.github.io.colorprefutil.ColorPrefUtil;

public class MainActivity extends AppCompatActivity {

    public static final String NIGHT_MODE_SWITCH_ON_OFF = "NIGHT_MODE_SWITCH_ON_OFF";
    public static final String EXTRA_MAIN = "EXTRA_MAIN";
    private String[] mNavigationDrawerItemTitles;
    private ActionBarDrawerToggle mDrawerToggle;
    public static final String THEME_SELECTED = "THEME_SELECTED";
    private ActivityMainBinding activityMainBinding;
    private static final String TAG = "MainActivity";
    private List<NavModel> drawerItem = new ArrayList<>();
    public static EditText search;
    public static ImageView logo;
    public static TextView toolbarText;

    @Override
    public void onBackPressed() {

        List<Fragment> fragmentList = getSupportFragmentManager().getFragments();

        boolean handled = false;
        for (Fragment f : fragmentList) {
            if (f instanceof LastNewsFragment) {
                handled = ((LastNewsFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
                }
            }
            if (f instanceof FixturesFragment) {
                handled = ((FixturesFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
//                    break;
                }
            }
            if (f instanceof AllTournamentsFragment) {
                handled = ((AllTournamentsFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
//                    break;
                }
            }
            if (f instanceof FavoriteFragment) {
                handled = ((FavoriteFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
//                    break;
                }
            }

            if (f instanceof NotificationsFragment) {
                handled = ((NotificationsFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
//                    break;
                }
            }
            if (f instanceof SettingsFragment) {
                handled = ((SettingsFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
//                    break;
                }
            }
            if (f instanceof PrivacyPolicyFragment) {
                handled = ((PrivacyPolicyFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
//                    break;
                }
            }
            if (f instanceof LoginFragment) {
                handled = ((LoginFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
//                    break;
                }
            }
            if (f instanceof AddNewsFragment) {
                handled = ((AddNewsFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
//                    break;
                }
            }

            if (f instanceof AddCompitationNewsFragment) {
                handled = ((AddCompitationNewsFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
//                    break;
                }
            }
            if (f instanceof AddGameFragment) {
                handled = ((AddGameFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
//                    break;
                }
            }
            if (f instanceof UpdateGameResultFragment) {
                handled = ((UpdateGameResultFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
//                    break;
                }
            }

            if (f instanceof AddCompitationFragment) {
                handled = ((AddCompitationFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
//                    break;
                }
            }
            if (f instanceof AddSeasonFragment) {
                handled = ((AddSeasonFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
//                    break;
                }
            }

            if (f instanceof AddCountryFragment) {
                handled = ((AddCountryFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
//                    break;
                }
            }
            if (f instanceof AddTeamFragment) {
                handled = ((AddTeamFragment) f).onBackPressed();

                if (handled) {
                    Log.e(TAG, "onBackPressed: FixturesFragment");
                    selectItem(0);
//                    break;
                }
            }


        }

        if (!handled) {
            super.onBackPressed();
        }
    }


//    public static void loadFavorite() {
//        List<Fragment> fragmentList = this.getContext().getSupportFragmentManager().getFragments();
//
//        boolean handled = false;
//        for (Fragment f : fragmentList) {
//            if (f instanceof FavoriteFragment) {
//                handled = ((FavoriteFragment) f).onReload();
//
//                if (handled) {
//                    Log.e(TAG, "onReload:");
//                    selectItem(4);
////                    break;
//                }
//            }
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int themeSelected = MyApplication.getPref().getInt(THEME_SELECTED);
        ColorPrefUtil.changeThemeStyle(this, themeSelected);
        Log.e(TAG, "onCreate: " + themeSelected);
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);


        setupToolbar();
        setupDrawer();
        search = activityMainBinding.search;
        logo = activityMainBinding.logo;
        toolbarText = activityMainBinding.toolbarTitle;
//        MobileAds.initialize(this, "ca-app-pub-6734139800346657~5107154850");
//        MobileAds.initialize(this, new OnInitializationCompleteListener() {
//            @Override
//            public void onInitializationComplete(InitializationStatus initializationStatus) {
//            }
//        });
    }

    private void setupDrawer() {
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        drawerItem = new ArrayList<>();
        drawerItem.add(new NavModel(R.drawable.ic_main, getResources().getString(R.string.main)));
        drawerItem.add(new NavModel(R.drawable.ic_last_news, getResources().getString(R.string.last_news)));
        drawerItem.add(new NavModel(R.drawable.ic_fixtures, getResources().getString(R.string.fixtures)));
        drawerItem.add(new NavModel(R.drawable.ic_all_tournaments, getResources().getString(R.string.all_tournaments)));
        drawerItem.add(new NavModel(R.drawable.ic_favorites, getResources().getString(R.string.favorite)));
        drawerItem.add(new NavModel(R.drawable.ic_notification, getResources().getString(R.string.notifications)));
        drawerItem.add(new NavModel(R.drawable.ic_settings, getResources().getString(R.string.settings)));
        drawerItem.add(new NavModel(R.drawable.ic_privacy, getResources().getString(R.string.privacy_policy)));
        drawerItem.add(new NavModel(R.drawable.ic_login, getResources().getString(R.string.login)));
        Log.e(TAG, "setupDrawer: " + MyApplication.getPref().getInt("TYPE"));
        if (MyApplication.getPref().getInt("TYPE") == 1 && MyApplication.getPref().getBoolean("LOGIN")) {
            drawerItem.add(new NavModel(R.drawable.ic_add_news, getResources().getString(R.string.add_news)));
            drawerItem.add(new NavModel(R.drawable.ic_competion_news, getResources().getString(R.string.add_news_in_compitation)));
            drawerItem.add(new NavModel(R.drawable.ic_ball, getResources().getString(R.string.add_game)));
            drawerItem.add(new NavModel(R.drawable.ic_result, getResources().getString(R.string.update_result)));
            drawerItem.add(new NavModel(R.drawable.ic_all_tournaments, getResources().getString(R.string.add_compitation)));
            drawerItem.add(new NavModel(R.drawable.ic_season, getResources().getString(R.string.add_season)));
            drawerItem.add(new NavModel(R.drawable.ic_countries, getResources().getString(R.string.add_country)));
            drawerItem.add(new NavModel(R.drawable.ic_team, getResources().getString(R.string.add_team)));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        selectItem(0);


        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            if (intent.hasExtra(SettingsFragment.EXTRA_SETTINGS)) {
                selectItem(0);
            }
            if (intent.hasExtra(EXTRA_MAIN)) {
                selectItem(0);
            }
            if (intent.getExtras().getBoolean(NewsDetailsActivity.EXTRA_LOGIN)) {
                selectItem(8);
            }
            if (intent.hasExtra(AllTournamentsFragment.EXTRA_TOURNAMENTS)) {
                selectItem(3);
            }
            if (intent.hasExtra(MainFragment.EXTRA_FIXTURE)) {
                selectItem(2);
            }
            if (intent.hasExtra(FavoriteFragment.EXTRA_FAVORITE)) {
                selectItem(4);
            }
            if (intent.hasExtra(FavoriteFragment.EXTRA_UN_FAVORITE)) {
                selectItem(4);
            }
        }
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        activityMainBinding.leftDrawer.setAdapter(adapter);
        activityMainBinding.leftDrawer.setOnItemClickListener(new DrawerItemClickListener());
        activityMainBinding.drawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private  void showLogo() {
       activityMainBinding. logo.setVisibility(View.VISIBLE);
       activityMainBinding.toolbarTitle.setVisibility(View.GONE);
    }

    private  void hideLogo() {
        activityMainBinding.logo.setVisibility(View.GONE);
        activityMainBinding.toolbarTitle.setVisibility(View.VISIBLE);
    }

    public void selectItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new MainFragment();
                showLogo();
                break;
            case 1:
                fragment = new LastNewsFragment();
                hideLogo();
                break;
            case 2:
                fragment = new FixturesFragment();
                hideLogo();
                break;
            case 3:
                fragment = new AllTournamentsFragment();
                hideLogo();
                break;
            case 4:
                fragment = new FavoriteFragment();
                hideLogo();
                break;
            case 5:
                fragment = new NotificationsFragment();
                hideLogo();
                break;
            case 6:
                fragment = new SettingsFragment();
                hideLogo();
                break;
            case 7:
                fragment = new PrivacyPolicyFragment();
                hideLogo();

                break;

            case 8:
                fragment = new LoginFragment();
                hideLogo();
                break;

            case 9:
                fragment = new AddNewsFragment();
                break;
            case 10:
                fragment = new AddCompitationNewsFragment();
                break;
            case 11:
                fragment = new AddGameFragment();
                break;
            case 12:
                fragment = new UpdateGameResultFragment();
                break;
            case 13:
                fragment = new AddCompitationFragment();
                break;
            case 14:
                fragment = new AddSeasonFragment();
                break;
            case 15:
                fragment = new AddCountryFragment();
                break;
            case 16:
                fragment = new AddTeamFragment();
                break;

            default:
                fragment = new MainFragment();
                showLogo();
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment, mNavigationDrawerItemTitles[position]);
            fragmentTransaction.commit();

            activityMainBinding.leftDrawer.setItemChecked(position, true);
            activityMainBinding.leftDrawer.setSelection(position);

            activityMainBinding.toolbar.setTitle("");
            activityMainBinding.toolbar.setSubtitle("");

            activityMainBinding.toolbarTitle.setText(mNavigationDrawerItemTitles[position]);
            activityMainBinding.drawerLayout.closeDrawer(GravityCompat.START);

        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    void setupToolbar() {

        setSupportActionBar(activityMainBinding.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, activityMainBinding.drawerLayout, activityMainBinding.toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_custom_drawer_icon);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMainBinding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mDrawerToggle.syncState();
    }
}
