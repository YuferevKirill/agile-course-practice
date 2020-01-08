package ru.unn.agile.converter.infrastructure;

import ru.unn.agile.converter.viewmodel.LengthConverterILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class LengthConverterTxtLogger implements LengthConverterILogger {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter logWriter;
    private final String logFileName;

    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }

    public LengthConverterTxtLogger(final String logFileName) {
        this.logFileName = logFileName;

        BufferedWriter initialWriter = null;
        try {
            initialWriter = new BufferedWriter(new FileWriter(logFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        logWriter = initialWriter;
    }

    @Override
    public void log(final String s) {
        try {
            logWriter.write(now() + " > " + s);
            logWriter.newLine();
            logWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(logFileName));
            String lineFromLog = reader.readLine();

            while (lineFromLog != null) {
                log.add(lineFromLog);
                lineFromLog = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }

}
