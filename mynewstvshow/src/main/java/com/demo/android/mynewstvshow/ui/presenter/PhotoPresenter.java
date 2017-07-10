package com.demo.android.mynewstvshow.ui.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.demo.android.mynewstvshow.bean.Photos;
import com.demo.android.mynewstvshow.ui.activity.PhotoDetailActivity;
import com.demo.android.mynewstvshow.ui.adapter.PhotoAdapter;
import com.demo.android.mynewstvshow.ui.base.BasePresent;
import com.demo.android.mynewstvshow.ui.model.AtyPhotoModel;
import com.demo.android.mynewstvshow.ui.view.IAcyPhoto;
import com.jakewharton.rxbinding2.support.v7.widget.RecyclerViewScrollEvent;
import com.jakewharton.rxbinding2.support.v7.widget.RxRecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import es.dmoral.toasty.Toasty;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by XiongRun on 2017/7/6.
 */

public class PhotoPresenter extends BasePresent<IAcyPhoto> {

	private AtyPhotoModel atyPhotoModel;
	private int page = 1;
	private int totlePage = 5;
	private RecyclerView recycler;
	private SwipeRefreshLayout refreshLayout;
	private boolean isLoadMore = false; // 是否加载过更多
	private List<Photos.ResultsBean> mData;
	private PhotoAdapter adapter;
	private GridLayoutManager layoutManager;
	private IAcyPhoto iAcyPhoto;
	private Context context;
	private int lastVisibleItem;

	public PhotoPresenter(Context context){
		this.atyPhotoModel = new AtyPhotoModel();
		mData = new ArrayList<>();
		this.context = context;

	}

	public void getPhotoList(){
		iAcyPhoto = getRefView();
		checkViewAttached();
		if (iAcyPhoto != null){
			recycler = getRefView().getRecyclerView();
			refreshLayout = getRefView().getRefreshLayout();
			layoutManager = getRefView().getLayoutManager();

			if (isLoadMore){
				page +=1 ;
			}
			atyPhotoModel.getMeiZhiData(page)
					.doOnSubscribe(new Consumer<Disposable>() {
						@Override
						public void accept(@NonNull Disposable disposable) throws Exception {
							getRefView().toggleShowLoading(true,null);
						}
					})
					.subscribe(new Observer<Photos>() {
						@Override
						public void onSubscribe(@NonNull Disposable d) {

						}

						@Override
						public void onNext(@NonNull Photos photos) {
							loadData(photos.getResults(), getRefView(),recycler,refreshLayout);
						}

						@Override
						public void onError(@NonNull Throwable e) {
							getRefView().toggleNetworkError(true,preDataClickListener);
						}

						@Override
						public void onComplete() {
							getRefView().toggleShowLoading(false,null);
						}
					});
		}


	}

	/**
	 * 加载数据
	 * @param results
	 * @param iAcyPhoto
	 * @param recycler
	 * @param refreshLayout
	 */
	private void loadData(List<Photos.ResultsBean> results, IAcyPhoto iAcyPhoto, RecyclerView recycler, SwipeRefreshLayout refreshLayout) {


			if (isLoadMore){
				mData.addAll(results);
				adapter.notifyDataSetChanged();
			}else {
				mData.addAll(results);
				adapter = new PhotoAdapter(mData);
				adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
				adapter.isFirstOnly(false);
				layoutManager = new GridLayoutManager(context,2);
				recycler.setAdapter(adapter);
			}






			adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
				@Override
				public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
					Intent intent = new Intent(context, PhotoDetailActivity.class);

					Bundle bundle = new Bundle();
					bundle.putString("url",mData.get(position).getUrl());
					intent.putExtras(bundle);
					context.startActivity(intent);
				}
			});
	}

	private View.OnClickListener preDataClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			getPhotoList();
		}
	};


	public void loadMoreData(){
	/*	recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				if (newState == RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem + 1 == layoutManager.getItemCount()) {
					isLoadMore = true;
					new Handler().postDelayed(new Runnable() {
						@Override
						public void run() {
							getPhotoList();
						}
					},1000);

				}
			}


			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = layoutManager.findLastVisibleItemPosition();
			}
		});*/


		RxRecyclerView.scrollEvents(recycler)
				.throttleFirst(1, TimeUnit.SECONDS)
				.subscribe(new Observer<RecyclerViewScrollEvent>() {
					@Override
					public void onSubscribe(@NonNull Disposable d) {

					}

					@Override
					public void onNext(@NonNull RecyclerViewScrollEvent recyclerViewScrollEvent) {

						recyclerViewScrollEvent.view().addOnScrollListener(new RecyclerView.OnScrollListener() {
							@Override
							public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
								if (newState == RecyclerView.SCROLL_STATE_IDLE&&lastVisibleItem + 1 == layoutManager.getItemCount()) {
									isLoadMore = true;
									new Handler().postDelayed(new Runnable() {
										@Override
										public void run() {
											getPhotoList();
										}
									},1000);

								}
							}

							@Override
							public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
								super.onScrolled(recyclerView, dx, dy);
								lastVisibleItem = layoutManager.findLastVisibleItemPosition();
							}
						});
					}

					@Override
					public void onError(@NonNull Throwable e) {

					}

					@Override
					public void onComplete() {

					}
				});

	}

	/**
	 * 下拉刷新
	 */
	public void refresh() {
		//iAcyPhoto = getRefView();
		refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				if (refreshLayout == null){
					return;
				}

				refreshLayout.postDelayed(new Runnable() {
					@Override
					public void run() {
						if (refreshLayout != null){
							refreshLayout.setRefreshing(false);
							Toasty.custom(context,"完成刷新",null, Toast.LENGTH_SHORT,false).show();
						}
					}
				},1000);
			}
		});


	}
}

