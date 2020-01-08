package ru.unn.agile.Matrix.infrastructure;

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

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String SEPARATOR = " >> ";
    private final String fileName;
    private final BufferedWriter logWritter;

    private static String currentLocalTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
    }

    public TxtLogger(final String fileName) {
        BufferedWriter writter = null;
        this.fileName = fileName;

        try {
            writter = new BufferedWriter(new FileWriter(fileName));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        this.logWritter = writter;
    }

    @Override
    public void log(final String string) {
        try {
            logWritter.write(currentLocalDataTime() + SEPARATOR + string);
            logWritter.newLine();
            logWritter.flush();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            while (line != null) {
                log.add(line);
                line = reader.readLine();
            }
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }

        return log;
    }
}
