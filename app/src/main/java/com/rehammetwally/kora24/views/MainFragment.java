package com.rehammetwally.kora24.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

//import com.google.android.gms.ads.MobileAds;
import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.ClubsLogoAdapter;
import com.rehammetwally.kora24.adapters.NewsAdapter;
import com.rehammetwally.kora24.adapters.MatchesAdapter;
import com.rehammetwally.kora24.databinding.FragmentMainBinding;
import com.rehammetwally.kora24.models.MatchList;
import com.rehammetwally.kora24.models.News;
import com.rehammetwally.kora24.models.Match;
import com.rehammetwally.kora24.models.NewsReation;
import com.rehammetwally.kora24.models.Team;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.viewmodels.NewsViewModel;
import com.rehammetwally.kora24.viewmodels.TournamentsViewModel;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements View.OnClickListener {

    public static final String EXTRA_FIXTURE = "EXTRA_FIXTURE";
    private FragmentMainBinding binding;
    private News news;
    private NewsViewModel newsViewModel;
    private TournamentsViewModel tournamentsViewModel;
    List<News> newsList = new ArrayList<>();
    private NewsAdapter newsAdapter;
    private static final String TAG = "MainFragment";

    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_main, container, false);
        View view = binding.getRoot();
        tournamentsViewModel = ViewModelProviders.of(getActivity()).get(TournamentsViewModel.class);
        newsViewModel = ViewModelProviders.of(getActivity()).get(NewsViewModel.class);
        newsViewModel.showGeneralNews().observe((LifecycleOwner) getContext(), new Observer<List<News>>() {
            @Override
            public void onChanged(final List<News> news) {
                Log.e(TAG, "onChanged:showGeneralNews " + news.size());
                if (news.size() > 0) {
                    binding.setNews(news.get(news.size() - 1));
                    binding.headNewsCard.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showNewsDetails(news.get(news.size() - 1));
                        }
                    });

                    for (int i = 0; i < news.size(); i++) {
                        if (i != news.size() - 1) {
                            newsList.add(news.get(i));
                        }

                    }
                    showMostWatched(newsList);
                }
            }
        });


        showClubs();
        showMatches();


        binding.allMatchesLayout.setOnClickListener(this);
        binding.shareNews.setOnClickListener(this);


        return view;
    }

    private void showNewsDetails(News news) {
        Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
        Log.e(TAG, "onClick: " + news.title);
        intent.putExtra("DETAILS", news);
        startActivity(intent);
    }

    private void showMostWatched(List<News> news) {
        binding.mostWatchedList.setHasFixedSize(true);
        binding.mostWatchedList.setLayoutManager(new LinearLayoutManager(getContext()));
        newsAdapter = new NewsAdapter(getContext(),newsViewModel);
        newsAdapter.submitList(news);
        binding.mostWatchedList.setAdapter(newsAdapter);
    }


//    private void showViews(News news) {
//        if (news.views < 1000) {//blue
//            binding.lastNewsViewsImage.setImageResource(R.drawable.less_views);
//        } else if (news.views > 1000 && news.views < 10000) {//orange
//            binding.lastNewsViewsImage.setImageResource(R.drawable.mid_views);
//        } else if (news.views > 10000) {//red
//            binding.lastNewsViewsImage.setImageResource(R.drawable.most_views);
//        }
//    }

    private void showMatches() {
        binding.todayMatchesList.setHasFixedSize(true);
        binding.todayMatchesList.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.todayMatchesList.getContext(),
                DividerItemDecoration.VERTICAL);
        binding.todayMatchesList.addItemDecoration(dividerItemDecoration);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateStr = formatter.format(date);
        Log.e(TAG, "showMatches: " + dateStr);
        tournamentsViewModel.getGamesSchedule(dateStr).observe(getActivity(), new Observer<MatchList>() {
            @Override
            public void onChanged(MatchList matchList) {
                Log.e(TAG, "onChanged:getGamesSchedule " + matchList.message);
                if (matchList.match != null) {

                    if (matchList.match.size() > 0) {
                        binding.todayMatchesList.setVisibility(View.VISIBLE);
                        binding.noGamesToday.setVisibility(View.GONE);
                        Log.e(TAG, "onChanged:getGamesSchedule " + matchList.match.size());
                        binding.todayMatchesListCard.setVisibility(View.VISIBLE);
                        final MatchesAdapter matchesAdapter = new MatchesAdapter(getContext());
                        matchesAdapter.submitList(matchList.match);
                        binding.todayMatchesList.setAdapter(matchesAdapter);
                    } else {
                        binding.todayMatchesList.setVisibility(View.GONE);
                        binding.noGamesToday.setVisibility(View.VISIBLE);
                    }
                } else {
                    binding.todayMatchesList.setVisibility(View.GONE);
                    binding.noGamesToday.setVisibility(View.VISIBLE);

                }
            }
        });

    }

    private void showClubs() {
        binding.clubsLogosList.setHasFixedSize(true);
        binding.clubsLogosList.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        final ClubsLogoAdapter clubsLogoAdapter = new ClubsLogoAdapter(getContext());
        tournamentsViewModel.showAllTeams().observe(getActivity(), new Observer<List<Team>>() {
            @Override
            public void onChanged(List<Team> teams) {
                Log.e(TAG, "onChanged: " + teams.size());
                clubsLogoAdapter.submitList(teams);
            }
        });
        binding.clubsLogosList.setAdapter(clubsLogoAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case R.id.search:
                Toast.makeText(getContext(), "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.share:
                MyApplication.shareApp(getContext());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.all_matches_layout:
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra(EXTRA_FIXTURE, true);
                startActivity(intent);
                break;
            case R.id.share_news:
                MyApplication.shareApp(getContext());
                break;
        }
    }
}
