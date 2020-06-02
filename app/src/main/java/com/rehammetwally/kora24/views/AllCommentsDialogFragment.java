package com.rehammetwally.kora24.views;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.adapters.CommentsAdapter;
import com.rehammetwally.kora24.adapters.CustomSpinnerAdapter;
import com.rehammetwally.kora24.databinding.FragmentAllCommentsDialogBinding;
import com.rehammetwally.kora24.models.Comments;
import com.rehammetwally.kora24.viewmodels.UserViewModel;

import java.util.ArrayList;


public class AllCommentsDialogFragment extends DialogFragment {


    private FragmentAllCommentsDialogBinding binding;
    private int id;
    private UserViewModel userViewModel;
    private CommentsAdapter commentsAdapter;
    private static final String TAG = "AllCommentsDialogFragme";

    public AllCommentsDialogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_comments_dialog, container, false);
        View view = binding.getRoot();
        userViewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(UserViewModel.class);
        id = getArguments().getInt("NEWS_ID");
        binding.commentsList.setHasFixedSize(true);
        binding.commentsList.setLayoutManager(new LinearLayoutManager(getContext()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.commentsList.getContext(),
                DividerItemDecoration.VERTICAL);
        binding.commentsList.addItemDecoration(dividerItemDecoration);
        commentsAdapter = new CommentsAdapter(getContext());
        Log.e(TAG, "setComments:news.id " + id);
        userViewModel.showCommentsOfNews(id).observe(this, new Observer<Comments>() {
            @Override
            public void onChanged(Comments comments) {
                if (comments != null) {
                    if (comments.comments.size() > 0) {
                        commentsAdapter.submitList(comments.comments);
                        binding.commentsList.setAdapter(commentsAdapter);
                    }
                }
            }
        });
        return view;
    }
}