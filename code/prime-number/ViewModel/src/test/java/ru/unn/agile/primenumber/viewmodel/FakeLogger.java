package ru.unn.agile.primenumber.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements Logger {
    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public void writeLog(final String s) {
        log.add(s);
    }

    @Override
    public List<String> readLog() {
        return log;
    }
}
