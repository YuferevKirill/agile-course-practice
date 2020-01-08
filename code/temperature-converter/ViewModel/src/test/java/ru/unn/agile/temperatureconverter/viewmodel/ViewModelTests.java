package ru.unn.agile.temperatureconverter.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.temperatureconverter.viewmodel.ViewModel.ListOfTemperatures;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;
    private final double delta = 0.001;

    @Before
    public void setUp() {
        FakeLogger fakeLogger = new FakeLogger();
        viewModel = new ViewModel(fakeLogger);
    }

    public void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultValues() {
        assertEquals("", viewModel.getFromTemperature());
        assertEquals("", viewModel.getResultTemperature());
        assertEquals(ListOfTemperatures.CELSIUS, viewModel.getFrom());
        assertEquals(ListOfTemperatures.CELSIUS, viewModel.getTo());
        assertEquals("", viewModel.getResultTemperature());
        assertFalse(viewModel.isConvertButtonEnabled());
        assertFalse(viewModel.isErrorMessageDisplayed());
    }

    @Test
    public void convertButtonIsDisabledInitially() {
        assertFalse(viewModel.isConvertButtonEnabled());
    }

    @Test
    public void convertButtonIsDisabledWhenFormatIsBad() {
        viewModel.setFromTemperature("trash");
        viewModel.processInput();
        assertFalse(viewModel.isConvertButtonEnabled());
    }

    @Test
    public void statusTextWhenFormatIsBad() {
        viewModel.setFromTemperature("trash");
        viewModel.processInput();
        assertEquals("Error. Please enter correct temperature", viewModel.getStatusText());
    }

    @Test
    public void convertButtonIsDisabledWithEmptyInput() {
        viewModel.processInput();

        assertFalse(viewModel.isConvertButtonEnabled());
    }

    @Test
    public void canSetCelsiusFrom() {
        viewModel.setFrom(ListOfTemperatures.CELSIUS);
        assertEquals(ListOfTemperatures.CELSIUS, viewModel.getFrom());
    }

    @Test
    public void canSetNewtonTo() {
        viewModel.setTo(ListOfTemperatures.NEWTON);
        assertEquals(ListOfTemperatures.NEWTON, viewModel.getTo());
    }

    @Test
    public void celsiusIsDefaultTemperatureFrom() {
        assertEquals(ListOfTemperatures.CELSIUS, viewModel.getFrom());
    }

    @Test
    public void celsiusIsDefaultTemperatureTo() {
        assertEquals(ListOfTemperatures.CELSIUS, viewModel.getTo());
    }

    @Test
    public void convertFromCelsiusToCelsiusHasCorrectResult() {
        viewModel.setFromTemperature("0.0");
        viewModel.calculate();

        assertEquals("0.0", viewModel.getResultTemperature());
    }

    @Test
    public void convertFromCelsiusToNewtonHasCorrectResult() {
        viewModel.setFromTemperature("33.0");
        viewModel.setTo(ListOfTemperatures.NEWTON);
        viewModel.calculate();

        assertEquals("10.89", viewModel.getResultTemperature());
    }

    @Test
    public void convertFromCelsiusToNewtonHasIncorrectResult() {
        viewModel.setFromTemperature("-300.0");
        viewModel.setTo(ListOfTemperatures.NEWTON);
        viewModel.calculate();

        assertEquals("The temperature is less than absolute zero!", viewModel.getStatusText());
    }
    @Test
    public void convertFromCelsius0ToFahrenheit32() {
        viewModel.setFromTemperature("0.0");
        viewModel.setTo(ListOfTemperatures.FAHRENHEIT);
        viewModel.calculate();

        assertEquals("32.0", viewModel.getResultTemperature());
    }

    @Test
    public void convertFromCelsius5ToFahrenheit41() {
        viewModel.setFromTemperature("5.0");
        viewModel.setTo(ListOfTemperatures.FAHRENHEIT);
        viewModel.calculate();

        assertEquals("41.0", viewModel.getResultTemperature());
    }

    @Test
    public void convertCelsius0ToKelvin273() {
        viewModel.setFrom(ListOfTemperatures.CELSIUS);
        viewModel.setFromTemperature("0.0");
        viewModel.setTo(ListOfTemperatures.KELVIN);
        viewModel.calculate();

        assertEquals("273.15", viewModel.getResultTemperature());
    }
    @Test
    public void convertFahrenheit41ToCelsius5() {
        viewModel.setFrom(ListOfTemperatures.FAHRENHEIT);
        viewModel.setFromTemperature("41.0");
        viewModel.setTo(ListOfTemperatures.CELSIUS);
        viewModel.calculate();

        assertEquals("5.0", viewModel.getResultTemperature());
    }

    @Test
    public void convertFahrenheit10ToKelvin260() {
        viewModel.setFrom(ListOfTemperatures.FAHRENHEIT);
        viewModel.setFromTemperature("10.0");
        viewModel.setTo(ListOfTemperatures.KELVIN);
        viewModel.calculate();

        assertEquals(260.927, viewModel.getDoubleResult(), delta);
    }

    @Test
    public void convertFahrenheit0ToKelvin255() {
        viewModel.setFrom(ListOfTemperatures.FAHRENHEIT);
        viewModel.setFromTemperature("0.0");
        viewModel.setTo(ListOfTemperatures.KELVIN);
        viewModel.calculate();

        assertEquals(255.372, viewModel.getDoubleResult(), delta);
    }

    @Test
    public void convertFahrenheit32ToNewton0() {
        viewModel.setFrom(ListOfTemperatures.FAHRENHEIT);
        viewModel.setFromTemperature("32.0");
        viewModel.setTo(ListOfTemperatures.NEWTON);
        viewModel.calculate();

        assertEquals("0.0", viewModel.getResultTemperature());
    }

    @Test
    public void convertKelvin0ToCelsiusAbsoluteZero() {
        viewModel.setFrom(ListOfTemperatures.KELVIN);
        viewModel.setFromTemperature("0.0");
        viewModel.setTo(ListOfTemperatures.CELSIUS);
        viewModel.calculate();

        assertEquals("-273.15", viewModel.getResultTemperature());
    }

    @Test
    public void convertKelvin0ToFahrenheitAbsoluteZero() {
        viewModel.setFrom(ListOfTemperatures.KELVIN);
        viewModel.setFromTemperature("0.0");
        viewModel.setTo(ListOfTemperatures.FAHRENHEIT);
        viewModel.calculate();

        assertEquals(-459.67, viewModel.getDoubleResult(), delta);
    }

    @Test
    public void convertKelvin455ToNewton60() {
        viewModel.setFrom(ListOfTemperatures.KELVIN);
        viewModel.setFromTemperature("455.0");
        viewModel.setTo(ListOfTemperatures.NEWTON);
        viewModel.calculate();

        assertEquals(60.01, viewModel.getDoubleResult(), delta);
    }

    @Test
    public void convertNewton0ToCelsius0() {
        viewModel.setFrom(ListOfTemperatures.NEWTON);
        viewModel.setFromTemperature("0.0");
        viewModel.setTo(ListOfTemperatures.CELSIUS);
        viewModel.calculate();

        assertEquals("0.0", viewModel.getResultTemperature());
    }

    @Test
    public void convertNewton33ToCelsius100() {
        viewModel.setFrom(ListOfTemperatures.NEWTON);
        viewModel.setFromTemperature("33.0");
        viewModel.setTo(ListOfTemperatures.CELSIUS);
        viewModel.calculate();

        assertEquals("100.0", viewModel.getResultTemperature());
    }

    @Test
    public void convertNewton0ToFahrenheit32() {
        viewModel.setFrom(ListOfTemperatures.NEWTON);
        viewModel.setFromTemperature("0.0");
        viewModel.setTo(ListOfTemperatures.FAHRENHEIT);
        viewModel.calculate();

        assertEquals("32.0", viewModel.getResultTemperature());
    }

    @Test
    public void convertNewton61ToKelvin458() {
        viewModel.setFrom(ListOfTemperatures.NEWTON);
        viewModel.setFromTemperature("61.0");
        viewModel.setTo(ListOfTemperatures.KELVIN);
        viewModel.calculate();

        assertEquals(457.998, viewModel.getDoubleResult(), delta);
    }

    @Test
    public void convertCelsius0ToCelsius0() {
        viewModel.setTo(ListOfTemperatures.CELSIUS);
        viewModel.setFromTemperature("0.0");
        viewModel.setTo(ListOfTemperatures.CELSIUS);
        viewModel.calculate();

        assertEquals("0.0", viewModel.getResultTemperature());
    }

    @Test
    public void convertCelsius100ToCelsius100() {
        viewModel.setTo(ListOfTemperatures.CELSIUS);
        viewModel.setFromTemperature("100.0");
        viewModel.setTo(ListOfTemperatures.CELSIUS);
        viewModel.calculate();

        assertEquals("100.0", viewModel.getResultTemperature());
    }

    @Test
    public void canCreateViewModelWithLogger() {
        FakeLogger logger = new FakeLogger();
        ViewModel viewModel = new ViewModel(logger);

        assertNotNull(viewModel);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenCreateViewModelWithNullLogger() {
        ViewModel viewModel = new ViewModel(null);
    }

    @Test
    public void doesLogMessageContainTwoMessageInitially() {
        List<String> log = viewModel.getLog();
        assertEquals(2, log.size());
    }

    @Test
    public void isLogMessageNotEmptyAfterSetFromTemperature() {
        viewModel.setFromTemperature("0.0");
        List<String> log = viewModel.getLog();

        assertTrue(log.get(2).matches(".*" + "Input updated" + ".*"));
    }

    @Test
    public void doesLogMessageContainCorrectTemperature() {
        viewModel.setFromTemperature("0.0");
        List<String> log = viewModel.getLog();

        assertTrue(log.get(2).matches(".*"
                + "Input updated: value of from temperature = " + 0.0 + ".*"));
    }

    @Test
    public void isLogMessageNotEmptyAfterSetFrom() {
        viewModel.setFrom(ListOfTemperatures.NEWTON);
        List<String> log = viewModel.getLog();

        assertTrue(log.get(2).matches(".*" + "Input updated" + ".*"));
    }

    @Test
    public void doesLogMessageContainCorrectFromOption() {
        viewModel.setFrom(ListOfTemperatures.KELVIN);
        List<String> log = viewModel.getLog();

        assertTrue(log.get(2).matches(".*" + "Input updated: "
                + "from temperature = " + ListOfTemperatures.KELVIN + ".*"));
    }

    @Test
    public void isLogMessageNotEmptyAfterSetTo() {
        viewModel.setTo(ListOfTemperatures.FAHRENHEIT);
        List<String> log = viewModel.getLog();

        assertTrue(log.get(2).matches(".*" + "Input updated" + ".*"));
    }

    @Test
    public void doesLogMessageContainCorrectToOption() {
        viewModel.setTo(ListOfTemperatures.FAHRENHEIT);
        List<String> log = viewModel.getLog();

        assertTrue(log.get(2).matches(".*" + "Input updated: "
                + "to temperature = " + ListOfTemperatures.FAHRENHEIT + ".*"));
    }

    @Test
    public void doesLogMessageContainErrorMessage() {
        viewModel.setFromTemperature("abc");
        viewModel.processInput();
        List<String> log = viewModel.getLog();

        assertTrue(log.get(3).matches(".*" + "Error is displayed" + ".*"));
    }

    @Test
    public void doesLogMessageContainErrorMessageWithTextInCaseIncorrectInput() {
        viewModel.setFromTemperature("abc");
        viewModel.processInput();
        List<String> log = viewModel.getLog();

        assertTrue(log.get(3).matches(".*" + "Error is displayed: "
                + viewModel.getStatusText() + ".*"));
    }

    @Test
    public void doesLogMessageContainErrorMessageWithTextInCaseAbsoluteZero() {
        viewModel.setFromTemperature("-300");
        viewModel.processInput();
        List<String> log = viewModel.getLog();

        assertTrue(log.get(3).matches(".*" + "Error is displayed: "
                + viewModel.getStatusText() + ".*"));
    }

    @Test
    public void doesLogMessageContainInfoAboutPressCalculate() {
        viewModel.setFromTemperature("0.0");
        viewModel.calculate();
        List<String> log = viewModel.getLog();

        assertTrue(log.get(3).matches(".*" + "Calculate" + ".*"));
    }

    @Test
    public void isLogMessageNotUpdatedAfterSetEqualFromTemperature() {
        viewModel.setFromTemperature("0.0");
        viewModel.setFromTemperature("0.0");
        List<String> log = viewModel.getLog();

        assertEquals(3, log.size());
    }

    @Test
    public void isLogMessageNotUpdatedAfterSetEqualFrom() {
        viewModel.setFrom(ListOfTemperatures.KELVIN);
        viewModel.setFrom(ListOfTemperatures.KELVIN);
        List<String> log = viewModel.getLog();

        assertEquals(3, log.size());
    }

    @Test
    public void doesLogMessageContainDefaultFromTemperature() {
        List<String> log = viewModel.getLog();

        assertTrue(log.get(0).matches(".*" + "Input updated: "
                + "from temperature = " + ListOfTemperatures.CELSIUS + ".*"));
    }

    @Test
    public void doesLogMessageContainDefaultToTemperature() {
        List<String> log = viewModel.getLog();

        assertTrue(log.get(1).matches(".*" + "Input updated: "
                + "to temperature = " + ListOfTemperatures.CELSIUS + ".*"));
    }

    @Test
    public void isLogMessageNotUpdatedAfterSetEqualTo() {
        viewModel.setTo(ListOfTemperatures.KELVIN);
        viewModel.setTo(ListOfTemperatures.KELVIN);
        List<String> log = viewModel.getLog();

        assertEquals(3, log.size());
    }

    @Test
    public void doesLogMessageContainResultAfterCalculate() {
        viewModel.setFromTemperature("0.0");
        viewModel.calculate();
        List<String> log = viewModel.getLog();

        assertTrue(log.get(4).matches(".*" + "Result temperature = 0.0" + ".*"));
    }

    @Test
    public void doesLogMessageNotContainResultAfterCalculateWithBadInput() {
        viewModel.setFromTemperature("abc");
        viewModel.calculate();
        List<String> log = viewModel.getLog();

        assertEquals(5, log.size());
    }

    @Test
    public void doesLogMessageContainFromForCalculate() {
        viewModel.setFromTemperature("0.0");
        viewModel.calculate();
        List<String> log = viewModel.getLog();

        assertTrue(log.get(4).matches(".*" + "From: " + ListOfTemperatures.CELSIUS + ".*"));
    }

    @Test
    public void doesLogMessageContainToForCalculate() {
        viewModel.setFromTemperature("0.0");
        viewModel.calculate();
        List<String> log = viewModel.getLog();

        assertTrue(log.get(4).matches(".*" + "To: " + ListOfTemperatures.CELSIUS + ".*"));
    }

    @Test
    public void doesLogMessageContainFromTemperatureForCalculate() {
        viewModel.setFromTemperature("0.0");
        viewModel.calculate();
        List<String> log = viewModel.getLog();

        assertTrue(log.get(4).matches(".*" + "Initial temperature = 0.0" + ".*"));
    }

    @Test
    public void doesLogMessageContainCorrectError() {
        viewModel.setFromTemperature("0.");
        viewModel.processInput();
        List<String> log = viewModel.getLog();

        assertTrue(log.get(3).matches(".*" + "Error is displayed: "
                + "Error. Please enter correct temperature" + ".*"));
    }
}
