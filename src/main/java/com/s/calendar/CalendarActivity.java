package com.s.calendar;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

/**
 *  日历demo
 */
public class CalendarActivity extends FragmentActivity {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);
		LogUtils.i("begining");
		initView();

	}

	private void initView() {

		CalendarFragment caldroidFragment = new CalendarFragment();

		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.heihei, caldroidFragment);
		t.commit();

	}


}



