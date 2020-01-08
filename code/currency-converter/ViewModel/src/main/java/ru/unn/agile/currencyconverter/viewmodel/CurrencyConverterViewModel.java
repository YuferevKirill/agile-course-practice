package ru.unn.agile.currencyconverter.viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.currencyconverter.model.CurrencyConverter;
import ru.unn.agile.currencyconverter.model.CurrencyPair;

import java.util.List;

public class CurrencyConverterViewModel {

    private ObjectProperty<CurrencyPair> currencyPair = new SimpleObjectProperty<>();
    private StringProperty error = new SimpleStringProperty();
    private StringProperty inputCurrency = new SimpleStringProperty();
    private StringProperty outputCurrency = new SimpleStringProperty();
    private BooleanProperty btnDisabled = new SimpleBooleanProperty();
    private StringProperty log = new SimpleStringProperty();
    private ILogger logger;
    private List<String> logList;

    private final ObjectProperty<ObservableList<CurrencyPair>> currencyPairs =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(CurrencyPair.values()));

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public CurrencyConverterViewModel(final ILogger logger) {
        this();
        setLogger(logger);
    }

    public CurrencyConverterViewModel() {
        inputCurrency.set("");
        error.set("");
        outputCurrency.set("");
        btnDisabled.set(true);
        currencyPair.set(CurrencyPair.RUBLE_TO_DOLLAR);
        log.set("");

        inputCurrency.addListener((observable, oldValue, newValue) -> onInput(newValue));

        currencyPair.addListener((observable, oldValue, newValue) -> {
            onTypeChange(oldValue, newValue);
        });
    }

    public StringProperty getInputCurrency() {
        return inputCurrency;
    }

    public StringProperty getOutputCurrency() {
        return outputCurrency;
    }

    public BooleanProperty isConvertButtonDisabled() {
        return btnDisabled;
    }

    public ObjectProperty<CurrencyPair> getCurrencyPair() {
        return currencyPair;
    }

    public ObservableList<CurrencyPair> getCurrencyPairs() {
        return currencyPairs.get();
    }

    public StringProperty getError() {
        return error;
    }

    public StringProperty getLog() {
        return log;
    }

    public List<String> getLogList() {
        logList = logger.getLog();
        return logList;
    }

    public void addToLog(final String row) {
        log.set(log.get().concat(row));
        logger.log(row);
    }

    public void convert() {
        double value = Double.parseDouble(inputCurrency.get());
        value = CurrencyConverter.convert(getCurrencyPair().get(), value);
        outputCurrency.set(String.format("%s", value));
        addToLog(getCurrencyPair().getValue() + ": "
                + inputCurrency.get() + LogMessages.CONVERT_TO + outputCurrency.get());
    }

    private void onInput(final String newValue) {
        boolean isDouble = newValue.matches("\\d+(\\.\\d+)?");
        btnDisabled.set(newValue.isEmpty());
        if (!isDouble && !newValue.isEmpty()) {
            error.set(LogMessages.INCORRECT_INPUT);
            addToLog(LogMessages.INCORRECT_INPUT);
            btnDisabled.set(true);
        } else {
            error.set("");
            addToLog(LogMessages.CORRECT_INPUT);
        }
        outputCurrency.set("");
    }

    private void onTypeChange(final CurrencyPair oldValue, final CurrencyPair newValue) {
        addToLog(oldValue + LogMessages.CHANGED_BY + newValue);
        outputCurrency.set("");
    }

    static final class LogMessages {
        private LogMessages() {
        }

        static final String CORRECT_INPUT = "Input is correct!";
        static final String INCORRECT_INPUT = "Input is incorrect!";
        static final String CHANGED_BY = " was changed by ";
        static final String CONVERT_TO = " => ";
    }
}
