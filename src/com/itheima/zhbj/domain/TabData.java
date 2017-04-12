package com.itheima.zhbj.domain;

import java.util.ArrayList;

/**
 * 从Json中获取的数据封装此处
 * 
 * @author sunzhaung
 * 
 */
public class TabData {
	public int retcode;
	public TabDetail data;

	public class TabDetail {
		public String title;
		public String more;
		public ArrayList<TabNews> news;
		public ArrayList<TabTopNews> topnews;

		@Override
		public String toString() {
			return "TabDetail [title=" + title + "]";
		}

	}

	public class TabNews {
		public String id;
		public String listimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;

		@Override
		public String toString() {
			return "TabNews [title=" + title + "]";
		}

	}

	public class TabTopNews {
		public String id;
		public String topimage;
		public String pubdate;
		public String title;
		public String type;
		public String url;

		@Override
		public String toString() {
			return "TabTopNews [title=" + title + "]";
		}

	}

	@Override
	public String toString() {
		return "TabData [data=" + data + "]";
	}

}
