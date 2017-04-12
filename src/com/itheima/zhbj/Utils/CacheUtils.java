package com.itheima.zhbj.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class CacheUtils {
	

	/**
	 * 设置缓存
	 * 
	 * @param Key
	 * @param Value
	 * @param activity
	 */
	public static void setCache(String Key, String Value, Activity activity) {
		SharedPreferences sp = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
		sp.edit().putString(Key, Value);
	}

	/**
	 * 得到缓存
	 * 
	 * @param Key
	 * @param Value
	 * @param activity
	 * @return 
	 */
	public static  String getCache(String Key,Activity activity) {
		SharedPreferences sp = activity.getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getString(Key, null);
	}
}
