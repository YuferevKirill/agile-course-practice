package ru.unn.agile.complexnumbercalculator.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private ArrayList<String> log = new ArrayList<String>();

    @Override
    public void addToLog(final String inputString) {
        log.add(inputString);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
