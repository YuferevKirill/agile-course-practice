package ru.unn.agile.triangle.infrastructure;

import ru.unn.agile.triangle.viewmodel.TriangleILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

public class TriangleTxtLogger implements TriangleILogger {
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writer;
    private final String fileName;

    private static String currentDateTime() {
        Date dateNow = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH);
        return sdf.format(dateNow.getTime());
    }

    public TriangleTxtLogger(final String fileName) {
        BufferedWriter logWriter = null;

        this.fileName = fileName;

        try {
            logWriter = new BufferedWriter(new FileWriter(fileName));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        writer = logWriter;
    }

    @Override
    public void log(final String s) {
        try {
            writer.write(currentDateTime() + " > " + s);
            writer.newLine();
            writer.flush();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader reader;
        ArrayList<String> log = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();

            while (line != null) {
                log.add(line);
                line = reader.readLine();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return log;
    }

}
