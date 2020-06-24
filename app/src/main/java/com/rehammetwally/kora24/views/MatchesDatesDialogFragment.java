package com.rehammetwally.kora24.views;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.FileUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.FragmentMatchesDatesDialogBinding;
import com.rehammetwally.kora24.models.Match;
import com.rehammetwally.kora24.utils.MyApplication;
import com.rehammetwally.kora24.viewmodels.UserViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;



public class MatchesDatesDialogFragment extends DialogFragment {

    ArrayList<Match> days;
    private FragmentMatchesDatesDialogBinding binding;
    private UserViewModel userViewModel;
    private static final String TAG = "AllCommentsDialogFragme";

    public MatchesDatesDialogFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_matches_dates_dialog, container, false);
        View view = binding.getRoot();
//        binding.calendarView.setHeaderColor([color]);
//        calendarView.setHeaderLabelColor([color]);
//        calendarView.setForwardButtonImage([drawable]);
//        calendarView.setPreviousButtonImage([drawable]);
//        List<EventDay> events = new ArrayList<>();
//
//        Calendar calendar = Calendar.getInstance();
//        events.add(new EventDay(calendar, R.drawable.circle, R.color.colorAccent));
//        calendar.set(2020, 07, 5);
//        events.add(new EventDay(calendar, R.drawable.circle, Color.parseColor("#228B22")));
//
//        binding.calendarView.setEvents(events);
//        binding.calendarView.setOnDayClickListener(new OnDayClickListener() {
//            @Override
//            public void onDayClick(EventDay eventDay) {
//                Calendar clickedDayCalendar = eventDay.getCalendar();
//                Log.e(TAG, "onDayClick: "+clickedDayCalendar.toString() );
//            }
//        });
//        userViewModel = ViewModelProviders.of((FragmentActivity) getContext()).get(UserViewModel.class);
//        ArrayList<DateData> dates=new ArrayList<>();
//        dates.add(new DateData(2018,04,26));
//        dates.add(new DateData(2018,04,27));
//
//        for(int i=0;i<dates.size();i++) {
//            binding.calendar.markDate(dates.get(i).getYear(),dates.get(i).getMonth(),dates.get(i).getDay());//mark multiple dates with this code.
//        }
//
//
//        Log.e("marked dates:-",""+binding.calendar.getMarkedDates());//get all marked dates.
//        days = (ArrayList<Match>) getArguments().getSerializable("DAYS");
//        for (int i = 0; i <days.size() ; i++) {
//            Log.e(TAG, "onCreateView: "+days.get(i).match_date );
//            Log.e(TAG, "onCreateView: "+days.get(i).match_date.split("-") );
//            String[] dates=days.get(i).match_date.split("-");
//            Log.e(TAG, "onCreateView: "+dates[0]+"// "+dates[1]+"//"+dates[2]);
//            binding.calendar.markDate(
//                    new DateData(Integer.parseInt(dates[0]), Integer.parseInt(dates[1]), Integer.parseInt(dates[2])).setMarkStyle(new MarkStyle(MarkStyle.DOT, R.color.colorAccent))
//            );
//        }
//        binding.commentsList.setHasFixedSize(true);
//        binding.commentsList.setLayoutManager(new LinearLayoutManager(getContext()));
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(binding.commentsList.getContext(),
//                DividerItemDecoration.VERTICAL);
//        binding.commentsList.addItemDecoration(dividerItemDecoration);
//        commentsAdapter = new CommentsAdapter(getContext());
//        Log.e(TAG, "setComments:news.id " + id);
//        userViewModel.showCommentsOfNews(id).observe(this, new Observer<Comments>() {
//            @Override
//            public void onChanged(Comments comments) {
//                if (comments != null) {
//                    if (comments.comments.size() > 0) {
//                        commentsAdapter.submitList(comments.comments);
//                        binding.commentsList.setAdapter(commentsAdapter);
//                    }
//                }
//            }
//        });
        return view;
    }
}