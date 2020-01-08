package ru.unn.agile.complexnumbercalculator.infrastructure;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class RealCalendar implements ICalendar {
    @Override
    public String getTime() {
        String dateFormat = "yyyy-MM-dd HH:mm:ss";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(cal.getTime());
    }
}
