package ru.unn.agile.quadraticequation.infrastructure;


import ru.unn.agile.quadraticequation.viewmodel.QuadraticEquationViewModel;
import ru.unn.agile.quadraticequation.viewmodel.QuadraticEquationViewModelTest;

public class ViewModelWithTxtLoggerTests extends QuadraticEquationViewModelTest {

    @Override
    public void setUp() {
        QuadraticEquationTxtLogger realLogger =
                new QuadraticEquationTxtLogger("./QuadraticEquationViewModel_with_TxtLogger.log");
        super.setExternalViewModel(new QuadraticEquationViewModel(realLogger));
    }
}
