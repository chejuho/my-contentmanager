package jp.co.gmod.manage.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String getTodayDate() {
		return sdf.format(new Date());
	}
}
