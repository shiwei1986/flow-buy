package com.senld.gzlt.flowBuy.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @Author wusw
 * @Date 2020年6月10日
 * @Description:日期工具类
 * @Version: 1.0
 *
 */
public class DateUtils {
	private static final String DEFAULT_PATTER = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 使用指定格式生产时间字符串
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String format(Long time, String pattern) {
		Date date = new Date(time);
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	/**
	 * 使用默认格式生产时间字符串
	 * 
	 * @param time
	 * @return
	 */
	public static String format(Long time) {
		Date date = new Date(time);
		SimpleDateFormat df = new SimpleDateFormat(DEFAULT_PATTER);
		return df.format(date);
	}

	public static Date parse(String time) {
		try {
			SimpleDateFormat df = new SimpleDateFormat(DEFAULT_PATTER);
			Date date = df.parse(time);
			return date;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date beforeHourToGiveDate(String date, int hours) {
		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTER);
		try {
			c.setTime(sdf.parse(date));
			c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - hours);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c.getTime();
	}

	public static Date beforeHourToNowDate(int hours) {
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(new Date());
			c.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY) - hours);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return c.getTime();
	}
}
