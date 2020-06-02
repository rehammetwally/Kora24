package com.rehammetwally.kora24.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.NewsAdapter;
import com.rehammetwally.kora24.databinding.FragmentLastNewsBinding;
import com.rehammetwally.kora24.interfaces.ItemClickListener;
import com.rehammetwally.kora24.models.News;
import com.rehammetwally.kora24.viewmodels.NewsViewModel;
import com.rehammetwally.kora24.viewmodels.TournamentsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LastNewsFragment extends Fragment {

    private FragmentLastNewsBinding binding;
    private static final String TAG = "LastNewsFragment";
    private NewsViewModel newsViewModel;

    public LastNewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_last_news, container, false);
        View view = binding.getRoot();
        newsViewModel = ViewModelProviders.of(getActivity()).get(NewsViewModel.class);


        binding.lastNewsList.setHasFixedSize(true);
        binding.lastNewsList.setLayoutManager(new LinearLayoutManager(getContext()));
        final NewsAdapter newsAdapter = new NewsAdapter(getContext(),newsViewModel);

        if (getArguments() != null) {
            int cId = getArguments().getInt("COMPITIONID");
            Log.e(TAG, "onCreateView: " + cId);

            if (cId != 0) {
                newsViewModel.showCompetitionNews(cId).observe(getActivity(), new Observer<List<News>>() {
                    @Override
                    public void onChanged(List<News> news) {
                        Log.e(TAG, "showCompetitionNewsonChanged: " + news.size());
                        newsAdapter.submitList(news);
                    }
                });
            }
        } else {
            newsViewModel.showGeneralNews().observe(getActivity(), new Observer<List<News>>() {
                @Override
                public void onChanged(List<News> news) {
                    Log.e(TAG, "showGeneralNewsonChanged: " + news.size());
                    List<News> newsList=new ArrayList<>();
                    for (int i = 0; i <news.size() ; i++) {
                        if (i == 0) {
                            news.get(i).isHead = true;
                            newsList.add(news.get(i));
                        }else {
                           news.get(i).isHead=false;
                           newsList.add(news.get(i));
                        }
                    }
                    newsAdapter.submitList(newsList);
                }
            });
        }

//        final List<News> list = new ArrayList<>();
//        list.add(new News(R.drawable.splash_bg, "رسميا - يويفا يرفض تغيير اسم يورو 2020 رغم التاجيل", "الاحد 14 اكتوبر 2018", 10001, 30, true));
//        list.add(new News(R.drawable.splash_bg, "لاعبو شباب بلوزداد يتبرعون بجزء من راتبهم", "منذ 1 ساعة", 2000, 2, false));
//        list.add(new News(R.drawable.splash_bg, " حسام عاشور يرد على انتقاله الى الزمالك", "منذ 44 دقيقة", 999, 3, false));
//        list.add(new News(R.drawable.splash_bg, "لاعبو شباب بلوزداد يتبرعون بجزء من راتبهم", "منذ 1 ساعة", 2000, 2, false));
//        list.add(new News(R.drawable.splash_bg, " حسام عاشور يرد على انتقاله الى الزمالك", "منذ 44 دقيقة", 999, 3, false));
//        list.add(new News(R.drawable.splash_bg, "لاعبو شباب بلوزداد يتبرعون بجزء من راتبهم", "منذ 1 ساعة", 2000, 2, false));
//        list.add(new News(R.drawable.splash_bg, " حسام عاشور يرد على انتقاله الى الزمالك", "منذ 44 دقيقة", 999, 3, false));
//        list.add(new News(R.drawable.splash_bg, "لاعبو شباب بلوزداد يتبرعون بجزء من راتبهم", "منذ 1 ساعة", 2000, 2, false));
//        list.add(new News(R.drawable.splash_bg, " حسام عاشور يرد على انتقاله الى الزمالك", "منذ 44 دقيقة", 999, 3, false));
//        list.add(new News(R.drawable.splash_bg, "لاعبو شباب بلوزداد يتبرعون بجزء من راتبهم", "منذ 1 ساعة", 2000, 2, false));
//        list.add(new News(R.drawable.splash_bg, " حسام عاشور يرد على انتقاله الى الزمالك", "منذ 44 دقيقة", 999, 3, false));
//        list.add(new News(R.drawable.splash_bg, "لاعبو شباب بلوزداد يتبرعون بجزء من راتبهم", "منذ 1 ساعة", 2000, 2, false));
//        newsAdapter.submitList(list);
        binding.lastNewsList.setAdapter(newsAdapter);
        if (newsAdapter.getCurrentList().size() < 10) {
            binding.fab.setVisibility(View.INVISIBLE);
        } else {
            binding.fab.setVisibility(View.VISIBLE);
        }
        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (newsAdapter.getCurrentList().size() != 0)
                    binding.lastNewsList.smoothScrollToPosition(newsAdapter.getItemCount() - 1);
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.details_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(getContext(), MainActivity.class);
                intent.putExtra(MainActivity.EXTRA_MAIN, true);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
