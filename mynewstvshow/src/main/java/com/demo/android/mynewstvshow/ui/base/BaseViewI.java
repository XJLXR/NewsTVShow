package com.demo.android.mynewstvshow.ui.base;

import android.view.View;

/**
 * Created by XiongRun on 2017/5/14.
 */

public interface BaseViewI<V> {


	/**
	 * 数据加载失败
	 * tag==1,正常错误，可以只显示toast
	 * tag=0,非正常错误，需要考虑显示错误界面
	 * */
	public void showDataError(String errorMessage, int tag);

	/**
	 *数据加载成功
	 * */
	public void showDataSuccess(V datas);

	/**
	 * toggle show loading
	 * @param toggle
	 * 正在加载中布局
	 */
	public void toggleShowLoading(boolean toggle, String msg);


	/**
	 * toggle show empty
	 * @param toggle
	 * 空数据布局
	 */
	public void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener);

	/**
	 * toggle show error
	 * @param toggle
	 * 数据加载错误布局
	 */
	public void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener);


	/**
	 * toggle show network error
	 * @param toggle
	 * 网络错误布局
	 */
	public void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener);

	/**
	 * 重新加载
	 */
	public void toggleRestore();
}
