package com.demo.android.mynewstvshow.ui.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.demo.android.mynewstvshow.R;
import com.demo.android.mynewstvshow.bean.NewslistBean;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by XiongRun on 2017/5/9.
 */

public class NewsAdapter extends CommonAdapter<NewslistBean> {


	public NewsAdapter(Context context, int layoutId, List<NewslistBean> datas) {
		super(context, layoutId, datas);
	}

	@Override
	protected void convert(ViewHolder holder, NewslistBean newslistBean, int position) {
		holder.setText(R.id.tv_title,newslistBean.getTitle());
		holder.setText(R.id.tv_time,newslistBean.getCtime());
		holder.setText(R.id.tv_from,newslistBean.getDescription());

		Glide.with(mContext)
				.load(newslistBean.getPicUrl())
				.asBitmap()
				.into((ImageView) holder.getView(R.id.iv));


	}
}
