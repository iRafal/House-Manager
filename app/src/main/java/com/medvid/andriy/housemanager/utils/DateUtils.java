package com.medvid.andriy.housemanager.utils;

import java.util.Calendar;

/**
 * Created by Андрій on 5/19/2015.
 */
public class DateUtils {

    public static int compareTwoDates(Calendar firstDate, Calendar secondDate) {

        int result = 0;

        if ((firstDate.get(Calendar.YEAR) == secondDate.get(Calendar.YEAR))
                && (firstDate.get(Calendar.MONTH) == secondDate.get(Calendar.MONTH))
                && (firstDate.get(Calendar.DAY_OF_YEAR) == secondDate.get(Calendar.DAY_OF_YEAR))) {
            result = 0;
        }   else    if((firstDate.get(Calendar.YEAR) < secondDate.get(Calendar.YEAR))
                || (firstDate.get(Calendar.MONTH) < secondDate.get(Calendar.MONTH))
                || (firstDate.get(Calendar.DAY_OF_YEAR) < secondDate.get(Calendar.DAY_OF_YEAR))) {
            result = -1;
        }else   if((firstDate.get(Calendar.YEAR) > secondDate.get(Calendar.YEAR))
                || (firstDate.get(Calendar.MONTH) > secondDate.get(Calendar.MONTH))
                || (firstDate.get(Calendar.DAY_OF_YEAR) > secondDate.get(Calendar.DAY_OF_YEAR))) {
            result = 1;
        }

        return result;
    }

    public static int compareTwoTimes(Calendar firstTime, Calendar secondTime)  {
        int result = 0;

        if ((firstTime.get(Calendar.HOUR_OF_DAY) == secondTime.get(Calendar.HOUR_OF_DAY))
                && (firstTime.get(Calendar.MINUTE) == secondTime.get(Calendar.MINUTE))) {
            result = 0;
        }   else    if((firstTime.get(Calendar.HOUR_OF_DAY) < secondTime.get(Calendar.HOUR_OF_DAY))
                || (firstTime.get(Calendar.MINUTE) < secondTime.get(Calendar.MINUTE))) {
            result = -1;
        }else   if((firstTime.get(Calendar.HOUR_OF_DAY) > secondTime.get(Calendar.HOUR_OF_DAY))
                || (firstTime.get(Calendar.MINUTE) > secondTime.get(Calendar.MINUTE))) {
            result = 1;
        }

        return result;
    }
}
