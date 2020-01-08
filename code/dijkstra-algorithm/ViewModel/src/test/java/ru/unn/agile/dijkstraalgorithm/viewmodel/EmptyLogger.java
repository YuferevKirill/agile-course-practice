package ru.unn.agile.dijkstraalgorithm.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class EmptyLogger implements ILogger {
    @Override
    public void log(final String s) {
        log.add(s);
    }

    @Override
    public List<String> getLog() {
        return log;
    }

    private final ArrayList<String> log = new ArrayList<>();
}
