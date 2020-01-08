package ru.unn.agile.datastructure.set.infrastructure;

import ru.unn.agile.datastructure.set.viewmodel.ViewModel;
import ru.unn.agile.datastructure.set.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void setUp() {
        setViewModel(new ViewModel(new TxtLogger("./ViewModel_with_TxtLogger_Tests-lab3.log")));
    }
}
