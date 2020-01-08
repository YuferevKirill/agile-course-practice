package ru.unn.agile.interpolationsearch.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class LogFaker implements ILogger {
    private List<String> logging = new ArrayList<String>();

    @Override
    public List<String> getLoggingList() {
        return logging;
    }

    @Override
    public void addingLog(final String logMessage) {
        logging.add(logMessage);
    }
}
