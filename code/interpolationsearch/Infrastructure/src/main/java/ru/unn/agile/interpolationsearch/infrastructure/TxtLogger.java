package ru.unn.agile.interpolationsearch.infrastructure;

import ru.unn.agile.interpolationsearch.viewmodel.ILogger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    private static final String FORMAT_CURRENT_TIME = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writer;
    private final String filename;

    public TxtLogger(final String filename) throws IOException {
        BufferedWriter logWriter = null;
        logWriter = new BufferedWriter(new FileWriter(filename));

        this.filename = filename;
        writer = logWriter;
    }

    @Override
    public void addingLog(final String logMessage) throws IOException {
        writer.write(now() + " > " + logMessage);
        writer.newLine();
        writer.flush();
    }

    @Override
    public List<String> getLoggingList() throws IOException {
        ArrayList<String> log = new ArrayList<String>();
        BufferedReader reader;

        reader = new BufferedReader(new FileReader(filename));
        String line = reader.readLine();
        while (line != null) {
            log.add(line);
            line = reader.readLine();
        }

        return log;
    }

    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_CURRENT_TIME, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }
}
