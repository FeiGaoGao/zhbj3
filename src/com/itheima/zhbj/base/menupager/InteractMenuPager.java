package com.itheima.zhbj.base.menupager;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.itheima.zhbj.base.BaseMenuPager;

public class InteractMenuPager extends BaseMenuPager {

	public InteractMenuPager(Activity activity) {
		super(activity);
	}

	@Override
	public View initView() {
		TextView text = new TextView(mActivity);
		text.setText("第二个:专题");
		text.setTextColor(Color.RED);
		text.setTextSize(25);
		text.setGravity(Gravity.CENTER);
		return text;
	}

}
