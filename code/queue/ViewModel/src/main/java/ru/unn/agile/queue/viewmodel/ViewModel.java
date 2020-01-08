package ru.unn.agile.queue.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.queue.model.Queue;
import java.util.ArrayList;
import java.util.List;

public class ViewModel {

    private Queue<Double> queue = new Queue<>();
    private final StringProperty queueElementInput = new SimpleStringProperty();
    private final StringProperty queueResult = new SimpleStringProperty();
    private final StringProperty queueStatus = new SimpleStringProperty();
    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();
    private double key;
    private ILogger logger;
    private StringProperty textLogger = new SimpleStringProperty();

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public ViewModel() {
        init();
    }

    public ViewModel(final ILogger logger) {
        setLogger(logger);
        init();
    }

    private void init() {
        queueElementInput.set("");
        queueResult.set("");
        queueStatus.set("");
        textLogger.set("");

        final List<StringProperty> fields = new ArrayList<>() { {
            add(queueElementInput);
            add(queueResult);
            add(queueStatus);
        }};
        for (StringProperty field: fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public List<String> getLog() {
        if (logger != null) {
            return logger.getLog();
        }
        return null;
    }

    private class ValueChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            queueStatus.set(getStatus().toString());
        }
    }
    public StringProperty textLoggerProperty() {
        return textLogger;
    }

    public String getTextLogger() {
        return textLogger.get();
    }

    public StringProperty queueElementProperty() {
        return queueElementInput;
    }

    public StringProperty queueResultProperty() {
        return queueResult;
    }

    public StringProperty queueStatusProperty() {
        return queueStatus;
    }

    public void setQueueInputElement(final String input) {
        queueElementInput.set(input);
    }

    public void setQueueResult(final String input) {
        queueResult.set(input);
    }

    public void setQueueStatus(final String input) {
        queueStatus.set(input);
    }

    public String getQueueElement() {
        return queueElementInput.get();
    }

    public String getQueueResult() {
        return queueResult.get();
    }

    public String getQueueStatus() {
        return queueStatus.get();
    }

    private void writeLog(final String logMessage) {
        logger.log(logMessage);
        StringBuilder messages = new StringBuilder();
        for (String log : logger.getLog()) {
            messages.append(log).append("\n");
        }
        textLogger.set(messages.toString());
    }

    public void pushProcess() {
        try {
            var status = getQueueStatus();
            if (!status.equals(Status.READY.toString())) {
                if (status.equals(Status.BAD_ELEMENT_FORMAT.toString())) {
                    var element = getQueueElement();
                    var message = "Pushing element " + element + " has incorrect format";
                    throw new IllegalArgumentException(message);
                }
                throw new IllegalArgumentException("Pushing element is empty");
            }
            queue.push(key);
            queueStatus.set(Status.SUCCESS.toString());
            queueResult.set("Push element: " + Double.toString(key));
            writeLog("Pushed " + Double.toString(key) + " to queue");
        } catch (IllegalArgumentException iae) {
            queueResult.set(iae.getMessage());
            writeLog(iae.getMessage());
        }
    }

    public void popProcess() {
        try {
            double popElement = queue.pop();
            queueStatus.set(Status.SUCCESS.toString());
            queueResult.set("Pop element: " + Double.toString(popElement));
            writeLog("Popped " + Double.toString(popElement) + " from queue");
        } catch (NullPointerException npe) {
            queueResult.set("Queue is empty.");
            writeLog("Impossible to pop from empty queue");
        }
    }

    public void clearProcess() {
        queue.clear();
        queueStatus.set(Status.SUCCESS.toString());
        queueResult.set("Queue cleared");
        writeLog("Queue was cleared");
    }

    public void getHeadProcess() {
        try {
            double head = queue.getHead();
            queueStatus.set(Status.SUCCESS.toString());
            queueResult.set("Head is: " + Double.toString(head));
            writeLog("Head element " + Double.toString(head) + " was received");
        } catch (NullPointerException npe) {
            queueResult.set("Queue is empty.");
            writeLog("Impossible to get head from empty queue");
        }
    }

    public void getTailProcess() {
        try {
            double tail = queue.getTail();
            queueStatus.set(Status.SUCCESS.toString());
            queueResult.set("Tail is: " + Double.toString(tail));
            writeLog("Tail element " + Double.toString(tail) + " was received");
        } catch (NullPointerException npe) {
            queueResult.set("Queue is empty.");
            writeLog("Impossible to get tail from empty queue");
        }
    }

    public Status getStatus() {
        Status status = Status.READY;
        try {
            key = Double.parseDouble(queueElementInput.get());
        } catch (NumberFormatException nfe) {
            status = Status.BAD_ELEMENT_FORMAT;
        }
        if (getQueueElement().isEmpty()) {
            status = Status.WAITING;
        }

        return status;
    }
}

enum Status {
    WAITING("Waiting for input"),
    READY("Press Add element"),
    BAD_ELEMENT_FORMAT("Bad element format."),
    SUCCESS("Success");

    private final String name;
    Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
