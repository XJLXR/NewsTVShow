package com.demo.android.mynewstvshow.ui.model;

import com.demo.android.mynewstvshow.bean.News;
import com.demo.android.mynewstvshow.http.HttpMethod;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by XiongRun on 2017/7/2.
 */

public class FgNewsModel {

	public Observable<News> getNewsList(String type, Map<String,String> map){
		return HttpMethod.getNewsApi().getNewsList(type,map)
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

}
