package com.demo.android.mynewstvshow.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.demo.android.mynewstvshow.MyApplication;
import com.demo.android.mynewstvshow.R;
import com.demo.android.mynewstvshow.bean.News;
import com.demo.android.mynewstvshow.bean.NewslistBean;
import com.demo.android.mynewstvshow.common.Config;
import com.demo.android.mynewstvshow.ui.activity.NewsDetailActivity;
import com.demo.android.mynewstvshow.ui.adapter.FgNewsAdapter;
import com.demo.android.mynewstvshow.ui.base.BaseFragment;
import com.demo.android.mynewstvshow.ui.presenter.NewsPresenter;
import com.demo.android.mynewstvshow.ui.view.IFgNews;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

/**
 * Created by XiongRun on 2017/7/2.
 */

public class NewsFragment extends BaseFragment<NewsPresenter, News> implements IFgNews {


	@BindView(R.id.recycler)
	RecyclerView recycler;
	@BindView(R.id.swipe_refresh)
	SwipeRefreshLayout swipeRefresh;
	@BindView(R.id.main_content)
	CoordinatorLayout mainContent;
	private List<NewslistBean> newslist;
	private List<NewslistBean> mData;
	private String channelName;
	private LinearLayoutManager manager;
	private FgNewsAdapter adapter;


	private int pageSize=10;
	private int pageIndex=1;
	private Map<String, String> map;
	private int totalIndex = 5;
	private Boolean onSuccess = true;

	@Override
	protected NewsPresenter getPresenter() {
		return new NewsPresenter();
	}

	@Override
	protected View getLoadingTargetView() {
		return mainContent;
	}

	@Override
	protected void initData() {
		swipeRefresh.setRefreshing(false);
		mData = new ArrayList<>();
		if (getArguments() != null) {
			channelName = getArguments().getString("type");
		}

		map = new HashMap<>();
		map.put("key", Config.IPIKEY);
		map.put("num",pageSize+"");
		map.put("page",pageIndex+"");

		pageIndex=1;
		preData();
		adapter = new FgNewsAdapter(mData);
		adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
		adapter.isFirstOnly(false);
		manager = new LinearLayoutManager(MyApplication.getAppContext());
		recycler.setLayoutManager(manager);
		recycler.setAdapter(adapter);


		adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
			@Override
			public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

				Bundle bundle = new Bundle();
				bundle.putSerializable("data", (Serializable) newslist.get(position));
				readyGo(NewsDetailActivity.class,bundle);

			}
		});
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);


	}

	/**
	 * 初始化数据
	 */
	private void preData() {
		mPresenter.getNewsList(channelName,map);
	}

	@Override
	protected void initToolbar() {

	}

	@Override
	protected void initView(View rootView) {
		mPresenter.attachView(this);
		if(swipeRefresh != null){
			swipeRefresh.setColorSchemeResources(R.color.refresh_progress_1,
					R.color.refresh_progress_2, R.color.refresh_progress_3);
			swipeRefresh.setProgressViewOffset(true, 0, (int) TypedValue
					.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,getResources().getDisplayMetrics()));

		}
		initRefresh();
		//initOnloaderMore();


	}

	/**
	 * 加载更多
	 */
	private void initOnloaderMore() {
		adapter.setEnableLoadMore(true);
		adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
			@Override
			public void onLoadMoreRequested() {

				if (pageIndex>=totalIndex){
					Toasty.custom(getActivity(), "没有更多数据", null, Toast.LENGTH_SHORT, false).show();
					adapter.loadMoreEnd();
				}else {
					if (onSuccess){
						pageIndex++;
						preData();
						adapter.addData(newslist);
						adapter.notifyDataSetChanged();
						adapter.loadMoreComplete();
					}else {
						onSuccess = true;
						pageIndex = 1;
						Toasty.custom(getActivity(), "加载失败", null, Toast.LENGTH_SHORT, false).show();
						adapter.loadMoreFail();
					}
				}

			}
		});

	}

	/**
	 * 下拉刷新
	 */
	private void initRefresh() {

		swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (swipeRefresh == null){
					return;
				}

				swipeRefresh.postDelayed(new Runnable() {
					@Override
					public void run() {
						if (swipeRefresh != null){
							swipeRefresh.setRefreshing(false);
							Toasty.custom(MyApplication.getAppContext(),"完成刷新",null ,Toast.LENGTH_LONG,false).show();
						}
					}
				},1000);
			}
		});
	}

	@Override
	protected int createViewLayoutId() {
		return R.layout.fragment_news;
	}


	@Override
	public void showDataSuccess(News datas) {
		super.showDataSuccess(datas);
		if (datas != null) {
			if (pageIndex == 1){
				newslist = datas.getNewslist();
				adapter.addData(newslist);
			}else {

			}



		}
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();

	}

	@Override
	public RecyclerView getRecyclerView() {
		return recycler;
	}

	@Override
	public LinearLayoutManager getLayoutManager() {
		return manager;
	}
}
