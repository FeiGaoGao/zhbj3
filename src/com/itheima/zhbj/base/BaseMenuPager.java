package com.itheima.zhbj.base;

import android.app.Activity;
import android.view.View;

public abstract class BaseMenuPager {
	public Activity mActivity;
	public View mView;

	public BaseMenuPager(Activity activity) {
		this.mActivity = activity;
		mView = initView();
	}

	/**
	 * 初始化数据
	 */
	public void initData() {
	}

	/**
	 * 初始化数据
	 * 
	 * @return
	 */
	public abstract View initView();
}
