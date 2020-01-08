package ru.unn.agile.range.infrastructure;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public final class DateUtils {

    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";

    private DateUtils() {
    }

    public static String getCurrentTime() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }
}
