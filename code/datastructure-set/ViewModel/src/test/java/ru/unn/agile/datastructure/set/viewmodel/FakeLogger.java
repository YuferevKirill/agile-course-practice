package ru.unn.agile.datastructure.set.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements Logger {
    private List<String> log = new ArrayList<>();

    @Override public void log(final String message) {
        log.add(message);
    }

    @Override public List<String> getLog() {
        return log;
    }
}
