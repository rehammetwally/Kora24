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

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.ads.MobileAds;
import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.ClubsLogoAdapter;
import com.rehammetwally.kora24.adapters.NewsAdapter;
import com.rehammetwally.kora24.adapters.MatchesAdapter;
import com.rehammetwally.kora24.adapters.SearchAdapter;
import com.rehammetwally.kora24.databinding.FragmentMainBinding;
import com.rehammetwally.kora24.models.MatchList;
import com.rehammetwally.kora24.models.News;
import com.rehammetwally.kora24.models.Match;
import com.rehammetwally.kora24.models.NewsReation;
import com.rehammetwally.kora24.models.SearchResult;
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
public class MainFragment extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {

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

        binding.searchList.setHasFixedSize(true);
        binding.searchList.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.searchList.getContext(),
                DividerItemDecoration.VERTICAL);
        binding.searchList.addItemDecoration(dividerItemDecoration);
        MainActivity.search.setOnEditorActionListener(this);
        MainActivity.search.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                Log.e(TAG, "afterTextChanged: " + s);
                if (s.toString().isEmpty() || s == null || s.equals(" ")|| s.equals("")) {
                    binding.searchList.setVisibility(View.GONE);
                    binding.mainLayout.setVisibility(View.VISIBLE);
                } else {
                    search(s.toString());
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.e(TAG, "beforeTextChanged: " + s);
                if (s.toString().isEmpty() || s == null|| s.equals(" ")|| s.equals("")) {
                    binding.searchList.setVisibility(View.GONE);
                    binding.mainLayout.setVisibility(View.VISIBLE);
                } else {
                    search(s.toString());
                }
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG, "onTextChanged: " + s);
                if (s.toString().isEmpty() || s == null|| s.equals(" ")|| s.equals("")) {
                    binding.searchList.setVisibility(View.GONE);
                    binding.mainLayout.setVisibility(View.VISIBLE);
                } else {
                    search(s.toString());
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
        newsAdapter = new NewsAdapter(getContext(), newsViewModel);
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
                MainActivity.search.setVisibility(View.VISIBLE);
                MainActivity.logo.setVisibility(View.GONE);
                MainActivity.toolbarText.setVisibility(View.GONE);
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

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH
                || actionId == EditorInfo.IME_ACTION_DONE
                || event.getAction() == KeyEvent.ACTION_DOWN
                || event.getAction() == KeyEvent.KEYCODE_ENTER) {
            Log.e("search", v.getText().toString());
            String q = v.getText().toString();
            if (q.isEmpty() || q == null) {
                MyApplication.showMessageBottom(binding.nestedScrollView, getResources().getString(R.string.error_message_empty));
                binding.searchList.setVisibility(View.GONE);
                binding.mainLayout.setVisibility(View.VISIBLE);
            } else {
                search(q);
            }


            return true;
        }
        return false;
    }

    private void search(String q) {
        List<SearchResult.Result> results = new ArrayList<>();
        newsViewModel.search(q).observe((LifecycleOwner) getContext(), new Observer<SearchResult>() {
            @Override
            public void onChanged(SearchResult searchResult) {
                if (searchResult.message.equals("Returned Successfully")) {
                    SearchAdapter searchAdapter = new SearchAdapter(getContext());

                    for (int i = 0; i < searchResult.results.size(); i++) {
                        for (int j = 0; j < searchResult.results.get(i).size(); j++) {
                            if (searchResult.results.get(i).get(j).name != null) {
                                results.add(searchResult.results.get(i).get(j));
                            }
                            Log.e(TAG, "onChanged: " + searchResult.results.get(i).get(j).name);
                        }
                    }
                    if (results.size() > 0) {
                        binding.searchList.setVisibility(View.VISIBLE);
                        binding.mainLayout.setVisibility(View.GONE);
                        searchAdapter.submitList(results);
                        binding.searchList.setAdapter(searchAdapter);
                        Log.e(TAG, "onChanged: " + results.size());
                    } else {
                        MyApplication.showMessageBottom(binding.nestedScrollView, getResources().getString(R.string.search_empty) + " " + q);
                        binding.searchList.setVisibility(View.GONE);
                        binding.mainLayout.setVisibility(View.VISIBLE);
                    }
                }
            }
        });
    }
}
