package com.s.calendar;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by suo on 2017/01/04.
 */
public class CalendarGridViewItem extends RelativeLayout {


	private FrameLayout mFrameLayout;

	private TextView tv;
	private ImageView iv;

	public CalendarGridViewItem(Context context, int resource) {

		super(context);

		initView( resource);

	}

	private void initView( int resource ) {

		View view =  View.inflate(getContext(),resource,this);

		mFrameLayout = (FrameLayout) view.findViewById(R.id.calendar_itemcolor);

		tv = (TextView) view.findViewById(R.id.calendar_tv);

		iv = (ImageView) view.findViewById(R.id.calendar_iv);

	}

	public void setViewBackground(int resource){
		mFrameLayout.setBackgroundResource(resource);
	}

	public void setViewText(String text){
		tv.setText(text);
	}

	public void setViewTextColor(int resource){
		tv.setTextColor(resource);
	}

	public void setViewState(int dateState){

		if (dateState == 2)
			iv.setImageResource(R.mipmap.roundpromptthree2x);
		else if (dateState == 3)
			iv.setImageResource(R.mipmap.roundpromptfour2x);
		else
			iv.setImageResource(android.R.color.transparent);

	}




}
