package com.demo.android.mynewstvshow.ui.activity;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.demo.android.mynewstvshow.MyApplication;
import com.demo.android.mynewstvshow.R;
import com.demo.android.mynewstvshow.bean.NewslistBean;
import com.demo.android.mynewstvshow.gen.NewslistBeanDao;
import com.demo.android.mynewstvshow.ui.base.BaseActivity;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.List;

import butterknife.BindView;

/**
 * Created by XiongRun on 2017/7/9.
 */

public class NewsDetailActivity extends BaseActivity {

	@BindView(R.id.news_detail_picture_iv)
	ImageView newsDetailPictureIv;
	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.toolbar_layout)
	CollapsingToolbarLayout toolbarLayout;
	@BindView(R.id.app_bar)
	AppBarLayout appBar;

	@BindView(R.id.nest)
	NestedScrollView nest;
	@BindView(R.id.root)
	CoordinatorLayout root;
	@BindView(R.id.tencent_webView)
	WebView tencentWebView;
	private NewslistBean data;
	private WebSettings webView;
	private NewslistBeanDao dao;

	@Override
	protected View getLoadingTargetView() {
		return null;
	}

	@Override
	protected void initToolBar() {

		toolbar.setTitle(data.getTitle());
		setSupportActionBar(toolbar);

		Glide.with(mContext)
				.load(data.getPicUrl())
				.asBitmap()
				.placeholder(R.mipmap.tangyan)
				.into(newsDetailPictureIv);


		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void initData() {
		data = (NewslistBean) getIntent().getSerializableExtra("data");

		webView = tencentWebView.getSettings();
		webView.setJavaScriptEnabled(true);
		webView.setPluginsEnabled(true);

		//设置自适应屏幕，两者合用
		webView.setUseWideViewPort(true); //将图片调整到适合webview的大小
		webView.setLoadWithOverviewMode(true); // 缩放至屏幕的大小



		tencentWebView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});

		tencentWebView.loadUrl(data.getUrl());
	}

	@Override
	protected int getContentView() {
		return R.layout.activity_news_detail;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_news_detail, menu);
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()){
			case R.id.conlication:

				unique(data.getTitle(),data);
				break;

			default:
				break;
		}
		return true;
	}
	//判断数据库里面的数据是否存在
	private void unique(String msg, NewslistBean data) {
		boolean isHave = false;
		dao = MyApplication.getDaoSession().getNewslistBeanDao();
		List<NewslistBean> newslistBeen = dao.loadAll();

		for (int i = 0;i<newslistBeen.size();i++){
			String title = newslistBeen.get(i).getTitle();
			isHave = msg.equals(title);
		}
		if (isHave)
		{
			Toast.makeText(this, "您已收藏，切勿重复添加！", Toast.LENGTH_SHORT).show();
		}else
		{
			dao.insert(data);
			Toast.makeText(this, "收藏成功！", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && tencentWebView.canGoBack()) {
			tencentWebView.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
