package ru.unn.agile.mortgagecalculator.viewmodel;

import java.util.ArrayList;
import java.util.List;

public class MortgageCalculatorFakeLogger implements MortgageCalculatorILogger {
    private final ArrayList<String> log = new ArrayList<>();

    @Override
    public void log(final String s) {
        log.add(s);
    }

    @Override
    public List<String> getLog() {
        return log;
    }
}
