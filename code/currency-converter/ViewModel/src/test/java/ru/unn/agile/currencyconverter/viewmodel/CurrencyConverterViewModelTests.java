package ru.unn.agile.currencyconverter.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.currencyconverter.model.CurrencyPair;

import java.util.List;

import static org.junit.Assert.*;

public class CurrencyConverterViewModelTests {

    private CurrencyConverterViewModel viewModel;

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new CurrencyConverterViewModel(new SimpleLogger());
        }
    }

    public void setExternalViewModel(final CurrencyConverterViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void setsEmptyStingToInputByDefault() {
        assertEquals("", viewModel.getInputCurrency().get());
    }

    @Test
    public void setsEmptyStringInOutputByDefault() {
        assertEquals("", viewModel.getOutputCurrency().get());
    }

    @Test
    public void setsConvertButtonDisabledByDefault() {
        assertTrue(viewModel.isConvertButtonDisabled().get());
    }

    @Test
    public void setsListFromPairsByDefault() {
        assertEquals(CurrencyPair.RUBLE_TO_DOLLAR, viewModel.getCurrencyPair().get());
    }

    @Test
    public void currencyPairsHasCorrectValues() {
        assertArrayEquals(CurrencyPair.values(), viewModel.getCurrencyPairs().toArray());
    }

    @Test
    public void setsConvertButtonEnabledAfterInput() {
        viewModel.getInputCurrency().set("1");

        assertFalse(viewModel.isConvertButtonDisabled().get());
    }

    @Test
    public void setsConvertButtonDisabledAfterClearInput() {
        viewModel.getInputCurrency().set("1");
        viewModel.getInputCurrency().set("");

        assertTrue(viewModel.isConvertButtonDisabled().get());
    }

    @Test
    public void hasErrorAfterIncorrectInput() {
        viewModel.getInputCurrency().set("word");

        assertEquals(CurrencyConverterViewModel.LogMessages.INCORRECT_INPUT,
                viewModel.getError().get());
    }

    @Test
    public void hasNoErrorAfterCorrectInput() {
        viewModel.getInputCurrency().set("word");
        viewModel.getInputCurrency().set("3");

        assertEquals("", viewModel.getError().get());
    }

    @Test
    public void doesntConvertWithIncorrectInput() {
        viewModel.getInputCurrency().set("word");

        assertTrue(viewModel.isConvertButtonDisabled().get());
    }

    @Test
    public void hasNoErrorByDefault() {
        assertEquals("", viewModel.getError().get());
    }

    @Test
    public void hasNoErrorAfterClearInput() {
        viewModel.getInputCurrency().set("word");
        viewModel.getInputCurrency().set("");

        assertEquals("", viewModel.getError().get());
    }

    @Test
    public void setsEuroToDollarInCurrencyPair() {
        viewModel.getCurrencyPair().set(CurrencyPair.EURO_TO_DOLLAR);

        assertEquals(CurrencyPair.EURO_TO_DOLLAR, viewModel.getCurrencyPair().get());
    }

    @Test
    public void setsDollarToEuroInCurrencyPair() {
        viewModel.getCurrencyPair().set(CurrencyPair.DOLLAR_TO_EURO);

        assertEquals(CurrencyPair.DOLLAR_TO_EURO, viewModel.getCurrencyPair().get());
    }

    @Test
    public void setsDollarToRubleInCurrencyPair() {
        viewModel.getCurrencyPair().set(CurrencyPair.DOLLAR_TO_RUBLE);

        assertEquals(CurrencyPair.DOLLAR_TO_RUBLE, viewModel.getCurrencyPair().get());
    }

    @Test
    public void setsEuroToRubleInCurrencyPair() {
        viewModel.getCurrencyPair().set(CurrencyPair.EURO_TO_RUBLE);

        assertEquals(CurrencyPair.EURO_TO_RUBLE, viewModel.getCurrencyPair().get());
    }

    @Test
    public void setsRubleToEuroInCurrencyPair() {
        viewModel.getCurrencyPair().set(CurrencyPair.RUBLE_TO_EURO);

        assertEquals(CurrencyPair.RUBLE_TO_EURO, viewModel.getCurrencyPair().get());
    }

    @Test
    public void convertsRubleToDollarByDefault() {
        viewModel.getInputCurrency().set("10000");

        viewModel.convert();

        assertEquals("160.0", viewModel.getOutputCurrency().get());
    }

    @Test
    public void convertsEuroToRuble() {
        viewModel.getInputCurrency().set("1");

        viewModel.getCurrencyPair().set(CurrencyPair.EURO_TO_RUBLE);
        viewModel.convert();

        assertEquals("69.86", viewModel.getOutputCurrency().get());
    }

    @Test
    public void convertsDollarToRuble() {
        viewModel.getInputCurrency().set("10");

        viewModel.getCurrencyPair().set(CurrencyPair.DOLLAR_TO_RUBLE);
        viewModel.convert();

        assertEquals("625.5", viewModel.getOutputCurrency().get());
    }

    @Test
    public void convertsRubleToEuro() {
        viewModel.getInputCurrency().set("10000");

        viewModel.getCurrencyPair().set(CurrencyPair.RUBLE_TO_EURO);
        viewModel.convert();

        assertEquals("140.0", viewModel.getOutputCurrency().get());
    }

    @Test
    public void convertsDollarToEuro() {
        viewModel.getInputCurrency().set("10000");

        viewModel.getCurrencyPair().set(CurrencyPair.DOLLAR_TO_EURO);
        viewModel.convert();

        assertEquals("8900.0", viewModel.getOutputCurrency().get());
    }

    @Test
    public void convertsEuroToDollar() {
        viewModel.getInputCurrency().set("1");

        viewModel.getCurrencyPair().set(CurrencyPair.EURO_TO_DOLLAR);
        viewModel.convert();

        assertEquals("1.12", viewModel.getOutputCurrency().get());
    }

    @Test
    public void clearsOutputAfterChangeInput() {
        viewModel.getInputCurrency().set("1");

        viewModel.convert();
        viewModel.getInputCurrency().set("2");

        assertEquals("", viewModel.getOutputCurrency().get());
    }

    @Test
    public void clearsOutputAfterChangeCurrencyPair() {
        viewModel.getInputCurrency().set("2");

        viewModel.convert();
        viewModel.getCurrencyPair().set(CurrencyPair.RUBLE_TO_EURO);

        assertEquals("", viewModel.getOutputCurrency().get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void canInitLoggerWithNull() {
        viewModel.setLogger(null);
    }

    @Test
    public void logIsEmptyInTheBeginning() {
        List<String> log = viewModel.getLogList();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsMessageThatInputCorrectAfterConversion() {
        viewModel.getInputCurrency().set("2");

        viewModel.convert();
        String message = viewModel.getLogList().get(0);

        assertTrue(message.matches(".*"
                + CurrencyConverterViewModel.LogMessages.CORRECT_INPUT + ".*"));
    }

    @Test
    public void logContainsMessageWithConvertedValueAfterConversion() {
        viewModel.getInputCurrency().set("2");

        viewModel.convert();
        String message = viewModel.getLogList().get(1);

        assertTrue(message.matches(".*"
                + CurrencyPair.RUBLE_TO_DOLLAR.toString() + ".*"
                + CurrencyConverterViewModel.LogMessages.CONVERT_TO + ".*"));
    }

    @Test
    public void logContainsCorrectMessageAfterWrongInput() {
        viewModel.getInputCurrency().set("string");

        String message = viewModel.getLogList().get(0);

        assertTrue(message.matches(".*"
                + CurrencyConverterViewModel.LogMessages.INCORRECT_INPUT + ".*"));
    }

    @Test
    public void logContainsInputArgumentsAfterAddingEdge() {
        viewModel.getCurrencyPair().set(CurrencyPair.DOLLAR_TO_EURO);

        String message = viewModel.getLogList().get(0);

        assertTrue(message.matches(".*"
                + CurrencyConverterViewModel.LogMessages.CHANGED_BY  + ".*"));
    }
}
