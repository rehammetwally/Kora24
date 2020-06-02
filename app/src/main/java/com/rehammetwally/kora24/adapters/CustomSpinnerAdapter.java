package com.rehammetwally.kora24.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import androidx.databinding.DataBindingUtil;
import com.rehammetwally.kora24.R;
import com.rehammetwally.kora24.databinding.CustomSpinnerItemsBinding;
import com.rehammetwally.kora24.models.Team;
import java.util.List;

public class CustomSpinnerAdapter extends BaseAdapter {
    private Context context;
    private List<?> list;
    private LayoutInflater inflater;
    private CustomSpinnerItemsBinding binding;

    public CustomSpinnerAdapter(Context applicationContext, List<?> list) {
        this.context = applicationContext;
        this.list = list;
        inflater = (LayoutInflater.from(applicationContext));
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
        return list.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        binding = DataBindingUtil.inflate(inflater, R.layout.custom_spinner_items, null,false);
        binding.spinnerText.setText(list.get(i).toString());
        return binding.getRoot();
    }
}