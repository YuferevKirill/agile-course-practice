package ru.unn.agile.Matrix.infrastructure;

import ru.unn.agile.polygon.Matrix.ViewModel;
import ru.unn.agile.polygon.Matrix.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {

    @Override
    public void setUp() {
        TxtLogger realLogger =
                new TxtLogger("././ViewModelWithTxtLoggerTests-matrix-lab3.log");
        super.setTestViewModel(new ViewModel(realLogger));
    }
}
