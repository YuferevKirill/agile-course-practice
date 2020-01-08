package ru.unn.agile.calc.infrastructure;

import ru.unn.agile.calc.viewmodel.StringCalcViewModel;
import ru.unn.agile.calc.viewmodel.StringCalcViewModelTests;

public class StringCalcViewModelWithTxtLoggerTests extends StringCalcViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
                new TxtLogger("./ViewModel_with_TxtLogger_Tests-lab3.log");
        super.setExternalViewModel(new StringCalcViewModel(realLogger));
    }
}
