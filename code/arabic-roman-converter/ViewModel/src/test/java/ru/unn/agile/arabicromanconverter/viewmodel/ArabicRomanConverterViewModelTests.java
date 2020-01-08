package ru.unn.agile.arabicromanconverter.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.arabicromanconverter.model.*;

import java.util.List;

import static org.junit.Assert.*;

public class ArabicRomanConverterViewModelTests {

    private ViewModel viewModel;

    public void setExternalViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new ViewModel(new FakeLogger());
        }
    }

    public void setCorrectInput() {
        viewModel.getInput().set("1");
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateViewModelWithNullLogger() {
        new ViewModel(null);
    }

    @Test
    public void logIsEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsProperMessageAfterSetInput() {
        setCorrectInput();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.EDITING_FINISHED + ".*"));
    }

    @Test
    public void logContainsNewValueAfterSetInput() {
        String newValue = "1";
        viewModel.getInput().set(newValue);
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.EDITING_FINISHED + newValue + ".*"));
    }

    @Test
    public void logContainsProperMessageAfterConvertion() {
        setCorrectInput();
        viewModel.convert();
        String message = viewModel.getLog().get(1);

        assertTrue(message.matches(".*" + LogMessages.CONVERT_WAS_PRESSED + ".*"));
    }

    @Test
    public void logContainsInputAfterConvertion() {
        String input = "1";
        viewModel.getInput().set(input);
        viewModel.convert();
        String message = viewModel.getLog().get(1);

        assertTrue(message.matches(".*" + "from number: " + input + ".*"));
    }

    @Test
    public void logContainsOutputAfterConvertion() {
        String output = "I";
        setCorrectInput();
        viewModel.convert();
        String message = viewModel.getLog().get(1);

        assertTrue(message.matches(".*" + "to number: " + output + ".*"));
    }

    @Test
    public void logContainsProperMessageAfterSwapConverterType() {
        viewModel.swap();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.CONVERTER_WAS_CHANGED + ".*"));
    }

    @Test
    public void logContainsOldConverterTypeAfterSwap() {
        String oldConverterType = ConverterType.ARABICTOROMAN.toString();
        viewModel.swap();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + "from type: " + oldConverterType + ".*"));
    }

    @Test
    public void logContainsNewConverterTypeAfterSwap() {
        String newConverterType = ConverterType.ROMANTOARABIC.toString();
        viewModel.swap();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + "to type: " + newConverterType + ".*"));
    }

    @Test
    public void logContainsOldConverterTypeAfterSecondSwap() {
        String oldConverterType = ConverterType.ROMANTOARABIC.toString();
        viewModel.swap();
        viewModel.swap();
        String message = viewModel.getLog().get(1);

        assertTrue(message.matches(".*" + "from type: " + oldConverterType + ".*"));
    }

    @Test
    public void logContainsNewConverterTypeAfterSecondSwap() {
        String newConverterType = ConverterType.ARABICTOROMAN.toString();
        viewModel.swap();
        viewModel.swap();
        String message = viewModel.getLog().get(1);

        assertTrue(message.matches(".*" + "to type: " + newConverterType + ".*"));
    }

    @Test
    public void logContainsProperMessageAfterIncorrectInput() {
        viewModel.getInput().set("bag");
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.INCORRECT_INPUT + ".*"));
    }

    @Test
    public void logContainsIncorrectValueAfterIncorrectInput() {
        String incorrectValue = "bag";

        viewModel.getInput().set(incorrectValue);
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.INCORRECT_INPUT + incorrectValue + ".*"));
    }

    @Test
    public void logDoesntContainMessagesIfInputNotChanged() {
        viewModel.getInput().set("1");
        viewModel.getInput().set("1");

        assertEquals(1, viewModel.getLog().size());
    }

    @Test
    public void logContainsAllTypesOfMessages() {
        viewModel.swap();
        viewModel.getInput().set("1");
        viewModel.getInput().set("I");
        viewModel.convert();

        assertEquals(4, viewModel.getLog().size());
    }

    @Test
    public void canSetEmptyInputField() {
        assertEquals("", viewModel.getInput().get());
    }

    @Test
    public void canSetEmptyOutputField() {
        assertEquals("", viewModel.getOutput().get());
    }

    @Test
    public void canSetEmptyErrorField() {
        assertEquals("", viewModel.getError().get());
    }

    @Test
    public void canRefreshField() {
        viewModel.getInput().set("test_input");
        viewModel.getOutput().set("test_output");
        viewModel.getError().set("test_error");

        viewModel.refresh();

        String result = viewModel.getInput().get()
                + viewModel.getOutput().get()
                + viewModel.getError().get();
        assertEquals("", result);
    }

    @Test
    public void isConvertLabelArabicByDefault() {
        assertEquals("Arabic to Roman", viewModel.getConvert().get());
    }

    @Test
    public void isConvertButtonDisabledByDefault() {
        assertEquals(true, viewModel.isConvertButtonDisabled().get());
    }

    @Test
    public void isConvertButtonNotDisabledAfterInsertingValue() {
        setCorrectInput();

        assertEquals(false, viewModel.isConvertButtonDisabled().get());
    }

    @Test
    public void isConvertButtonDisabledAfterDeletingValue() {
        setCorrectInput();
        viewModel.getInput().set("");

        assertEquals(true, viewModel.isConvertButtonDisabled().get());
    }

    @Test
    public void cantEnterNanWhileArabicSelected() {
        viewModel.getInput().set("I");

        assertEquals("Insert correct arabic number", viewModel.getError().get());
    }

    @Test
    public void canConvertFromArabicToRoman() {
        setCorrectInput();
        viewModel.convert();

        assertEquals("I", viewModel.getOutput().get());
    }

    @Test
    public void isConvertLabelRomanAfterSwap() {
        viewModel.swap();

        assertEquals("Roman to Arabic", viewModel.getConvert().get());
    }

    @Test
    public void cantEnterNanWhileRomanSelected() {
        viewModel.swap();
        setCorrectInput();

        assertEquals("Insert correct roman number", viewModel.getError().get());
    }

    @Test
    public void cantEnterNegativeWhileArabicSelected() {
        viewModel.getInput().set("-1");

        assertEquals("Insert correct arabic number", viewModel.getError().get());
    }

    @Test
    public void cantEnterNumberMoreThanMaxWhileArabicSelected() {
        viewModel.getInput().set("4000");

        assertEquals("Insert correct arabic number", viewModel.getError().get());
    }

    @Test
    public void canConvertFromRomanToArabic() {
        viewModel.swap();
        viewModel.getInput().set("I");

        viewModel.convert();

        assertEquals("1", viewModel.getOutput().get());
    }

    @Test
    public void canConvertWhileNonValidationError() {
        setCorrectInput();

        assertEquals(false, viewModel.isConvertButtonDisabled().get());
    }

    @Test
    public void cantConvertWhileGettingValidationError() {
        viewModel.getInput().set("I");

        assertEquals(true, viewModel.isConvertButtonDisabled().get());
    }

    @Test
    public void canConvertWhileNonValidationErrorRomanToArabic() {
        viewModel.swap();
        viewModel.getInput().set("I");

        assertEquals(false, viewModel.isConvertButtonDisabled().get());
    }

    @Test
    public void cantConvertWhileGettingValidationErrorRomanToArabic() {
        viewModel.swap();
        setCorrectInput();

        assertEquals(true, viewModel.isConvertButtonDisabled().get());
    }

    @Test
    public void isCleanFieldsAfterSwap() {
        viewModel.getInput().set("10");
        viewModel.convert();

        viewModel.swap();

        String result = viewModel.getInput().get()
                + viewModel.getOutput().get()
                + viewModel.getError().get();
        assertEquals("", result);
    }
}
