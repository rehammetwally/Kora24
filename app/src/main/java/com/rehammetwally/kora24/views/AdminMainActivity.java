package com.rehammetwally.kora24.views;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.DrawerItemCustomAdapter;
import com.rehammetwally.kora24.databinding.ActivityAdminMainBinding;
import com.rehammetwally.kora24.models.NavModel;
import com.rehammetwally.kora24.utils.MyApplication;

import java.util.ArrayList;
import java.util.List;

import akndmr.github.io.colorprefutil.ColorPrefUtil;


public class AdminMainActivity extends AppCompatActivity {
    private ActivityAdminMainBinding binding;
    private String[] mNavigationDrawerItemTitles;
    private ActionBarDrawerToggle mDrawerToggle;
    private static final String TAG = "AdminMainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int themeSelected = MyApplication.getPref().getInt(MainActivity.THEME_SELECTED);
        ColorPrefUtil.changeThemeStyle(this, themeSelected);
        Log.e(TAG, "onCreate: " + themeSelected);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_main);

        setupToolbar();
        setupDrawer();
    }



    private void setupDrawer() {
        mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_admin_array);
        List<NavModel> drawerItem = new ArrayList<>();

        drawerItem.add( new NavModel(R.drawable.ic_main, getResources().getString(R.string.main)));
        drawerItem.add( new NavModel(R.drawable.ic_add_news, getResources().getString(R.string.add_news)));
        drawerItem.add( new NavModel(R.drawable.ic_competion_news, getResources().getString(R.string.add_news_in_compitation)));
        drawerItem.add( new NavModel(R.drawable.ic_ball, getResources().getString(R.string.add_game)));
        drawerItem.add( new NavModel(R.drawable.ic_result, getResources().getString(R.string.update_result)));
        drawerItem.add( new NavModel(R.drawable.ic_all_tournaments, getResources().getString(R.string.add_compitation)));
        drawerItem.add( new NavModel(R.drawable.ic_season, getResources().getString(R.string.add_season)));
        drawerItem.add( new NavModel(R.drawable.ic_countries, getResources().getString(R.string.add_country)));
        drawerItem.add( new NavModel(R.drawable.ic_team, getResources().getString(R.string.add_team)));
        drawerItem.add( new NavModel(R.drawable.ic_logout, getResources().getString(R.string.logout)));
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);



        selectItem(1);

        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.list_view_item_row, drawerItem);
        binding.leftDrawer.setAdapter(adapter);
        binding.leftDrawer.setOnItemClickListener(new DrawerItemClickListener());
        binding.drawerLayout.addDrawerListener(mDrawerToggle);
        setupDrawerToggle();
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }


    public void selectItem(int position) {

        Fragment fragment = null;
        switch (position) {
            case 0:
                startActivity(new Intent(this,MainActivity.class));
                break;
            case 1:
                fragment = new AddNewsFragment();
                break;
            case 2:
                fragment = new AddCompitationNewsFragment();
                break;
            case 3:
                fragment = new AddGameFragment();
                break;
            case 4:
                fragment = new UpdateGameResultFragment();
                break;
            case 5:
                fragment = new AddCompitationFragment();
                break;
            case 6:
                fragment = new AddSeasonFragment();
                break;
            case 7:
                fragment = new AddCountryFragment();
                break;
            case 8:
                fragment = new AddTeamFragment();
                break;

            case 9:
                MyApplication.showMessageBottom(binding.drawerLayout, getResources().getString(R.string.logout_success));
                MyApplication.getPref().remove("LOGIN");
                MyApplication.getPref().remove("TYPE");
                startActivity(new Intent(this,StartActivity.class));
                break;

            default:
                fragment = new AddNewsFragment();
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content_frame, fragment, mNavigationDrawerItemTitles[position]);
            fragmentTransaction.commit();

            binding.leftDrawer.setItemChecked(position, true);
            binding.leftDrawer.setSelection(position);

            binding.toolbar.setTitle("");
            binding.toolbar.setSubtitle("");

            binding.toolbarTitle.setText(mNavigationDrawerItemTitles[position]);
            binding.drawerLayout.closeDrawer(GravityCompat.START);

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
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    void setupDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.app_name, R.string.app_name);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        mDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_custom_drawer_icon);
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mDrawerToggle.syncState();
    }
}
