package ru.unn.agile.polynomialcalculator.infrastructure;

import ru.unn.agile.polynomialcalculator.viewmodel.ViewModelTests;
import ru.unn.agile.polynomialcalculator.viewmodel.ViewModel;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger = new TxtLogger(
                "ViewModelWithTxtLoggerTests-polynomial-calculator.log");
        setViewModel(new ViewModel(realLogger));
    }
}
