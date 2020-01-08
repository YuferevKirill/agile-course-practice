package ru.unn.agile.primenumber.infrastructure;

import ru.unn.agile.primenumber.viewmodel.ViewModel;
import ru.unn.agile.primenumber.viewmodel.ViewModelTests;

public class ViewModelWithFileLoggerTest extends ViewModelTests {
    @Override
    public void setUp() {
        ActionLogger realLogger =
                new ActionLogger("./ViewModel_with_FileLogger_Tests-lab3.log");
        super.setExternalViewModel(new ViewModel(realLogger));
    }
}
