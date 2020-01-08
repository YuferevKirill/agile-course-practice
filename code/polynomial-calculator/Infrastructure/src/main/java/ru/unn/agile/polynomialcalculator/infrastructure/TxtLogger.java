package ru.unn.agile.polynomialcalculator.infrastructure;

import ru.unn.agile.polynomialcalculator.viewmodel.ILogger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements ILogger {
    private String fileName;
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writer;

    private static String now() {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(cal.getTime());
    }

    public TxtLogger(final String fileName) {
        this.fileName = fileName;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(fileName));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        writer = logWriter;
    }

    @Override
    public List<String> getLogMessage() {
        ArrayList<String> log = new ArrayList<String>();
        try {
            BufferedReader reader =  new BufferedReader(new FileReader(fileName));
            var text = reader.readLine();
            while (text != null) {
                log.add(text);
                text = reader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return log;
    }

    @Override
    public void addLog(final String message) {
        try {
            writer.write(now() + " << " + message + '\n');
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
