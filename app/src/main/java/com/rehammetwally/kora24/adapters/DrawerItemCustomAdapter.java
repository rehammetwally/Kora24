package com.rehammetwally.kora24.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import androidx.databinding.DataBindingUtil;
import com.rehammetwally.kora24.databinding.ListViewItemRowBinding;
import com.rehammetwally.kora24.models.NavModel;

import java.util.List;

public class DrawerItemCustomAdapter extends ArrayAdapter<NavModel> {

    private Context mContext;
    private int layoutResourceId;
    private List<NavModel> data;
    private ListViewItemRowBinding binding;

    public DrawerItemCustomAdapter(Context mContext, int layoutResourceId, List<NavModel> data) {

        super(mContext, layoutResourceId, data);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        layoutResourceId, parent, false);


        NavModel folder = data.get(position);

        binding.setNav(folder);

        return binding.getRoot();
    }

}
