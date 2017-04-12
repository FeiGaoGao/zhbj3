package com.itheima.zhbj.view;

import java.text.SimpleDateFormat;
import java.util.Date;
import com.itheima.zhbj.R;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;

import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class RefrashListView extends ListView implements
		android.widget.AdapterView.OnItemClickListener {
	private static final int STATE_PULL_REFRESH = 0;// 下拉刷新
	private static final int STATE_RELEASE_REFRESH = 1;// 松开刷新
	private static final int STATE_REFRESHING = 2;// 正在刷新

	private int mCurrrentState = STATE_PULL_REFRESH;// 当前状态
	private View view;
	private int startY = -1;// 令startY=-1
	private int endY;
	private int measuredHeight;
	private TextView tvTitle;
	private TextView tvTime;
	private ImageView ivArrow;
	private ProgressBar pbProgress;
	private RotateAnimation animUp;
	private RotateAnimation animDown;
	public boolean isLoadingMore;

	public RefrashListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initHeadView();
		initFooterView();
	}

	public RefrashListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initHeadView();
		initFooterView();
	}

	public RefrashListView(Context context) {
		super(context);
		initHeadView();
		initFooterView();
	}

	private void initHeadView() {
		view = View.inflate(getContext(), R.layout.refresh_header, null);
		tvTitle = (TextView) view.findViewById(R.id.tv_title);
		tvTime = (TextView) view.findViewById(R.id.tv_time);
		ivArrow = (ImageView) view.findViewById(R.id.iv_arr);
		pbProgress = (ProgressBar) view.findViewById(R.id.pb_progress);
		this.addHeaderView(view);
		view.measure(0, 0);
		measuredHeight = view.getMeasuredHeight();
		initArrowAnim();
		view.setPadding(0, -measuredHeight, 0, 0);
		tvTime.setText(getCurrentTime());
	}

	private void initFooterView() {
		footerInflate = View.inflate(getContext(),
				R.layout.refresh_listview_footer, null);
		this.addFooterView(footerInflate);
		footerInflate.measure(0, 0);
		mFooterViewHeight = footerInflate.getMeasuredHeight();
		footerInflate.setPadding(0, -mFooterViewHeight, 0, 0);// 隐藏

		this.setOnScrollListener(new OnScrollListener() {

			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}

			// ListView 向下滑动 加载更多的监听
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (scrollState == SCROLL_STATE_IDLE
						|| scrollState == SCROLL_STATE_FLING) {
					if (getLastVisiblePosition() == getCount() - 1
							&& !isLoadingMore) {
						System.out.println("到底了");
						footerInflate.setPadding(0, 0, 0, 0);// 显示
						setSelection(getCount() - 1);
						isLoadingMore = true;
						mlistener.onLoadingMore();
					}
				}
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = (int) ev.getRawY();
			break;
		case MotionEvent.ACTION_MOVE:
			if (startY == -1) { // 如果 startY == -1 就重新调一遍startY = (int)
								// ev.getRawY();
				startY = (int) ev.getRawY();
			}
			if (mCurrrentState == STATE_REFRESHING) {// 正在刷新
				break;
			}
			endY = (int) ev.getRawY();
			int dy = endY - startY;// 移动便宜量
			if (dy > 0 && getFirstVisiblePosition() == 0) {// 只有下拉并且当前是第一个item,才允许下拉
				int padding = dy - measuredHeight;
				view.setPadding(0, padding, 0, 0);
				if (padding > 0 && mCurrrentState != STATE_RELEASE_REFRESH) {
					mCurrrentState = STATE_RELEASE_REFRESH; // 松开刷新
					refreshState();
				} else if (padding < 0 && mCurrrentState != STATE_PULL_REFRESH) {
					mCurrrentState = STATE_PULL_REFRESH; // 下拉刷新
				}
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
			startY = -1;
			if (mCurrrentState == STATE_RELEASE_REFRESH) {// 松开刷新
				mCurrrentState = STATE_REFRESHING;
				view.setPadding(0, 0, 0, 0); // 回到padding等于0时
				refreshState();
			} else if (mCurrrentState == STATE_PULL_REFRESH) {// 下拉刷新
				view.setPadding(0, -measuredHeight, 0, 0);// 隐藏
			}
			break;
		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 下拉刷新的处理
	 */
	private void refreshState() {
		switch (mCurrrentState) {
		case STATE_PULL_REFRESH: // 下拉刷新
			tvTitle.setText("下拉刷新");
			ivArrow.setVisibility(View.VISIBLE);
			pbProgress.setVisibility(View.INVISIBLE);
			ivArrow.startAnimation(animDown);

			break;
		case STATE_RELEASE_REFRESH:// 松开刷新
			tvTitle.setText("松开刷新");
			ivArrow.setVisibility(View.VISIBLE);
			pbProgress.setVisibility(View.INVISIBLE);
			ivArrow.startAnimation(animUp);
			break;
		case STATE_REFRESHING:// 正在刷新
			tvTitle.setText("正在刷新");
			ivArrow.clearAnimation(); // 必须先清除动画才能隐藏
			ivArrow.setVisibility(View.INVISIBLE);
			pbProgress.setVisibility(View.VISIBLE);
			mlistener.onRefresh();
			break;

		default:
			break;
		}
	}

	/**
	 * 初始化箭头动画
	 */
	private void initArrowAnim() {
		animUp = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		animUp.setDuration(200);
		animUp.setFillAfter(true);

		animDown = new RotateAnimation(-180, 0, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animDown.setDuration(200);
		animDown.setFillAfter(true);

	}

	/**
	 * 自定义数据的监听
	 */
	 public OnRefalshListener mlistener;
	private View footerInflate;
	private int mFooterViewHeight;

	public void setOnRefalshListener(OnRefalshListener listener) {
		mlistener = listener;
	}

	public interface OnRefalshListener {
		public void onRefresh();

		public void onLoadingMore();
	}

	/**
	 * 收起下拉刷新
	 */
	public void onRefalshFinish(boolean success) {
		if (isLoadingMore) {
			footerInflate.setPadding(0, -mFooterViewHeight, 0, 0);// 显示脚部局
			isLoadingMore = false;
		} else {
			mCurrrentState = STATE_PULL_REFRESH;
			tvTitle.setText("下拉刷新");
			ivArrow.setVisibility(View.VISIBLE);
			pbProgress.setVisibility(View.INVISIBLE);
			view.setPadding(0, -measuredHeight, 0, 0);
			tvTime.setText(getCurrentTime());
		}
	}

	/**
	 * 获取当前时间
	 */
	public String getCurrentTime() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	/**
	 * 改变被点击条目的位置 的回调
	 */
	public OnItemClickListener mListener;

	public void setOnItemClickListener(
			android.widget.AdapterView.OnItemClickListener listener) {
		super.setOnItemClickListener(this);
		mListener = listener;
	}

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		if (mListener != null) {
			mListener.onItemClick(parent, view, position
					- getHeaderViewsCount(), id);
		}

	}
}
