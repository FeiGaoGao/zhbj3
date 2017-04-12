package com.itheima.zhbj;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.TextSize;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class NewsDetailActivity extends Activity implements OnClickListener {
	private WebView wv_News;
	private ImageView ib_Back;
	private ImageView ib_Share;
	private ImageView ib_TextSize;
	private ProgressBar pb_Progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_newsdetail);
		wv_News = (WebView) findViewById(R.id.wv_news);
		ib_Back = (ImageView) findViewById(R.id.ib_back);
		ib_Share = (ImageView) findViewById(R.id.ib_share);
		ib_TextSize = (ImageView) findViewById(R.id.ib_textsize);
		pb_Progress = (ProgressBar) findViewById(R.id.pb_progress);
		String url = getIntent().getStringExtra("url");
		ib_Back.setOnClickListener(this);
		ib_Share.setOnClickListener(this);
		ib_TextSize.setOnClickListener(this);
		WebSettings set = wv_News.getSettings();
		set.setJavaScriptEnabled(true);// 支持js

		wv_News.loadUrl(url);// 加载网页
		wv_News.setWebViewClient(new WebViewClient() {
			/**
			 * 网页加载
			 */
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				pb_Progress.setVisibility(View.VISIBLE);
			}

			/**
			 * 网页结束加载
			 */
			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				pb_Progress.setVisibility(View.GONE);
			}

			/**
			 * 所有网页的跳转都在此应用中
			 */
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_back:
			finish();
			break;
		case R.id.ib_textsize:
			showChooseDialog();
			break;
		case R.id.ib_share:
              Toast.makeText(getApplicationContext(), "暂未实现", 0).show();
			break;
		default:
			break;
		}
	}

	/**
	 * 选择字体的大小
	 */
	private int mCurrentChooseItem;// 记录当前选中的item, 点击确定前
	int checkedItem = 2;

	private void showChooseDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String[] items = new String[] { "超大号字体", "大号字体", "正常字体", "小号字体",
				"超小号字体" };
		builder.setTitle("字体设置");
		builder.setSingleChoiceItems(items, checkedItem,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						mCurrentChooseItem = which;
					}
				});
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				WebSettings set = wv_News.getSettings();
				switch (mCurrentChooseItem) {
				case 0:
					set.setTextSize(TextSize.LARGEST);
					break;
				case 1:
					set.setTextSize(TextSize.LARGER);
					break;
				case 2:
					set.setTextSize(TextSize.NORMAL);
					break;
				case 3:
					set.setTextSize(TextSize.SMALLER);
					break;
				case 4:
					set.setTextSize(TextSize.SMALLEST);
					break;

				default:
					break;
				}
				//记录选择项
				checkedItem=mCurrentChooseItem;
			}
		});
		builder.setNegativeButton("取消", null);
		builder.show();
	}
}
