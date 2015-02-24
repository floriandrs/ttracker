package com.slart.ttracker.util;

import java.text.Format;
import java.util.Calendar;
import java.util.GregorianCalendar;

import android.content.Context;
import android.widget.Toast;

public class Util {
	
	public static void toast(Context context, String s) {
		Toast toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	public static Format getSystemDateFormat(Context context) {
		return android.text.format.DateFormat.getDateFormat(context);
	}

	public static Format getSystemTimeFormat(Context context) {
		return android.text.format.DateFormat.getTimeFormat(context);
	}
	
	public static String formatDateFromEpoch(Context context, Long epoch) {
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(epoch);
		return formatDate(context, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
	}

	public static String formatTimeFromEpoch(Context context, Long epoch) {
		Calendar cal = new GregorianCalendar();
		cal.setTimeInMillis(epoch);
		return formatTime(context, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
	}
	
	public static Calendar buildDate(int year, int month, int day) {
		 return new GregorianCalendar(year, month, day);
	}
	
	public static long buildDateEpoch(int year, int month, int day) {
		return buildDate(year, month, day).getTime().getTime();
	}

	public static long buildTimeEpoch(int hour, int minute) {
		 return hour*60*60*1000 + minute*60*1000;
	}

	public static String formatDate(Context context, int year, int month, int day) {
		return getSystemDateFormat(context).format(buildDate(year, month, day).getTime());
	}
	
	public static String formatTime(Context context, int hour, int minute) {
		Calendar dateTime = new GregorianCalendar();
		dateTime.set(1970, 0, 1, hour, minute);
		return getSystemTimeFormat(context).format(dateTime.getTime());
	}
	
	public static Long addEpochs(Long e1, Long e2) {
		Long result = 0L;
		if (e1!=null) {
			result += e1;
		}
		if (e2!=null) {
			result += e2;
		}
		return result;
	}
	
}
