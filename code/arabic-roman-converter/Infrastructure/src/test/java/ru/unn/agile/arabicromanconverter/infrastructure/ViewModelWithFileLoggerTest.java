package ru.unn.agile.arabicromanconverter.infrastructure;

import ru.unn.agile.arabicromanconverter.viewmodel.ViewModel;
import ru.unn.agile.arabicromanconverter.viewmodel.ArabicRomanConverterViewModelTests;

public class ViewModelWithFileLoggerTest extends ArabicRomanConverterViewModelTests {
    @Override
    public void setUp() {
        FileLogger realLogger =
                new FileLogger("./ViewModel_with_FileLogger_Tests-lab3.log");
        super.setExternalViewModel(new ViewModel(realLogger));
    }
}
