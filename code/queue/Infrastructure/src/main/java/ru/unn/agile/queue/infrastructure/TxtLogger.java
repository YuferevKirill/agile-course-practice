package ru.unn.agile.queue.infrastructure;

import ru.unn.agile.queue.viewmodel.ILogger;
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
    private static final String DATE_AND_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

    private final BufferedWriter writer;
    private final String file;

    public TxtLogger(final String filename) {
        this.file = filename;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(file));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        this.writer = logWriter;
    }

    private static String now() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_AND_TIME_FORMAT,
                Locale.ENGLISH);
        return simpleDateFormat.format(calendar.getTime());
    }

    @Override
    public void log(final String message) {
        try {
            var separator = " > ";
            writer.write(now() + separator + message);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        ArrayList<String> log = new ArrayList<String>();
        try {
            BufferedReader logReader = new BufferedReader(new FileReader(file));

            String messageLine = logReader.readLine();
            while (messageLine != null) {
                log.add(messageLine);
                messageLine = logReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }
}
