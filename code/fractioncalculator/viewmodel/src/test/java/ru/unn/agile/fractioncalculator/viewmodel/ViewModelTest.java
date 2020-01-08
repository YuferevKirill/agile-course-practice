package ru.unn.agile.fractioncalculator.viewmodel;

import javafx.beans.property.StringProperty;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewModelTest {

    private ViewModel viewModel;

    @Before
    public void setUp() {
        viewModel = new ViewModel();
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canGetFirstFraction() {
        assertNotNull(viewModel.firstFractionProperty());
    }

    @Test
    public void canGetSecondFraction() {
        assertNotNull(viewModel.secondFractionProperty());
    }

    @Test
    public void canGetEmptyResult() {
        StringProperty res = viewModel.resultFractionProperty();
        String actual = res.get();
        assertNull(actual);
    }

    @Test
    public void canSetFirstFraction() {
        String expected = "11/22";
        viewModel.setFirstFraction(expected);
        String actual = viewModel.firstFractionProperty().get();

        assertEquals(expected, actual);
    }

    @Test
    public void canSetSecondFraction() {
        String expected = "33/44";
        viewModel.setSecondFraction(expected);
        String actual = viewModel.secondFractionProperty().get();

        assertEquals(expected, actual);
    }

    @Test
    public void canSumFractions() {
        String firstFractionStr = "10/20";
        String secondFractionStr = "5/20";
        viewModel.setFirstFraction(firstFractionStr);
        viewModel.setSecondFraction(secondFractionStr);
        String expected = "3/4";

        viewModel.calculateSum();
        String actual = viewModel.resultFractionProperty().get();
        assertEquals(expected, actual);
    }

    @Test
    public void canSumNegativeFractions() {
        String firstFractionStr = "-10/20";
        String secondFractionStr = "-5/20";
        viewModel.setFirstFraction(firstFractionStr);
        viewModel.setSecondFraction(secondFractionStr);
        String expected = "-3/4";

        viewModel.calculateSum();
        String actual = viewModel.resultFractionProperty().get();
        assertEquals(expected, actual);
    }

    @Test
    public void canMinusFractions() {
        String firstFractionStr = "10/20";
        String secondFractionStr = "5/20";
        viewModel.setFirstFraction(firstFractionStr);
        viewModel.setSecondFraction(secondFractionStr);
        String expected = "1/4";

        viewModel.calculateMinus();
        String actual = viewModel.resultFractionProperty().get();
        assertEquals(expected, actual);
    }

    @Test
    public void cantMinusFractions() {
        String firstFractionStr = "10/20";
        String secondFractionStr = "qwe";
        viewModel.setFirstFraction(firstFractionStr);
        viewModel.setSecondFraction(secondFractionStr);

        viewModel.calculateMinus();
        String res = viewModel.resultFractionProperty().get();

        assertEquals(ViewModel.EMPTY_CALC_RESULT, res);
    }

    @Test
    public void canMultipleFractions() {
        String firstFractionStr = "2/3";
        String secondFractionStr = "3/4";
        viewModel.setFirstFraction(firstFractionStr);
        viewModel.setSecondFraction(secondFractionStr);
        String expected = "1/2";

        viewModel.calculateMultiple();
        String res = viewModel.resultFractionProperty().get();

        assertEquals(expected, res);
    }

    @Test
    public void canMultipleNegativePositiveFractions() {
        String firstFractionStr = "-2/3";
        String secondFractionStr = "3/4";
        viewModel.setFirstFraction(firstFractionStr);
        viewModel.setSecondFraction(secondFractionStr);
        String expected = "-1/2";

        viewModel.calculateMultiple();
        String res = viewModel.resultFractionProperty().get();

        assertEquals(expected, res);
    }

    @Test
    public void canMultipleNegativeNegativeFractions() {
        String firstFractionStr = "-2/3";
        String secondFractionStr = "-3/4";
        viewModel.setFirstFraction(firstFractionStr);
        viewModel.setSecondFraction(secondFractionStr);
        String expected = "1/2";

        viewModel.calculateMultiple();
        String res = viewModel.resultFractionProperty().get();

        assertEquals(expected, res);
    }

    @Test
    public void canDivideFractions() {
        String firstFractionStr = "2/3";
        String secondFractionStr = "4/5";
        viewModel.setFirstFraction(firstFractionStr);
        viewModel.setSecondFraction(secondFractionStr);
        String expected = "5/6";

        viewModel.calculateDivide();
        String res = viewModel.resultFractionProperty().get();

        assertEquals(expected, res);
    }

    @Test
    public void cantDivideIncorrectFractions() {
        String firstFractionStr = "2/0";
        String secondFractionStr = "1/5";
        viewModel.setFirstFraction(firstFractionStr);
        viewModel.setSecondFraction(secondFractionStr);

        viewModel.calculateDivide();
        String res = viewModel.resultFractionProperty().get();

        assertEquals(ViewModel.EMPTY_CALC_RESULT, res);
    }
}
