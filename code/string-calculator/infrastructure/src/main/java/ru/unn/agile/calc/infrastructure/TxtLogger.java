package ru.unn.agile.calc.infrastructure;

import ru.unn.agile.calc.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class TxtLogger implements ILogger {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String LOG_MESSAGE_FORMAT = "%s >> %s";
    private final String fileName;
    private final BufferedWriter bufferedWriter;

    private static String currentLocalDataTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT));
    }

    public TxtLogger(final String fileName) {
        BufferedWriter logWriter = null;
        this.fileName = fileName;

        try {
            logWriter = new BufferedWriter(new FileWriter(fileName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        bufferedWriter = logWriter;
    }

    @Override
    public void log(final String s) {

        try {
            bufferedWriter.write(String.format(LOG_MESSAGE_FORMAT, currentLocalDataTime(), s));
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader logReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            logReader = new BufferedReader(new FileReader(fileName));
            String line = logReader.readLine();

            while (line != null) {
                log.add(line);
                line = logReader.readLine();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return log;
    }
}
