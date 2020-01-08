package ru.unn.agile.crosssections.viewmodel;

import java.util.List;

public interface Logger {
    void log(String message);

    List<String> getLog();
}
