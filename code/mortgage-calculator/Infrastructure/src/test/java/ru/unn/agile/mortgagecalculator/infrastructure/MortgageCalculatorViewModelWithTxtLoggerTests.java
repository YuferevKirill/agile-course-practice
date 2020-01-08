package ru.unn.agile.mortgagecalculator.infrastructure;

import ru.unn.agile.mortgagecalculator.viewmodel.MortgageCalculatorViewModel;
import ru.unn.agile.mortgagecalculator.viewmodel.MortgageCalculatorViewModelTests;

public class MortgageCalculatorViewModelWithTxtLoggerTests
        extends MortgageCalculatorViewModelTests {
    @Override
    public void setUp() {
        MortgageCalculatorTxtLogger realLogger =
                new MortgageCalculatorTxtLogger(
                        "./MortgageCalculatorViewModel_with_TxtLogger_Tests.log"
                );
        super.setExternalViewModel(new MortgageCalculatorViewModel(realLogger));
    }
}
