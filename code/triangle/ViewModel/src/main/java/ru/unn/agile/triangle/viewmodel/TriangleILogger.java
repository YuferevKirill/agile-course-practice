package ru.unn.agile.triangle.viewmodel;

import java.util.List;

public interface TriangleILogger {
    void log(String s);

    List<String> getLog();
}
