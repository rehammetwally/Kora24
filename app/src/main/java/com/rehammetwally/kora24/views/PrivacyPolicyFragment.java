package com.rehammetwally.kora24.views;

import android.content.Intent;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.FragmentPrivacyPolicyBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class PrivacyPolicyFragment extends Fragment {

    private FragmentPrivacyPolicyBinding binding;
    public PrivacyPolicyFragment() {
        // Required empty public constructor
    }
    public boolean onBackPressed() {
        return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_privacy_policy, container, false);
        View view=binding.getRoot();
//        http://kora24life-tk.preview-domain.com/kora24-privacy/privacy_policy.html
        WebSettings webSettings = binding.webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
        binding.webview.loadUrl("http://kora24life-tk.preview-domain.com/kora24-privacy/privacy_policy.html");
        return view;
    }

}
