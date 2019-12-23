package ru.unn.agile.binarysearch.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.binarysearch.model.BinarySearch;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {

    private final StringProperty arrayInput = new SimpleStringProperty();
    private final StringProperty elementInput = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final StringProperty result = new SimpleStringProperty();
    private boolean arrayCorrect;
    private boolean elementCorrect;
    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();
    private int key;
    private BinarySearch binarySearch;
    private final StringProperty log = new SimpleStringProperty();

    private ILogger logger;

    public ViewModel() {
       init();
    }

    public ViewModel(final ILogger logger) {

        setLogger(logger);

        this.logger = logger;

        init();
    }

    public void init() {
        arrayInput.set("");
        elementInput.set("");
        status.set("");
        result.set("");
        log.set("");
        arrayCorrect = false;
        elementCorrect = false;

        final List<StringProperty> fields = new ArrayList<StringProperty>() {
            {
                add(arrayInput);
                add(elementInput);
            }
        };
        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public final List<String> getLog() {
        return logger.getLogList();
    }

    public StringProperty arrayInputProperty() {
        return arrayInput;
    }

    public StringProperty elementInputProperty() {
        return elementInput;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public StringProperty resultProperty() {
        return result;
    }

    public String getArrayInputProperty() {
        return arrayInput.get();
    }

    public String getElementInputProperty() {
        return elementInput.get();
    }

    public String getStatusProperty() {
        return status.get();
    }

    public String getResultProperty() {
        return result.get();
    }

    public int[] getBinarySearchArray() {
        return binarySearch.getArray();
    }

    public StringProperty logProperty() {
        return log;
    }

    public void setArrayInputProperty(final String input) {
        logger.log("Update Input, array: " + input);
        log.set(String.join(";\n", logger.getLogList()));
        arrayInput.set(input);
    }

    public void setElementInputProperty(final String input) {
        logger.log("Update Input, element: " + input);
        log.set(String.join(";\n", logger.getLogList()));
        elementInput.set(input);
    }
    private void setResultProperty(final String res) {
        logger.log("Result: " + res);
        log.set(String.join(";\n", logger.getLogList()));
        result.set(res);
    }

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public void search() {
        int index = binarySearch.search(key);
        if (index == binarySearch.NOT_FOUND) {
            setResultProperty("Key not found ;");
        } else if (index == binarySearch.NOT_SORTED) {
            setResultProperty("Array not sorted ;");
        } else {
            setResultProperty("Found key, index " + Integer.toString(index) + " ;");
        }
    }

    public Status getStatus() {
        Status status = Status.READY;
        try {
            String[] split = arrayInput.get().split(",");
            int[] arr = new int[split.length];
            for (int i = 0; i < split.length; i++) {
                arr[i] = Integer.parseInt(split[i]);
            }
            arrayCorrect = true;
            binarySearch = new BinarySearch(arr);
        } catch (NumberFormatException nfe) {
            status = Status.BAD_ARRAY_FORMAT;
        }
        try {
            key = Integer.parseInt(elementInput.get());
            elementCorrect = true;
        } catch (NumberFormatException nfe) {
            status = Status.BAD_ELEMENT_FORMAT;
        }
        if ((getArrayInputProperty() == "") || (getElementInputProperty() == "")) {
            status = Status.WAITING;
        }
        return status;
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLogList();
        String record = new String("");
        for (String log : fullLog) {
            record += log + "\n";
        }
        log.set(record);
    }

    public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
        if (!oldValue && newValue) {
            return;
        }

        for (ValueChangeListener listener : valueChangedListeners) {
            if (listener.isChanged()) {
                StringBuilder message = new StringBuilder("Update Input: ");
                message.append("Input arguments are: [")
                        .append(arrayInput.get()).append("; ")
                        .append(elementInput.get()).append("] ");
                logger.log(message.toString());
                updateLogs();
                break;
            }
        }
    }

    private class ValueChangeListener implements ChangeListener<String> {
        private String prevValue = new String("");
        private String curValue = new String("");
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            if (oldValue.equals(newValue)) {
                return;
            }
            status.set(getStatus().toString());
            curValue = newValue;
        }
        public boolean isChanged() {
            return !prevValue.equals(curValue);
        }
        public void cache() {
            prevValue = curValue;
        }
    }
}

enum Status {
    WAITING("Waiting for input"),
    READY("Press Search"),
    BAD_ARRAY_FORMAT("Bad array format, please enter \n array of ints, comma separated"),
    BAD_ELEMENT_FORMAT("Bad element format, please enter \n the key to search");

    private final String name;

    Status(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}
