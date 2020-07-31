package com.jess.arms.utils;

import android.text.format.Time;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Locale;

/**
 * ===============================================
 */
public class DateUtils {
    /* =====Date方法 next:0为本-1为上====== */
//日期格式
    public static final String FORMART_DAY = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMART_YMD_CN = "yyyy年MM月dd日 HH:mm:ss";
    public static final String FORMART_YMD_EN = "yyyy-MM-dd HH:mm:ss";
    public static final String FORMART_MD_EN = "MM-dd HH:mm:ss";
    public static final String FORMART_YMD_HM = "yyyy-MM-dd HH:mm";
    public static final String FORMART_HM = "HH:mm";
    public static final String FORMART_YMD = "yyyy-MM-dd";
    public static final String FORMART_YM = "yyyy-MM";
    public static final String FORMART_MD = "MM-dd";
    public static double NONE_DATE_VALUE = -1; //没有数据时候的默认值

    public static String getAllDay(long day) {
        // 定义日期格式
        SimpleDateFormat matter = new SimpleDateFormat(FORMART_DAY);
        Date date = new Date(day);
        return matter.format(date);
    }

    public static CharSequence getFormatSecond(long updateTime, String format) {
        // 定义日期格式
        SimpleDateFormat matter = new SimpleDateFormat(format);
        Date date = new Date(updateTime);
        return matter.format(date);
    }

    public static String getFormatDay(String day, String style) {
        // 定义日期格式
        SimpleDateFormat matter = new SimpleDateFormat(style);
        try {
            Date date = matter.parse(day);
            return matter.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return day;
        }
    }

    /**
     * @param day
     * @return 获取消息的时间格式
     */
    public static String getMsgDate(String day) {
        SimpleDateFormat matter = new SimpleDateFormat(FORMART_DAY);
        SimpleDateFormat matterCn = new SimpleDateFormat(FORMART_YMD_CN);
        SimpleDateFormat matterCnMD = new SimpleDateFormat("MM月dd日");
        SimpleDateFormat matterHM = new SimpleDateFormat("HH:mm");
        try {
            Date date = matter.parse(day);
            Calendar c1 = Calendar.getInstance();
            c1.setTime(date);
            int year1 = c1.get(Calendar.YEAR);
            int month1 = c1.get(Calendar.MONTH) + 1;
            int day1 = c1.get(Calendar.DAY_OF_MONTH);
            Calendar c2 = Calendar.getInstance();
            c2.setTime(new Date());
            int year2 = c2.get(Calendar.YEAR);
            int month2 = c2.get(Calendar.MONTH) + 1;
            int day2 = c2.get(Calendar.DAY_OF_MONTH);
            if (year1 == year2) {

                if (year1 == year2 && month1 == month2 && day1 == day2) {
                    return matterHM.format(date);
                }

                return matterCnMD.format(date);
            }

            return matterCn.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * @desc 字符串转Date
     */
    public static Date str2Date(String dateStr) {
        SimpleDateFormat matter = new SimpleDateFormat(FORMART_DAY);
        try {
            Date date = matter.parse(dateStr);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @NonNull
    public static String getYearMon(String date) {
        StringBuilder sb = new StringBuilder(date);
        return sb.substring(0, 7).toString();
    }

    @NonNull
    public static String getYMD(String date) {
        StringBuilder sb = new StringBuilder(date);
        return sb.substring(0, 10).toString();
    }

    public static String getTime(String date) {
        StringBuilder sb = new StringBuilder(date);
        return sb.substring(8, 19).toString();
    }

    /**
     * @throws Exception
     * @功能：获取时间 hh:mm
     * @param：
     * @return：
     */
    public static String getTimeTwo(long day) {
        // 定义日期格式
        SimpleDateFormat matter = new SimpleDateFormat("HH:mm");
        Date date = new Date(day);
        return matter.format(date);
    }

    /**
     * @throws Exception
     * @功能：计算相差的天数
     * @param：
     * @return：
     */
    public static long daysOfTwo(long date1, long date2) {

        Date smdate = new Date(date1);
        Date bdate = new Date(date2);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time1 - time2) / (1000 * 3600 * 24);

        return between_days;

    }

    // 获取本月的第一天        next==提前多少年
    public static String getMonFirst(int next) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, next);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return format.format(c.getTime());
    }

    // 获取本月的第一天        next==提前多少年
    public static String getMonFirst(int next, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, next);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return format.format(c.getTime());
    }

    // 获取本月的第一天        next==提前多少年
    public static String getMonFirstCutZero(int next, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, next);
        c.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        String date = format.format(c.getTime());
        return date.split("-")[0] + "年" + Integer.valueOf(date.split("-")[1]) + "月";
    }

    // 获取本年的第一天
    public static String getYeasFirst(int next) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_YMD);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, next);
        c.add(Calendar.MONTH, -c.get(Calendar.MONTH));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return format.format(c.getTime());
    }

    // 获取本年的第一天
    public static String getYeasFirst(int next, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, next);
        c.add(Calendar.MONTH, -c.get(Calendar.MONTH));
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
        return format.format(c.getTime());
    }

    // 获取本年的最后一天
    public static String getYeasLast(int next) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_YMD);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, next);
        c.add(Calendar.MONTH, 11 - c.get(Calendar.MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.DAY_OF_MONTH, 31);// 设置为1号,当前日期既为本月第一天
        return format.format(c.getTime());
    }


    //获取日信息
    public static String getDayAmont(String str) {
        Calendar calendar = Calendar.getInstance(); //得到日历
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return "--";
        }
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
    }

    public static String getLastDayOfMonth(String date) {
        String[] split = date.split("-");
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(split[0]));
        cal.set(Calendar.MONTH, Integer.valueOf(split[1]) - 1);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    //获取mon信息
    public static String getMonAmont(String str) {
        Calendar calendar = Calendar.getInstance(); //得到日历
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return "未知";
        }
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.MONTH) + 1);
    }

    //获取mon信息
    public static String getyearAmont(String str) {
        Calendar calendar = Calendar.getInstance(); //得到日历
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return "未知";
        }
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    //next=0获取今日   next=提前的月数 next=1获取提取1个月的时间
    public static String getToDayByMon(int next) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, next);
        return format.format(c.getTime());

    }

    //获取昨日     next=提前的月数
    public static String getYesTodayDayAmont(String str) {
        Calendar calendar = Calendar.getInstance(); //得到日历
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Date date = null;
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return "--";
        }
        calendar.setTime(date);
        return String.valueOf(calendar.get(Calendar.DAY_OF_MONTH) - 1);
    }

    //获取今日最后时间点      next=提前的月数
    public static String getToDayByMonLast(int next) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, next);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 0);
        return format.format(c.getTime());

    }

    //获取今日  00:00开始      next=提前的月数
    public static String getToDayByMonFirst(int next) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, next);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return format.format(calendar.getTime());

    }

    //获取今日  00:00开始      next=提前的月数
    public static String getToDayByMonFirst(int next, String style) {
        SimpleDateFormat format = new SimpleDateFormat(style);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, next);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return format.format(calendar.getTime());

    }

    //获取今日      next=提前的月数
    public static String getToDayByYear(int next) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR, next);
        return format.format(c.getTime());

    }

    //获取前/后一日
    public static String getBeforeDay(String day, int next) {
        Calendar calendar = Calendar.getInstance(); //得到日历
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Date date = null;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
            return "未知";
        }
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, next);
        return format.format(calendar.getTime());

    }

    //获取上一月最后一日
    public static String getBeforeDayLast(String day, int next) {
        Calendar calendar = Calendar.getInstance(); //得到日历
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Date date = null;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
            return "未知";
        }
        calendar.setTime(date);
        if (next != 0) {
            calendar.add(Calendar.DAY_OF_MONTH, next);
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        }
        return format.format(calendar.getTime());

    }


    /**
     * @param day
     * @param next 0为本月最后一天，其他为上月最后一天
     * @return
     */
    //获取上一月最后一日
    public static String getBefDayLastOrFirst(String day, int next) {
        Calendar calendar = Calendar.getInstance(); //得到日历
        SimpleDateFormat format = new SimpleDateFormat(FORMART_YMD);
        Date date = null;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
            return "未知";
        }
        calendar.setTime(date);
        if (next == 0) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        } else if (next == 1) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天

        } else {
            calendar.add(Calendar.DAY_OF_MONTH, next);
        }
        return format.format(calendar.getTime());

    }

    //获取当天最后时刻
    public static String getCurrentDayLast(int next) {
        Calendar calendar = Calendar.getInstance(); //得到日历
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Date date = null;
        try {
            date = format.parse(getToDayByMon(0));
        } catch (ParseException e) {
            e.printStackTrace();
            return "未知";
        }
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, next);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        return format.format(calendar.getTime());

    }

    //获取next月当天最后一时刻，0代表本月
    public static String getCurrentDayLast(String day, int next) {
        Calendar calendar = Calendar.getInstance(); //得到日历
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Date date = null;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            e.printStackTrace();
            return "未知";
        }
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, next);
//        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        return format.format(calendar.getTime());

    }

    //获取前/后一月

    public static String getBeforeMon(String day, int next) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Date date;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return day;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, next);
        return format.format(c.getTime());

    }

    //当前月的第一天,next>1下几个月，<0前几个月
    public static String getCurMonFirst(int next) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_YMD);
        Date date = new Date();
        date.setDate(1);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, next);
        return format.format(c.getTime());

    }

    //当前月的第一天
    public static String getCurMonFirst(String day) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Date date;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return day;
        }
        date.setDate(1);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return format.format(c.getTime());

    }
    //获取前/后一日

    public static String getBeforeMon(String day, int next, String style) {
        SimpleDateFormat format = new SimpleDateFormat(style);
        Date date;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return day;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, next);
        return format.format(c.getTime());

    }

    //获取前/后一日

    public static String getPreMon(String day, int next) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Date date;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return day;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, next);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return format.format(c.getTime());

    }

    //获取截止到日的日期
    public static String getToDD(String day) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Date date;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return day;
        }
        SimpleDateFormat formatNew = new SimpleDateFormat("yyyy-MM-dd");
        return formatNew.format(date);

    }

    //获取截止到月的日期
    public static String getToMM(String day) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Date date;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return day;
        }
        SimpleDateFormat formatNew = new SimpleDateFormat("yyyy-MM");
        return formatNew.format(date);

    }

    //获取截止到月的日期
    public static String getToYY(String day) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_YMD);
        Date date;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return day;
        }
        SimpleDateFormat formatNew = new SimpleDateFormat("yyyy");
        return formatNew.format(date);

    }

    //获取前几天或者后几天的数据
    public static String getADay(int next) {

        Date dNow = new Date();   //当前时间
        Date dBefore = new Date();

        Calendar calendar = Calendar.getInstance(); //得到日历
        calendar.setTime(dNow);//把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, next);  //设置为前一天
        dBefore = calendar.getTime();   //得到前一天的时间

        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        return format.format(dBefore);

    }

    // TODO: 2017/9/29 负荷曲线X轴时间缺0
    // 获取HH：ss格式
    public static String getMinFormart(String day) {
       /* SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        Date date;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return day;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MINUTE);*/
        String[] date = day.split(" ");
        try {
            return date[1].substring(0, 5);

        } catch (Exception e) {
            return day;
        }

    }

    //获取所有截至到今日的日期
    public static LinkedHashMap<String, String> getCurrentMonTotalDays(int next) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

        Date date;
        try {
            date = format.parse(getToDayByMon(next));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return map;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 1; i <= maxDay; i++) {
//            calendar.set(year, month, i,0,0,0);
            calendar.set(Calendar.DAY_OF_MONTH, i);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date date1 = calendar.getTime();
            map.put(format.format(date1), "0");
        }


        return map;
    }

    //获取所有截至到今日的日期
    public static LinkedHashMap<String, String> getCurrentMonTotalDays(String day) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

        Date date;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return map;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;

        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= maxDay; i++) {
//            calendar.set(year, month, i,0,0,0);
            calendar.set(Calendar.DAY_OF_MONTH, i);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date date1 = calendar.getTime();
            map.put(getDayAmont(format.format(date1)) + "日", String.valueOf(NONE_DATE_VALUE));
        }


        return map;
    }

    //获取所有截至到今日的月日 日期
    public static LinkedHashMap<String, String> getCurrentMonTotalDays(String day, String formatType) {
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

        Date date;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return map;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;

        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        for (int i = 1; i <= maxDay; i++) {
//            calendar.set(year, month, i,0,0,0);
            calendar.set(Calendar.DAY_OF_MONTH, i);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date date1 = calendar.getTime();
            map.put(format.format(date1).substring(5, 10), String.valueOf(NONE_DATE_VALUE));
        }


        return map;
    }


    //获取所有截至到今日的日期
    public static LinkedHashMap<String, String> getCurrentYearTotalMons(int next) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();

        Date date;
        try {
            date = format.parse(getYeasFirst(next));
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return map;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
//        int year = calendar.get(Calendar.YEAR);
//        int month = calendar.get(Calendar.MONTH) + 1;

        int maxDay = calendar.getMaximum(Calendar.MONTH);

        for (int i = 0; i <= maxDay; i++) {
//            calendar.set(year, month, i,0,0,0);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, i);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            Date date1 = calendar.getTime();
            map.put(format.format(date1), "0");
        }


        return map;
    }

    // 获取某一天所有15分数
    public static LinkedHashMap<String, String> getCurrentDayTotalMins(String day) {
        SimpleDateFormat format = new SimpleDateFormat(FORMART_DAY);
        LinkedHashMap<String, String> map = new LinkedHashMap<>();

        Date date;
        try {
            date = format.parse(day);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            return map;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        for (int i = 0; i < 24; i++) {
            // calendar.set(year, month, i,0,0,0);
            calendar.set(Calendar.HOUR_OF_DAY, i);
            for (int j = 0; j < 4; j++) {
                calendar.set(Calendar.MINUTE, j * 15);
                calendar.set(Calendar.SECOND, 0);
                Date date1 = calendar.getTime();
                map.put(DateUtils.getMinFormart(format.format(date1)), String.valueOf(NONE_DATE_VALUE));

            }

        }

        return map;
    }

    /**
     * 与当前时间比较早晚
     *
     * @param time 需要比较的时间
     * @return 输入的时间比现在时间晚则返回true
     */
    public static boolean compareNowTime(String time,String format) {
        boolean isDayu = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);

        try {
            Date parse = dateFormat.parse(time);
            Date parse1 = dateFormat.parse(getFormatDate(new Date(),format));

            long diff = parse1.getTime() - parse.getTime();
            if (diff <=0) {
                isDayu = true;
            } else {
                isDayu = false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isDayu;
    }

    /**
     * 与当前时间比较早晚
     *
     * @param time 需要比较的时间
     * @return 输入的时间比现在时间晚则返回true
     */
    public static boolean compareNowTime(String time) {
        boolean isDayu = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(time);
            Date parse1 = dateFormat.parse(getNowTime());

            long diff = parse1.getTime() - parse.getTime();
            if (diff < 0) {
                isDayu = true;
            } else {
                isDayu = false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isDayu;
    }

    /**
     * 与当前时间比较早晚
     *
     * @param time 需要比较的时间
     * @return 输入的时间比现在时间晚则返回true
     */
    public static boolean compareNowMonth(String time) {
        boolean isDayu = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

        try {
            Date parse = dateFormat.parse(time);
            Date parse1 = dateFormat.parse(getNowTime());

            long diff = parse1.getTime() - parse.getTime();
            if (diff < 0) {
                isDayu = true;
            } else {
                isDayu = false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isDayu;
    }

    /**
     * 与当前时间比较早晚
     *
     * @param time 需要比较的时间
     * @return 输入的时间比现在时间晚则返回true
     */
    public static boolean compareNowMonth(String time, String lastTime) {
        boolean isDayu = false;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM");

        try {
            Date parse = dateFormat.parse(time);
            Date parse1 = dateFormat.parse(lastTime);

            long diff = parse1.getTime() - parse.getTime();
            if (diff < 0) {
                isDayu = true;
            } else {
                isDayu = false;
            }
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return isDayu;
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getNowTime() {
        String timeString = null;
        Time time = new Time();
        time.setToNow();
        String year = thanTen(time.year);
        String month = thanTen(time.month + 1);
        String monthDay = thanTen(time.monthDay);
        String hour = thanTen(time.hour);
        String minute = thanTen(time.minute);
        String second = thanTen(time.second);

        timeString = year + "-" + month + "-" + monthDay + " " + hour + ":"
                + minute + ":" + second;
        // System.out.println("-------timeString----------" + timeString);
        return timeString;
    }

    /**
     * 获取当前天数
     *
     * @return
     */
    public static String getNowDay() {
        String timeString = null;
        Time time = new Time();
        time.setToNow();
        String monthDay = thanTen(time.monthDay);

        return monthDay;
    }


    /**
     * 十一下加零
     *
     * @param str
     * @return
     */
    public static String thanTen(int str) {

        String string = null;

        if (str < 10) {
            string = "0" + str;
        } else {

            string = "" + str;

        }
        return string;
    }

    //java获取当前月的天数
    public static int getDayOfMonth() {
        Calendar aCalendar = Calendar.getInstance(Locale.CHINA);
        int day = aCalendar.getActualMaximum(Calendar.DATE);
        return day;
    }

    public static String getFormatDate(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }


}
