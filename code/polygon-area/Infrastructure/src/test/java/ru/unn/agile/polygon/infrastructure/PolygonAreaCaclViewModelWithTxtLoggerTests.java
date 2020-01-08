package ru.unn.agile.polygon.infrastructure;

import ru.unn.agile.polygon.viewmodel.PolygonAreaCalcViewModel;
import ru.unn.agile.polygon.viewmodel.PolygonAreaCalcViewModelTests;

public class PolygonAreaCaclViewModelWithTxtLoggerTests extends PolygonAreaCalcViewModelTests {

    @Override
    public void setUp() {
        TxtLogger realLogger =
                new TxtLogger("./PolygonAreaCalcViewModel_with_TxtLogger_Tests-lab3.log");
        super.setTestViewModel(new PolygonAreaCalcViewModel(realLogger));
    }
}
