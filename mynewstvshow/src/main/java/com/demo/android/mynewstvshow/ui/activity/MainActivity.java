package com.demo.android.mynewstvshow.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.demo.android.mynewstvshow.R;
import com.demo.android.mynewstvshow.common.Config;
import com.demo.android.mynewstvshow.ui.adapter.NewsFragmentAdapter;
import com.demo.android.mynewstvshow.ui.base.BaseActivity;
import com.demo.android.mynewstvshow.ui.fragment.NewsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity {


	@BindView(R.id.toolbar)
	Toolbar toolbar;
	@BindView(R.id.main_tabs)
	TabLayout mainTabs;
	@BindView(R.id.navigation)
	NavigationView navigation;
	@BindView(R.id.drawer)
	DrawerLayout drawer;
	@BindView(R.id.viewPager)
	ViewPager viewPager;

	private List<Fragment> fragments;
	private List<String> mlist = new ArrayList<>();//加载新闻类型的集合
	private NewsFragmentAdapter adapter;

	@Override
	protected View getLoadingTargetView() {
		return null;
	}

	@Override
	protected void initToolBar() {
		toolbar.setNavigationIcon(R.mipmap.ic_menu_black_24dp);
		toolbar.setTitle("新闻");
		setSupportActionBar(toolbar);
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	protected void initView() {

	}

	@Override
	protected void initData() {

	}

	@Override
	protected int getContentView() {
		return R.layout.activity_main;
	}

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initTabs();
		initDrawer();
	}

	private void initDrawer() {
		navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(@NonNull MenuItem item) {
				drawer.closeDrawer(GravityCompat.START);

				switch (item.getItemId()) {

					//新闻
					case R.id.drawer_news:
						Toasty.info(mContext, "drawer_news", Toast.LENGTH_LONG, true).show();
						drawer.closeDrawer(Gravity.LEFT, true);
						break;

					//图片
					case R.id.drawer_photo:
						readyGo(PhotoActivity.class);
						drawer.closeDrawer(Gravity.LEFT, true);
						break;

					//视频
					case R.id.drawer_video:
						Toasty.info(mContext, "drawer_news", Toast.LENGTH_LONG, true).show();
						drawer.closeDrawer(Gravity.LEFT, true);
						break;

					//关于
					case R.id.drawer_about:
						Toasty.info(mContext, "drawer_news", Toast.LENGTH_LONG, true).show();
						drawer.closeDrawer(Gravity.LEFT, true);
						break;

					//收藏
					case R.id.drawer_like:
						readyGo(CollectionActivity.class);
						drawer.closeDrawer(Gravity.LEFT, true);
						break;

					//日夜模式
					case R.id.drawer_night:
						Toasty.info(mContext, "drawer_news", Toast.LENGTH_LONG, true).show();
						drawer.closeDrawer(Gravity.LEFT, true);
						break;

					//设置
					case R.id.drawer_setting:
						Toasty.info(mContext, "drawer_news", Toast.LENGTH_LONG, true).show();
						drawer.closeDrawer(Gravity.LEFT, true);
						break;

					//分享
					case R.id.drawer_share:
						Toasty.info(mContext, "drawer_news", Toast.LENGTH_LONG, true).show();
						drawer.closeDrawer(Gravity.LEFT, true);
						break;
				}

				return true;
			}
		});
	}

	private void initTabs() {
		fragments = new ArrayList<>(Config.channels.length);
		for (int i = 0;i<Config.channels.length;i++){
			mainTabs.addTab(mainTabs.newTab().setText(Config.channels[i]));

			NewsFragment fragment = new NewsFragment();
			Bundle b = new Bundle();
			b.putString("type", Config.channelsKey[i]);
			fragment.setArguments(b);
			fragments.add(fragment);
		}

		adapter = new NewsFragmentAdapter(
				getSupportFragmentManager(),
				fragments,
				Arrays.asList(Config.channels));
		viewPager.setAdapter(adapter);
		mainTabs.setupWithViewPager(viewPager);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				drawer.openDrawer(Gravity.LEFT, true);
				break;
			default:
				break;
		}

		return true;
	}
}
