package com.s.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import hirondelle.date4j.DateTime;

public class CalendarGridAdapter extends BaseAdapter {

	private ArrayList<DateTime> datetimeList;
	private ArrayList<View> viewList;

	private int month;
	private int year;
	private Context mContext;

	private DateTime today;

	private LayoutInflater localInflater;

	private int cellViewResoure;
	private int darkText;
	private int blackText;

	/**
	 * Constructor
	 *
	 * @param context
	 * @param month
	 * @param year
	 */
	public CalendarGridAdapter(Context context, int month, int year) {

		super();
		this.mContext = context;
		this.month = month;
		this.year = year;

		// Get data
		initData();

	}

	/**
	 * Retrieve internal parameters
	 */
	private void initData() {

		darkText = mContext.getResources().getColor(android.R.color.darker_gray);
		blackText = mContext.getResources().getColor(android.R.color.black);

		localInflater = (LayoutInflater) mContext
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		datetimeList = CalendarHelper.getFullWeeks(this.month, this.year, 1, false);

		viewList = new ArrayList<>();

		/**
		 *   无论是显示五行还是六行 占用的空间都一样。
		 */
		if (datetimeList.size() > 35)
			cellViewResoure = R.layout.custom_calendar_itemtext45;
		else
			cellViewResoure = R.layout.custom_calendar_itemtext54;

	}

	/**
	 * 12点过后，南瓜车就会消失，调用此方法，魔法继续。
	 */
	public void updateToday() {
		today = CalendarHelper.convertDateToDateTime(new Date());
	}

	protected DateTime getToday() {

		if (today == null) {
			today = CalendarHelper.convertDateToDateTime(new Date());
		}
		return today;

	}

	@Override
	public int getCount() {
		return this.datetimeList.size();
	}

	@Override
	public Object getItem(int position) {
		return datetimeList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		convertView = new CalendarGridViewItem(mContext, cellViewResoure);

		//写成这样  只是为了点击的时候圆圈能圆一点
		customizeTextView(position, (CalendarGridViewItem) convertView);

		setImagetViewBackground((CalendarGridViewItem) convertView);

		viewList.add(convertView);

		return convertView;

	}

	/**
	 * @param position
	 */
	protected void customizeTextView(int position, CalendarGridViewItem item) {

		// Get dateTime of this text
		DateTime dateTime = this.datetimeList.get(position);

		TagBean tag = new TagBean();
		tag.setClickTag(0);

		if (dateTime.equals(getToday())) {

			item.setViewBackground(R.drawable.unit_calendar_date_cell_roundbg);
			tag.setClickTag(1);

		}

		// Set color of the dates in previous / next month
		if (dateTime.getMonth() != month) {

			item.setViewTextColor(darkText);

			if (dateTime.getMonth() < month) {

				if (dateTime.getYear() > year)
					tag.setClickTag(3);
				else
					tag.setClickTag(2);

			}

			if (dateTime.getMonth() > month) {

				if (dateTime.getYear() < year)
					tag.setClickTag(2);
				else
					tag.setClickTag(3);

			}

		}

		/**
		 *   这段代码的添加
		 *      选中某一个日期后背景颜色变化、
		 *      在未选中其它日期时，它的背景颜色始终如一
		 *      （******即便原view被销毁重建了，依然爱你******）
		 */
		if (CalendarFragment.mView != null) {

			TagBean viewTag = (TagBean) CalendarFragment.mView.getTag();
			TagBean.DateTag dateTag = viewTag.getDateTag();
			if (dateTag.getDay() == dateTime.getDay()
					&& tag.getClickTag() == 0
					&& dateTag.getMonth() == dateTime.getMonth()
					&& dateTag.getYear() == dateTime.getYear()
					) {
				item.setViewBackground(R.drawable.unit_calendar_date_cell_roundcoverbg);
				CalendarFragment.mView = item;
			}

		}

		tag.setDateTag(tag.new DateTag(dateTime.getYear(), dateTime.getMonth(), dateTime.getDay()));

		item.setTag(tag);

		item.setViewText(String.valueOf(dateTime.getDay()));

	}

	/**
	 * CalendarGridAdapter 查询数据库后更新状态
	 *
	 * @param view
	 */
	private void setImagetViewBackground(CalendarGridViewItem view) {

		int dateState = new Random().nextInt(4);

		view.setViewState(dateState);

	}

	public ArrayList<View> getViewList() {

		return viewList;

	}

}
