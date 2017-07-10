package com.demo.android.mynewstvshow.ui.view;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.demo.android.mynewstvshow.bean.Photos;
import com.demo.android.mynewstvshow.ui.base.BaseViewI;

/**
 * Created by XiongRun on 2017/7/6.
 */

public interface IAcyPhoto extends BaseViewI<Photos> {

	RecyclerView getRecyclerView();
	SwipeRefreshLayout getRefreshLayout();
	GridLayoutManager getLayoutManager();
}
