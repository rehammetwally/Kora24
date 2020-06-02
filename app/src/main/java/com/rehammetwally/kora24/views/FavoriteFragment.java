package com.rehammetwally.kora24.views;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.material.tabs.TabLayout;
import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.ViewPagerAdapter;
import com.rehammetwally.kora24.databinding.FragmentFavoriteBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    public static final String EXTRA_FAVORITE = "EXTRA_FAVORITE";
    public static final String EXTRA_UN_FAVORITE = "EXTRA_UN_FAVORITE";
    private FragmentFavoriteBinding binding;
    ViewPagerAdapter adapter;
    private static final String TAG = "FavoriteFragment";
    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false);

        View view = binding.getRoot();
        adapter = new ViewPagerAdapter(getFragmentManager());
        setupViewPager(binding.pager);
        binding.tabLayout.setupWithViewPager(binding.pager);

        for (int i = 0; i < binding.tabLayout.getTabCount(); i++) {
            View tab = ((ViewGroup) binding.tabLayout.getChildAt(0)).getChildAt(i);
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) tab.getLayoutParams();
            p.setMargins(16, 16, 16, 16);
            tab.requestLayout();
        }

//        if (getActivity().getIntent().getExtras() != null) {
//            if (getActivity().getIntent().getExtras().getBoolean(FavoriteFragment.EXTRA_FAVORITE)) {
//                Log.e(TAG, "onCreateView: EXTRA_FAVORITE" );
//                binding.pager.setCurrentItem(1);
//            }
//            if (getActivity().getIntent().getExtras().getBoolean(FavoriteFragment.EXTRA_UN_FAVORITE)) {
//                Log.e(TAG, "onCreateView: EXTRA_UN_FAVORITE" );
//                binding.pager.setCurrentItem(0);
//            }
//        }

        MobileAds.initialize(getContext(),"ca-app-pub-6734139800346657~5107154850");


        AdRequest adRequest = new AdRequest.Builder().build();
        binding.adView.loadAd(adRequest);

//        AdView adView = new AdView(getContext());
//        adView.setAdSize(AdSize.BANNER);
//        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");

//        ca-app-pub-6734139800346657/3914612248 release
        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
//        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new YourFavoriteFragment(), getResources().getString(R.string.your_favorite));
        adapter.addFragment(new AllBandsFragment(), getResources().getString(R.string.all_bands));
        viewPager.setAdapter(adapter);
    }

//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setHasOptionsMenu(true);
//    }
//    @Override
//    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        inflater.inflate(R.menu.details_menu, menu);
//        super.onCreateOptionsMenu(menu, inflater);
//    }
//
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                Intent intent = new Intent(getContext(), MainActivity.class);
//                intent.putExtra(MainActivity.EXTRA_MAIN,true);
//                startActivity(intent);
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
}
