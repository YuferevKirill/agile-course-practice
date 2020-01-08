package ru.unn.agile.triangle.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements TriangleILogger {
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
