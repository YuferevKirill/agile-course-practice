package ru.unn.agile.converter.viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.converter.model.LengthConverter;
import ru.unn.agile.converter.model.LengthType;

import java.util.List;

public class LengthConverterViewModel {

    private StringProperty input = new SimpleStringProperty();
    private StringProperty error = new SimpleStringProperty();
    private StringProperty output = new SimpleStringProperty();
    private final StringProperty logs = new SimpleStringProperty();
    private BooleanProperty btnDisabled = new SimpleBooleanProperty();
    private ObjectProperty<LengthType> fromType = new SimpleObjectProperty<>();
    private ObjectProperty<LengthType> toType = new SimpleObjectProperty<>();

    private final ObjectProperty<ObservableList<LengthType>> lengthTypes =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(LengthType.values()));

    private LengthConverterILogger logger;

    public final void setLogger(final LengthConverterILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public LengthConverterViewModel() {
        init();
    }


    public LengthConverterViewModel(final LengthConverterILogger logger) {
        setLogger(logger);
        init();
    }

    private void init() {
        input.set("");
        error.set("");
        output.set("");
        btnDisabled.set(true);
        fromType.set(LengthType.METER);
        toType.set(LengthType.CENTIMETER);

        input.addListener((observable, oldValue, newValue) -> {
            onInput(newValue);
        });
        fromType.addListener((observable, oldValue, newValue) -> {
            StringBuilder message = new StringBuilder(LogMessages.START_LENGHT_TYPE_WAS_CHANGED);
            message.append("to type: ").append(newValue)
                    .append(".");
            logger.log(message.toString());
            addToLog();
            onTypeChange();
        });
        toType.addListener((observable, oldValue, newValue) -> {
            StringBuilder message = new StringBuilder(LogMessages.RESULT_LENGHT_TYPE_WAS_CHANGED);
            message.append("to type: ").append(newValue)
                    .append(".");
            logger.log(message.toString());
            addToLog();
            onTypeChange();
        });
    }

    public StringProperty getInput() {
        return input;
    }

    public StringProperty getOutput() {
        return output;
    }

    public BooleanProperty isConvertButtonDisabled() {
        return btnDisabled;
    }

    public ObjectProperty<LengthType> getFromType() {
        return fromType;
    }

    public ObjectProperty<LengthType> getToType() {
        return toType;
    }

    public ObservableList<LengthType> getLengthTypes() {
        return lengthTypes.get();
    }

    public StringProperty getError() {
        return error;
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final String getLogs() {
        return logs.get();
    }

    public final List<String> getLog() {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        return logger.getLog();
    }


    public void convert() {
        if (isConvertButtonDisabled().getValue()) {
            return;
        }
        double value = Double.parseDouble(input.get());
        LengthConverter converter = new LengthConverter(value, getFromType().get());
        value = converter.convert(getToType().get());
        output.set(format(value));
        StringBuilder message = new StringBuilder(LogMessages.CONVERT_WAS_PRESSED);
        message.append("from type: ").append(getFromType().getValue())
                .append(" to type: ").append(getToType().getValue())
                .append(".");
        logger.log(message.toString());
        addToLog();
    }

    public void swap() {
        LengthType tmp = fromType.get();
        fromType.set(toType.get());
        toType.set(tmp);
        logger.log(LogMessages.SWAP_PRESSED);
        addToLog();
    }

    private void onInput(final String newValue) {
        boolean isNumeric = isNumeric(newValue);
        if (isNumeric || newValue.isEmpty()) {
            error.set("");
        } else {
            error.set("invalid");
            logger.log(LogMessages.INCORRECT_INPUT);
            addToLog();
        }
        btnDisabled.set(newValue.isEmpty() || !isNumeric);
        output.set("");
    }

    private boolean isNumeric(final String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    private static String format(final double d) {
        if (d == (long) d) {
            return String.format("%d", (long) d);
        } else {
            return String.format("%s", d);
        }
    }

    private void onTypeChange() {
        output.set("");
    }

    private void addToLog() {
        List<String> log = logger.getLog();
        StringBuilder records = new StringBuilder("");
        for (String logString : log) {
            records.append(logString).append("\n");
        }
        logs.set(records.toString());
    }

}

final class LogMessages {
    public static final String CONVERT_WAS_PRESSED = "Convert following: ";
    public static final String RESULT_LENGHT_TYPE_WAS_CHANGED =
            "Result length type was changed to ";
    public static final String START_LENGHT_TYPE_WAS_CHANGED =
            "Start length type was changed to ";
    public static final String SWAP_PRESSED = "Length types swapped.";
    public static final String INCORRECT_INPUT = "Incorrect input.";

    private LogMessages() { }
}
