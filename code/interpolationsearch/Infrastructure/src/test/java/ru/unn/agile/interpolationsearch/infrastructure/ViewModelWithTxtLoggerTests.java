package ru.unn.agile.interpolationsearch.infrastructure;

import ru.unn.agile.interpolationsearch.viewmodel.ViewModel;
import ru.unn.agile.interpolationsearch.viewmodel.ViewModelTests;

import java.io.IOException;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() throws IOException {
        TxtLogger realLogger = new TxtLogger("./ViewModelWithTxtLoggerTests.log");
        super.setOuterViewModel(new ViewModel(realLogger));
    }
}
