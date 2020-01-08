package ru.unn.agile.primenumber.infrastructure;

import ru.unn.agile.primenumber.viewmodel.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ActionLogger implements Logger {

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String SEPARATOR = " ~~~ ";
    private final String processedFile;
    private final BufferedWriter writer;

    public ActionLogger(final String processedFile) {
        BufferedWriter bufferedWriter = null;
        this.processedFile = processedFile;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(processedFile));
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        this.writer = bufferedWriter;
    }

    private static String now() {
        Calendar calendar = Calendar.getInstance();
        return new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(calendar.getTime());
    }

    @Override
    public List<String> readLog() {
        BufferedReader reader;
        ArrayList<String> logs = new ArrayList<String>();

        try {
            reader = new BufferedReader(new FileReader(processedFile));
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

    @Override
    public void writeLog(final String logMessage) {
        try {
            this.writer.write(now() + SEPARATOR + logMessage);
            this.writer.newLine();
            this.writer.flush();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

}
