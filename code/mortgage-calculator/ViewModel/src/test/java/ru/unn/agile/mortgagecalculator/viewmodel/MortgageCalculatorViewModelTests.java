package ru.unn.agile.mortgagecalculator.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class MortgageCalculatorViewModelTests {
    private MortgageCalculatorViewModel viewModel;

    private void setCorrectInputs() {
        viewModel.loanPeriodProperty().set("12");
        viewModel.loanPeriodTypeProperty().set("Month");
        viewModel.apartmentPriceProperty().set("200");
        viewModel.firstPaymentProperty().set("20");
        viewModel.interestRateProperty().set("3");
        viewModel.monthlyCommissionsProperty().set("1");
        viewModel.monthlyCommissionsTypeProperty().set("Rubles");
        viewModel.oneTimeCommissionsProperty().set("2");
        viewModel.oneTimeCommissionsTypeProperty().set("Percent");
        viewModel.typeOfPaymentProperty().set("Annuity");
    }

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new MortgageCalculatorViewModel(new MortgageCalculatorFakeLogger());
        }
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void setEmptyStringInTextFieldsByDefault() {
        StringBuilder textFields = new StringBuilder(viewModel.getApartmentPrice());
        textFields.append(viewModel.getFirstPayment())
                .append(viewModel.getInterestRate())
                .append(viewModel.getLoanPeriod())
                .append(viewModel.getLoanPeriodType())
                .append(viewModel.getMonthlyCommissions())
                .append(viewModel.getMonthlyCommissionsType())
                .append(viewModel.getOneTimeCommissions())
                .append(viewModel.getOneTimeCommissionsType())
                .append(viewModel.getTypeOfPayment());
        assertEquals("", textFields.toString());
    }

    public void setExternalViewModel(final MortgageCalculatorViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Test
    public void canShowErrorAfterInvalidInput() {
        viewModel.apartmentPriceProperty().set("ee");
        assertEquals("Incorrect input", viewModel.getResult());
    }

    @Test
    public void canShowErrorAfterInvalidInputPeriod() {
        viewModel.loanPeriodProperty().set("1.2");
        assertEquals("Incorrect input", viewModel.getResult());
    }

    @Test
    public void canClearErrorAfterValidInput() {
        viewModel.apartmentPriceProperty().set("ee");
        setCorrectInputs();
        assertEquals("", viewModel.getResult());
    }

    @Test
    public void canClearResultByDefault() {
        assertEquals("", viewModel.getResult());
    }

    @Test
    public void canCalculate() {
        setCorrectInputs();

        viewModel.calculate();

        assertEquals("Final amount 198.54; Overpayment "
                + "18.539999999999992; With month payment "
                + "16.24 for 12 months.", viewModel.getResult());
    }

    @Test
    public void canClearOutputAfterChangeInput() {
        setCorrectInputs();

        viewModel.calculate();
        viewModel.apartmentPriceProperty().set("2");

        assertEquals("", viewModel.getResult());
    }

    @Test
    public void canCalculateWithDifferentialPayment() {
        setCorrectInputs();
        viewModel.typeOfPaymentProperty().set("Differential");

        viewModel.calculate();

        assertEquals("Final amount 198.53; Overpayment 18.53; "
                + "With month payment 16.45 for 12 months.", viewModel.getResult());
    }

    @Test
    public void canCalculateWithCommisionInPercent() {
        setCorrectInputs();
        viewModel.monthlyCommissionsTypeProperty().set("Percent");

        viewModel.calculate();

        assertEquals("Final amount 208.14; Overpayment 28.139999999999986; "
                + "With month payment 17.04 for 12 months.", viewModel.getResult());
    }

    @Test
    public void cantCalculateWithEmptyLoanPeriodProperty() {
        setCorrectInputs();
        viewModel.loanPeriodProperty().set("");

        viewModel.calculate();

        assertEquals("Incorrect input", viewModel.getResult());
    }

    @Test
    public void cantCalculateWithEmptyLoanPeriodTypeProperty() {
        setCorrectInputs();
        viewModel.loanPeriodTypeProperty().set("");

        viewModel.calculate();

        assertEquals("Incorrect input", viewModel.getResult());
    }

    @Test
    public void cantCalculateWithEmptyFirstPaymentProperty() {
        setCorrectInputs();
        viewModel.firstPaymentProperty().set("");

        viewModel.calculate();

        assertEquals("Incorrect input", viewModel.getResult());
    }

    @Test
    public void cantCalculateWithEmptyInterestRateProperty() {
        setCorrectInputs();
        viewModel.interestRateProperty().set("");

        viewModel.calculate();

        assertEquals("Incorrect input", viewModel.getResult());
    }

    @Test
    public void cantCalculateWithEmptyMonthlyCommissionsProperty() {
        setCorrectInputs();
        viewModel.monthlyCommissionsProperty().set("");

        viewModel.calculate();

        assertEquals("Incorrect input", viewModel.getResult());
    }

    @Test
    public void cantCalculateWithEmptyMonthlyCommissionsTypeProperty() {
        setCorrectInputs();
        viewModel.monthlyCommissionsTypeProperty().set("");

        viewModel.calculate();

        assertEquals("Incorrect input", viewModel.getResult());
    }

    @Test
    public void cantCalculateWithEmptyOneTimeCommissionsProperty() {
        setCorrectInputs();
        viewModel.oneTimeCommissionsProperty().set("");

        viewModel.calculate();

        assertEquals("Incorrect input", viewModel.getResult());
    }

    @Test
    public void cantCalculateWithEmptyOneTimeCommissionsTypeProperty() {
        setCorrectInputs();
        viewModel.oneTimeCommissionsTypeProperty().set("");

        viewModel.calculate();

        assertEquals("Incorrect input", viewModel.getResult());
    }

    @Test
    public void cantCalculateWithEmptyTypeOfPaymentProperty() {
        setCorrectInputs();
        viewModel.typeOfPaymentProperty().set("");

        viewModel.calculate();

        assertEquals("Incorrect input", viewModel.getResult());
    }

    @Test
    public void cantCalculateWithNegativeValues() {
        setCorrectInputs();
        viewModel.firstPaymentProperty().set("-5");

        viewModel.calculate();

        assertEquals("Incorrect input", viewModel.getResult());
    }

    @Test
    public void cantCalculateFirstPaymentBiggerOrEqualsToPrice() {
        setCorrectInputs();
        viewModel.firstPaymentProperty().set("5");
        viewModel.apartmentPriceProperty().set("5");

        viewModel.calculate();

        assertEquals("Incorrect input", viewModel.getResult());
    }

    @Test
    public void cantCalculateWithInterestRateBiggerThen60() {
        setCorrectInputs();
        viewModel.interestRateProperty().set("61");

        viewModel.calculate();

        assertEquals("Incorrect input", viewModel.getResult());
    }

    @Test
    public void cantCalculateWithPercentBiggerThen100() {
        setCorrectInputs();
        viewModel.monthlyCommissionsProperty().set("101");
        viewModel.monthlyCommissionsTypeProperty().set("Percent");

        viewModel.calculate();

        assertEquals("Incorrect input", viewModel.getResult());
    }

    @Test
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        try {
            new MortgageCalculatorViewModel(null);
            fail("Exception was not thrown");
        } catch (IllegalArgumentException ex) {
            assertEquals("Logger parameter can not be null", ex.getMessage());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void logIsEmptyAfterInit() {
        List<String> log = viewModel.getLog();
        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsProperMessageAfterCalculation() {
        setCorrectInputs();
        viewModel.calculate();
        String message = viewModel.getLog().get(3);

        assertTrue(message.matches(".*" + MortgageCalculatorViewModel.
                LogMessages.CALCULATE_WAS_PRESSED + ".*"));
    }

    @Test
    public void logContainsInputArgumentsAfterCalculation() {
        setCorrectInputs();

        viewModel.calculate();

        String message = viewModel.getLog().get(3);
        assertTrue(message.matches(".*" + viewModel.getApartmentPrice()
                + ".*" + viewModel.getFirstPayment()
                + ".*" + viewModel.getInterestRate()
                + ".*" + viewModel.getLoanPeriod()
                + ".*" + viewModel.getLoanPeriodType()
                + ".*" + viewModel.getMonthlyCommissions()
                + ".*" + viewModel.getMonthlyCommissionsType()
                + ".*" + viewModel.getOneTimeCommissions()
                + ".*" + viewModel.getOneTimeCommissionsType()
                + ".*" + viewModel.getTypeOfPayment() + ".*"));
    }

    @Test
    public void canPutSeveralLogMessages() {
        setCorrectInputs();

        viewModel.calculate();
        viewModel.calculate();

        assertEquals(5, viewModel.getLog().size());
    }

    @Test
    public void calculateIsNotWorkingWithEmptyParameters() {
        viewModel.calculate();

        assertTrue(viewModel.getLog().isEmpty());
    }

    @Test
    public void canSeeLoanPeriodTypeChangeInLog() {
        setCorrectInputs();

        viewModel.loanPeriodTypeProperty().set("Year");

        String message = viewModel.getLog().get(3);
        assertTrue(message.matches(".*" + MortgageCalculatorViewModel.
                LogMessages.LOAN_PERIOD_TYPE_WAS_CHANGED + ".*"
        ));
    }

    @Test
    public void canSeeOneTimeCommissionTypeChangeInLog() {
        setCorrectInputs();

        viewModel.oneTimeCommissionsTypeProperty().set("Rubles");

        String message = viewModel.getLog().get(3);
        assertTrue(message.matches(".*" + MortgageCalculatorViewModel.
                LogMessages.ONE_TIME_COMMISSIONS_TYPE_WAS_CHANGED + ".*"));
    }

    @Test
    public void canSeeMonthlyCommissionTypeChangeInLog() {
        setCorrectInputs();

        viewModel.monthlyCommissionsTypeProperty().set("Percent");

        String message = viewModel.getLog().get(3);
        assertTrue(message.matches(".*" + MortgageCalculatorViewModel.
                LogMessages.MONTHLY_COMMISSIONS_TYPE_WAS_CHANGED + ".*"));
    }

    @Test
    public void canSeeIncorrectInputInLog() {
        setCorrectInputs();

        viewModel.loanPeriodProperty().set("0");
        viewModel.calculate();

        String message = viewModel.getLog().get(3);
        assertTrue(message.matches(".*" + MortgageCalculatorViewModel.
                LogMessages.INCORRECT_INPUT + ".*"));
    }
}
