package ru.unn.agile.binarysearch.viewmodel;

import java.util.List;

public interface ILogger {
    void log(String logMessage);

    List<String> getLogList();
}
