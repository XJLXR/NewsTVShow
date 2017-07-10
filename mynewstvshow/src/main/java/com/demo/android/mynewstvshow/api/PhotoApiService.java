package com.demo.android.mynewstvshow.api;

import com.demo.android.mynewstvshow.bean.Photos;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by XiongRun on 2017/7/6.
 */

public interface PhotoApiService {

	@GET("data/福利/10/{page}")
	Observable<Photos> getMeizhiData(@Path("page") int page);
}
