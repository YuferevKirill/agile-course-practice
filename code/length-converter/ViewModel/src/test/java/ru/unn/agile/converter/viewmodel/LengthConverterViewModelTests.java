package ru.unn.agile.converter.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.converter.model.LengthType;

import java.util.List;

import static org.junit.Assert.*;

public class LengthConverterViewModelTests {

    private LengthConverterViewModel viewModel;

    public void setExternalViewModel(final LengthConverterViewModel viewModel) {
        this.viewModel = viewModel;
    }

    private void setCorrectInput() {
        viewModel.getInput().set("2");
        viewModel.getFromType().set(LengthType.KILOMETER);
        viewModel.getToType().set(LengthType.METER);
    }

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new LengthConverterViewModel(new LengthConverterFakeLogger());
        }
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetEmptyStringInTextFieldsByDefault() {
        assertEquals("", viewModel.getInput().get());
        assertEquals("", viewModel.getOutput().get());
    }

    @Test
    public void canSetConvertButtonDisabledByDefault() {
        assertTrue(viewModel.isConvertButtonDisabled().get());
    }

    @Test
    public void canSetListFromTypesByDefault() {
        assertEquals(LengthType.METER, viewModel.getFromType().get());
    }

    @Test
    public void canSetListToTypesByDefault() {
        assertEquals(LengthType.CENTIMETER, viewModel.getToType().get());
    }

    @Test
    public void isLengthTypesListHasCorrectValues() {
        assertEquals(LengthType.values()[3], viewModel.getLengthTypes().get(3));
    }

    @Test
    public void canSetConvertButtonEnabledAfterInput() {
        viewModel.getInput().set("1");

        assertFalse(viewModel.isConvertButtonDisabled().get());
    }

    @Test
    public void canSetConvertButtonDisabledAfterClearInput() {
        viewModel.getInput().set("1");
        viewModel.getInput().set("");

        assertTrue(viewModel.isConvertButtonDisabled().get());
    }

    @Test
    public void canShowErrorAfterInvalidInput() {
        viewModel.getInput().set("one");

        assertEquals("invalid", viewModel.getError().get());
    }

    @Test
    public void canClearErrorAfterValidInput() {
        viewModel.getInput().set("one");
        viewModel.getInput().set("3");

        assertEquals("", viewModel.getError().get());
    }

    @Test
    public void canSetConvertButtonDisabledInvalidInput() {
        viewModel.getInput().set("one");

        assertTrue(viewModel.isConvertButtonDisabled().get());
    }

    @Test
    public void canClearErrorByDefault() {
        assertEquals("", viewModel.getError().get());
    }

    @Test
    public void canClearErrorAfterClearInput() {
        viewModel.getInput().set("one");
        viewModel.getInput().set("");

        assertEquals("", viewModel.getError().get());
    }

    @Test
    public void canSetMileInFromType() {
        viewModel.getFromType().set(LengthType.MILE);

        assertEquals(LengthType.MILE, viewModel.getFromType().get());
    }

    @Test
    public void canSetFootInToType() {
        viewModel.getToType().set(LengthType.FOOT);

        assertEquals(LengthType.FOOT, viewModel.getToType().get());
    }

    @Test
    public void canConvert() {
        viewModel.getInput().set("1");

        viewModel.convert();

        assertEquals("100", viewModel.getOutput().get());
    }

    @Test
    public void canClearOutputAfterChangeInput() {
        viewModel.getInput().set("1");

        viewModel.convert();
        viewModel.getInput().set("2");

        assertEquals("", viewModel.getOutput().get());
    }

    @Test
    public void canConvert2MetersTo200Centimeters() {
        viewModel.getInput().set("2");

        viewModel.convert();

        assertEquals("200", viewModel.getOutput().get());
    }

    @Test
    public void canConvert2KilometersTo2000Meters() {
        viewModel.getInput().set("2");
        viewModel.getFromType().set(LengthType.KILOMETER);
        viewModel.getToType().set(LengthType.METER);

        viewModel.convert();

        assertEquals("2000", viewModel.getOutput().get());
    }

    @Test
    public void canConvert2InchesTo0050799972568014815Meters() {
        viewModel.getInput().set("2");
        viewModel.getFromType().set(LengthType.INCH);
        viewModel.getToType().set(LengthType.METER);

        viewModel.convert();

        assertEquals("0.050799972568014815", viewModel.getOutput().get());
    }

    @Test
    public void canClearOutputAfterChangeFromType() {
        viewModel.getInput().set("2");

        viewModel.convert();
        viewModel.getFromType().set(LengthType.INCH);

        assertEquals("", viewModel.getOutput().get());
    }

    @Test
    public void canClearOutputAfterChangeToType() {
        viewModel.getInput().set("2");

        viewModel.convert();
        viewModel.getToType().set(LengthType.INCH);

        assertEquals("", viewModel.getOutput().get());
    }

    @Test
    public void canSwapFromAndToTypes() {
        viewModel.getFromType().set(LengthType.KILOMETER);
        viewModel.getToType().set(LengthType.MILE);

        viewModel.swap();

        assertEquals(LengthType.KILOMETER, viewModel.getToType().get());
        assertEquals(LengthType.MILE, viewModel.getFromType().get());
    }

    @Test
    public void canClearOutputAfterSwap() {
        viewModel.getInput().set("1");

        viewModel.convert();
        viewModel.swap();

        assertEquals("", viewModel.getOutput().get());
    }

    @Test
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        try {
            new LengthConverterViewModel(null);
            fail("Exception wasn't thrown");
        } catch (IllegalArgumentException ex) {
            assertEquals("Logger parameter can't be null", ex.getMessage());
        } catch (Exception ex) {
            fail("Invalid exception type");
        }
    }

    @Test
    public void logIsEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsProperMessageAfterConvertion() {
        setCorrectInput();
        viewModel.convert();
        String message = viewModel.getLog().get(2);

        assertTrue(message.matches(".*" + LogMessages.CONVERT_WAS_PRESSED + ".*"));
    }

    @Test
    public void logContainsInputArgumentsAfterConvertion() {
        setCorrectInput();

        viewModel.convert();

        String message = viewModel.getLog().get(2);
        assertTrue(message.matches(".*" + viewModel.getFromType().get()
                + ".*" + viewModel.getToType().get() + ".*"));
    }

    @Test
    public void swappingIsMentionedInTheLog() {
        setCorrectInput();

        viewModel.swap();

        String message = viewModel.getLog().get(4);
        assertTrue(message.matches(".*" + LogMessages.SWAP_PRESSED + ".*"));
    }

    @Test
    public void canPutSeveralLogMessages() {
        setCorrectInput();

        viewModel.convert();
        viewModel.convert();

        assertEquals(4, viewModel.getLog().size());
    }

    @Test
    public void canSeeFromTypeChangeInLog() {
        setCorrectInput();

        viewModel.getFromType().set(LengthType.METER);

        String message = viewModel.getLog().get(2);
        assertTrue(message.matches(".*" + LogMessages.START_LENGHT_TYPE_WAS_CHANGED + ".*"));
    }

    @Test
    public void canSeeToTypeChangeInLog() {
        setCorrectInput();

        viewModel.getToType().set(LengthType.CENTIMETER);

        String message = viewModel.getLog().get(2);
        assertTrue(message.matches(".*" + LogMessages.RESULT_LENGHT_TYPE_WAS_CHANGED + ".*"));
    }

    @Test
    public void typeChangeIsNotLoggedIfNotChanged() {
        viewModel.getToType().set(LengthType.CENTIMETER);
        viewModel.getFromType().set(LengthType.METER);

        assertEquals(0, viewModel.getLog().size());
    }

    @Test
    public void convertIsNotCalledWhenButtonIsDisabled() {
        viewModel.convert();

        assertTrue(viewModel.getLog().isEmpty());
    }
}
