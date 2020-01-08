package ru.unn.agile.mortgagecalculator.viewmodel;

import java.util.List;

public interface MortgageCalculatorILogger {
    void log(String s);

    List<String> getLog();
}
