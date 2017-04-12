package com.itheima.zhbj.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;


public class HorizontalViewPager extends ViewPager {

	public HorizontalViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public HorizontalViewPager(Context context) {
		super(context);
	}
	 @Override
	    public boolean dispatchTouchEvent(MotionEvent ev) {
		 if (getCurrentItem()== 0) {
			 getParent().requestDisallowInterceptTouchEvent(false);
		}else{
	    	getParent().requestDisallowInterceptTouchEvent(true);
		}
	    	return super.dispatchTouchEvent(ev);
	    }
}
