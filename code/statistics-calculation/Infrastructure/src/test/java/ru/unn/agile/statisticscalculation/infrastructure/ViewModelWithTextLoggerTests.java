package ru.unn.agile.statisticscalculation.infrastructure;

import ru.unn.agile.statisticscalculation.viewmodel.ViewModel;
import ru.unn.agile.statisticscalculation.viewmodel.ViewModelTests;

public class ViewModelWithTextLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TextLogger realLogger =
            new TextLogger("./ViewModel_with_TxtLogger_Tests-lab3.log");
        super.setExternalViewModel(new ViewModel(realLogger));
    }
}
