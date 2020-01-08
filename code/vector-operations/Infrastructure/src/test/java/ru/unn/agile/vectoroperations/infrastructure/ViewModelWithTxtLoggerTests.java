package ru.unn.agile.vectoroperations.infrastructure;

import ru.unn.agile.vectoroperations.viewmodel.ViewModel;
import ru.unn.agile.vectoroperations.viewmodel.ViewModelTests;

public class ViewModelWithTxtLoggerTests extends ViewModelTests {
    @Override
    public void beforeStart() {
        TxtLogger realLogger =
            new TxtLogger("./ViewModelTxt.log");
        super.setViewModel(new ViewModel(realLogger));
    }

}
