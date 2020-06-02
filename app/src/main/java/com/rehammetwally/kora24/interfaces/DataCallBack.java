package com.rehammetwally.kora24.interfaces;

import java.util.List;

public interface DataCallBack {

    interface  LoadDataListCallback<T> {

        void onDataLoaded(List<T> list);

        void onDataNotAvailable(String message);
    }

    interface  LoadDataCallback<T> {

        void onDataLoaded(T item);

        void onDataNotAvailable(String message);

    }
}
