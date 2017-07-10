package com.demo.android.mynewstvshow.api;

import com.demo.android.mynewstvshow.bean.News;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by XiongRun on 2017/7/2.
 */

public interface NewsApiService {


	@GET("{type}")
	Observable<News> getNewsList( @Path("type") String type, @Query("key") String key, @Query("num") int num, @Query("page") int page);

	@GET("{type}")
	Observable<News> getNewsList(  @Path("type") String type,@QueryMap Map<String, String> map);
}
