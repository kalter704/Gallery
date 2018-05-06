package com.yad.vasilii.gallery.data;

import java.util.*;

public class CalendarManager {

    public Calendar getCurrentCalender(){
        return Calendar.getInstance();
    }

    public Calendar getCalender(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return calendar;
    }

}
