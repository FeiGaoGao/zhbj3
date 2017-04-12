package com.itheima.zhbj.base.impg;

import java.util.ArrayList;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itheima.zhbj.MainActivity;
import com.itheima.zhbj.Utils.CacheUtils;
import com.itheima.zhbj.base.BaseMenuPager;
import com.itheima.zhbj.base.BasePager;
import com.itheima.zhbj.base.menupager.InteractMenuPager;
import com.itheima.zhbj.base.menupager.NewsMenuPager;
import com.itheima.zhbj.base.menupager.PhotoMenuPager;
import com.itheima.zhbj.base.menupager.TopicMenuPager;
import com.itheima.zhbj.domain.NewsData;
import com.itheima.zhbj.domain.NewsData.NewsMenuData;
import com.itheima.zhbj.fragment.LeftMenuFragment;
import com.itheima.zhbj.global.GlobalContants;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class NewsContentPager extends BasePager {
	ArrayList<BaseMenuPager> mMenuList;
	private NewsData data;

	public NewsContentPager(Activity mActivity) {
		super(mActivity);
	}

	@Override
	public void initData() {
		HomeContentPager.setSlidingMenuEnable(false);
		// tv_title.setText("新闻中心");
		/*
		 * TextView text = new TextView(mActivity); text.setText("新闻");
		 * text.setTextColor(Color.RED); text.setTextSize(25);
		 * text.setGravity(Gravity.CENTER);
		 * 
		 * // 向FrameLayout中动态添加布局 fl_content.addView(text);
		 */
		String cache = CacheUtils.getCache(GlobalContants.CATEGORIES_URL,
				mActivity);
		if (!TextUtils.isEmpty(cache)) {
			parseData(cache);
		}
		getDataForService();

		super.initData();
	}

	private void getDataForService() {
		HttpUtils xUtils = new HttpUtils();
		xUtils.send(HttpMethod.GET, GlobalContants.CATEGORIES_URL,
				new RequestCallBack<String>() {
					@Override
					public void onSuccess(ResponseInfo responseInfo) {
						String result = (String) responseInfo.result;
						System.out.println("结果:" + result);
						parseData(result);
						CacheUtils.setCache(GlobalContants.CATEGORIES_URL,
								result, mActivity);
					}

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(mActivity, "请求失败", Toast.LENGTH_SHORT)
								.show();
						arg0.printStackTrace();
					}

				});
	}

	protected void parseData(String result) {
		Gson gson = new Gson();
		data = gson.fromJson(result, NewsData.class);
		System.out.println("Gson:" + data);
		MainActivity main = (MainActivity) mActivity;
		LeftMenuFragment leftMenuFragment = main.getLeftMenuFragment();
		leftMenuFragment.getMenuData(data);
		mMenuList = new ArrayList<BaseMenuPager>();
		mMenuList.add(new NewsMenuPager(mActivity, data.data.get(0).children));
		mMenuList.add(new InteractMenuPager(mActivity));
		mMenuList.add(new PhotoMenuPager(mActivity,ib_photo));
		mMenuList.add(new TopicMenuPager(mActivity));

		setCurrentMenuPager(0);
	}

	public void setCurrentMenuPager(int position) {
		BaseMenuPager pager = mMenuList.get(position);
		fl_content.removeAllViews();
		fl_content.addView(pager.mView);
		NewsMenuData data2 = data.data.get(position);// 设置每个侧边栏的选项的标题
		tv_title.setText(data2.title);
		pager.initData();
		if (pager instanceof PhotoMenuPager) {
			ib_photo.setVisibility(View.VISIBLE);
		} else {
			ib_photo.setVisibility(View.GONE);
		}

	};

	/**
	 * 初始化视图
	 */
	@Override
	public void intiView() {

		super.intiView();
	}

}
