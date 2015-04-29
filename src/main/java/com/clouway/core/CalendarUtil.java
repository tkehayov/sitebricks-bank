package com.clouway.core;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Tihomir Kehayov <kehayov89@gmail.com>
 */
public class CalendarUtil {
  public static Long dateOf(int year, int month, int day) {
    Calendar calendar = Calendar.getInstance();

    calendar.set(Calendar.MONTH, month - 1);
    calendar.set(Calendar.DAY_OF_MONTH, day);
    calendar.set(Calendar.YEAR, year);

    return calendar.getTimeInMillis();
  }
}
