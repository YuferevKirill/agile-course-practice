package ru.unn.agile.huffman.infrastructure;

import ru.unn.agile.huffman.viewmodel.ILogger;

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

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String SEPARATOR = " >> ";
    private final BufferedWriter logWriter;
    private final String filename;

    public TxtLogger(final String filename) {
        this.filename = filename;

        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        logWriter = writer;
    }

    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }

    @Override
    public void logMessage(final String message) {
        if ("".equals(message)) {
            throw new IllegalArgumentException("Can't log empty message");
        }
        try {
            logWriter.write(now() + SEPARATOR + message);
            logWriter.newLine();
            logWriter.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        ArrayList<String> log = new ArrayList<String>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(filename));
            String messageFromLog = reader.readLine();

            while (messageFromLog != null) {
                log.add(messageFromLog);
                messageFromLog = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return log;
    }
}
