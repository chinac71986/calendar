package com.s.calendar;

/**
 * Created by suo on 2016/12/30.
 */
public class TagBean {

	private int clickTag;
	private DateTag dateTag;

	public int getClickTag() {
		return clickTag;
	}

	public void setClickTag(int clickTag) {
		this.clickTag = clickTag;
	}

	public DateTag getDateTag() {
		return dateTag;
	}

	public void setDateTag(DateTag dateTag) {
		this.dateTag = dateTag;
	}

	public class DateTag{

		private int year;
		private int month;
		private int day;

		public DateTag(int year, int month, int day) {
			this.year = year;
			this.month = month;
			this.day = day;
		}

		public int getYear() {
			return year;
		}

		public void setYear(int year) {
			this.year = year;
		}

		public int getMonth() {
			return month;
		}

		public void setMonth(int month) {
			this.month = month;
		}

		public int getDay() {
			return day;
		}

		public void setDay(int day) {
			this.day = day;
		}
	}

}
