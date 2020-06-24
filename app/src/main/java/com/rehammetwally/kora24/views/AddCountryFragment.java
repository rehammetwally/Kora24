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
import com.rehammetwally.kora24.databinding.FragmentAddCompitationBinding;
import com.rehammetwally.kora24.databinding.FragmentAddCountryBinding;
import com.rehammetwally.kora24.models.Message;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.viewmodels.TournamentsViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddCountryFragment extends Fragment implements View.OnClickListener {
    private FragmentAddCountryBinding binding;
    private TournamentsViewModel tournamentsViewModel;

    public AddCountryFragment() {
        // Required empty public constructor
    }
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_country, container, false);
        View view = binding.getRoot();
        tournamentsViewModel = ViewModelProviders.of(getActivity()).get(TournamentsViewModel.class);
        binding.addCountryButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.add_country_button:
                String country = binding.countryEdittext.getText().toString();
                if (country.isEmpty()) {
                    MyApplication.showMessageBottom(v, getResources().getString(R.string.add_country) +" "+ getResources().getString(R.string.error_message_empty));
                    return;
                }
                tournamentsViewModel.addCountry(country).observe(getActivity(), new Observer<Message>() {
                    @Override
                    public void onChanged(Message message) {
                        if (message.message.equals("country added successfully")) {
                            MyApplication.showMessageBottom(v, getResources().getString(R.string.add_country_success));
                            binding.countryEdittext.setText("");
                        }
                    }
                });
                break;
        }
    }
}
