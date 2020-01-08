package ru.unn.agile.quadraticequation.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class QuadraticEquationFakeLogger implements QuadraticEquationILogger {

    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public void log(final String message) {
        log.add(message);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
