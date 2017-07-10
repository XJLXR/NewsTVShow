package com.demo.android.mynewstvshow.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.demo.android.mynewstvshow.R;
import com.demo.android.mynewstvshow.ui.widget.loading.VaryViewHelperController;
import com.demo.android.mynewstvshow.utils.MyLogger;
import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;

/**
 * Created by XiongRun on 2017/5/14.
 */

public abstract class BaseFragment<T extends BasePresent,V> extends RxFragment implements BaseViewI<V>{


	protected View rootView;
	protected MyLogger logger;
	protected String TAG ;
	public T mPresenter;
	protected VaryViewHelperController mVaryViewHelperController;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		if (rootView == null){
			rootView = inflater.inflate(createViewLayoutId(),container,false);
			TAG=this.getClass().getSimpleName();
			ButterKnife.bind(this, rootView);




			if (null != getLoadingTargetView()) {
				mVaryViewHelperController = new VaryViewHelperController(getLoadingTargetView());
			}

			//获取present引用允许为空，不是所有都要实现MVP模式
			if (getPresenter() != null){
				mPresenter = getPresenter();
				mPresenter.attachView(this);
			}

			initData();
			initView(rootView);
			initToolbar();


		}else {
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (null != parent) {
				parent.removeView(rootView);
			}
		}
		return rootView;
	}

	protected abstract T getPresenter();

	protected abstract View getLoadingTargetView();

	protected abstract void initData();

	protected abstract void initToolbar();

	protected abstract void initView(View rootView);

	protected abstract int createViewLayoutId();

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		logger = MyLogger.kLog();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if(mPresenter!=null){
			mPresenter.dettachView();
		}
	}

	/**
	 * startActivity with bundle
	 *
	 * @param clazz
	 * @param bundle
	 */
	protected void readyGo(Class<?> clazz, Bundle bundle) {
		Intent intent = new Intent(getActivity(), clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}
	/**
	 * startActivity
	 *
	 * @param clazz
	 */
	protected void readyGo(Class<?> clazz) {
		Intent intent = new Intent(getActivity(), clazz);
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

	/**
	 * startActivityForResult
	 *
	 * @param clazz
	 * @param requestCode
	 */
	protected void readyGoForResult(Class<?> clazz, int requestCode) {
		Intent intent = new Intent(getActivity(), clazz);
		startActivityForResult(intent, requestCode);
		getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

	/**
	 * startActivityForResult with bundle
	 *
	 * @param clazz
	 * @param requestCode
	 * @param bundle
	 */
	protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
		Intent intent = new Intent(getActivity(), clazz);
		if (null != bundle) {
			intent.putExtras(bundle);
		}
		startActivityForResult(intent, requestCode);
		getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
	}

	@Override
	public void showDataError(String errorMessage, int tag) {

	}

	@Override
	public void showDataSuccess(V datas) {

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

}
