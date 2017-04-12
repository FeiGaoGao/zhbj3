package com.itheima.zhbj.base.impg;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.itheima.zhbj.base.BasePager;

public class GovAffairsPager extends BasePager {

	public GovAffairsPager(Activity mActivity) {
		super(mActivity);
	}

@Override
public void initData() {
	HomeContentPager.setSlidingMenuEnable(false);
	tv_title.setText("人口");
	TextView  text=new TextView(mActivity);
	 text.setText("政务");
	 text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);

		// 向FrameLayout中动态添加布局
		fl_content.addView(text);
	super.initData();
}
@Override
public void intiView() {
	
	super.intiView();
}
}
