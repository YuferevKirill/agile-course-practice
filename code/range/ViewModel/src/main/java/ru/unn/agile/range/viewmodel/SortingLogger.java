package ru.unn.agile.range.viewmodel;

import java.util.List;

public interface SortingLogger {
    void log(String s);

    List<String> getLog();
}
