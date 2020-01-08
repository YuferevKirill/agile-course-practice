package ru.unn.agile.temperatureconverter.viewmodel;

import java.util.List;

public interface ILogger {
    void addLog(String message);

    List<String> getLogMessage();
}
