package cn.ichano.common.util;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.DateUtils;
import org.apache.log4j.Logger;

/**
 * 时间工具类
 * 
 * @author wenjp
 *
 */
public class TimeUtil {

	private static final Logger LOGGER = Logger.getLogger(TimeUtil.class);

	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	public static final String YYYYMMDD = "yyyyMMdd";

	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	
	public static final String YYYYMM = "yyyyMM";
	
	public static final String YMD = "yyyy-MM-dd";
	public static final String YMDHMSM = "yyyy-MM-dd HH:mm:ss:ms";
	public static final String YMDHMS = "yyyy-MM-dd HH:mm:ss";

	private TimeUtil() {
	}

	public static SimpleDateFormat getDateFormat(String dateType) {
		return new SimpleDateFormat(dateType);
	}

	/**
	 * 获取指定日期、指定格式化的字符串
	 * 
	 * @param date
	 * @param dateType
	 * @return
	 */
	public static String getTimeString(Date date, String dateType) {
		SimpleDateFormat dateFormat = getDateFormat(dateType);
		String dataStr = null;
		try {
			dataStr = dateFormat.format(date);
		} catch (Exception e) {
			LOGGER.error("日期转换失败", e);
		}
		return dataStr;
	}

	/**
	 * 将当前时间转换成指定格式
	 * 
	 * @param dateType
	 * @return
	 */
	public static String getCurrentTimeString(String dateType) {
		Calendar c = Calendar.getInstance();
		Date date = c.getTime();
		return getTimeString(date, dateType);
	}

	/**
	 * 查询指定date的当天起始时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateStart(Date date) {
		// c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR)-1);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date dateZero = c.getTime();
		return dateZero;
	}

	/**
	 * 查询指定date的当天结束时间
	 * 
	 * @param date
	 * @return
	 */
	public static Date getDateEnd(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		Date dateZero = c.getTime();
		return dateZero;
	}

	public static String formatRFC1036(Date date) {
		return DateUtils.formatDate(date, DateUtils.PATTERN_RFC1036);
	}
	
	
	public static String getPreMonth(String month,String dataType){
		SimpleDateFormat format = getDateFormat(dataType);
		
		Date date = new Date();
		try {
			date = format.parse(month);
		} catch (ParseException e) {

		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) -1);
		Date d = c.getTime();
		return getTimeString(d,YYYYMM);
	}
	
	/**
	 * 下一个月字符串
	 * 
	 * @param month
	 * @param dataType
	 * @return
	 */
	public static String getNextMonth(String month, String dataType) {
		SimpleDateFormat format = getDateFormat(dataType);

		Date date = new Date();
		try {
			date = format.parse(month);
		} catch (ParseException e) {

		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.MONTH, c.get(Calendar.MONTH) + 1);
		Date d = c.getTime();
		return getTimeString(d, YYYYMM);
	}

	/**
	 * 判断是否当前月份
	 * 
	 * @param month
	 * @param dataType
	 * @return
	 */
	public static boolean isCurrentMonth(String month, String dataType) {

		String currentMonth = getCurrentTimeString(YYYYMM);
		if (currentMonth.equals(month)) {
			return true;
		}
		return false;
	}

	/**
	 * 是否之前月份
	 * 
	 * @param month
	 * @param dataType
	 * @return
	 */
	public static boolean isPreMonth(String month, String dataType) {
		String currentMonth = getCurrentTimeString(dataType);
		if (month.compareTo(currentMonth) < 0) {
			return true;
		}
		return false;
	}

	/**
	 * 获取指定月份最大的天数
	 * 
	 * @param month
	 * @param dataType
	 * @return
	 */
	public static int getMaxDayOfMonth(String month, String dataType) {
		SimpleDateFormat format = getDateFormat(dataType);
		String nextMonth = getNextMonth(month, dataType);

		Date date = null;
		try {
			date = format.parse(nextMonth);
		} catch (ParseException e) {

		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - 1);
		return c.get(Calendar.DAY_OF_MONTH);
		// return getTimeString(d, YYYYMM);
	}

	public static boolean isToday(String day) {
		return isToday(day, YYYYMMDD);
	}

	public static boolean isToday(String day, String dataType) {
		return day.equals(getCurrentTimeString(dataType));

	}
	
	public static boolean isYesterday(String day,String dateType) {
		Date date = getYesterday();
		String lastDay = TimeUtil.getTimeString(date, dateType);
		return lastDay.equals(day);
	}

	public static Date getYesterday() {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_YEAR, c.get(Calendar.DAY_OF_YEAR) - 1);
		Date date = c.getTime();
		return date;
	}

	/** 
	* @Title: getNextDay 
	* @Description: TODO(获得下一天  整点 无时分秒) 
	* @param @param date
	* @param @return    设定文件 
	* @return Date    返回类型 
	* @throws 
	*/
	public static Date getNextDay(Date date) {
		String dateType = YMD;
		return getNextDay(date, dateType);
	}
	
	public static Date getInvalidtime(Date date){
		final SimpleDateFormat formatter = new SimpleDateFormat(YMD);
		try {
			date = formatter.parse(formatter.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return new Date(org.apache.commons.lang3.time.DateUtils.addDays(date, 2).getTime()-1000);
	}

	/**
	 * 获得下一个日期
	 * 
	 * @param date
	 * @param dateType
	 * @return
	 */
	public static Date getNextDay(Date date, String dateType) {

		final SimpleDateFormat formatter = new SimpleDateFormat(dateType);
		try {
			date = formatter.parse(formatter.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return org.apache.commons.lang3.time.DateUtils.addDays(date, 1);
	}
	
	public static Date getToDay(Date date) {
		final SimpleDateFormat formatter = new SimpleDateFormat(YMD);
		try {
			date = formatter.parse(formatter.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date str2date(String strdate,String format){
		if(null==strdate||"".equals(strdate)){
			return null;
		}
		if(null==format||"".equals(format)){
			format = YMDHMS;
		}
		final SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = new Date();
		try {
			date = formatter.parse(strdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date str2date(String strdate){
		if(null==strdate||"".equals(strdate)){
			return null;
		}
		String format = "";
		if(strdate.length()==YMD.length()&&strdate.contains("-")){
			format = YMD;
		}else if(strdate.length()==YMDHMS.length()&&strdate.contains("-")){
			format = YMDHMS;
		}else if(strdate.length()==YMDHMSM.length()&&strdate.contains("-")){
			format = YMDHMSM;
		}else if(strdate.length()==YYYYMMDD.length()){
			format = YYYYMMDD;
		}else if(strdate.length()==YYYYMM.length()){
			format = YYYYMM;
		}else if(strdate.length()==YYYYMMDDHHMMSS.length()){
			format = YYYYMMDDHHMMSS;
		}
		if(StringUtils.isEmpty(format)){
			return null;
		}
		final SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = formatter.parse(strdate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

}
