package com.itheima.zhbj.base.impg;

import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.itheima.zhbj.MainActivity;
import com.itheima.zhbj.base.BasePager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

public class HomeContentPager extends BasePager {

	public HomeContentPager(Activity mActivity) {
		super(mActivity);
	}

	@Override
	public void initData() {
		setSlidingMenuEnable(true);
		ib_menubtn.setVisibility(View.GONE);

		tv_title.setText("智慧北京");
		TextView text = new TextView(mActivity);
		text.setText("首页");
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

	public static void setSlidingMenuEnable(boolean enable) {
		MainActivity main =(MainActivity) mActivity;
		SlidingMenu slidingMenu = main.getSlidingMenu();
		if (enable) {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		} else {
			slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}

	}
}
