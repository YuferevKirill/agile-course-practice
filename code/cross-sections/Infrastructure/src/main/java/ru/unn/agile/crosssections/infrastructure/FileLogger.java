package ru.unn.agile.crosssections.infrastructure;

import ru.unn.agile.crosssections.viewmodel.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FileLogger implements Logger {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String LOG_FORMAT = "%s : %s";
    private final BufferedWriter bufferLogWriter;
    private final String filename;

    public FileLogger(final String filename) {
        this.filename = filename;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        bufferLogWriter = logWriter;
    }

    @Override
    public List<String> getLog() {
        List<String> listOfLogs = new ArrayList<String>();
        try {
            var reader = new BufferedReader(new FileReader(filename));
            var line = reader.readLine();

            while (line != null) {
                listOfLogs.add(line);
                line = reader.readLine();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return listOfLogs;
    }

    @Override
    public void log(final String logInfo) {
        try {
            bufferLogWriter.write(String.format(
                    LOG_FORMAT,
                    new SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH).format(new Date()),
                    logInfo
                    )
            );
            bufferLogWriter.newLine();
            bufferLogWriter.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
