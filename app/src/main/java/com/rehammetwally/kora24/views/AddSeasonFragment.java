package com.rehammetwally.kora24.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.FragmentAddNewsBindingImpl;
import com.rehammetwally.kora24.databinding.FragmentAddSeasonBinding;
import com.rehammetwally.kora24.models.Message;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.viewmodels.TournamentsViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddSeasonFragment extends Fragment implements View.OnClickListener {
    private FragmentAddSeasonBinding binding;
    private TournamentsViewModel tournamentsViewModel;
    private String season;

    public AddSeasonFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_season, container, false);
        View view = binding.getRoot();
        tournamentsViewModel = ViewModelProviders.of(getActivity()).get(TournamentsViewModel.class);
        binding.addSeasonButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.add_season_button:
                season = binding.seasonEdittext.getText().toString();
                if (season.isEmpty()) {
                    MyApplication.showMessageBottom(v, getResources().getString(R.string.add_season) +" "+ getResources().getString(R.string.error_message_empty));
                    return;
                }
                tournamentsViewModel.addSeason(season).observe(getActivity(), new Observer<Message>() {
                    @Override
                    public void onChanged(Message message) {
                        if (message.message.equals("season added successfully")) {
                            MyApplication.showMessageBottom(v, getResources().getString(R.string.add_season_success));
                            binding.seasonEdittext.setText("");
                        }
                    }
                });
                break;
        }
    }
}
