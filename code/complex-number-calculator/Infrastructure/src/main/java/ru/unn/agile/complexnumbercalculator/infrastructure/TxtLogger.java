package ru.unn.agile.complexnumbercalculator.infrastructure;

import ru.unn.agile.complexnumbercalculator.viewmodel.ILogger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class TxtLogger implements ILogger {
    private final String filename;
    private final BufferedWriter writer;
    private final ICalendar calendar;

    public TxtLogger(final String filename, final ICalendar calendar) {
        this.filename = filename;
        this.calendar = calendar;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = logWriter;
    }

    @Override
    public void addToLog(final String inputString) {
        try {
            writer.write(calendar.getTime() + " > " + inputString);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader bufferedReader;
        ArrayList<String> log = new ArrayList<String>();
        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
            String string = bufferedReader.readLine();

            while (string != null) {
                log.add(string);
                string = bufferedReader.readLine();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return log;
    }

}
