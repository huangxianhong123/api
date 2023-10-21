package com.liuc.server.api.util;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DateTable {

    private int year;
    private int month;

    private List<List<DayItem>> list;

    public DateTable(int y, int m) {
        year = y;
        month = m;
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

    public List<List<DayItem>> getList() {
        return list;
    }

    public void setList(List<List<DayItem>> list) {
        this.list = list;
    }

    /**
     * leapyear判断闰年方法
     */
    public boolean leapYear(int y) {
        if (y % 400 == 0 || ((y % 4 == 0) && (y % 100 != 0))) {
            return true;
        }
        return false;
    }

    /**
     * 计算某个月有多少日
     */
    int getDayOfMonth(int month) {
        /**判断month*/
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            /**二月需要判断是否为闰年，调用leapyear闰年方法*/
            case 2:
                if (leapYear(this.year))
                    return 29;
                else
                    return 28;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            default:
                return 0;
        }
    }

    /**
     * 计算1900至今有多少天，先算年，然后把没满今年的月份天数继续加上
     */
    public int getSumTo1900() {
        /**定义一个days整数来累加求和*/
        int days = 0;
        /**有多少年就循环多少天*/
        for (int i = 1900; i < this.year; i++) {
            /**判断闰年，如果是闰年就加366天，如果不是就加365天*/
            if (leapYear(i)) {
                days += 366;
            } else {
                days += 365;
            }
        }
        /**把今年的前几个月天数循环累加起来*/
        for (int i = 1; i < month; i++) {
            days += getDayOfMonth(i);
        }
        /**返回1900至今有多少天*/
        return days;
    }

    /**
     * 输出日历
     */
    public void genList() {
        list = new ArrayList<>();
        /**计数器，来控制换行*/
        int count = 1;
        List<DayItem> listInside = new ArrayList<>();
        /**定义days来存储本月有多少天*/
        int days = getDayOfMonth(this.month);
        /**根据1900至今来计算第一天前面的空格有多少个，存到space里面*/
        int spaces = getSumTo1900() % 7;
        for (int i = 0; i <= spaces; i++) {
            DayItem dayItem = new DayItem();
            dayItem.setAlt(true);
            listInside.add(dayItem);
            /**输出一次count++，逢7换行*/
            count++;
        }
        list.add(listInside);
        /**接着循环输出本月日期*/
        for (int i = 0; i < days; i++) {
            listInside.add(new DayItem(count % 7 == 1 || count % 7 == 0, i + 1, getDayTimestamp(i + 1), false, false));
            /**输出一次count++，逢7换行*/
            if (count % 7 == 0) {
                listInside = new ArrayList<>();
                list.add(listInside);
            }
            count++;
        }
    }

    public static class DayItem {
        private Boolean day;
        private Integer date;
        private Long timeStamp;
        private Boolean enable;
        private Boolean isAlt;

        public DayItem() {
        }

        public DayItem(Boolean day, Integer date, Long timeStamp, Boolean enable, Boolean isAlt) {
            this.day = day;
            this.date = date;
            this.timeStamp = timeStamp;
            this.enable = enable;
            this.isAlt = isAlt;
        }

        public Boolean getDay() {
            return day;
        }

        public void setDay(Boolean day) {
            this.day = day;
        }

        public Integer getDate() {
            return date;
        }

        public void setDate(Integer date) {
            this.date = date;
        }

        public Long getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(Long timeStamp) {
            this.timeStamp = timeStamp;
        }

        public Boolean getEnable() {
            return enable;
        }

        public void setEnable(Boolean enable) {
            this.enable = enable;
        }

        public Boolean getAlt() {
            return isAlt;
        }

        public void setAlt(Boolean alt) {
            isAlt = alt;
        }
    }

    private Long getDayTimestamp(int day) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = null;
        try {
            date = df.parse(year + "-" + month + "-" + day);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        long timestamp = cal.getTimeInMillis();
        return timestamp;
    }

    public static List<DateTable> genNextSixMonth() {
        List<DateTable> list = new ArrayList<>();
        Calendar date = Calendar.getInstance();
        int year = date.get(Calendar.YEAR);
        int month = date.get(Calendar.MONTH) + 1;
        for (int i = 0; i < 6; i++) {
            DateTable dateTable = new DateTable(year, month);
            dateTable.genList();
            list.add(dateTable);
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
        }
        return list;
    }

}
