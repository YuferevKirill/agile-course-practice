package ru.unn.agile.arabicromanconverter.infrastructure;

import ru.unn.agile.arabicromanconverter.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class FileLogger implements ILogger {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final String filename;
    private final BufferedWriter writer;

    private static String now() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(calendar.getTime());
    }

    public FileLogger(final String filename) {
        BufferedWriter logWriter = null;
        this.filename = filename;

        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        writer = logWriter;
    }

    @Override
    public void log(final String logMessage) {
        try {
            writer.write(now() + " ~ " + logMessage);

            writer.newLine();

            writer.flush();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public List<String> readLog() {
        BufferedReader reader;
        ArrayList<String> logs = new ArrayList<String>();

        try {
            reader = new BufferedReader(new FileReader(filename));
            String line = reader.readLine();

            while (line != null) {
                logs.add(line);
                line = reader.readLine();
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return logs;
    }

}
