package com.itheima.zhbj;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.RelativeLayout;

public class SplashActivity extends Activity {

	private RelativeLayout ll_splash;
	private Button btn_guide;

	// boolean isGuide = sp.getBoolean("is_guide", false);
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		ll_splash = (RelativeLayout) findViewById(R.id.ll_splash);
		btn_guide = (Button) findViewById(R.id.btn_guide);
		initView();

	}

	private void initView() {
		// 设置集合动画
		AnimationSet set = new AnimationSet(false);
		// 旋转动画
		RotateAnimation rotate = new RotateAnimation(0, 360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		rotate.setDuration(1000); // 动画时长
		rotate.setFillAfter(true);// 保持动画时间

		// 缩放动画
		ScaleAnimation scale = new ScaleAnimation(0, 1, 0, 1,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		scale.setDuration(1000);
		scale.setFillAfter(true);

		// 渐变动画
		AlphaAnimation alpha = new AlphaAnimation(0, 1);
		scale.setDuration(1000);
		scale.setFillAfter(true);

		set.addAnimation(scale);
		set.addAnimation(rotate);
		set.addAnimation(alpha);
		// 跳转页面的监听 也是动画的监听
		set.setAnimationListener(new AnimationListener() {

			private SharedPreferences sp;

			public void onAnimationStart(Animation animation) {

			}

			public void onAnimationRepeat(Animation animation) {

			}

			public void onAnimationEnd(Animation animation) {
				sp = getSharedPreferences("config", MODE_PRIVATE);
				boolean isGuide = sp.getBoolean("is_guide", false);
				if (!isGuide) {
					startActivity(new Intent(SplashActivity.this,
							GuideActivity.class));
				} else {
					startActivity(new Intent(SplashActivity.this,
							MainActivity.class));
				}
				finish();
			}
		});
		ll_splash.startAnimation(set);

	}

}
