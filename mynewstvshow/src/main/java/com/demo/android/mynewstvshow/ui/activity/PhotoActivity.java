package com.demo.android.mynewstvshow.ui.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.demo.android.mynewstvshow.R;
import com.demo.android.mynewstvshow.bean.Photos;
import com.demo.android.mynewstvshow.ui.base.BaseActivity;
import com.demo.android.mynewstvshow.ui.presenter.PhotoPresenter;
import com.demo.android.mynewstvshow.ui.view.IAcyPhoto;

import butterknife.BindView;

/**
 * Created by XiongRun on 2017/7/5.
 */

public class PhotoActivity extends BaseActivity<PhotoPresenter, Photos> implements IAcyPhoto {

	@BindView(R.id.toolbar_title)
	TextView toolbarTitle;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.appbar)
	AppBarLayout appbar;
	@BindView(R.id.recycler)
	RecyclerView recycler;
	@BindView(R.id.swipe_refresh)
	SwipeRefreshLayout swipeRefresh;
	@BindView(R.id.con)
	CoordinatorLayout con;
	private int line = 2;
	private GridLayoutManager manager;

	@Override
	public PhotoPresenter getPresenter() {
		return new PhotoPresenter(mContext);
	}

	@Override
	protected View getLoadingTargetView() {
		return con;
	}

	@Override
	protected void initToolBar() {
		toolbar.setNavigationIcon(R.mipmap.ic_arrow_back);
		toolbar.setTitle("");
		toolbarTitle.setText("女神来了");
		setSupportActionBar(toolbar);
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void initView() {
		manager = new GridLayoutManager(mContext, line);
		recycler.setLayoutManager(manager);
		if (swipeRefresh != null) {
			swipeRefresh.setColorSchemeResources(R.color.refresh_progress_1,
					R.color.refresh_progress_2, R.color.refresh_progress_3);
			swipeRefresh.setProgressViewOffset(true, 0, (int) TypedValue
					.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
		}

		initRefresh();
		loadMore();
	}

	private void initRefresh() {
		mPresenter.refresh();
	}

	private void loadMore() {

		mPresenter.loadMoreData();
	}


	@Override
	protected void initData() {
		mPresenter.getPhotoList();
	}

	@Override
	protected int getContentView() {
		return R.layout.activity_photo;
	}

	@Override
	public void showDataSuccess(Photos datas) {
	}

	@Override
	public RecyclerView getRecyclerView() {
		return recycler;
	}

	@Override
	public SwipeRefreshLayout getRefreshLayout() {
		return swipeRefresh;
	}

	@Override
	public GridLayoutManager getLayoutManager() {
		return manager;
	}


	@Override
	public void onDestroy() {
		super.onDestroy();
	}


}
