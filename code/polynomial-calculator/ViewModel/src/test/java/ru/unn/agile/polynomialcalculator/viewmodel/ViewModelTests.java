package ru.unn.agile.polynomialcalculator.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.polynomialcalculator.model.Polynomial;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        FakeLogger fakeLogger = new FakeLogger();
        viewModel = new ViewModel(fakeLogger);
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void isAddingValidInput() {
        setInputData("2.5", "1");
        viewModel.addPolynomial();
        assertEquals(new Polynomial(2.5, 1).getCoef(1),
                viewModel.getPolynomialsList().get(0).getCoef(1), 1e-10);
        assertEquals(new Polynomial(2.5, 1).getDegree(),
                viewModel.getPolynomialsList().get(0).getDegree());
    }

    @Test(expected = IllegalArgumentException.class)
    public void isNotAddedInvalidInput() {
        setInputData("-26vrt1.55", "2..645");
        viewModel.addPolynomial();
    }

    @Test(expected = IllegalArgumentException.class)
    public void isNotAddedEmptyInput() {
        setInputData("", "");
        viewModel.addPolynomial();
    }

    @Test
    public void cantCalcPolynomialWhenPointListIsEmpty() {
        viewModel.calcPolynomialAdd();
        assertEquals(null, viewModel.resultProperty().get());
    }

    @Test
    public void canCalcPolyOfTwoPolynomials() {
        setInputData("1.1", "0");
        viewModel.addPolynomial();
        setInputData("2.4", "0");
        viewModel.addPolynomial();
        viewModel.calcPolynomialAdd();
        assertEquals(3.5, Double.parseDouble(viewModel.resultProperty().get()), 1e-12);
    }

    @Test
    public void isFormInputsEmptyAfterAddingNewPolynomials() {
        setInputData("-36.516", "3");
        viewModel.addPolynomial();
        assertTrue(viewModel.degreeProperty().get().isEmpty()
                && viewModel.coeffProperty().get().isEmpty()
        );
    }

    @Test
    public void canCreateViewModelWithLogger() {
        FakeLogger logger = new FakeLogger();
        ViewModel viewModel = new ViewModel(logger);

        assertNotNull(viewModel);
    }

    @Test(expected = NullPointerException.class)
    public void throwWhenCreateViewModelWithNullLogger() {
        ViewModel viewModel = new ViewModel(null);
    }

    @Test
    public void doesLogMessageContainOneMessageInitially() {
        List<String> log = viewModel.getLog();
        assertEquals(1, log.size());
        assertTrue(log.get(0).matches(".*" + "Clear input" + ".*"));
    }

    @Test
    public void canLogAddingPolinomial() {
        setInputData("1.1", "0");
        viewModel.addPolynomial();

        List<String> log = viewModel.getLog();
        assertEquals(5, log.size());
        assertTrue(log.get(1).matches(".*"
                + "Check of polynomial is passed: degree = 0, coeff = 1.1" + ".*"));
        assertTrue(log.get(2).matches(".*" + "Added new polynomial: 1.1" + ".*"));
        assertTrue(log.get(3).matches(".*" + "Updated list of polynomials: \\[1.1\\]" + ".*"));
        assertTrue(log.get(4).matches(".*" + "Clear input" + ".*"));
    }

    @Test
    public void canLogCalcPolynomialAdd() {
        setInputData("1.1", "0");
        viewModel.addPolynomial();
        viewModel.calcPolynomialAdd();

        List<String> log = viewModel.getLog();

        assertEquals(7, log.size());
        assertTrue(log.get(5).matches(".*"
                + "Trying to compute sum of farther polynomials: \\[1.1\\]" + ".*"));
        assertTrue(log.get(6).matches(".*" + "Result is 1.1" + ".*"));
    }

    @Test
    public void canLogEmptyList() {
        viewModel.calcPolynomialAdd();

        List<String> log = viewModel.getLog();

        assertEquals(2, log.size());
        assertTrue(log.get(1).matches(".*"
                + "Polynomial add can't be calculated due to empty list" + ".*"));
    }

    @Test
    public void canLogWronginput() {
        setInputData("1.1f", "0");
        try {
            viewModel.addPolynomial();
        } catch (Exception e) {
            // nothing
        }

        List<String> log = viewModel.getLog();

        assertEquals(2, log.size());
        assertTrue(log.get(1).matches(".*"
                + "Check of polynomial is failed: degree = 0, coeff = 1.1f" + ".*"));
    }

    private void setInputData(final String coeff, final String degree) {
        viewModel.coeffProperty().set(coeff);
        viewModel.degreeProperty().set(degree);
    }

    protected void setViewModel(final ViewModel other) {
        viewModel = other;
    }
}
