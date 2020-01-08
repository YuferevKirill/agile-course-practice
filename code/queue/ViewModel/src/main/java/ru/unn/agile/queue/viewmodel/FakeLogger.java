package ru.unn.agile.queue.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class FakeLogger implements ILogger {
    private List<String> logger = new ArrayList<String>();

    @Override
    public void log(final String message) {
        logger.add(message);
    }

    @Override
    public List<String> getLog() {
        return logger;
    }
}
