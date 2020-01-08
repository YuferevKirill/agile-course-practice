package ru.unn.agile.primenumber.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.primenumber.model.PrimeNumberFinder;

import java.util.ArrayList;
import java.util.List;

public class ViewModel {
    private final StringProperty startElement = new SimpleStringProperty();
    private final StringProperty endElement = new SimpleStringProperty();
    private final StringProperty outputField = new SimpleStringProperty();
    private final BooleanProperty findBtnDisabled = new SimpleBooleanProperty();
    private final StringProperty status = new SimpleStringProperty();
    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();
    private final List<StringProperty> fields = new ArrayList<>() {
        {
            add(startElement);
            add(endElement);
        }
    };

    public BooleanProperty findBtnDisabledProperty() {
        return findBtnDisabled;
    }

    public StringProperty startElemProperty() {
        return startElement;
    }

    public StringProperty endElemProperty() {
        return endElement;
    }

    public StringProperty outputProperty() {
        return outputField;
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final List<String> getLog() {
        return logger.readLog();
    }

    private Logger logger;

    public ViewModel() {
        init();
    }

    public final void setLogger(final Logger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public ViewModel(final Logger logger) {
        setLogger(logger);
        init();
    }

    private void init() {
        fields.get(0).set("");
        fields.get(1).set("");
        outputField.set("");
        status.set(Status.WAITING.toString());

        BooleanBinding couldFind = new BooleanBinding() {
            {
                super.bind(fields.get(0), fields.get(1));
            }

            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };
        findBtnDisabled.bind(couldFind.not());

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void findPrimaryNums() {
        logger.writeLog(LogTemplates.FIND_WAS_PRESSED);

        if (findBtnDisabled.get()) {
            return;
        }

        try {
            Integer start = Integer.parseInt(fields.get(0).get());
            Integer end = Integer.parseInt(fields.get(1).get());
            PrimeNumberFinder setOfPrimeNums = new PrimeNumberFinder(
                    start, end);
            List<Integer> primeNumsList = setOfPrimeNums.findNumbers();
            String result = primeNumsList.toString();
            outputField.set(result);
            String successStatus = Status.SUCCESS.toString();
            status.set(successStatus);
            logger.writeLog(LogTemplates.STATUS_WAS_UPDATED + successStatus);
            logger.writeLog(LogTemplates.RESULT_WAS_UPDATED + result);
        } catch (IllegalArgumentException e) {
            String message = e.getMessage();
            outputField.set(message);
        }
    }

    private Status getInputStatus() {
        Status status = Status.READY;
        if (fields.get(0).get().isEmpty() || fields.get(1).get().isEmpty()) {
            status = Status.WAITING;
        }
        try {
            if (!fields.get(0).get().isEmpty()) {
                Integer.parseInt(fields.get(0).get());
            }
            if (!fields.get(1).get().isEmpty()) {
                Integer.parseInt(fields.get(1).get());
            }
        } catch (NumberFormatException e) {
            status = Status.BAD_FORMAT;
            logger.writeLog(LogTemplates.INCORRECT_INPUT);
        }
        return status;
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            logger.writeLog(LogTemplates.INPUT_WAS_UPDATED + newValue);
            String currentStatus = getInputStatus().toString();
            status.set(currentStatus);
            logger.writeLog(LogTemplates.STATUS_WAS_UPDATED + currentStatus);
            if (!currentStatus.equals(Status.SUCCESS)) {
                outputField.set(status.get());
            }
        }
    }
}
