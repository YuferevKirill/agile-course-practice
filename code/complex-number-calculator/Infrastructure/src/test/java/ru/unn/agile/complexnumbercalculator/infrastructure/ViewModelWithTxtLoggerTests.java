package ru.unn.agile.complexnumbercalculator.infrastructure;

import ru.unn.agile.complexnumbercalculator.viewmodel.ViewModel;
import ru.unn.agile.complexnumbercalculator.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        RealCalendar calendar = new RealCalendar();
        TxtLogger realLogger =
            new TxtLogger("./ViewModelWithTxtLoggerTests-complex-number-calculator.log", calendar);
        super.setViewModel(new ViewModel(realLogger));
    }
}
