package ru.unn.agile.quadraticequation.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.ComplexNumber.model.ComplexNumber;

import java.util.List;

import static org.junit.Assert.*;

public class QuadraticEquationViewModelTest {
    private QuadraticEquationViewModel viewModel;

    public void setExternalViewModel(final QuadraticEquationViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new QuadraticEquationViewModel(new QuadraticEquationFakeLogger());
        }
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        QuadraticEquationViewModel viewModel = new QuadraticEquationViewModel(null);
    }

    @Test
    public void whenNoDataInputCalcButtonIsDisable() {
        assertTrue(viewModel.isCalculateButtonDisabled().get());
    }

    @Test
    public void afterInputCalcButtonIsEnabled() {
        setParams("10", "10", "10");
        assertFalse(viewModel.isCalculateButtonDisabled().get());
    }

    @Test
    public void afterInputNotAllDataCalcButtonIsDisabled() {
        viewModel.getTxtCoeffAProperty().setValue("10");
        assertTrue(viewModel.isCalculateButtonDisabled().get());
    }

    @Test
    public void afterCleanButtonIsDisabled() {
        setParams("10", "10", "10");
        viewModel.getTxtCoeffAProperty().setValue("");
        assertTrue(viewModel.isCalculateButtonDisabled().get());
    }

    @Test
    public void canCalculateByClick() {
        setParams("2", "2", "-4");

        viewModel.calculate();
        ComplexNumber[] solution = new ComplexNumber[2];
        solution[0] = new ComplexNumber(1, 0);
        solution[1] = new ComplexNumber(-2, 0);
        assertEquals(
                "X1 = 1.0 + 0.0i; X2 = -2.0 + 0.0i",
                viewModel.getTxtResultProperty().get()
        );
    }

    @Test
    public void getErrorWhenInputIsNumbersButIncorrect() {
        setParams("0", "0", "10");
        viewModel.calculate();
        assertEquals("Incorrect Input Data", viewModel.getTxtErrorProperty().get());
    }

    @Test
    public void calcButtonIsDisabledWhenNotNumbersInput() {
        setParams("abc", "0", "10");
        assertTrue(viewModel.isCalculateButtonDisabled().get());
    }

    @Test
    public void canOutputWhenOneSolution() {
        setParams("0", "3", "-9");

        viewModel.calculate();
        ComplexNumber[] solution = new ComplexNumber[1];
        solution[0] = new ComplexNumber(3, 0);
        assertEquals(
                "X1 = 3.0 + 0.0i",
                viewModel.getTxtResultProperty().get()
        );
    }

    @Test
    public void canCleanErrorLabelAfterInputCorrectData() {
        setParams("0", "0", "10");
        viewModel.calculate();
        viewModel.getTxtCoeffAProperty().setValue("10");
        viewModel.calculate();
        assertEquals("", viewModel.getTxtErrorProperty().get());
    }

    @Test
    public void canEnabledCalcButtonAfterChangeIncorrectCoeff() {
        setParams("abc", "0", "10");
        viewModel.getTxtCoeffAProperty().setValue("10");
        assertFalse(viewModel.isCalculateButtonDisabled().get());
    }

    @Test
    public void canParseAndCalculateWithDoubleValue() {
        setParams("-0.2", "0.2", "0.4");

        viewModel.calculate();
        ComplexNumber[] solution = new ComplexNumber[2];
        solution[0] = new ComplexNumber(-1, 0);
        solution[1] = new ComplexNumber(2, 0);
        assertEquals(
                "X1 = -1.0 + 0.0i; X2 = 2.0 + 0.0i",
                viewModel.getTxtResultProperty().get()
        );
    }

    @Test
    public void logIsEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();
        assertTrue(log.isEmpty());
    }

    @Test
    public void logIsNotEmptyAfterCalculation() {
        setParams("-2", "2", "4");

        viewModel.calculate();
        List<String> log = viewModel.getLog();

        assertFalse(log.isEmpty());
    }

    @Test
    public void logContainsInputArgumentsAfterCalculation() {
        setParams("-2", "2", "4");

        viewModel.calculate();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + viewModel.getTxtCoeffAProperty().get()
                + ".*" + viewModel.getTxtCoeffBProperty().get()
                + ".*" + viewModel.getTxtCoeffCProperty().get() + ".*"));
    }

    @Test
    public void argumentsInfoIsProperlyFormatted() {
        setParams("-2", "2", "4");

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*"
                + ": A = " + viewModel.getTxtCoeffAProperty().get()
                + " B = " + viewModel.getTxtCoeffBProperty().get()
                + " C = " + viewModel.getTxtCoeffCProperty().get() + ".*"));
    }

    @Test
    public void logContainsResult() {
        setParams("-2", "2", "4");

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + "Result" + ".*"));
    }

    @Test
    public void resultProperlyFormattedInLog() {
        setParams("-2", "2", "4");

        viewModel.calculate();

        String message = viewModel.getLog().get(0);
        String resultMessage = message.substring(message.indexOf("Result"));
        assertEquals("Result: " + viewModel.getTxtResultProperty().get(), resultMessage);
    }

    private void setParams(final String a, final String b, final String c) {
        viewModel.getTxtCoeffAProperty().setValue(a);
        viewModel.getTxtCoeffBProperty().setValue(b);
        viewModel.getTxtCoeffCProperty().setValue(c);
    }
}
