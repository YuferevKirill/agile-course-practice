package ru.unn.agile.huffman.viewmodel;

import java.util.List;

public interface ILogger {
    void logMessage(String message);

    List<String> getLog();
}
