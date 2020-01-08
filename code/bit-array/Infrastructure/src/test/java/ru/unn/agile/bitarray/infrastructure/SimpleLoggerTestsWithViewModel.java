package ru.unn.agile.bitarray.infrastructure;

import ru.unn.agile.bitarray.viewmodel.ViewModel;
import ru.unn.agile.bitarray.viewmodel.ViewModelTests;

public class SimpleLoggerTestsWithViewModel extends ViewModelTests {
    @Override
    public void setUp() {
        SimpleLogger realLogger = new SimpleLogger("SimpleLoggerTestsWithViewModel.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
