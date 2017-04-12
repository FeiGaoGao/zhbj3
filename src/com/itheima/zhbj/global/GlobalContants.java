package com.itheima.zhbj.global;

/**
 * 获取网络的接口URL
 * 
 * @author sunzhaung
 * 
 */
public class GlobalContants {
	public static final String SERVER_URL = "http://192.168.56.1:8080/zhbj";

	public static final String CATEGORIES_URL = SERVER_URL + "/categories.json";// 获取分类信息的接口
	public static final String PHOTOS_URL = SERVER_URL
			+ "/photos/photos_1.json";// 获取组图信息的接口
}
