package com.s.calendar;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.HashMap;


/**
 * Created by suo on 2016/12/28.
 */
public class CalendarPage {

	private Context mContext;

	private GridViewOnItemClickListener mGridViewOnItemClickListener;
	private GridView mGridView;

	private HashMap<Integer,ArrayList<View>> viewMap;

	/**
	 * single
	 */
	public CalendarPage(Context context, GridViewOnItemClickListener gridViewOnItemClickListener) {

		this.mContext = context;
		this.mGridViewOnItemClickListener = gridViewOnItemClickListener;

	}

	public View initView(int position,int month, int year) {

		mGridView = (GridView) View.inflate(mContext, R.layout.custom_calendar_gridview, null);

		CalendarGridAdapter newDatesGridAdapter = getNewDatesGridAdapter(month, year);

		mGridView.setAdapter(newDatesGridAdapter);

		if(viewMap == null)
			viewMap = new HashMap<>();
		viewMap.put(position,newDatesGridAdapter.getViewList());

		initListener();

		return mGridView;

	}

	private void initListener() {

		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				if (mGridViewOnItemClickListener != null)
					mGridViewOnItemClickListener.onSelected((CalendarGridViewItem)view, position);

			}

		});

	}

	public CalendarGridViewItem searchView(TagBean.DateTag date){

		for(int key:viewMap.keySet()){

			ArrayList<View> views = viewMap.get(key);

			for (int j = 0; j < views.size(); j++) {

				View view = views.get(j);
				TagBean tag = (TagBean) view.getTag();

				if(tag.getDateTag().getDay() == date.getDay()
						&&tag.getClickTag() == 0
						&&tag.getDateTag().getMonth() == date.getMonth()
						&&tag.getDateTag().getYear() == date.getYear()
						){
					return (CalendarGridViewItem)view;
				}

			}

		}

		return null;

	}

	public void removeViewMap(int position){

		viewMap.remove(position);

	}

	/**
	 * 初始化本页数据
	 */
	public void initData() {

	}

	public CalendarGridAdapter getNewDatesGridAdapter(int month, int year) {
		return new CalendarGridAdapter(mContext, month, year);
	}

	public interface GridViewOnItemClickListener {
		public void onSelected(CalendarGridViewItem view, int position);
	}

}
