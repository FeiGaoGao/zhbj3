package com.itheima.zhbj.base;

import java.util.ArrayList;

import org.w3c.dom.Text;

import com.google.gson.Gson;
import com.itheima.zhbj.R;
import com.itheima.zhbj.Utils.CacheUtils;
import com.itheima.zhbj.domain.TabData;
import com.itheima.zhbj.domain.NewsData.NewsTabData;
import com.itheima.zhbj.domain.TabData.TabNews;
import com.itheima.zhbj.domain.TabData.TabTopNews;
import com.itheima.zhbj.global.GlobalContants;
import com.itheima.zhbj.view.RefrashListView;
import com.itheima.zhbj.view.RefrashListView.OnRefalshListener;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

public class NewsTabPager extends BaseMenuPager implements OnPageChangeListener {

	private NewsTabData mData;
	String Url;
	private TabData fromJson;
	@ViewInject(R.id.vp_topNews)
	ViewPager vp_TopNews;

	@ViewInject(R.id.tv_topnews_title)
	TextView tv_Topnews_Title;
	@ViewInject(R.id.lv_list)
	RefrashListView lv_List;
	public ArrayList<TabNews> newsList;
	private CirclePageIndicator indicator;
	private String moreNews;
	private ListNewsAdapter newsAdapter;
	SharedPreferences sp = mActivity.getSharedPreferences("config",
			Context.MODE_PRIVATE);
	private Handler mHandler;
	private ArrayList<TabTopNews> topnews;

	public NewsTabPager(Activity activity, NewsTabData newsTabData) {
		super(activity);
		mData = newsTabData;
		Url = GlobalContants.SERVER_URL + mData.url;
		initData();
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.tab_detail_pager, null);
		ViewUtils.inject(this, view);
		View headView = View.inflate(mActivity, R.layout.list_head_view, null);
		ViewUtils.inject(this, headView);
		// 添加一个头布局
		lv_List.addHeaderView(headView);
		indicator = (CirclePageIndicator) view.findViewById(R.id.indicator);

		// 自定义设置刷新和加载跟多的监听    通过写回调方法
		lv_List.setOnRefalshListener(new OnRefalshListener() {

			public void onRefresh() {
				getDataFormService();
			}

			public void onLoadingMore() {
				if (moreNews != null) {
					// 加载更多
					getMoreFormService();
				} else {
					Toast.makeText(mActivity, "最后了", 0).show();
					lv_List.onRefalshFinish(false);// 收起加载更多
				}
			}
		});
		return view;
	}

	@Override
	public void initData() {
		// text.setText(mData.title);
		String cache = CacheUtils.getCache(Url, mActivity);
		if (!TextUtils.isEmpty(cache)) {
			parseData(cache, false);
		}
		getDataFormService();

	}

	/**
	 * 从服务器获取更多数据
	 */
	private void getMoreFormService() {
		HttpUtils xutils = new HttpUtils();
		xutils.send(HttpMethod.GET, moreNews, new RequestParams(),
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(mActivity, "请求失败", 0).show();
						arg0.printStackTrace();
						lv_List.onRefalshFinish(false);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = (String) responseInfo.result;
						System.out.println("详情页结果:" + result);
						parseData(result, true);
						lv_List.onRefalshFinish(true);
					}
				});
	}

	/**
	 * 从服务器获取数据
	 */
	private void getDataFormService() {
		HttpUtils xutils = new HttpUtils();
		xutils.send(HttpMethod.GET, Url, new RequestParams(),
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						Toast.makeText(mActivity, "请求失败", 0).show();
						arg0.printStackTrace();
						lv_List.onRefalshFinish(false);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = (String) responseInfo.result;
						System.out.println("详情页结果:" + result);
						parseData(result, false);
						lv_List.onRefalshFinish(true);
						CacheUtils.setCache(Url, result, mActivity);
					}
				});
	}

	/**
	 * 从服务器获取数据
	 * 
	 * @param result
	 */
	public void parseData(String result, boolean isMore) {
		Gson gson = new Gson();
		fromJson = gson.fromJson(result, TabData.class);
		// System.out.println("fromJson" + fromJson);

		// 处理下一个列表新闻的url
		String moreList = fromJson.data.more;
		if (!TextUtils.isEmpty(moreList)) {
			moreNews = GlobalContants.SERVER_URL + moreList;
		} else {
			moreNews = null;
		}
		// 没有更多新闻
		if (!isMore) {
			newsList = fromJson.data.news;
			if (fromJson != null) {
				topnews = fromJson.data.topnews;
				//
				vp_TopNews.setAdapter(new NewsTopAdapter());
				// 易忘点
				indicator.setOnPageChangeListener(this);
				indicator.onPageSelected(0);// 让指示器重新定位到第一个点}

				indicator.setViewPager(vp_TopNews);
				String topTitle = fromJson.data.topnews.get(0).title;

				tv_Topnews_Title.setText(topTitle);
			}
			if (newsList != null) {
				// 新闻列表的适配器
				newsAdapter = new ListNewsAdapter();
				lv_List.setAdapter(newsAdapter);
			}
			// 自动轮播条显示
			if (mHandler == null) {
				mHandler = new Handler() {
					public void handleMessage(android.os.Message msg) {
						int currentItem = vp_TopNews.getCurrentItem();

						if (currentItem < topnews.size() - 1) {
							currentItem++;
						} else {
							currentItem = 0;
						}

						vp_TopNews.setCurrentItem(currentItem);// 切换到下一个页面
						mHandler.sendEmptyMessageDelayed(0, 3000);// 继续延时3秒发消息,
																	// 形成循环
					};
				};

				mHandler.sendEmptyMessageDelayed(0, 3000);// 延时3秒后发消息
			}
			// 有更多新闻
		} else {
			ArrayList<TabNews> arrayList = fromJson.data.news;
			newsList.addAll(arrayList);
			newsAdapter.notifyDataSetChanged();

		}
		/**
		 * 新闻被点击
		 */
		lv_List.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				//System.out.println("被点击的" + position);
				String ids = sp.getString("ids", "");
				String readId = newsList.get(position).id;
				if (!ids.contains(readId)) {
					ids = ids + readId + ",";
					sp.edit().putString("ids", ids);
				}
				// 改变已读新闻的颜色
				changeReadState(view);
				// 跳转新闻详情页
				Intent intent = new Intent();
				intent.setClass(mActivity,
						com.itheima.zhbj.NewsDetailActivity.class);
				intent.putExtra("url", newsList.get(position).url);
				mActivity.startActivity(intent);
			}

		});

	}

	/**
	 * 改变已读新闻的颜色
	 */
	private void changeReadState(View view) {
		TextView tvTitle = (TextView) view.findViewById(R.id.tv_title);
		tvTitle.setTextColor(Color.GRAY);
	}

	/**
	 * 设置新闻列表的适配器
	 * 
	 * @author sunzhaung
	 * 
	 */
	class ListNewsAdapter extends BaseAdapter {
		private BitmapUtils utils;

		public ListNewsAdapter() {
			utils = new BitmapUtils(mActivity);
			utils.configDefaultLoadingImage(R.drawable.pic_item_list_default);// 设置加载中的图片
		}

		public int getCount() {
			return newsList.size();
		}

		public TabNews getItem(int arg0) {
			return newsList.get(arg0);
		}

		public long getItemId(int arg0) {
			return arg0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(mActivity, R.layout.list_news_item,
						null);
				holder = new ViewHolder();
				holder.ivPic = (ImageView) convertView
						.findViewById(R.id.iv_pic);
				holder.tvTitle = (TextView) convertView
						.findViewById(R.id.tv_title);
				holder.tvDate = (TextView) convertView
						.findViewById(R.id.tv_date);

				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			TabNews item = getItem(position);

			holder.tvTitle.setText(item.title);
			holder.tvDate.setText(item.pubdate);

			utils.display(holder.ivPic, item.listimage);
			String ids = sp.getString("ids", "");
			// String readId = newsList.get(position).id;
			// System.out.println("ids"+readId);
			if (ids.contains(item.id)) {
				holder.tvTitle.setTextColor(Color.GRAY);
			} else {
				holder.tvTitle.setTextColor(Color.BLACK);
			}
			return convertView;
		}

	}

	static class ViewHolder {
		public TextView tvTitle;
		public TextView tvDate;
		public ImageView ivPic;
	}

	/**
	 * 头条新闻适配器
	 * 
	 * @author Kevin
	 * 
	 */
	class NewsTopAdapter extends PagerAdapter {
		private BitmapUtils utils;

		public NewsTopAdapter() {
			utils = new BitmapUtils(mActivity);
			utils.configDefaultLoadingImage(R.drawable.topnews_item_default);
		}

		@Override
		public int getCount() {
			return fromJson.data.topnews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {

			ImageView image = new ImageView(mActivity);

			image.setBackgroundResource(R.drawable.topnews_item_default);

			image.setScaleType(ScaleType.FIT_XY);

			utils.display(image, fromJson.data.topnews.get(position).topimage);// 将网络接受的图片传递过来

			container.addView(image);

			image.setOnTouchListener(new TopNewsTouchListener());

			return image;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}
	}

	/**
	 * OnPageChangeListener 切换页面的监听
	 */
	public void onPageScrollStateChanged(int arg0) {
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	public void onPageSelected(int arg0) {
		TabTopNews topTitle = fromJson.data.topnews.get(arg0);
		// System.out.println(topTitle);
		tv_Topnews_Title.setText(topTitle.title);
	}

	/**
	 * 头条新闻的触摸监听
	 * 
	 * @author
	 * 
	 */
	class TopNewsTouchListener implements OnTouchListener {

		public boolean onTouch(View v, MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				System.out.println("按下");
				mHandler.removeCallbacksAndMessages(null);// 删除Handler中的所有消息

				break;
			case MotionEvent.ACTION_CANCEL:
				
				System.out.println("事件取消");
				mHandler.sendEmptyMessageDelayed(0, 3000);
				break;
			case MotionEvent.ACTION_UP:
				
				System.out.println("抬起");
				mHandler.sendEmptyMessageDelayed(0, 3000);
				break;

			default:
				break;
			}

			return true;
		}

	}
}
