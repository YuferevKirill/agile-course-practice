package ru.unn.agile.binarysearch.infrastructure;

import ru.unn.agile.binarysearch.viewmodel.ViewModel;
import ru.unn.agile.binarysearch.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger = new TxtLogger("./ViewModelWithTxtLoggerTests.log");
        super.setOuterViewModel(new ViewModel(realLogger));
    }
}
