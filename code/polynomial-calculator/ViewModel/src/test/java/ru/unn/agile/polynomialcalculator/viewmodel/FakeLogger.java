package ru.unn.agile.polynomialcalculator.viewmodel;

import java.util.ArrayList;
import java.util.List;

class FakeLogger implements ILogger {
    private ArrayList<String> logList = new ArrayList<String>();

    @Override
    public List<String> getLogMessage() {
        return logList;
    }

    @Override
    public void addLog(final String message) {
        logList.add(message);
    }
}
