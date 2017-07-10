package com.demo.android.mynewstvshow.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.android.mynewstvshow.R;
import com.demo.android.mynewstvshow.bean.NewslistBean;

import java.util.List;

/**
 * Created by XiongRun on 2017/7/3.
 */

public class FgNewsAdapter extends BaseQuickAdapter<NewslistBean,BaseViewHolder> {


	public FgNewsAdapter(@Nullable List<NewslistBean> data) {
		super(R.layout.item_news,data);
	}

	@Override
	protected void convert(BaseViewHolder helper, NewslistBean item) {
		helper.setText(R.id.tv_title,item.getTitle());
		helper.setText(R.id.tv_time,item.getCtime());
		helper.setText(R.id.tv_from,item.getDescription());

		Glide.with(mContext)
				.load(item.getPicUrl())
				.asBitmap()
				.into((ImageView) helper.getView(R.id.iv));
	}
}
