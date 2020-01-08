package ru.unn.agile.datastructure.set.infrastructure;

import ru.unn.agile.datastructure.set.viewmodel.Logger;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TxtLogger implements Logger {
    private static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss.SSS";
    private static final String LOG_FORMAT = "%s >> %s";
    private final BufferedWriter writer;
    private final String filename;

    public TxtLogger(final String filename) {
        this.filename = filename;

        BufferedWriter logWriter = null;
        try {
            logWriter = new BufferedWriter(new FileWriter(filename));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        writer = logWriter;
    }

    @Override public void log(final String message) {
        try {
            writer.write(String.format(
                    LOG_FORMAT,
                    new SimpleDateFormat(DATE_FORMAT_NOW, Locale.ENGLISH).format(new Date()),
                    message
                    )
            );
            writer.newLine();
            writer.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override public List<String> getLog() {
        return new ArrayList<>() {{
            try {
                var reader = new BufferedReader(new FileReader(filename));
                var line = reader.readLine();

                while (line != null) {
                    add(line);
                    line = reader.readLine();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }};
    }
}
