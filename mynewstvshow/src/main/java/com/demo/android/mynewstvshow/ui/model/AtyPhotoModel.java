package com.demo.android.mynewstvshow.ui.model;

import com.demo.android.mynewstvshow.bean.Photos;
import com.demo.android.mynewstvshow.http.HttpMethod;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by XiongRun on 2017/7/6.
 */

public class AtyPhotoModel {


	public Observable<Photos> getMeiZhiData(int page ){
		return HttpMethod.getPhotosApi().getMeizhiData(page)
				.subscribeOn(Schedulers.io())
				.unsubscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
