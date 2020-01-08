package ru.unn.agile.calc.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class StringCalcViewModelTests {

    private static final String ERROR_MESSAGE = "Error. Check your input";

    private StringCalcViewModel viewModel;

    public void setExternalViewModel(final StringCalcViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new StringCalcViewModel(new FakeLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canCalculateExpression1() {
        setExpression("1+1");
        calculate();
        assertEquals("2.0", getResult());
    }

    @Test
    public void canCalculateExpression2() {
        setExpression("1+2*3-4/5");
        calculate();
        assertEquals("6.2", getResult());
    }

    @Test
    public void canCalculateValidExpressionWithSpaces() {
        setExpression(" 1 + 1 * 3 - 5 / 10");
        calculate();
        assertEquals("3.5", getResult());
    }

    @Test
    public void canCalculateSingleDigitExpression() {
        setExpression("111");
        calculate();
        assertEquals("111.0", getResult());
    }

    @Test
    public void isResultTheSameForSameExpression() {
        setExpression("1+1");

        calculate();
        String r1 = getResult();

        calculate();
        String r2 = getResult();

        assertEquals(r1, r2);
    }

    @Test
    public void canNotCalculateInvalidExpression1() {
        setExpression("+-*/");
        calculate();
        assertEquals(ERROR_MESSAGE, getResult());
    }

    @Test
    public void isCalculateButtonDisabledForSpaceSymbol() {
        setExpression(" ");
        assertTrue(isCalculateButtonDisabled());
    }

    @Test
    public void isCalculateButtonDisabledForLetterString() {
        setExpression("ABC");
        assertTrue(isCalculateButtonDisabled());
    }

    @Test
    public void areSpacesRemoving() {
        setExpression("1 + 2 + 3");
        assertEquals("1+2+3", getExpression());
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        viewModel = new StringCalcViewModel(null);
    }

    @Test
    public void canGetEmptyLogsAfterAppStart() {
        List<String> log = getLog();
        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsProperMessageAfterCalculation() {
        setExpressionAndCalculate("1+2*3-4/5");
        String message = getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.CALCULATE_WAS_PRESSED + ".*"));
    }

    @Test
    public void logContainsProperMessageAfterSuccessfulCalculation() {
        setExpressionAndCalculate("1+2*3-4/5");
        String message = getLog().get(1);

        assertTrue(message.matches(".*" + LogMessages.CALCULATION_WAS_SUCCESSFUL + ".*"));
    }

    @Test
    public void logContainsProperMessageAfterUnsuccessfulCalculation() {
        setExpressionAndCalculate("1+2*3--4/5");
        String message = getLog().get(1);

        assertTrue(message.matches(".*" + LogMessages.CALCULATION_WAS_UNSUCCESSFUL + ".*"));
    }

    @Test
    public void logContainsInputExpressionAfterCalculation() {
        setExpressionAndCalculate("225 / 5 / 5");
        String message = getLog().get(0);

        assertTrue(message.matches(".*" + getExpression() + ".*"));
    }

    @Test
    public void logContainsProperMessageAfterFocusChanged() {
        setExpression("1 + 3 + 7 + 7");
        viewModel.onExpressionTfFocusChanged();
        String message = getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.EDITING_FINISHED + ".*"));
    }

    @Test
    public void logContainsProperMessageAfterFocusChangedWhenExpressionIsValid() {
        setExpression("1 + 3 + 7 + 7");
        viewModel.onExpressionTfFocusChanged();
        String message = getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.EXPRESSION_IS_VALID + ".*"));
    }

    @Test
    public void logContainsProperMessageAfterFocusChangedWhenExpressionIsInValid() {
        setExpression("1 + 3 + 7 + A");
        viewModel.onExpressionTfFocusChanged();
        String message = getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.EXPRESSION_IS_INVALID + ".*"));
    }

    @Test
    public void canPutSeveralLogMessages() {
        setExpression("255 / 5 / 5");

        calculate();
        calculate();
        calculate();

        assertEquals(6, getLog().size());
    }

    private boolean isCalculateButtonDisabled() {
        return viewModel.calculationDisabledProperty().get();
    }

    private void setExpression(final String expression) {
        viewModel.expressionTfProperty().setValue(expression);
    }

    private String getExpression() {
        return viewModel.expressionTfProperty().getValue();
    }

    private void calculate() {
        viewModel.calculate();
    }

    private String getResult() {
        return viewModel.resultLblProperty().get();
    }

    private void setExpressionAndCalculate(final String expression) {
        setExpression(expression);
        calculate();
    }

    private List<String> getLog() {
        return viewModel.getLog();
    }
}
