package ru.unn.agile.queue.infrastructure;

import ru.unn.agile.queue.viewmodel.ViewModel;
import ru.unn.agile.queue.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger = new TxtLogger("./ViewModelWithTxtLoggerTests.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
