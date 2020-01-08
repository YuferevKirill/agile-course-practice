package ru.unn.agile.currencyconverter.infrastructure;

import ru.unn.agile.currencyconverter.viewmodel.ILogger;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.*;

public class TxtLogger implements ILogger {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private List<String> logRows;
    private String filename;

    private static String getCurrentTime() {
        Calendar calendar = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return sdf.format(calendar.getTime());
    }

    public TxtLogger(final String filename) {
        this.filename = filename;
        try {
            Files.deleteIfExists(Paths.get(filename));
            Files.createFile(Paths.get(filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void log(final String row) {
        String newRow = "[" + getCurrentTime() + "] " + row + "\n";
        try {
            Files.write(Paths.get(this.filename), newRow.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        try {
            logRows = Files.readAllLines(Paths.get(filename));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return logRows;
    }

}
