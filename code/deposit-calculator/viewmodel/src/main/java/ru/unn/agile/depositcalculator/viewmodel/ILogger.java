package ru.unn.agile.depositcalculator.viewmodel;

import java.util.List;

public interface ILogger {
    void log(String s);
    List<String> getLog();
}
