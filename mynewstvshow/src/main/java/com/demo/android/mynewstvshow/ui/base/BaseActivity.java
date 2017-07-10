package com.demo.android.mynewstvshow.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.demo.android.mynewstvshow.R;
import com.demo.android.mynewstvshow.ui.widget.loading.VaryViewHelperController;
import com.demo.android.mynewstvshow.utils.MyLogger;
import com.demo.android.mynewstvshow.utils.ToastShow;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by XiongRun on 2017/5/14.
 */

public abstract class BaseActivity<T extends BasePresent,V> extends RxAppCompatActivity implements BaseViewI<V> {

	protected T mPresenter;
	protected String TAG;
	public Context mContext;
	protected MyLogger logger;
	protected VaryViewHelperController mVaryViewHelperController;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(getContentView());

		this.mContext =this;
		logger = MyLogger.kLog();
		ButterKnife.bind(this);
		TAG = this.getClass().getSimpleName();

		//获取present引用允许为空，不是所有都要实现MVP模式
		if (getPresenter() != null){
			mPresenter = getPresenter();
			mPresenter.attachView(this);
		}

		if (null != getLoadingTargetView()) {
			mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
		}

		initData();
		initView();
		initToolBar();

	}

	protected abstract View getLoadingTargetView();

	/**
	 * 获取Presenter
	 * */
	public T getPresenter() {
		return null;
	}

	protected abstract void initToolBar();

	protected abstract void initView();

	protected abstract void initData();

	protected abstract int getContentView();


	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mPresenter!=null){
			mPresenter.dettachView();
		}
	}

	/**
	 * startActivity
	 *
	 * @param clazz
	 */
	protected void readyGo(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
	}

	/**
	 * startActivity with bundle
	 *
	 * @param clazz
	 * @param bundle
	 */
	protected void readyGo(Class<?> clazz, Bundle bundle) {
		Intent intent = new Intent(this, clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
	}

	/**
	 * startActivity then finish
	 *
	 * @param clazz
	 */
	protected void readyGoThenKill(Class<?> clazz) {
		Intent intent = new Intent(this, clazz);
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
		finish();
	}

	/**
	 * startActivity with bundle then finish
	 *
	 * @param clazz
	 * @param bundle
	 */
	protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
		Intent intent = new Intent(this, clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
		finish();
	}

	/**
	 * startActivityForResult
	 *
	 * @param clazz
	 * @param requestCode
	 */
	protected void readyGoForResult(Class<?> clazz, int requestCode) {
		Intent intent = new Intent(this, clazz);
		startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
	}

	/**
	 * startActivityForResult with bundle
	 *
	 * @param clazz
	 * @param requestCode
	 * @param bundle
	 */
	protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
		Intent intent = new Intent(this, clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
		overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
	}


	/**
	 * 添加 Fragment
	 *
	 * @param containerViewId
	 * @param fragment
	 */
	protected void addFragment(int containerViewId, Fragment fragment) {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.add(containerViewId, fragment);
		fragmentTransaction.commit();
	}

	/**
	 * 添加 Fragment
	 *
	 * @param containerViewId
	 * @param fragment
	 */
	protected void addFragment(int containerViewId, Fragment fragment, String tag) {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		// 设置tag，不然下面 findFragmentByTag(tag)找不到
		fragmentTransaction.add(containerViewId, fragment, tag);
		fragmentTransaction.addToBackStack(tag);
		fragmentTransaction.commit();
	}

	/**
	 * 替换 Fragment
	 *
	 * @param containerViewId
	 * @param fragment
	 */
	protected void replaceFragment(int containerViewId, Fragment fragment) {
		FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
		fragmentTransaction.replace(containerViewId, fragment);
		fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		fragmentTransaction.addToBackStack(null);
		fragmentTransaction.commit();
	}

	/**
	 * 替换 Fragment
	 *
	 * @param containerViewId
	 * @param fragment
	 */
	protected void replaceFragment(int containerViewId, Fragment fragment, String tag) {
		if (getSupportFragmentManager().findFragmentByTag(tag) == null) {
			FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
			// 设置tag
			fragmentTransaction.replace(containerViewId, fragment, tag);
			fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
			// 这里要设置tag，上面也要设置tag
			fragmentTransaction.addToBackStack(tag);
			fragmentTransaction.commit();
		} else {
			// 存在则弹出在它上面的所有fragment，并显示对应fragment
			getSupportFragmentManager().popBackStack(tag, 0);
		}
	}


	protected void showNetWorkError() {
		ToastShow.getInstance(mContext).toastShow(getResources().getString(R.string.network_error_tips));
	}

	@Override
	public void showDataSuccess(V datas) {

	}

	@Override
	public void showDataError(String errorMessage, int tag){

	}

	@Override
	public void toggleShowLoading(boolean toggle, String msg) {
		if (null == mVaryViewHelperController) {
			throw new IllegalArgumentException("You must return a right target view for loading");
		}

		if (toggle) {
			mVaryViewHelperController.showLoading(msg);
		} else {
			mVaryViewHelperController.restore();
		}
	}

	@Override
	public void toggleShowEmpty(boolean toggle, String msg, View.OnClickListener onClickListener) {
		if (null == mVaryViewHelperController) {
			throw new IllegalArgumentException("You must return a right target view for loading");
		}

		if (toggle) {
			mVaryViewHelperController.showEmpty(msg, onClickListener);
		} else {
			mVaryViewHelperController.restore();
		}
	}

	@Override
	public void toggleShowError(boolean toggle, String msg, View.OnClickListener onClickListener) {
		if (null == mVaryViewHelperController) {
			throw new IllegalArgumentException("You must return a right target view for loading");
		}

		if (toggle) {
			mVaryViewHelperController.showError(msg, onClickListener);
		} else {
			mVaryViewHelperController.restore();
		}
	}

	@Override
	public void toggleNetworkError(boolean toggle, View.OnClickListener onClickListener) {
		if (null == mVaryViewHelperController) {
			throw new IllegalArgumentException("You must return a right target view for loading");
		}

		if (toggle) {
			mVaryViewHelperController.showNetworkError(onClickListener);
		} else {
			mVaryViewHelperController.restore();
		}
	}

	/**
	 * 重新加载布局
	 */
	public void toggleRestore(){
		mVaryViewHelperController.restore();
	}

	/**
	 * 判断当前 Activity 是否允许返回
	 * 主界面不允许返回，次级界面允许返回
	 *
	 * @return false
	 */
	public boolean canBack() {
		return false;
	}

}
