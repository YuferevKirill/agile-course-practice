package ru.unn.agile.binarysearch.infrastructure;

import ru.unn.agile.binarysearch.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writer;
    private final String filename;

    public TxtLogger(final String filename) {
        this.filename = filename;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception error) {
            error.printStackTrace();
        }
        writer = logWriter;
    }

    @Override
    public void log(final String logMessage) {
        try {
            writer.write(now() + " > " + logMessage);
            writer.newLine();
            writer.flush();
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
    }

    @Override
    public List<String> getLogList() {

        ArrayList<String> log = new ArrayList<String>();
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();
            while (line != null) {
                log.add(line);
                line = reader.readLine();
            }
        } catch (Exception error) {
            System.out.println(error.getMessage());
        }
        return log;
    }

    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }
}
