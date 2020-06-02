package com.rehammetwally.kora24.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import androidx.databinding.DataBindingUtil;

import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.CustomSpinnerItemsBinding;
import com.rehammetwally.kora24.databinding.DaysSpinnerItemsBinding;
import com.rehammetwally.kora24.models.Match;

import java.util.List;

public class DaysSpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<Match> list;
    private LayoutInflater inflater;
    private DaysSpinnerItemsBinding binding;

    public DaysSpinnerAdapter(Context context, List<Match> list) {
        this.context = context;
        this.list = list;
//        inflater = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return list.get(i).id();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.days_spinner_items, null,false);
//        binding.spinnerText.setText(list.get(i).day());
//        binding..setText(list.get(i).day());
        binding.setGame(list.get(i));
        return binding.getRoot();
    }
}