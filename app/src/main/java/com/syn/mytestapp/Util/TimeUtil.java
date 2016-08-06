package com.syn.mytestapp.Util;

import android.util.Log;


import com.syn.mytestapp.R;
import com.syn.mytestapp.activity.MainActivity;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by 孙亚楠 on 2016/7/27.
 */
public class TimeUtil {
    private static Calendar calendar;
    private static Date date;

    private static void setTime() {
        calendar = Calendar.getInstance();
        date = new Date();
        calendar.setTime(date);
    }


    public static long getTimestamp() {
        setTime();
        long time = 0;
        time = date.getTime();
        return time;
    }


    public static String getYearMonthDay() {
        setTime();
        String year = "";
        String month = "";
        String day = "";
        DecimalFormat df = new DecimalFormat("00");
        year = df.format(calendar.get(Calendar.YEAR));
        month = df.format(calendar.get(Calendar.MONTH)+1);
        day = df.format(calendar.get(Calendar.DAY_OF_MONTH));
        return year + "-" + month + "-" + day;
    }


    public static String getYear_xx() {
        setTime();
        String year = "";
        DecimalFormat df = new DecimalFormat("00");
        year = df.format(calendar.get(Calendar.YEAR));
        return year;
    }


    public static String getYear_xxxx() {
        setTime();
        String year = "";
        DecimalFormat df = new DecimalFormat("0000");
        year = df.format(calendar.get(Calendar.YEAR));
        return year;
    }


    public static String getMonth(){
        setTime();
        String month= "";
        DecimalFormat df = new DecimalFormat("00");
        month = df.format(calendar.get(Calendar.MONTH));
        return month;
    }


    public static String getDay(){
        setTime();
        String day="";
        DecimalFormat df=new DecimalFormat("00");
        day=df.format(calendar.get(Calendar.DAY_OF_MONTH)+1);
        return  day;
    }


    public static String getFullTime(){
        setTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String nowTime=dateFormat.format(date);
        return nowTime;
    }


    public static int getDayOfWeek(){
        setTime();
        int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(dayOfWeek==0)dayOfWeek=7;
        return dayOfWeek;
    }


    public static String getTimeDiff(String dateStart,String dateEnd){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        boolean flag = true;
        try {
            Date d1 = df.parse(dateStart);
            Date d2 = df.parse(dateEnd);
            long diff = Math.abs(d2.getTime()-d1.getTime())/(1000*60*60*24);
            if (d2.getTime() < d1.getTime()){
                flag = false;
            }
            if (!flag){
                diff = (-diff);
            }
            return Long.toString(diff);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWeekString(){
        String time=null;
        setTime();
        int dayOfWeek=calendar.get(Calendar.DAY_OF_WEEK)-1;
        if(dayOfWeek==0)dayOfWeek=7;
        switch (dayOfWeek){
            case 1:
                time= "monday";
                break;
            case 2:
                time= "tuesday";
                break;
            case 3:
                time="wednesday";
                break;
            case 4:
                time="thusday";
                break;
            case 5:
                time= "friday";
                break;
            case 6:
                time= "saturday";
                break;
            case 7:
                time= "sunday";
                break;
        }
        return time;
    }

    public static int getHour(){
        setTime();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        return hour;
    }
    public static int getMinute(){
        setTime();
        int minute=calendar.get(Calendar.MINUTE);
        return minute;
    }

    public static int getHourMinute(){
        setTime();
        int hour=calendar.get(Calendar.HOUR_OF_DAY);
        int minute=calendar.get(Calendar.MINUTE);
        return hour*100+minute;
    }

    public static String getCourseArea(int i){
        String time = null;
        switch (i) {
            case 0:
                time = "08:00~09:30";
                break;
            case 1:
                time = "09:40~10:20";
                break;
            case 2:
                time = "10:30~11:10";
                break;
            case 3:
                time = "11:20~12:00";
                break;
            case 4:
                time = "14:00~15:30";
                break;
            case 5:
                time = "15:40~17:10";
                break;
            case 6:
                time = "19:00~20:30";
                break;
        }
        return time;
    }

    public static String getTerm(){
        String y=getYear_xxxx();
        int m = Integer.parseInt(getMonth());//月份
        m = (m >= 6 ? 9 : 3);
        String T = y + "/" + m + "/1 0:00:00";
        Log.d("取得的日期为：",T);
        return T;
    }


    /**
     * 根据jxnugo返回的日期算出天数
     */
    public static String getJxnuGoPassTime(String strTime){
        if(!TextUtil.isNull(strTime)){
            long year=Integer.parseInt(strTime.split(" ")[0].split("-")[0]);
            long month=Integer.parseInt(strTime.split(" ")[0].split("-")[1]);
            long day=Integer.parseInt(strTime.split(" ")[0].split("-")[2]);
            long hour=Integer.parseInt(strTime.split(" ")[1].split(":")[0]);
            long minute=Integer.parseInt(strTime.split(" ")[1].split(":")[1]);
            long nowYear=Integer.parseInt(getYear_xxxx());
            long nowMonth=Integer.parseInt(getMonth())+1;
            long nowDay=Integer.parseInt(getDay());
            long nowHour=getHour();
            long nowMinute=getMinute();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            try {
                Date date = df.parse(strTime);
                long fullTime=date.getTime()/1000;
                long nowFullTime=getTimestamp()/1000;
                long result=nowFullTime-fullTime;

                if(result<=60)return "Just Now";
                else if(result<3600)return result/60+"Minutes ago";
                else if(result<86400)return (result/3600)+"Hours ago";
                else if((nowMonth-month==0||result<2678400)&&nowYear-year==0)return (result/86400)+"Days ago";
                else if(nowMonth-month>0&&nowYear-year==0)return (nowMonth-month)+"Months ago";
                else if(nowYear-year>0)return (nowYear-year)+"Years ago";
            } catch (ParseException e) {
                e.printStackTrace();
            }



        }
        return strTime;
    }
}

