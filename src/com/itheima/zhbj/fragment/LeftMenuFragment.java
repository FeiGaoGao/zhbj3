package com.itheima.zhbj.fragment;

import java.util.ArrayList;

import com.itheima.zhbj.MainActivity;
import com.itheima.zhbj.R;
import com.itheima.zhbj.base.impg.NewsContentPager;
import com.itheima.zhbj.domain.NewsData;
import com.itheima.zhbj.domain.NewsData.NewsMenuData;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class LeftMenuFragment extends BaseFragment {
	private ArrayList<NewsMenuData> mMenuList;
	@ViewInject(R.id.lv_Left_Menu)
	ListView lv_Left_Menu;
	public int onClickItem;
	private LeftMeunAdapter meunAdapter;

	@Override
	public View initView() {
		View view = View.inflate(getActivity(), R.layout.fragment_left_menu,
				null);
		ViewUtils.inject(this, view);
		return view;
	}

	@Override
	public void initData() {
		super.initData();
		lv_Left_Menu.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				onClickItem = arg2;
				meunAdapter.notifyDataSetChanged();
				setCurrentMenuPager(arg2);
				toggleSlidingMenu();
			}
		});
	}

	protected void toggleSlidingMenu() {
		MainActivity main = (MainActivity) mActivity;
		SlidingMenu slidingMenu = main.getSlidingMenu();
		slidingMenu.toggle();
	}

	protected void setCurrentMenuPager(int position) {
		MainActivity main = (MainActivity) mActivity;
		ContentFragment fragment = main.getContentMenuFragment();
		NewsContentPager pager = fragment.getNewsConterPager();
		pager.setCurrentMenuPager(position);
	}

	public void getMenuData(NewsData data) {
		// System.out.println("shuju"+ data);
		mMenuList = data.data;
		meunAdapter = new LeftMeunAdapter();
		lv_Left_Menu.setAdapter(meunAdapter);
	}

	class LeftMeunAdapter extends BaseAdapter {

		public int getCount() {
			return mMenuList.size();
		}

		public NewsMenuData getItem(int arg0) {
			return mMenuList.get(arg0);
		}

		public long getItemId(int arg0) {
			return arg0;
		}

		public View getView(int arg0, View arg1, ViewGroup arg2) {
			View view = View.inflate(mActivity, R.layout.item_left_menu, null);
			TextView textView1 = (TextView) view.findViewById(R.id.textView1);
			NewsMenuData data = getItem(arg0);
			textView1.setText(data.title);
			if (onClickItem == arg0) {
				textView1.setEnabled(true);
			} else {
				textView1.setEnabled(false);
			}
			return view;
		}

	}
}
