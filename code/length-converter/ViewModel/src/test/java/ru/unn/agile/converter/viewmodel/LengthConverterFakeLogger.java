package ru.unn.agile.converter.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class LengthConverterFakeLogger implements LengthConverterILogger {
    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public void log(final String s) {
        log.add(s);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
