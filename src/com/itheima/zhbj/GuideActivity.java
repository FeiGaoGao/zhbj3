package com.itheima.zhbj;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class GuideActivity extends Activity {
	private ViewPager vp_guide;
	private static final int[] imageGroup = new int[] { R.drawable.guide_1,
			R.drawable.guide_2, R.drawable.guide_3 };
	ArrayList<ImageView> mList = new ArrayList<ImageView>();
	private ImageView image;
	private Button btn_guide;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题
		setContentView(R.layout.activity_guide);

		btn_guide = (Button) findViewById(R.id.btn_guide);
		vp_guide = (ViewPager) findViewById(R.id.vp_guide);

		vp_guide.setAdapter(new GuideAdapter());
		vp_guide.setOnPageChangeListener(new GuidePageListener());
		initView();
		btn_guide.setOnClickListener(new OnClickListener() {

			private SharedPreferences sp;

			public void onClick(View v) {
				sp = getSharedPreferences("config", MODE_PRIVATE);
				sp.edit().putBoolean("is_guide", true).commit();
				startActivity(new Intent(GuideActivity.this, MainActivity.class));
			}
		});
	}

	class GuidePageListener implements OnPageChangeListener {

		public void onPageScrollStateChanged(int state) {

		}

		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {

		}

		public void onPageSelected(int position) {
			if (position == imageGroup.length - 1) {
				btn_guide.setVisibility(View.VISIBLE);
			} else {
				btn_guide.setVisibility(View.INVISIBLE);
			}
		}

	}

	private void initView() {
		for (int i = 0; i < imageGroup.length; i++) {
			image = new ImageView(this);
			image.setBackgroundResource(imageGroup[i]);
			mList.add(image);
		}
	}

	class GuideAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return imageGroup.length;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mList.get(position));
			return mList.get(position);
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

	}
}
