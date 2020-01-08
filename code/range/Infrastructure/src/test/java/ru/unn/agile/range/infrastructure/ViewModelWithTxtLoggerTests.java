package ru.unn.agile.range.infrastructure;

import ru.unn.agile.range.viewmodel.ViewModel;
import ru.unn.agile.range.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
                new TxtLogger("./ViewModel_with_TxtLogger_Tests-lab3.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
