package ru.unn.agile.interpolationsearch.viewmodel;

import java.io.IOException;
import java.util.List;

public interface ILogger {
    void addingLog(String logMessage) throws IOException;

    List<String> getLoggingList() throws IOException;
}
