package algorithms.utils;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;

/**
 * Collection of utils for basic Date manipulation
 * @author Francois Fournier
 *
 */
public abstract class DateUtils {
	public static final String DATE_FORMAT_INTERNATIONAL_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_FILE_DEFAULT = "yyyy-MM-dd-HHmmss";
	

	/**
	 * Now date and time with the international format
	 * @return the system date at the time of calling the method.
	 */
	public static String now() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_INTERNATIONAL_DEFAULT);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * Now date and time with a format compatible with files names, as defined by DateUtils.DATE_FORMAT_FILE_DEFAULT
	 * @return the system date at the time of calling the method.
	 */
	public static String nowDateForFile() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_FILE_DEFAULT);
		return sdf.format(cal.getTime());
	}

	/**
	 * Now date and time with the specified format as in SimpleDateFormat
	 * @param dateFormat
	 * @return the system date at the time of calling the method.
	 * @see SimpleDateFormat
	 */
	public static String now(String dateFormat) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(cal.getTime());
	}
	
	/**
	 * The provided date and time formated with the international format
	 * @param dateTime
	 * @return date and time at international format
	 */
	public static String time(Date dateTime) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_INTERNATIONAL_DEFAULT);
		return sdf.format(dateTime);
	}
	
	/**
	 * The provided date and time with the specified format as in SimpleDateFormat
	 * @param dateTime
	 * @param dateFormat
	 * @return date and time at provided format
	 */
	public static String time(Date dateTime, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(dateTime);
	}

	/**
	 * @param arg
	 * test method
	 */
	/*public static void  main(String arg[]) {
		System.out.println("Now : " + DateUtils.now());
		System.out.println(DateUtils.now("dd MMMMM yyyy"));
		System.out.println(DateUtils.now("yyyyMMdd"));
		System.out.println(DateUtils.now("dd.MM.yy"));
		System.out.println(DateUtils.now("MM/dd/yy"));
		System.out.println(DateUtils.now("yyyy.MM.dd G 'at' hh:mm:ss z"));
		System.out.println(DateUtils.now("EEE, MMM d, ''yy"));
		System.out.println(DateUtils.now("h:mm a"));
		System.out.println(DateUtils.now("H:mm:ss:SSS"));
		System.out.println(DateUtils.now("K:mm a,z"));
		System.out.println(DateUtils.now("yyyy.MMMMM.dd GGG hh:mm aaa"));
	}*/
}