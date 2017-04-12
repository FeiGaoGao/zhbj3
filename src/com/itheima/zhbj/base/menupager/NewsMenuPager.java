package com.itheima.zhbj.base.menupager;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;

import com.itheima.zhbj.MainActivity;
import com.itheima.zhbj.R;
import com.itheima.zhbj.base.BaseMenuPager;
import com.itheima.zhbj.base.NewsTabPager;
import com.itheima.zhbj.domain.NewsData.NewsTabData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.viewpagerindicator.TabPageIndicator;

public class NewsMenuPager extends BaseMenuPager implements  OnPageChangeListener{

	private ViewPager vp_news;
	ArrayList<NewsTabPager> tabList;// NewsTabPager是新闻详情页的模板页
	ArrayList<NewsTabData> mTabData;// 从Json中获取的数据
	private NewsAdapter newsAdapter;
	private View view;

	// private ImageButton ib_next;

	public NewsMenuPager(Activity activity, ArrayList<NewsTabData> children) {
		super(activity);
		mTabData = children;
	}

	@Override
	public View initView() {
		view = View.inflate(mActivity, R.layout.item_news_menu, null);
		vp_news = (ViewPager) view.findViewById(R.id.vp_news);
		ViewUtils.inject(this, view);
	
		return view;
	}

	@Override
	public void initData() {
		tabList = new ArrayList<NewsTabPager>();
		for (int i = 0; i < mTabData.size(); i++) {
			NewsTabPager pager = new NewsTabPager(mActivity, mTabData.get(i));
			tabList.add(pager);
		}
		newsAdapter = new NewsAdapter();
		vp_news.setAdapter(newsAdapter);
		// 初始化自定义控件

		TabPageIndicator indicator = (TabPageIndicator) view
				.findViewById(R.id.indicator);
		indicator.setViewPager(vp_news);
		
		//vp_news.setOnPageChangeListener(this);
		indicator.setOnPageChangeListener(this);
		
		// ib_next = (ImageButton)view.findViewById(R.id.ib_next);
		super.initData();
	}
    //自定义控件俺的滑动按钮
	@OnClick(R.id.ib_next)
	public void setNext(View view) {
		int currentItem = vp_news.getCurrentItem();
		vp_news.setCurrentItem(++currentItem);
	}

	class NewsAdapter extends PagerAdapter {
		@Override
		public CharSequence getPageTitle(int position) {
			return mTabData.get(position).title;
		}

		@Override
		public int getCount() {
			return tabList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			NewsTabPager pager = tabList.get(position);
			container.addView(pager.mView);
			return pager.mView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	public void onPageScrollStateChanged(int arg0) {
		
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	public void onPageSelected(int arg0) {
		 MainActivity main=(MainActivity) mActivity;
		 SlidingMenu slidingMenu = main.getSlidingMenu();
		 if (arg0== 0) {
			 slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		}else{
			 slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}
}
