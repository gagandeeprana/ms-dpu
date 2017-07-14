package com.dpu.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
 
	public static Date changeStringToDate(String dateVal) {
		String[] stArr = dateVal.split("-");
		Calendar cal = Calendar.getInstance();
		cal.set(Integer.parseInt(stArr[0]), Integer.parseInt(stArr[1]), Integer.parseInt(stArr[2]));
		Date date = cal.getTime();
		return date;
	}
}
