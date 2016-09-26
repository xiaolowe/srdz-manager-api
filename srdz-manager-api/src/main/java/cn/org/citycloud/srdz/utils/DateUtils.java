package cn.org.citycloud.srdz.utils;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * 日期处理工具类.
 *
 * @author demon
 * @Date 2016/4/21 11:32
 */
public class DateUtils {
    public static final String DATE_PATTERN_1 = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_PATTERN_2 = "yyyy-MM-dd HH:mm";

    public static final String DATE_PATTERN_3 = "yyyy-MM-dd";

    /**
     * 根据日期格式获取Date
     *
     * @param date 日期参数
     * @param pattern 日期格式
     * @return
     */
    public static Date parse(String date, String pattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date resultDate = null;
        try {
            resultDate = simpleDateFormat.parse(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return resultDate;
    }

    /**
     * 获取今天+‘00：00：00’的时间
     *
     * @return
     */
    public static Date getDayStartTime() {
        Calendar todayStart = new GregorianCalendar();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 获取今天+‘23：59：59’的时间
     *
     * @return
     */
    public static Date getDayEndTime() {
        Calendar todayEnd = new GregorianCalendar();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 给定日期与当前日期相差的天数
     *
     * @param date
     * @return
     */
    public static int dayDiffFromToday(Date date) {
        DateTime now = DateTime.now();
        DateTime param = new DateTime(date);
        return Days.daysBetween(param, now).getDays();
    }

    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = "2016-06-11";
        System.out.println(dayDiffFromToday(format.parse(date)));
    }
}
