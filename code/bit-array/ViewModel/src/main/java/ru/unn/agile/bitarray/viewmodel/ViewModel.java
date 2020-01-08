package ru.unn.agile.bitarray.viewmodel;

import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.bitarray.model.BitArray;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewModel {
    private LoggerInterface log;
    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();

    private final StringProperty inputBitArray = new SimpleStringProperty();
    private final StringProperty inputBit = new SimpleStringProperty();

    private final StringProperty fieldInputArrayStatus = new SimpleStringProperty();
    private final StringProperty fieldInputBitStatus   = new SimpleStringProperty();

    private final StringProperty fieldBitArray = new SimpleStringProperty();

    private String patternArrayInput = "[0,1]+";
    private String patternBitInput = "[0-9]+";

    private BitArray bitArray;

    public ViewModel(final LoggerInterface log) {
        if (log == null) {
            throw new IllegalArgumentException("Log parameter must be set not to null");
        }
        this.log = log;

        inputBit.set("");
        inputBitArray.set("");
        fieldBitArray.set("");

        fieldInputArrayStatus.set(Status.WAITING.toString());
        fieldInputBitStatus.set(Status.WAITING.toString());

        final List<StringProperty> triggers = new ArrayList<>() {
            {
                add(inputBitArray);
                add(inputBit);
            }
        };

        for (StringProperty trigger : triggers) {
            final ValueChangeListener listener = new ValueChangeListener();
            trigger.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public StringProperty inputBitArrayProperty() {
        return inputBitArray;
    }

    public StringProperty inputBitProperty() {
        return inputBit;
    }

    public List<String> getLog() {
        return log.get();
    }

    public StringProperty fieldBitArrayProperty() {
        if (bitArray != null) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int index = 0; index < bitArray.getCountBit(); ++index) {
                if (bitArray.isBit(index)) {
                    stringBuilder.append(1);
                } else {
                    stringBuilder.append(0);
                }
            }
            fieldBitArray.set(stringBuilder.toString());
        }
        return fieldBitArray;
    }

    public StringProperty fieldInputArrayStatusProperty() {
        return fieldInputArrayStatus;
    }

    public StringProperty fieldInputBitStatusProperty() {
        return fieldInputBitStatus;
    }

    private boolean patternMatch(final String inputString, final String patternString) {
        Pattern pattern = Pattern.compile(patternString);
        Matcher matcher = pattern.matcher(inputString);
        return matcher.matches();
    }

    private Status getFieldInputArrayStatus() {
        Status inputStatus = Status.READY;
        final String arrayInputStr = inputBitArray.get();
        boolean matchArrayInput = patternMatch(arrayInputStr, patternArrayInput);
        if (!matchArrayInput) {
            inputStatus = Status.BAD_FORMAT_ARRAY;
            return inputStatus;
        }
        return inputStatus;
    }

    private Status getFieldInputBitStatus() {
        Status inputStatus = Status.READY;
        final String bitInputStr = inputBit.get();
        boolean matchArrayInput = patternMatch(bitInputStr, patternBitInput);
        if (!matchArrayInput) {
            inputStatus = Status.BAD_FORMAT_BIT;
            return inputStatus;
        }
        if (bitArray == null) {
            inputStatus = Status.NOT_CREATED;
            return inputStatus;
        }
        return inputStatus;
    }

    public void create() {
        if (getFieldInputArrayStatus() != Status.READY) {
            return;
        }
        final String arrayInputStr = inputBitArray.get();
        bitArray = new BitArray(arrayInputStr.length());
        for (int index = 0; index < arrayInputStr.length(); ++index) {
            if (arrayInputStr.charAt(index) == '1') {
                bitArray.setBit(index);
            }
        }
        fieldBitArrayProperty();
        fieldInputArrayStatus.set(Status.SUCCESS.toString());

        log.add(LogOutput.CREATE_BIT_ARRAY_PRESSED
                + "Operation input: "
                + arrayInputStr);
    }

    public void setBit() {
        if (getFieldInputBitStatus() != Status.READY) {
            return;
        }
        final String bitInputStr = inputBit.get();
        final int index = Integer.parseInt(bitInputStr);
        bitArray.setBit(index);

        fieldBitArrayProperty();
        fieldInputBitStatus.set(Status.SUCCESS.toString());

        log.add(LogOutput.SET_BIT_PRESSED
                + "Operation input: "
                + bitInputStr);
    }

    public void unsetBit() {
        if (getFieldInputBitStatus() != Status.READY) {
            return;
        }
        final String bitInputStr = inputBit.get();
        final int index = Integer.parseInt(bitInputStr);
        bitArray.unsetBit(index);

        fieldBitArrayProperty();
        fieldInputBitStatus.set(Status.SUCCESS.toString());

        log.add(LogOutput.UNSET_BIT_PRESSED
                + "Operation input: "
                + bitInputStr);
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            fieldInputArrayStatus.set(getFieldInputArrayStatus().toString());
            log.add(LogOutput.INPUT_CHANGED
                    + "Field input array changed with new value:"
                    + newValue);
            fieldInputBitStatus.set(getFieldInputBitStatus().toString());
            log.add(LogOutput.INPUT_CHANGED
                    + "Field input bit changed with new value:"
                    + newValue);
        }
    }
}

enum Status {
    WAITING("Please provide input data"),
    READY("Press button or Enter"),
    BAD_FORMAT_ARRAY("Incorrect format. Required: [0-1]+"),
    BAD_FORMAT_BIT("Incorrect format. Index required."),
    NOT_CREATED("Bit array is not created to change it."),
    SUCCESS("Success");

    private final String name;
    Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}

final class LogOutput {
    public static final String CREATE_BIT_ARRAY_PRESSED = "Created array. ";
    public static final String SET_BIT_PRESSED = "Bit set. ";
    public static final String UNSET_BIT_PRESSED = "Bit unset. ";
    public static final String INPUT_CHANGED = "Input changed. ";
    private LogOutput() { }
}
