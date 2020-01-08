package ru.unn.agile.currencyconverter.infrastructure;

import ru.unn.agile.currencyconverter.viewmodel.CurrencyConverterViewModel;
import ru.unn.agile.currencyconverter.viewmodel.CurrencyConverterViewModelTests;

public class ViewModelWithTxtLoggerTests extends CurrencyConverterViewModelTests {
    @Override
    public void setUp() {
        TxtLogger realLogger =
            new TxtLogger("./CurrencyConverterViewModel_with_TxtLogger_Tests-lab3.log");
        super.setExternalViewModel(new CurrencyConverterViewModel(realLogger));
    }
}
