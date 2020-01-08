package ru.unn.agile.temperatureconverter.infrastructure;

import ru.unn.agile.temperatureconverter.viewmodel.ViewModelTests;
import ru.unn.agile.temperatureconverter.viewmodel.ViewModel;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger = new TxtLogger(
                "ViewModelWithTxtLoggerTests-temperature-converter.log");
        super.setViewModel(new ViewModel(realLogger));
    }
}
