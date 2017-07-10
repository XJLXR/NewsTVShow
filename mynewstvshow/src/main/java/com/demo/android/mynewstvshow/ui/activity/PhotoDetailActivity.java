package com.demo.android.mynewstvshow.ui.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.demo.android.mynewstvshow.R;
import com.demo.android.mynewstvshow.ui.base.BaseActivity;
import com.demo.android.mynewstvshow.utils.SystemUiVisibilityUtil;
import com.jakewharton.rxbinding2.support.v7.widget.RxToolbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by XiongRun on 2017/7/8.
 */

public class PhotoDetailActivity extends BaseActivity {

	@BindView(R.id.photo_iv)
	PhotoView photoIv;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	private String url;

	private boolean isHidden = false;
	private boolean mIsStatusBarHidden = false;


	@Override
	protected View getLoadingTargetView() {
		return null;
	}

	@Override
	protected void initToolBar() {
		setSupportActionBar(toolbar);
	}

	@Override
	protected void initView() {
		RxToolbar.navigationClicks(toolbar)
				.throttleFirst(1, TimeUnit.SECONDS)
				.subscribe(new Consumer<Object>() {
					@Override
					public void accept(@NonNull Object o) throws Exception {
						finish();
					}
				});
	}

	@Override
	protected void initData() {
		url = getIntent().getStringExtra("url");

		Glide.with(mContext)
				.load(url)
				.asBitmap()
				.into(photoIv);

		photoIv.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
			@Override
			public void onPhotoTap(View view, float v, float v1) {
				hideToolBarAndTextView();
				hideOrShowStatusBar();
			}

			@Override
			public void onOutsidePhotoTap() {

			}
		});

		initLazyLoadView();
	}

	public void hideToolBarAndTextView() {
		isHidden = !isHidden;
		if (isHidden) {
			startAnimation(true, 1.0f, 0.0f);
		} else {
			startAnimation(false, 0.1f, 1.0f);
		}
	}

	private void startAnimation(final boolean endState, float startValue, float endValue) {
		ValueAnimator animator = ValueAnimator.ofFloat(startValue, endValue).setDuration(500);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				float y1;
				if (endState) {
					y1 = (0 - animation.getAnimatedFraction()) * toolbar.getHeight();
				} else {
					y1 = (animation.getAnimatedFraction() - 1) * toolbar.getHeight();
				}
				toolbar.setTranslationY(y1);
			}
		});
		animator.start();
	}

	private void hideOrShowStatusBar() {
		if (mIsStatusBarHidden) {
			SystemUiVisibilityUtil.enter(PhotoDetailActivity.this);
		} else {
			SystemUiVisibilityUtil.exit(PhotoDetailActivity.this);
		}
		mIsStatusBarHidden = !mIsStatusBarHidden;
	}

	private void initLazyLoadView() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().getEnterTransition().addListener(new Transition.TransitionListener() {

				@Override
				public void onTransitionStart(Transition transition) {

				}

				@Override
				public void onTransitionEnd(Transition transition) {
					loadPhotoView();
				}

				@Override
				public void onTransitionCancel(Transition transition) {

				}

				@Override
				public void onTransitionPause(Transition transition) {

				}

				@Override
				public void onTransitionResume(Transition transition) {

				}
			});
		} else {
			loadPhotoView();
		}
	}

	private void loadPhotoView() {
		Glide.with(mContext)
				.load(url)
				.asBitmap()
				.into(photoIv);
	}

	@Override
	protected int getContentView() {
		return R.layout.activity_photo_detail;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_photo_detail, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
			case R.id.action_save:
				saveImg();
				return true;

			case R.id.action_share:
				return true;

		}
		return super.onOptionsItemSelected(item);
	}

	public void saveImg() {
		photoIv.setDrawingCacheEnabled(true);
		photoIv.buildDrawingCache();
		Bitmap bitmap = photoIv.getDrawingCache();

		//将Bitmap 转换成二进制，写入本地
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		byte[] byteArray = stream.toByteArray();

		File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/meinv");
		if (!dir.exists()) {
			dir.mkdir();
		}
		String filename = System.currentTimeMillis() + ".png";
		File file = new File(dir, filename);
		try {
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(byteArray, 0, byteArray.length);
			fos.flush();
			//用广播通知相册进行更新相册
			Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
			Uri uri = Uri.fromFile(file);
			intent.setData(uri);
			PhotoDetailActivity.this.sendBroadcast(intent);
			Toasty.custom(mContext, "保存图片成功", null, Toast.LENGTH_SHORT, false).show();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toasty.custom(mContext, e.getMessage(), null, Toast.LENGTH_SHORT, false).show();
		} catch (IOException e) {
			e.printStackTrace();
			Toasty.custom(mContext, e.getMessage(), null, Toast.LENGTH_SHORT, false).show();
		}
	}

}