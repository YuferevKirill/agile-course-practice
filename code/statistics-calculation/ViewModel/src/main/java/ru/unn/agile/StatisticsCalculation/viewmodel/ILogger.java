package ru.unn.agile.statisticscalculation.viewmodel;

import java.util.List;

public interface ILogger {
    void addLog(String mes);

    List<String> getLog();
}
