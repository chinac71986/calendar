package com.s.calendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import hirondelle.date4j.DateTime;

/**
 * Convenient helper to work with date, Date4J DateTime and String
 *  2016 12 20  copy
 *
 * @author thomasdao
 */
public class CalendarHelper {

	private static SimpleDateFormat yyyyMMddFormat;

	public static void setup() {
		yyyyMMddFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
	}

	/**
	 * 此处查询数据库
	 *
	 * @param month
	 * @param year
	 * @param startDayOfWeek :  can start from customized date instead of Sunday
	 * @return
	 */
	public static ArrayList<DateTime> getFullWeeks(int month, int year,
	                                               int startDayOfWeek, boolean sixWeeksInCalendar) {

		ArrayList<DateTime> datetimeList = new ArrayList<>();

		//本月的第一天
		DateTime firstDateOfMonth = new DateTime(year, month, 1, 0, 0, 0, 0);

		//本月的最后一天
		DateTime lastDateOfMonth = firstDateOfMonth.plusDays(firstDateOfMonth
				.getNumDaysInMonth() - 1);

		// 本月从日历的第几天开始
		int weekdayOfFirstDate = firstDateOfMonth.getWeekDay();

		// If weekdayOfFirstDate smaller than startDayOfWeek
		// For e.g: weekdayFirstDate is Monday, startDayOfWeek is Tuesday
		// increase the weekday of FirstDate because it's in the future
		if (weekdayOfFirstDate < startDayOfWeek) {
			weekdayOfFirstDate += 7;
		}

		//画第一行
		while (weekdayOfFirstDate > 0) {
			DateTime dateTime = firstDateOfMonth.minusDays(weekdayOfFirstDate
					- startDayOfWeek);
			if (!dateTime.lt(firstDateOfMonth)) {
				break;
			}

			datetimeList.add(dateTime);
			weekdayOfFirstDate--;
		}

		// 画本月的所有的日历
		for (int i = 0; i < lastDateOfMonth.getDay(); i++) {

			/**
			   新加的判断 永远只显示五行
			 *//*
			if(datetimeList.size() > 34)
				return datetimeList;*/
			datetimeList.add(firstDateOfMonth.plusDays(i));

		}

		// Add dates of last week from next month
		int endDayOfWeek = startDayOfWeek - 1;

		if (endDayOfWeek == 0) {
			endDayOfWeek = 7;
		}

		if (lastDateOfMonth.getWeekDay() != endDayOfWeek) {
			int i = 1;
			while (true) {
				DateTime nextDay = lastDateOfMonth.plusDays(i);
				datetimeList.add(nextDay);
				i++;
				if (nextDay.getWeekDay() == endDayOfWeek) {
					break;
				}
			}
		}

		// Add more weeks to fill remaining rows
		if (sixWeeksInCalendar) {
			int size = datetimeList.size();
			int row = size / 7;
			int numOfDays = (6 - row) * 7;
			DateTime lastDateTime = datetimeList.get(size - 1);
			for (int i = 1; i <= numOfDays; i++) {
				DateTime nextDateTime = lastDateTime.plusDays(i);
				datetimeList.add(nextDateTime);
			}
		}

		return datetimeList;

	}

	/**
	 * Get the DateTime from Date, with hour and min is 0
	 *
	 * @param date
	 * @return
	 */
	public static DateTime convertDateToDateTime(Date date) {
		// Get year, javaMonth, date
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTime(date);

		int year = calendar.get(Calendar.YEAR);
		int javaMonth = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DATE);

		// javaMonth start at 0. Need to plus 1 to get datetimeMonth
		return new DateTime(year, javaMonth + 1, day, 0, 0, 0, 0);
	}

	public static Date convertDateTimeToDate(DateTime dateTime) {
		int year = dateTime.getYear();
		int datetimeMonth = dateTime.getMonth();
		int day = dateTime.getDay();

		Calendar calendar = Calendar.getInstance();
		calendar.clear();

		// datetimeMonth start at 1. Need to minus 1 to get javaMonth
		calendar.set(year, datetimeMonth - 1, day);

		return calendar.getTime();
	}

	/**
	 * Get the Date from String with custom format. Default format is yyyy-MM-dd
	 *
	 * @param dateString
	 * @param dateFormat
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateFromString(String dateString, String dateFormat)
			throws ParseException {
		SimpleDateFormat formatter;
		if (dateFormat == null) {
			if (yyyyMMddFormat == null) {
				setup();
			}

			formatter = yyyyMMddFormat;
		} else {
			formatter = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
		}

		return formatter.parse(dateString);
	}

	/**
	 * Get the DateTime from String with custom format. Default format is
	 * yyyy-MM-dd
	 *
	 * @param dateString
	 * @param dateFormat
	 * @return
	 */
	public static DateTime getDateTimeFromString(String dateString,
	                                             String dateFormat) {
		Date date;
		try {
			date = getDateFromString(dateString, dateFormat);
			return convertDateToDateTime(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static ArrayList<String> convertToStringList(
			ArrayList<DateTime> dateTimes) {
		ArrayList<String> list = new ArrayList<String>();
		for (DateTime dateTime : dateTimes) {
			list.add(dateTime.format("YYYY-MM-DD"));
		}
		return list;
	}

}
