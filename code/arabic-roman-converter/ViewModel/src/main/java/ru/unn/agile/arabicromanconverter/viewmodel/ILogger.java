package ru.unn.agile.arabicromanconverter.viewmodel;

import java.util.List;

public interface ILogger {
    void log(String s);

    List<String> readLog();
}
