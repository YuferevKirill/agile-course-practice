package ru.unn.agile.Matrix.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeTextLogger implements ILogger {
    private ArrayList<String> logsList;

    public FakeTextLogger() {
        logsList = new ArrayList<String>();
    }

    @Override
    public void logMessage(final String s) {
        logsList.add(s);
    }

    @Override
    public List<String> getLog() {
        return logsList;
    }
}
