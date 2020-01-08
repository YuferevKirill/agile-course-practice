package ru.unn.agile.triangle.infrastructure;

import ru.unn.agile.triangle.viewmodel.TriangleViewModel;
import ru.unn.agile.triangle.viewmodel.TriangleViewModelTests;

public class TriangleViewModelWithTxtLoggerTests
        extends TriangleViewModelTests {
    @Override
    public void setUp() {
        TriangleTxtLogger realLogger =
                new TriangleTxtLogger("./ViewModel_with_TxtLogger_Tests-lab3.log");
        super.setExternalViewModel(new TriangleViewModel(realLogger));
    }
}
