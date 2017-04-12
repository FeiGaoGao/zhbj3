package com.itheima.zhbj.base.menupager;

import java.util.ArrayList;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.itheima.zhbj.R;
import com.itheima.zhbj.Utils.CacheUtils;
import com.itheima.zhbj.base.BaseMenuPager;
import com.itheima.zhbj.domain.PhotosData;
import com.itheima.zhbj.domain.PhotosData.PhotoInfo;
import com.itheima.zhbj.global.GlobalContants;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

public class PhotoMenuPager extends BaseMenuPager {

	private ListView lvPhoto;
	private GridView gvPhoto;

	private ArrayList<PhotoInfo> news;
	private photoAdapter adapter;
	ImageButton btnPhoto;

	public PhotoMenuPager(Activity activity, ImageButton ib_photo) {
		super(activity);
		btnPhoto = ib_photo;
	}

	@Override
	public View initView() {
		View view = View.inflate(mActivity, R.layout.menu_photo_pager, null);
		lvPhoto = (ListView) view.findViewById(R.id.lv_photo);
		gvPhoto = (GridView) view.findViewById(R.id.gv_photo);

		return view;
	}

	@Override
	public void initData() {
		String cache = CacheUtils
				.getCache(GlobalContants.PHOTOS_URL, mActivity);
		if (!TextUtils.isEmpty(cache)) {

		} else {
			getDataService();
		}
		btnPhoto.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				changeDisplay();
			}
		});
	}

	private void getDataService() {
		HttpUtils xUtils = new HttpUtils();
		xUtils.send(HttpMethod.GET, GlobalContants.PHOTOS_URL,
				new RequestCallBack<String>() {
					// 失败
					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT)
								.show();
						error.printStackTrace();
					}

					// 成功
					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						String result = (String) responseInfo.result;
						// System.out.println("结果:" + result);
						parseData(result);
						CacheUtils.setCache(GlobalContants.PHOTOS_URL, result,
								mActivity);
					}
				});
	}

	protected void parseData(String result) {
		Gson gson = new Gson();
		PhotosData data = gson.fromJson(result, PhotosData.class);
		news = data.data.news;
		if (news != null) {
			adapter = new photoAdapter();
			lvPhoto.setAdapter(adapter);
			gvPhoto.setAdapter(adapter);
		}
	}

	/**
	 * 获得图片适配器
	 * 
	 * @author sunzhaung
	 * 
	 */
	class photoAdapter extends BaseAdapter {

		private BitmapUtils utils;

		public photoAdapter() {
			utils = new BitmapUtils(mActivity);
			utils.configDefaultLoadingImage(R.drawable.news_pic_default);
		}

		public int getCount() {
			return news.size();
		}

		public PhotoInfo getItem(int position) {
			return news.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (convertView == null) {
				convertView = View.inflate(mActivity,
						R.layout.item_news_photos, null);
				holder = new ViewHolder();
				holder.ivPhoto = (ImageView) convertView
						.findViewById(R.id.imageView1);
				holder.tvTitle = (TextView) convertView
						.findViewById(R.id.textView1);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			PhotoInfo item = getItem(position);
			utils.display(holder.ivPhoto, item.listimage);
			holder.tvTitle.setText(item.title);
			return convertView;
		}

	}

	static class ViewHolder {
		public ImageView ivPhoto;
		public TextView tvTitle;
	}
	
	private boolean isListDisplay = true;// 是否是列表展示

	/**
	 * 切换展现方式
	 */
	private void changeDisplay() {
		if (isListDisplay) {
			isListDisplay = false;
			lvPhoto.setVisibility(View.GONE);
			gvPhoto.setVisibility(View.VISIBLE);

			btnPhoto.setImageResource(R.drawable.icon_pic_list_type);

		} else {
			isListDisplay = true;
			lvPhoto.setVisibility(View.VISIBLE);
			gvPhoto.setVisibility(View.GONE);
			btnPhoto.setImageResource(R.drawable.icon_pic_grid_type);
		}
	}
}
