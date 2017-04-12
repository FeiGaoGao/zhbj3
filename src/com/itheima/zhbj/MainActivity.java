package com.itheima.zhbj;

import com.itheima.zhbj.R;
import com.itheima.zhbj.fragment.ContentFragment;
import com.itheima.zhbj.fragment.LeftMenuFragment;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

public class MainActivity extends SlidingFragmentActivity {
	private static final String Fragment_Left_Menu = "fragment_left_menu";
	private static final String Fragment_Content_Menu = "fragment_content_menu";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.left_menu);// 设置侧边栏
		SlidingMenu slidingMenu = getSlidingMenu();// 获取侧边栏对象
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);// 设置全屏触摸
		slidingMenu.setBehindOffset(200);// 设置预留屏幕的宽度
		initFragment();
		
	}

	private void initFragment() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction beginTransaction = fragmentManager
				.beginTransaction();
		beginTransaction.replace(R.id.fl_left_menu, new LeftMenuFragment(),
				Fragment_Left_Menu);
		beginTransaction.replace(R.id.fl_activity_main, new ContentFragment(),
				Fragment_Content_Menu);
		beginTransaction.commit();
	}
   public LeftMenuFragment  getLeftMenuFragment(){
	   FragmentManager fragmentManager = getSupportFragmentManager();
	   LeftMenuFragment findFragmentByTag = (LeftMenuFragment) fragmentManager.findFragmentByTag(Fragment_Left_Menu);
	   return findFragmentByTag;
   }
   public ContentFragment  getContentMenuFragment(){
	   FragmentManager fragmentManager =getSupportFragmentManager();
	   ContentFragment findFragmentByTag = (ContentFragment) fragmentManager.findFragmentByTag(Fragment_Content_Menu);
	   return findFragmentByTag;
   }
}
