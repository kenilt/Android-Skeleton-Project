package com.kenilt.skeleton.managers.listeners;

import android.view.View;

/**
 * Created by thangnguyen on 2019-08-04.
 */
public interface OnItemListClickListener<T> {
    void onItemClick(View view, T model, int position);
}
