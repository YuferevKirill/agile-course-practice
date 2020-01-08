package ru.unn.agile.arabicromanconverter.viewmodel;

import javafx.beans.property.*;
import ru.unn.agile.arabicromanconverter.model.*;

import java.util.List;

public class ViewModel {

    private final StringProperty logs = new SimpleStringProperty();
    private StringProperty input = new SimpleStringProperty();
    private StringProperty output = new SimpleStringProperty();
    private StringProperty error = new SimpleStringProperty();
    private StringProperty convert = new SimpleStringProperty();
    private BooleanProperty btnDisabled = new SimpleBooleanProperty();
    private ConverterType selector = ConverterType.ARABICTOROMAN;
    private ILogger logger;

    public ViewModel() {
        init();
    }

    public ViewModel(final ILogger logger) {
        setLogger(logger);
        init();
    }

    private void init() {
        refresh();
        convert.set("Arabic to Roman");
        btnDisabled.set(true);

        input.addListener((observable, oldValue, newValue) -> {
            onInput(newValue);
        });
    }

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public void convert() {
        String value = input.get();
        Converter converter = selector.getConverter();
        String result = converter.convertValue(value);
        output.set(result);
        StringBuilder message = new StringBuilder(LogMessages.CONVERT_WAS_PRESSED);
        message.append("from number: ")
                .append(getInput().getValue())
                .append(" to number: ")
                .append(getOutput().getValue())
                .append(".");
        logger.log(message.toString());
        updateLogs();
    }

    public void swap() {
        StringBuilder message = new StringBuilder(LogMessages.CONVERTER_WAS_CHANGED);
        if (selector == ConverterType.ARABICTOROMAN) {
            message.append("from type: ")
                    .append(selector.toString());
            selector = ConverterType.ROMANTOARABIC;
            message.append(" to type: ")
                    .append(selector.toString())
                    .append(".");
        } else {
            message.append("from type: ")
                    .append(selector.toString());
            selector = ConverterType.ARABICTOROMAN;
            message.append(" to type: ")
                    .append(selector.toString())
                    .append(".");
        }
        refresh();
        logger.log(message.toString());
        updateLogs();
    }

    public void refresh() {
        input.set("");
        output.set("");
        error.set("");
        convert.set(selector.toString());
    }

    public final List<String> getLog() {
        return logger.readLog();
    }

    public StringProperty getInput() {
        return input;
    }

    public StringProperty getOutput() {
        return output;
    }

    public StringProperty getConvert() {
        return convert;
    }

    public StringProperty getError() {
        return error;
    }

    public BooleanProperty isConvertButtonDisabled() {
        return btnDisabled;
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final String getLogs() {
        return logs.get();
    }

    private void onInput(final String newValue) {
        boolean isValid = selector.getConverter().validate(newValue);
        if (isValid || newValue.isEmpty()) {
            error.set("");
            StringBuilder message = new StringBuilder(LogMessages.EDITING_FINISHED);
            message.append(getInput().getValue())
                    .append(".");
            logger.log(message.toString());
            updateLogs();
        } else {
            error.set(selector.getErrorMessage());
            StringBuilder message = new StringBuilder(LogMessages.INCORRECT_INPUT);
            message.append(newValue)
                    .append(".");
            logger.log(message.toString());
            updateLogs();
        }
        btnDisabled.set(newValue.isEmpty() || !isValid);
        output.set("");
    }

    private void updateLogs() {
        List<String> fullLog = logger.readLog();
        String record = new String("");
        for (String log : fullLog) {
            record += log + "\n";
        }
        logs.set(record);
    }
}

enum ConverterType {
    ARABICTOROMAN("Arabic to Roman", new ArabicToRomanConverter(), "Insert correct arabic number"),
    ROMANTOARABIC("Roman to Arabic", new RomanToArabicConverter(), "Insert correct roman number");

    private final String lblConvertingType;
    private final Converter converter;
    private final String errorMessage;

    ConverterType(final String label, final Converter converter, final String errorMessage) {
        this.lblConvertingType = label;
        this.converter = converter;
        this.errorMessage = errorMessage;
    }

    public String toString() {
        return lblConvertingType;
    }

    public Converter getConverter() {
        return converter;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}

final class LogMessages {
    public static final String CONVERT_WAS_PRESSED = "Converting following: ";
    public static final String CONVERTER_WAS_CHANGED = "Converter was changed to ";
    public static final String EDITING_FINISHED = "Updated input to ";
    public static final String INCORRECT_INPUT = "Incorrect input: ";

    private LogMessages() {
    }
}
