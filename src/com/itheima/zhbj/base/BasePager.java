package com.itheima.zhbj.base;

import com.itheima.zhbj.MainActivity;
import com.itheima.zhbj.R;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * 五个页面的基类
 * 
 * @author sunzhaung
 * 
 */
public class BasePager {
	public static Activity mActivity;
	public View mView;
	public TextView tv_title;
	public FrameLayout fl_content;
	public ImageButton ib_menubtn;
	public ImageButton ib_photo;

	public BasePager(Activity mActivity) {
		BasePager.mActivity = mActivity;
		intiView();
	}

	/**
	 * 初始化视图
	 */
	public void intiView() {
		mView = View.inflate(mActivity, R.layout.base_pager, null);
		tv_title = (TextView) mView.findViewById(R.id.tv_title);
		fl_content = (FrameLayout) mView.findViewById(R.id.fl_content);
		ib_menubtn = (ImageButton) mView.findViewById(R.id.ib_menubtn);
		ib_photo = (ImageButton) mView.findViewById(R.id.ib_photo);
		ib_menubtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				toggleSlidingMenu();
			}
		});
	}

	protected void toggleSlidingMenu() {
		MainActivity main = (MainActivity) mActivity;
		SlidingMenu slidingMenu = main.getSlidingMenu();
		slidingMenu.toggle();
	}

	/**
	 * 初始化布局
	 */
	public void initData() {
	}
}
