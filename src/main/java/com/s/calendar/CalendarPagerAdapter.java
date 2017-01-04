package com.s.calendar;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.TimeZone;

import hirondelle.date4j.DateTime;


/**
 * Created by suo on 2016/12/28.
 */
public class CalendarPagerAdapter extends PagerAdapter {

    /**
     * 总大小1000  大小并不影响效率  只是规定前翻后翻的界限
     */
    public static final int pageSize = 1000;

    /**
     * 中间页面 以此为基点 前后可翻动 500
     */
    private int sign = pageSize / 2;

    private int year;
    private int month;

    /**
     * 世界的中心 爱与正义的化身
     */
    private CalendarPage mCaldroidPage;

    public CalendarPagerAdapter(Context context, CalendarPage caldroidPage) {

        mCaldroidPage = caldroidPage;

        initDate();

    }

    private void initDate() {

        DateTime dateTime = DateTime.today(TimeZone.getDefault());
        year = dateTime.getYear();
        month = dateTime.getMonth();

    }

    @Override
    public int getCount() {
        return pageSize;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = getView(position);

        view.setTag(position);

        container.addView(view);

        return view;

    }

    private View getView(int position) {

        int[] values = calculate(position);

        return mCaldroidPage.initView(position, values[0], values[1]);

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        container.removeView((View) object);
        mCaldroidPage.removeViewMap(position);
        object = null;  //置空  祝早日回收

    }

    /**
     * 根据计算和 sign 的差  求出当前应显示的年和月
     *
     * @param position
     * @return
     */
    public int[] calculate(int position) {

        int cache;
        int year_position;
        int month_position;
        if (position > sign) {

            year_position = (cache = (position - sign + month) / 12) + year;
            month_position = (position - sign + month) % 12;

            if (cache > 0 && month_position == 0) {
                year_position -= 1;
                month_position = 12;
            }

        } else if (position < sign) {

            year_position = year - (cache = (sign - position + (12 - month)) / 12);
            month_position = (month - (sign - position) + cache * 12) % 12;

            if (month_position == 0)
                month_position = 12;

        } else {

            year_position = year;
            month_position = month;

        }

        return new int[]{month_position, year_position};

    }

}
