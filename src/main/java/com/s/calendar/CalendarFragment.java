package com.s.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * Created by suo on 2016/12/27.
 * <p/>
 * caldroid类库没有完全满足需求，且过于复杂不便于后期调试 重新写了一个
 * 1、预加载三个 每次翻页加载下一项  比caldroid速度快
 * 2、各种 字体设置 沿用caldroid形式  写在style文件里
 * 3、实现了点击灰色自动翻页并选中。
 * 4、实现回调接口即可根据日期查询
 */
public class CalendarFragment extends Fragment implements View.OnClickListener, CalendarPage.GridViewOnItemClickListener {

	private ViewPager mViewPager;
	private ImageView left;
	private ImageView right;
	private TextView monthTV;

	private CalendarPagerAdapter mCaldroidPagerAdapter;

	private int index = CalendarPagerAdapter.pageSize / 2;
	private CalendarPage mCaldroidPage;

	private OnGridViewItemClickListener mOnGridViewItemClickListener;

	public static CalendarGridViewItem mView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.custom_calendar, container, false);

		initView(view);
		initData();
		initListener();

		return view;

	}

	private void initView(View view) {

		left = (ImageView) view.findViewById(R.id.caldroid_leftarrow);
		right = (ImageView) view.findViewById(R.id.caldroid_rightarrow);
		monthTV = (TextView) view.findViewById(R.id.caldroid_monthTV);

		mViewPager = (ViewPager) view.findViewById(R.id.caldroid_viewpager);

	}

	private void initListener() {

		left.setOnClickListener(this);
		right.setOnClickListener(this);

	}

	/**
	 * 初始化页面数据
	 */
	private void initData() {

		mCaldroidPage = new CalendarPage(getContext(), this);
		mCaldroidPagerAdapter = new CalendarPagerAdapter(getContext(), mCaldroidPage);

		mViewPager.setAdapter(mCaldroidPagerAdapter);
		mViewPager.setCurrentItem(CalendarPagerAdapter.pageSize / 2);  //dont`t remove
		mViewPager.addOnPageChangeListener(new CaldroidPagerAdapterListener());

		refreshMonthTV();

	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {

			case R.id.caldroid_leftarrow:
				mViewPager.setCurrentItem(index - 1);
				break;

			case R.id.caldroid_rightarrow:
				mViewPager.setCurrentItem(index + 1);
				break;

		}

	}

	private void refreshMonthTV() {

		int[] values = mCaldroidPagerAdapter.calculate(index);

		monthTV.setText(values[1] + "年" + values[0] + "月");

	}

	class CaldroidPagerAdapterListener implements ViewPager.OnPageChangeListener {

		@Override
		public void onPageSelected(int position) {

			index = position;
			refreshMonthTV();

		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}

	}

	/**
	 * @param view
	 * @param position
	 */
	@Override
	public void onSelected(CalendarGridViewItem view, int position) {

		if (view.equals(mView))
			return;

		GridViewItemClick(view);

		/**
		 *  选中gridView某个条目之后的回调
		 */
		TagBean.DateTag tag = ((TagBean) view.getTag()).getDateTag();
		if (mOnGridViewItemClickListener != null)
			mOnGridViewItemClickListener.initListViewData(tag);

	}

	/**
	 * 点击灰色条目进行前翻页后翻页，普通条目变换背景颜色。
	 *
	 * @param view
	 */
	private void GridViewItemClick(CalendarGridViewItem view) {

		TagBean tag = (TagBean) view.getTag();

		if (tag.getClickTag() == 2) {//前翻

			mViewPager.setCurrentItem(index - 1);
			GridViewItemClick(mCaldroidPage.searchView(tag.getDateTag()));

		} else if (tag.getClickTag() == 3) {//后翻

			mViewPager.setCurrentItem(index + 1);
			GridViewItemClick(mCaldroidPage.searchView(tag.getDateTag()));

		} else {//普通的点击变色

			if (mView == null) {

				view.setViewBackground(R.drawable.unit_calendar_date_cell_roundcoverbg);

			} else {

				if (((TagBean) mView.getTag()).getClickTag() == 1)//如果是当天
					mView.setViewBackground(R.drawable.unit_calendar_date_cell_roundbg);
				else
					mView.setViewBackground(R.drawable.unit_calendar_date_cell_bg);

				view.setViewBackground(R.drawable.unit_calendar_date_cell_roundcoverbg);

			}

			mView = view;

		}

	}

	public void setOnGridViewItemClickListener(OnGridViewItemClickListener onGridViewItemClickListener) {
		mOnGridViewItemClickListener = onGridViewItemClickListener;
	}

	public interface OnGridViewItemClickListener {
		public void initListViewData(TagBean.DateTag tag);
	}

	/**
	 * 销毁
	 */
	@Override
	public void onDestroy() {
		super.onDestroy();

		mCaldroidPage = null;
		mView = null;

	}

}
