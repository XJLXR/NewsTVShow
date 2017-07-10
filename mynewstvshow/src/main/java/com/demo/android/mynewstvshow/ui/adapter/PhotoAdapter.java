package com.demo.android.mynewstvshow.ui.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.demo.android.mynewstvshow.R;
import com.demo.android.mynewstvshow.bean.Photos;

import java.util.List;

/**
 * Created by XiongRun on 2017/7/6.
 */

public class PhotoAdapter extends BaseQuickAdapter<Photos.ResultsBean,BaseViewHolder> {
	public PhotoAdapter(@Nullable List<Photos.ResultsBean> data) {
		super(R.layout.item_photo,data);
	}

	@Override
	protected void convert(BaseViewHolder helper, Photos.ResultsBean item) {
		helper.setText(R.id.tv_title,item.getCreatedAt());
		Glide
				.with(mContext)
				.load(item.getUrl())
				.asBitmap()
				.into((ImageView) helper.getView(R.id.iv_meizhi));
	}
}
