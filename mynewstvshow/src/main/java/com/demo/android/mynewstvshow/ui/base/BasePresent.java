package com.demo.android.mynewstvshow.ui.base;

/**
 * Created by XiongRun on 2017/5/14.
 */

public class BasePresent<V extends BaseViewI> {

	public V mView;

	/**
	 *  Presenter与View建立连接
	 * @param view
	 */
	public void attachView(V view){
		this.mView = view;
	}

	/**
	 * Presenter与View连接断开
	 */
	public void dettachView(){
		if (mView != null){
			mView = null;
		}
	}

	/**
	 * 是否与View建立连接
	 * @return
	 */
	public boolean isViewAttached() {
		return mView != null;
	}

	/**
	 * 获取view
	 */
	protected V getRefView(){
		return mView;
	}


	/**
	 * 每次调用业务请求的时候都要先调用方法检查是否与View建立连接，没有则抛出异常
	 */
	public void checkViewAttached() {
		if (!isViewAttached()) {
			throw new MvpViewNotAttachedException();
		}
	}

	public static class MvpViewNotAttachedException extends RuntimeException {
		public MvpViewNotAttachedException() {
			super("请求数据前请先调用 attachView(mView) 方法与View建立连接");
		}
	}
}
