package ru.unn.agile.crosssections.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements Logger {
    private List<String> listOfLogs = new ArrayList<>();

    @Override public void log(final String message) {
        listOfLogs.add(message);
    }

    @Override public List<String> getLog() {
        return listOfLogs;
    }
}
