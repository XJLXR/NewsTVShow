package com.demo.android.mynewstvshow.ui.view;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.demo.android.mynewstvshow.bean.News;
import com.demo.android.mynewstvshow.ui.base.BaseViewI;

/**
 * Created by XiongRun on 2017/7/2.
 */

public interface IFgNews extends BaseViewI<News> {

	RecyclerView getRecyclerView();
	LinearLayoutManager getLayoutManager();
}
