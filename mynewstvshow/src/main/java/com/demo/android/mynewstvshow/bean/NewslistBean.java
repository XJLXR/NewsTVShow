package com.demo.android.mynewstvshow.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

/**
 * Created by XiongRun on 2017/7/10.
 */
@Entity
public class NewslistBean implements Serializable {


	/**
	 * ctime : 2017-05-09
	 * title : 看不见的伤痕更深更疼？| 什么是“微笑抑郁症”（Smiling Depression）
	 * description : CPPA幸福中国
	 * picUrl : http://zxpic.gtimg.com/infonew/0/wechat_pics_-22999408.jpg/640
	 * url : http://mp.weixin.qq.com/s?__biz=MjM5MzAxMjMwOQ==&idx=2&mid=2654084295&sn=6bce487bdd0dcbdb569b00bca345363d
	 */

	private static final long serialVersionUID = 1L;
	@Property
	private String ctime;

	@Property
	private String title;

	@Property
	private String description;

	@Property
	private String picUrl;

	@Property
	private String url;

	@Generated(hash = 86141965)
	public NewslistBean(String ctime, String title, String description, String picUrl, String url) {
					this.ctime = ctime;
					this.title = title;
					this.description = description;
					this.picUrl = picUrl;
					this.url = url;
	}

	@Generated(hash = 923354944)
	public NewslistBean() {
	}

	public String getCtime() {
					return this.ctime;
	}

	public void setCtime(String ctime) {
					this.ctime = ctime;
	}

	public String getTitle() {
					return this.title;
	}

	public void setTitle(String title) {
					this.title = title;
	}

	public String getDescription() {
					return this.description;
	}

	public void setDescription(String description) {
					this.description = description;
	}

	public String getPicUrl() {
					return this.picUrl;
	}

	public void setPicUrl(String picUrl) {
					this.picUrl = picUrl;
	}

	public String getUrl() {
					return this.url;
	}

	public void setUrl(String url) {
					this.url = url;
	}


}
