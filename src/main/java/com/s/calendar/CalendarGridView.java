package com.s.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

/**
 * Created by Administrator on 2017-1-2.
 */
public class CalendarGridView extends GridView {

    private GridView mGridView;

    public CalendarGridView(Context context) {
        this(context,null);
    }

    public CalendarGridView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CalendarGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
//        initView(12,2016);
    }

    private GridViewOnItemClickListener mGridViewOnItemClickListener;

    private void initView(int month,int year) {

        View view = View.inflate(getContext(), R.layout.custom_calendar_gridview, this);

        mGridView = (GridView) view.findViewById(R.id.caldroid_gridview);
        mGridView.setAdapter(getNewDatesGridAdapter(month, year));

        initListener();

    }

    private void initListener() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (mGridViewOnItemClickListener != null)
                    mGridViewOnItemClickListener.onSelected(view, position);

            }

        });
    }

    public CalendarGridAdapter getNewDatesGridAdapter(int month, int year) {
        return new CalendarGridAdapter(getContext(), month, year);
    }

    public interface GridViewOnItemClickListener {
        public void onSelected(View view, int position);
    }
}
