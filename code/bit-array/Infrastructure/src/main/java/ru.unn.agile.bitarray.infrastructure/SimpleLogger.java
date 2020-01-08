package ru.unn.agile.bitarray.infrastructure;

import ru.unn.agile.bitarray.viewmodel.LoggerInterface;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SimpleLogger implements LoggerInterface {
    private final String fileName;
    private BufferedWriter bufferedWriter = null;
    private Integer msgCounter = 0;

    public SimpleLogger(final String logFileName) {
        fileName = logFileName;

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(logFileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void add(final String s) {
        if (bufferedWriter != null) {
            try {
                bufferedWriter.write("#" + msgCounter.toString() + ": " + s);
                bufferedWriter.flush();
                ++msgCounter;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<String> get() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return readLogFileLineByLine(reader);
    }

    private List<String> readLogFileLineByLine(final BufferedReader reader) {
        List<String> logEntity = new ArrayList<>();

        try {
            var line = reader.readLine();

            while (line != null) {
                logEntity.add(line);
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return logEntity;
    }


}
