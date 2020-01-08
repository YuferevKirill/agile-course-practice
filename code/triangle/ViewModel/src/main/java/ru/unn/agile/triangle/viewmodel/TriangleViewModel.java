package ru.unn.agile.triangle.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.triangle.model.Point;
import ru.unn.agile.triangle.model.Triangle;
import ru.unn.agile.triangle.model.Triangle.Operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TriangleViewModel {
    private final StringProperty ax = new SimpleStringProperty();
    private final StringProperty ay = new SimpleStringProperty();
    private final StringProperty bx = new SimpleStringProperty();
    private final StringProperty by = new SimpleStringProperty();
    private final StringProperty cx = new SimpleStringProperty();
    private final StringProperty cy = new SimpleStringProperty();

    private final StringProperty logs = new SimpleStringProperty();
    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    private final ObjectProperty<ObservableList<Operation>> operations =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(Operation.values()));
    private final ObjectProperty<Operation> operation = new SimpleObjectProperty<>();

    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();
    private final List<ValueChangeListener> valueChangedListeners = new ArrayList<>();
    private final String arguments = "ax = %s; ay = %s; bx = %s; by = %s; cx = %s; cy = %s";
    private TriangleILogger logger;

    public TriangleViewModel() {
        init();
    }

    public TriangleViewModel(final TriangleILogger logger) {
        setLogger(logger);
        init();
    }

    public final void setLogger(final TriangleILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public void init() {
        ax.set("");
        ay.set("");
        bx.set("");
        by.set("");
        cx.set("");
        cy.set("");
        operation.set(Operation.GETANGLEA);
        result.set("");
        status.set(Status.WAITING.toString());

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(ax, ay, bx, by, cx, cy);
            }

            @Override
            protected boolean computeValue() {
                return getInputStatus() == Status.READY;
            }
        };

        calculationDisabled.bind(couldCalculate.not());

        final List<StringProperty> fields = new ArrayList<StringProperty>() {
            {
                add(ax);
                add(ay);
                add(bx);
                add(by);
                add(cx);
                add(cy);
            }
        };

        for (StringProperty field : fields) {
            final ValueChangeListener listener = new ValueChangeListener();
            field.addListener(listener);
            valueChangedListeners.add(listener);
        }
    }

    public void calculate() {
        if (calculationDisabled.get()) {
            return;
        }

        Point a = new Point(ax.get(), ay.get());
        Point b = new Point(bx.get(), by.get());
        Point c = new Point(cx.get(), cy.get());

        try {
            Triangle triangle = new Triangle(a, b, c);
            result.set(operation.get().apply(triangle));
            status.set(Status.SUCCESS.toString());
            StringBuilder message = new StringBuilder(LogMessages.CALCULATE_WAS_PRESSED);
            message.append("Arguments: ").append(String.format(arguments,
                    ax.get(), ay.get(), bx.get(), by.get(), cx.get(), cy.get()))
                    .append(" Operation: ").append(operation.get().toString()).append(".");
            logger.log(message.toString());
        } catch (Exception ex) {
            status.set(ex.getMessage());
            logger.log(LogMessages.INCORRECT_INPUT);
        } finally {
            updateLogs();
        }
    }

    public void onOperationChanged(final Operation oldValue, final Operation newValue) {
        if (oldValue.equals(newValue)) {
            return;
        }
        StringBuilder message = new StringBuilder(LogMessages.OPERATION_WAS_CHANGED);
        message.append(newValue.toString());
        logger.log(message.toString());
        updateLogs();
    }

    public void onFocusChanged(final Boolean oldValue, final Boolean newValue) {
        if (!oldValue && newValue) {
            return;
        }

        for (ValueChangeListener listener : valueChangedListeners) {
            if (listener.isChanged()) {
                StringBuilder message = new StringBuilder(LogMessages.EDITING_FINISHED);
                message.append("Input arguments are: ").append(String.format(arguments,
                        ax.get(), ay.get(), bx.get(), by.get(), cx.get(), cy.get()));
                logger.log(message.toString());
                updateLogs();

                listener.cache();
                break;
            }
        }
    }

    public StringProperty axProperty() {
        return ax;
    }

    public StringProperty ayProperty() {
        return ay;
    }

    public StringProperty bxProperty() {
        return bx;
    }

    public StringProperty byProperty() {
        return by;
    }

    public StringProperty cxProperty() {
        return cx;
    }

    public StringProperty cyProperty() {
        return cy;
    }

    public ObjectProperty<ObservableList<Operation>> operationsProperty() {
        return operations;
    }

    public final ObservableList<Operation> getOperations() {
        return operations.get();
    }

    public ObjectProperty<Operation> operationProperty() {
        return operation;
    }

    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }

    public final boolean isCalculationDisabled() {
        return calculationDisabled.get();
    }

    public StringProperty resultProperty() {
        return result;
    }

    public final String getResult() {
        return result.get();
    }

    public StringProperty statusProperty() {
        return status;
    }

    public final String getStatus() {
        return status.get();
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final List<String> getLog() {
        if (logger != null) {
            return logger.getLog();
        }
        return Collections.emptyList();
    }

    public final String getLogs() {
        return logs.get();
    }

    private Status getInputStatus() {
        Status inputStatus = Status.READY;
        if (ax.get().isEmpty() || ay.get().isEmpty()
                || bx.get().isEmpty() || by.get().isEmpty()
                || cx.get().isEmpty() || cy.get().isEmpty()) {
            inputStatus = Status.WAITING;
        }
        try {
            if (!ax.get().isEmpty()) {
                Double.parseDouble(ax.get());
            }
            if (!ay.get().isEmpty()) {
                Double.parseDouble(ay.get());
            }
            if (!bx.get().isEmpty()) {
                Double.parseDouble(bx.get());
            }
            if (!by.get().isEmpty()) {
                Double.parseDouble(by.get());
            }
            if (!cx.get().isEmpty()) {
                Double.parseDouble(cx.get());
            }
            if (!cy.get().isEmpty()) {
                Double.parseDouble(cy.get());
            }
        } catch (NumberFormatException nfe) {
            inputStatus = Status.BAD_FORMAT;
        }

        return inputStatus;
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        String record = "";
        for (String log : fullLog) {
            record += log + "\n";
        }
        logs.set(record);
    }

    private class ValueChangeListener implements ChangeListener<String> {
        private String prevValue = "";
        private String curValue = "";

        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            if (oldValue.equals(newValue)) {
                return;
            }
            status.set(getInputStatus().toString());
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
    WAITING("Please provide input data"),
    READY("Press 'Calculate' or Enter"),
    BAD_FORMAT("Bad format"),
    SUCCESS("Success");

    private final String name;

    Status(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}

final class LogMessages {
    public static final String CALCULATE_WAS_PRESSED = "Calculate. ";
    public static final String OPERATION_WAS_CHANGED = "Operation was changed to ";
    public static final String EDITING_FINISHED = "Updated input. ";
    public static final String INCORRECT_INPUT = "Incorrect input. ";

    private LogMessages() {
    }
}
