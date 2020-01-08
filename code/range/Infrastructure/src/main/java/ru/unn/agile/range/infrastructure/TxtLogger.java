package ru.unn.agile.range.infrastructure;

import ru.unn.agile.range.viewmodel.SortingLogger;

import java.io.*;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.List;

public class TxtLogger implements SortingLogger {
    private final BufferedWriter writer;
    private final String filename;

    public TxtLogger(final String filename) {
        this.filename = filename;

        BufferedWriter messagesWriter = null;
        try {
            messagesWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception e) {
            e.printStackTrace();
        }
        writer = messagesWriter;
    }

    @Override
    public void log(final String s) {
        try {
            writer.write("[" + DateUtils.getCurrentTime() + "]: " + s);
            writer.newLine();
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<String> getLog() {
        BufferedReader bufferedReader;
        ArrayList<String> messages = new ArrayList<String>();
        try {
            bufferedReader = new BufferedReader(new FileReader(filename));
            String message = bufferedReader.readLine();

            while (message != null) {
                messages.add(message);
                message = bufferedReader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return messages;
    }

}


