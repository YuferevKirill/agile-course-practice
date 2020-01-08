package ru.unn.agile.quadraticequation.viewmodel;

import java.util.List;

public interface QuadraticEquationILogger {

    void log(String message);

    List<String> getLog();
}
