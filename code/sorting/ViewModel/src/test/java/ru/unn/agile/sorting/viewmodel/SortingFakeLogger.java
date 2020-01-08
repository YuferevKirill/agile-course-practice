package ru.unn.agile.sorting.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class SortingFakeLogger implements ISortingLogger {
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
