package com.itheima.zhbj.fragment;

import java.util.ArrayList;

import com.itheima.zhbj.R;
import com.itheima.zhbj.base.BasePager;
import com.itheima.zhbj.base.impg.GovAffairsPager;
import com.itheima.zhbj.base.impg.HomeContentPager;
import com.itheima.zhbj.base.impg.NewsContentPager;
import com.itheima.zhbj.base.impg.SettingPager;
import com.itheima.zhbj.base.impg.SmartServicePager;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ContentFragment extends BaseFragment {

	@ViewInject(R.id.rg_group)
	private RadioGroup rb_Gruop;

	@ViewInject(R.id.vp_content)
	private ViewPager vp_Content;

	public ArrayList<BasePager> mBaseList;

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.content_menu, null);
		ViewUtils.inject(this, view);
		return view;
	}

	public void initData() {
		rb_Gruop.check(R.id.rb_home);// 默认选择

		mBaseList = new ArrayList<BasePager>();

		/*
		 * for (int i = 0; i < mBaseList.size(); i++) { BasePager pager = new
		 * BasePager(mActivity); mBaseList.add(pager);
		 */

		mBaseList.add(new HomeContentPager(mActivity));
		mBaseList.add(new NewsContentPager(mActivity));
		mBaseList.add(new SmartServicePager(mActivity));
		mBaseList.add(new GovAffairsPager(mActivity));
		mBaseList.add(new SettingPager(mActivity));
		vp_Content.setAdapter(new ViewPagerAdapter());
		rb_Gruop.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
				case R.id.rb_home:
					vp_Content.setCurrentItem(0, false);
					break;
				case R.id.rb_news:
					vp_Content.setCurrentItem(1, false);
					break;
				case R.id.rb_smart:
					vp_Content.setCurrentItem(2, false);
					break;
				case R.id.rb_hgov:
					vp_Content.setCurrentItem(3, false);
					break;
				case R.id.rb_setting:
					vp_Content.setCurrentItem(4, false);
					break;

				default:
					break;
				}
			}
		});
		vp_Content.setOnPageChangeListener(new OnPageChangeListener() {

			public void onPageSelected(int arg0) {
				mBaseList.get(arg0).initData();
			}

			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			public void onPageScrollStateChanged(int arg0) {

			}
		});
		mBaseList.get(0).initData();
	}

	class ViewPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return mBaseList.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		public Object instantiateItem(ViewGroup container, int position) {
			BasePager pager = mBaseList.get(position);
			container.addView(pager.mView);
			// pager.initData();// 不要在此处加载数据否则会预加载下一个页面
			return pager.mView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	/**
	 * 获取新闻中心的首页
	 * 
	 * @return
	 */
	public NewsContentPager getNewsConterPager() {
		// BasePager baseMenuPager = mBaseList.get(1);
		return (NewsContentPager) mBaseList.get(1);
	}
}
