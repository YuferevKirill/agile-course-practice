package ru.unn.agile.primenumber.viewmodel;

import java.util.List;

public interface Logger {
    void writeLog(String s);

    List<String> readLog();
}
