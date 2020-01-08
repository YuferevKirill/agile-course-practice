package ru.unn.agile.bitarray.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class MockLogger implements LoggerInterface {
    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public void add(final String s) {
        log.add(s);
    }

    @Override
    public List<String> get() {
        return log;
    }
}
