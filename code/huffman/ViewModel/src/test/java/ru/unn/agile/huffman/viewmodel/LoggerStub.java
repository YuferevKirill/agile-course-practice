package ru.unn.agile.huffman.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class LoggerStub implements ILogger {
    private final ArrayList<String> logs = new ArrayList<>();
    @Override
    public void logMessage(final String s) {
        logs.add(s);
    }

    @Override
    public List<String> getLog() {
        return logs;
    }
}
