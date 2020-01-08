package ru.unn.agile.polygon.viewmodel;

import java.util.List;

public interface ILogger {

    void log(String logMessage);

    List<String> getLog();

}
