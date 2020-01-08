package ru.unn.agile.crosssections.infrastructure;

import ru.unn.agile.crosssections.viewmodel.ViewModel;
import ru.unn.agile.crosssections.viewmodel.ViewModelTests;

public class ViewModelWithFileLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        setViewModel(new ViewModel(new FileLogger("./ViewModel_FileLogger.log")));
    }
}
