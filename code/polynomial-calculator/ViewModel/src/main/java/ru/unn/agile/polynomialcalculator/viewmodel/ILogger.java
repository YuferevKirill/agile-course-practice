package ru.unn.agile.polynomialcalculator.viewmodel;

import java.io.IOException;
import java.util.List;

public interface ILogger {
    void addLog(String message);

    List<String> getLogMessage() throws IOException;
}
