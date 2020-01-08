package ru.unn.agile.currencyconverter.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class SimpleLogger implements ILogger {
    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public List<String> getLog() {
        return log;
    }

    @Override
    public void log(final String s) {
        log.add(s);
    }
}
