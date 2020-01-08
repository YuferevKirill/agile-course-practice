package ru.unn.agile.sorting.viewmodel;

import java.util.List;

public interface ISortingLogger {
    void log(String s);

    List<String> getLog();
}
