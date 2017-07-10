package com.demo.android.mynewstvshow.ui.presenter;

import android.view.View;

import com.demo.android.mynewstvshow.bean.News;
import com.demo.android.mynewstvshow.ui.base.BasePresent;
import com.demo.android.mynewstvshow.ui.model.FgNewsModel;
import com.demo.android.mynewstvshow.ui.view.IFgNews;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by XiongRun on 2017/7/2.
 */

public class NewsPresenter extends BasePresent<IFgNews> {

	private Map<String, String> map;
	private int pageSize=20;
	private int pageIndex=1;
	private FgNewsModel fgNewsModel;
	private String mType;
	private  IFgNews iFgNews;
	private boolean isLoadMore = false; // 是否加载过更多


	public NewsPresenter(){
		this.fgNewsModel = new FgNewsModel();
		map = new HashMap<>();

	}

	public void getNewsList(String type,Map<String,String> map) {
		this.map =map;
		this.mType = type;
		iFgNews = getRefView();
		checkViewAttached();
		if (iFgNews != null) {
			fgNewsModel.getNewsList(type, map)
					.doOnSubscribe(new Consumer<Disposable>() {
						@Override
						public void accept(@NonNull Disposable disposable) throws Exception {

						}
					})
					.subscribe(new Observer<News>() {
						Disposable disposable;
						@Override
						public void onSubscribe(@NonNull Disposable d) {
							this.disposable = d;
						}

						@Override
						public void onNext(@NonNull News news) {
							if (news.getCode() == 200){
								iFgNews.showDataSuccess(news);
							}else {
								iFgNews.showDataError(news.getMsg(),news.getCode());
							}
						}


						@Override
						public void onError(@NonNull Throwable e) {

							//iFgNews.toggleNetworkError(true, preDataClickListener);
						}

						@Override
						public void onComplete() {
							getRefView().toggleShowLoading(false, null);
						}
					});
		}


	}


	private View.OnClickListener preDataClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			getNewsList(mType,map);
		}
	};

}
