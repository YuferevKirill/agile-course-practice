package ru.unn.agile.polygon.infrastructure;

import ru.unn.agile.polygon.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TxtLogger implements ILogger {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final String fileName;
    private final BufferedWriter writer;

    private static String currentLocalDataTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    public TxtLogger(final String logFileName) {
        BufferedWriter logWriter = null;
        this.fileName = logFileName;

        try {
            logWriter = new BufferedWriter(new FileWriter(logFileName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        writer = logWriter;
    }

    @Override
    public void log(final String s) {
        try {
            writer.write(currentLocalDataTime() + " >> " + s);
            writer.newLine();
            writer.flush();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader logFileReader;
        final ArrayList<String> log = new ArrayList<>();
        try {
            logFileReader = new BufferedReader(new FileReader(fileName));
            String line = logFileReader.readLine();

            while (line != null) {
                log.add(line);
                line = logFileReader.readLine();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return log;
    }
}
