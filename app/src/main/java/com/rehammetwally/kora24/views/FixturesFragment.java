package com.rehammetwally.kora24.views;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.service.autofill.FieldClassification;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.DayDateFixtureAdapter;
import com.rehammetwally.kora24.adapters.MatchesFixturesListAdapter;
import com.rehammetwally.kora24.adapters.ViewPagerAdapter;
import com.rehammetwally.kora24.databinding.FragmentFixturesBinding;
import com.rehammetwally.kora24.models.GamesList;
import com.rehammetwally.kora24.models.Match;
import com.rehammetwally.kora24.models.MatchList;
import com.rehammetwally.kora24.utils.StringsUtils;
import com.rehammetwally.kora24.viewmodels.TournamentsViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 */
public class FixturesFragment extends Fragment {

    private FragmentFixturesBinding binding;
    private TournamentsViewModel tournamentsViewModel;
    private ViewPagerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private DayDateFixtureAdapter dayDateFixtureAdapter;
    private MatchesFixturesListAdapter matchesFixturesListAdapter;
    private int currentPage = 1;
    private static final String TAG = "FixturesFragment";
    private List<Match> matchesDateList;

    public FixturesFragment() {
        // Required empty public constructor
    }

    public boolean onBackPressed() {
        return true;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_fixtures, container, false);
        View view = binding.getRoot();
        tournamentsViewModel = ViewModelProviders.of(getActivity()).get(TournamentsViewModel.class);
        adapter = new ViewPagerAdapter(getFragmentManager());

        binding.dayDateList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false){
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        binding.dayDateList.setLayoutManager(layoutManager);


        tournamentsViewModel.showGamesDate().observe(getActivity(), new Observer<MatchList>() {
            @Override
            public void onChanged(MatchList messageMatch) {
                if (messageMatch.message.equals("Returned Successfully"))
                    if (messageMatch.match.size() > 0) {
                        dayDateFixtureAdapter = new DayDateFixtureAdapter(getContext(), messageMatch.match, tournamentsViewModel);
                        binding.dayDateList.setAdapter(dayDateFixtureAdapter);




                    }
            }
        });


        binding.next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick:Next ");
                if (currentPage < dayDateFixtureAdapter.getItemCount() - 1) {
                    currentPage += 1;
                    binding.dayDateList.getLayoutManager().scrollToPosition(currentPage);
                    binding.dayDateList.scrollToPosition(currentPage);
//                    dayDateFixtureAdapter.daysSpinnerAdapter.getItem(currentPage);
//                    dayDateFixtureAdapter.spinner.setSelection(currentPage);

                } else {
//                    binding.next.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorBackground), android.graphics.PorterDuff.Mode.SRC_IN);

                }
                Log.e(TAG, "onClick:next " + currentPage);
            }
        });

        binding.prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick:Prev ");
                if (currentPage > 0) {
                    currentPage -= 1;
                    binding.dayDateList.scrollToPosition(currentPage);
//                    dayDateFixtureAdapter.daysSpinnerAdapter.getItem(currentPage);
//                    dayDateFixtureAdapter.spinner.setSelection(currentPage);
                } else {
//                    binding.prev.setColorFilter(ContextCompat.getColor(getContext(), R.color.colorBackground), android.graphics.PorterDuff.Mode.SRC_IN);

                }
                Log.e(TAG, "onClick:prev " + currentPage);
            }
        });
        return view;
    }

    public List<Match> removeDuplicates(List<Match> list) {

        // Create a new ArrayList
        List<Match> newList = new ArrayList<Match>();

        // Traverse through the first list
        for (Match element : list) {

            // If this element is not present in newList
            // then add it
            if (!newList.contains(element.match_date)) {

                newList.add(element);
            }
        }

        // return the new list
        return newList;
    }
}
