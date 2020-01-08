package ru.unn.agile.vectoroperations.infrastructure;

import ru.unn.agile.vectoroperations.viewmodel.ILogger;

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

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private final BufferedWriter writerObj;
    private final String nameFile;

    public TxtLogger(final String nameFile) {
        this.nameFile = nameFile;

        BufferedWriter logWriterObj = null;
        try {
            logWriterObj = new BufferedWriter(new FileWriter(nameFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writerObj = logWriterObj;
    }

    @Override
    public void log(final String s) {
        try {
            writerObj.write(getNowDate() + " > " + s);
            writerObj.newLine();
            writerObj.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader readerObj;
        ArrayList<String> log = new ArrayList<>();
        try {
            readerObj = new BufferedReader(new FileReader(nameFile));
            String line = readerObj.readLine();

            while (line != null) {
                log.add(line);
                line = readerObj.readLine();
            }
        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }

        return log;
    }

    private static String getNowDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH);
        return format.format(calendar.getTime());
    }

}
