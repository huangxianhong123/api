package com.liuc.server.api.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtils {


    public static Date parseDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(date);
    }
    public static Date parseDate1(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(date);
    }

    public static Long longParseDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(date).getTime() /1000;
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String formatDate1(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String formatDate2(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");
        return sdf.format(date);
    }

    /*
     * 获取当前时间天数
     * */
    public static Date getTimeByDay(Integer day) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, day);//计算30天后的时间
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取本月第一天的开始时间
     */
    public static Date getStartMonth() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        Date theDate = calendar.getTime();
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        //设置为第一天
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        gcLast.set(Calendar.HOUR_OF_DAY, 0);
        gcLast.set(Calendar.MINUTE, 0);
        gcLast.set(Calendar.SECOND, 0);
        gcLast.set(Calendar.MILLISECOND, 0);
        //     String day_first=sf.format(gcLast.getTime());
        //打印本月第一天
        //     System.out.println(day_first);
        return gcLast.getTime();
    }

    /**
     * 获取本月的最后一天的结束时间
     */

    public static Date getEndMonth() {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取当前的开始时间
     *
     * @return
     */

    public static Date getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();

    }

    /**
     * 获取当前的结束时间
     *
     * @return
     */

    public static Date getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 获取昨天的开始时间
     *
     * @return
     */
    public static Date getBeginDayOfYesterday() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getStartTime());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取昨天的结束时间
     *
     * @return
     */

    public static Date getEndDayOfYesterDay() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getEndTime());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }


    /**
     * 日期转周几
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(String datetime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date parse = sdf.parse(datetime);

        return dateToWeek(parse);
    }

    /**
     * 日期转周几
     *
     * @param datetime
     * @return
     */
    public static int dateToWeek1(String datetime) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Date parse = sdf.parse(datetime);

        return dateToWeek1(parse);
    }

    /**
     * 日期转周几
     *
     * @param datetime
     * @return
     */
    public static String dateToWeek(Date datetime) {
        String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance(); // 获得一个日历

        cal.setTime(datetime);

        int w = cal.get(Calendar.DAY_OF_WEEK) - 1; // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    /**
     * 日期转周几
     *
     * @param datetime
     * @return
     */
    public static int dateToWeek1(Date datetime) {
        Calendar cal = Calendar.getInstance(); // 获得一个日历

        cal.setTime(datetime);

        int w = cal.get(Calendar.DAY_OF_WEEK); // 指示一个星期中的某天。
        if (w < 0)
            w = 0;
        return w;
    }


    /**
     * 获取前一小时的开始时间
     */

    public static Date getStartHour() {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        Calendar c = Calendar.getInstance();
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY) - 1);
        String startTime = date + " " + hour + ":00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data = null;
        try {
            data = sdf.parse(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获取前一小时的结束时间
     */

    public static Date getEndHour() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        Calendar c = Calendar.getInstance();
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY) - 1);
        String endTime = date + " " + hour + ":59:59";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date data = null;
        try {
            data = sdf.parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 获取上个月的开始时间
     *
     * @return
     */

    public static Date getStartLastMonth() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.MONTH, c.get(Calendar.MONTH) - 1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    /**
     * 获取上个月的结束时间
     *
     * @return
     */

    public static Date getEndLastMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 获取近七天的开始时间
     */

    public static Date geStartSevenDays() {

        //    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -7);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        //      Date monday = c.getTime();
        //       String preMonday = sdf.format(monday);
        return c.getTime();
    }

    /**
     * 获取近七天的结束时间
     *
     * @return
     */

    public static Date getEndSevenDays() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getEndTime());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取每个月的天数
     */
    public static int getDaysOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取前天开始时间
     *
     * @return
     */
    public static Date getStartTakeDay() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getBeginDayOfYesterday());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 获取前天结束时间
     *
     * @return
     */

    public static Date getEndTakeDay() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getEndDayOfYesterDay());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * 根据身份编号获取年龄
     *
     * @param birthDay yyyy-MM-dd
     * @return 年龄
     */
    public static int getAgeByDate(String birthDay) {
        String year = birthDay.substring(0, 4);
        Calendar cal = Calendar.getInstance();
        int iCurrYear = cal.get(Calendar.YEAR);
        int iAge = iCurrYear - Integer.valueOf(year);
        return iAge;
    }

    /**
     * 当前日期加上天数后的日期
     *
     * @param num 为增加的天数
     * @return
     */
    public static String DateAddDay(int num) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar ca = Calendar.getInstance();
        // num为增加的天数，可以改变的
        ca.add(Calendar.DATE, num);
        Date d = ca.getTime();
        String enddate = format.format(d);
        return enddate;
    }

    /*
    判读时间差距，两个时间相差多少天，时，分，秒
     */
    public static Long getDay(String date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long days = null;
        try {
            Date currentTime = dateFormat.parse(dateFormat.format(new Date()));//现在系统当前时间
            Date pastTime = dateFormat.parse(date);//过去时间
            long diff = currentTime.getTime() - pastTime.getTime();
            days = diff / (1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }


    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     * @return
     */
    public static int differentDaysByMillisecond(String date2) throws ParseException {
        date2 = date2+" 00:00:00";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        Date newDate = new Date();
        String date1 = format1.format(newDate)+" 00:00:00";
        int days = (int) ((format.parse(date2).getTime() - format.parse(date1).getTime()) / (1000*3600*24));
        return days;
    }


    /**
     * 计算时间
     * @param startTime ： 开始时间
     * @param endTime  ： 结束时间
     * @return
     */
    public static int DateSubOfDay(Date startTime,Date endTime) {

        Long l = 0L;

        long ts = startTime.getTime();
        long ts1 = endTime.getTime();

        l = (ts - ts1) / (1000 * 60 * 60 * 24);

        return l.intValue();
    }


    /**
     * 计算 时间 + 几天  或者 - 几天
     * @param date
     * @param dayNum
     * @return
     */
    public static Date getTimeByDay(Date date,int dayNum) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, dayNum);//+1今天的时间加一天
        date = calendar.getTime();
        return date;
    }

    /**
     * 返回今天是周几,星期天等于1,星期一等于2.....星期六等于7
     * @return
     */
    public static int getWeekDay(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        return day;
    }

    /**
     * 获取两个时间中的每一天,不包括结束时间
     * @param bigtimeStr 开始时间 yyyy-MM-dd
     * @param endTimeStr 结束时间 yyyy-MM-dd
     * @return
     * @throws ParseException
     */
    public static List<String> getFormatDays(String bigtimeStr, String endTimeStr) throws ParseException {
        Date bigtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(bigtimeStr + " 00:00:00");
        Date endtime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(endTimeStr + " 00:00:00");
        //定义一个接受时间的集合
        List<Date> lDate = new ArrayList<>();
        lDate.add(bigtime);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(bigtime);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(endtime);
        // 测试此日期是否在指定日期之后
        while (endtime.after(calBegin.getTime()))  {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        List<String> datas = new LinkedList<>();
        for (Date date : lDate) {
            datas.add(new SimpleDateFormat("yyyy-MM-dd").format(date));
        }
        datas.remove(datas.size()-1);
        return datas;
    }

    public static String DateAddMonth(int num) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        Calendar ca = Calendar.getInstance();
        // num为增加的天数，可以改变的
        ca.add(Calendar.MONTH, num);
        Date d = ca.getTime();
        String enddate = format.format(d);
        return enddate;
    }

    /**
     * 获取每个月份
     * @param bigtimeStr 开始时间 yyyy-MM
     * @param endTimeStr 结束时间 yyyy-MM
     * @return
     * @throws ParseException
     */
    public static List<String> getFormatMonth(String bigtimeStr, String endTimeStr) throws ParseException {
        Date bigtime = new SimpleDateFormat("yyyy-MM").parse(bigtimeStr + " 00:00:00");
        Date endtime = new SimpleDateFormat("yyyy-MM").parse(endTimeStr + " 00:00:00");
        //定义一个接受时间的集合
        List<Date> lDate = new ArrayList<>();
        lDate.add(bigtime);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(bigtime);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(endtime);
        // 测试此日期是否在指定日期之后
        while (endtime.after(calBegin.getTime()))  {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.MONTH, 1);
            lDate.add(calBegin.getTime());
        }
        List<String> datas = new LinkedList<>();
        for (Date date : lDate) {
            datas.add(new SimpleDateFormat("yyyy-MM").format(date));
        }
        return datas;
    }
}
