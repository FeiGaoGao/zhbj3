package com.itheima.zhbj.base.menupager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.itheima.zhbj.base.BaseMenuPager;

public class TopicMenuPager extends BaseMenuPager {

	public TopicMenuPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initView() {
		TextView text = new TextView(mActivity);
		text.setText("第四个:互动");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);
		return text;
	}

}
