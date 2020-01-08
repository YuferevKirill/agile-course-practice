package ru.unn.agile.queue.viewmodel;

import java.util.List;

public interface ILogger {
    void log(String message);

    List<String> getLog();
}
