package ru.unn.agile.converter.infrastructure;

import ru.unn.agile.converter.viewmodel.LengthConverterViewModel;
import ru.unn.agile.converter.viewmodel.LengthConverterViewModelTests;

public class ViewModelWithTxtLoggerTests extends LengthConverterViewModelTests {
    @Override
    public void setUp() {
        LengthConverterTxtLogger realLogger =
            new LengthConverterTxtLogger("./ViewModel_with_TxtLogger_Tests-lab3.log");
        super.setExternalViewModel(new LengthConverterViewModel(realLogger));
    }
}
