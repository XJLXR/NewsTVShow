package com.demo.android.mynewstvshow;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.demo.android.mynewstvshow.gen.DaoMaster;
import com.demo.android.mynewstvshow.gen.DaoSession;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by XiongRun on 2017/7/1.
 */

public class MyApplication extends Application {

	private static Context mContext;
	private static MyApplication instance;
	private List<Activity> activityList = new LinkedList<Activity>();
	private DaoMaster.DevOpenHelper helper;
	private static SQLiteDatabase db;
	private static DaoMaster daoMaster;
	private static DaoSession daoSession;


	public MyApplication(){

		getAppContext();
	}

	@Override
	public void onCreate() {
		super.onCreate();
		 mContext = this;
		setUpDataBase();
	}

	private void setUpDataBase() {

		//创建数据库
		helper = new DaoMaster.DevOpenHelper(mContext,"collection.db",null);
		//获取可写的数据库
		db = helper.getWritableDatabase();
		//获取数据库对象
		daoMaster = new DaoMaster(db);
		//获取Dao对象管理者
		daoSession = daoMaster.newSession();
	}

	public static DaoSession getDaoSession() {
		return daoSession;
	}

	public static DaoMaster getDaoMaster() {
		return daoMaster;
	}

	public static SQLiteDatabase getDb(){
		return db;
	}

	// 单例模式中获取唯一的MyApplication实例
	public static MyApplication getInstance(){

		synchronized (MyApplication.class){
			if (instance == null){
				synchronized (MyApplication.class){
					instance = new MyApplication();
				}
			}
		}
		return instance;
	}

	public static Context getAppContext() {
		return mContext;
	}

	// 添加Activity到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
		Log.v("TAG","添加数据了");
	}

	// 添加Activity到容器中
	public void clearActivity() {
		activityList.clear();
	}

}
