package com.rehammetwally.kora24.views;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
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
import com.rehammetwally.kora24.adapters.MatchesListAdapter;
import com.rehammetwally.kora24.databinding.FragmentMatchListBinding;
import com.rehammetwally.kora24.models.Match;
import com.rehammetwally.kora24.models.MatchList;
import com.rehammetwally.kora24.models.MessageMatch;
import com.rehammetwally.kora24.models.ModelObject;
import com.rehammetwally.kora24.viewmodels.TournamentsViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MatchListFragment extends Fragment {

    private FragmentMatchListBinding binding;


    private HashMap<ModelObject, List<Match>> listHashMap = new HashMap<>();
    //    private List<Match> matchList=new ArrayList<>();
    private static final String TAG = "MatchListFragment";
//    private List<List<Match>> listList = new ArrayList<>();

    public MatchListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_match_list, container, false);
        View view = binding.getRoot();
        Log.e(TAG, "onCreateView: " + getArguments().getString("DATE"));
        if (getArguments() != null) {
            String date = getArguments().getString("DATE");
            String day = getArguments().getString("DAY");
//            binding.day.setText(day);
//            binding.date.setText(date);
        }
//        List<Match> list = new ArrayList<>();
//        list.add(new Match(R.drawable.nemsa, R.drawable.romania, "النمسا", "رومانيا", " 0 : 0 ","0", "0", true));
//        list.add(new Match(R.drawable.nemsa, R.drawable.romania, "النمسا", "رومانيا", "08:45 م", "0","0", false));
//        list.add(new Match(R.drawable.nemsa, R.drawable.romania, "النمسا", "رومانيا", "08:45 م", "0","0", false));
//        listHashMap.put(new ModelObject("دورى الامم الاوروبية",R.drawable.ic_launcher),list);
//        List<Match> list2 = new ArrayList<>();
//
//        list2.add(new Match(R.drawable.nemsa, R.drawable.romania, "النمسا", "رومانيا", " 0 : 0 ","0", "0", true));
//        listHashMap.put(new ModelObject(" الدورى الانجليزى",R.drawable.ic_launcher),list2);
//        List<Match> list3 = new ArrayList<>();
//
//        list3.add(new Match(R.drawable.nemsa, R.drawable.romania, "النمسا", "رومانيا", " 0 : 0 ","0", "0", true));
//        list3.add(new Match(R.drawable.nemsa, R.drawable.romania, "النمسا", "رومانيا", "08:45 م", "0","0", false));
//        list3.add(new Match(R.drawable.nemsa, R.drawable.romania, "النمسا", "رومانيا", "08:45 م", "0","0", false));
//        list3.add(new Match(R.drawable.nemsa, R.drawable.romania, "النمسا", "رومانيا", "08:45 م", "0","0", false));
//
//        listHashMap.put(new ModelObject(" الدورى الانجليزى",R.drawable.ic_launcher),list3);


        binding.matchesList.setHasFixedSize(true);
        binding.matchesList.setLayoutManager(new LinearLayoutManager(getContext()));
//        MatchesListAdapter matchesListAdapter = new MatchesListAdapter(getContext(), listHashMap);
//        binding.matchesList.setAdapter(matchesListAdapter);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
