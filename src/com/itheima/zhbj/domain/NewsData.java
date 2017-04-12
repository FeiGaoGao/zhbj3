package com.itheima.zhbj.domain;

import java.util.ArrayList;
/**
 * 从Json中获取的数据封装此处
 * @author sunzhaung
 *
 */
public class NewsData {
	public int retcode;
	public ArrayList<NewsMenuData> data;

	public class NewsMenuData {
		public String id;
		public String title;
		public int type;
		public ArrayList<NewsTabData> children;

		@Override
		public String toString() {
			return "NewsMenuData [title=" + title + ", children=" + children + "]";
		}

	}

	public class NewsTabData {
		public String id;
		public String title;
		public int type;
		public String url;

		@Override
		public String toString() {
			return "NewsTabData [type=" + type + "]";
		}
	
	}
	@Override
	public String toString() {
		return "NewsData [data=" + data + "]";
	}

}
