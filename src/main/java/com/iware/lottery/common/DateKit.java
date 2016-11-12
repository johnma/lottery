package com.iware.lottery.common;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by wuhao on 16/11/7.
 */
public class DateKit {

    /*compare time*/
    public static boolean compareTime(Date preTime,int hours){
        boolean flag = true;
        long pre = preTime.getTime()+hours*60*60*1000;
        long now = System.currentTimeMillis();
        long diff = pre - now;
        if(diff > 0){

        }else{
            flag = false;
        }
        return flag;
    }


    /** time diff  minute*/
    public static Date dateMinuteModified(Date date,int minute){
        Calendar cal=Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minute);
        return cal.getTime();
    }

    public static void main(String[] args) {
        System.out.println(dateMinuteModified(new Date(),-10));
        Date testDate = dateMinuteModified(new Date(),-10);

        System.out.println(compareTime(testDate,1));

    }

}
