package ru.unn.agile.sorting.viewmodel;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.sorting.model.api.Direction;
import ru.unn.agile.sorting.model.api.Sorting;
import ru.unn.agile.sorting.model.impl.NumberSorting;

import java.util.Arrays;
import java.util.List;

public class ViewModel {
    private final StringProperty inputArray = new SimpleStringProperty();
    private final BooleanProperty sortButtonDisabled = new SimpleBooleanProperty();
    private final ObjectProperty<ObservableList<Direction>> directions =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(Direction.values()));
    private final ObjectProperty<Direction> direction = new SimpleObjectProperty<>();
    private final StringProperty sortedArray = new SimpleStringProperty();
    private final StringProperty error = new SimpleStringProperty();
    private final StringProperty logs = new SimpleStringProperty();
    private ISortingLogger logger;

    public final void setLogger(final ISortingLogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public ViewModel() {
        init();
    }

    public ViewModel(final ISortingLogger logger) {
        setLogger(logger);
        init();
    }

    private void init() {
        inputArray.set("");
        direction.set(Direction.ASC);
        sortButtonDisabled.set(true);
        sortedArray.set("");
        error.set("");
        inputArray.addListener((observable, oldValue, newValue) -> {
            onInput(newValue);
        });
        direction.addListener((observable, oldValue, newValue) -> {
            StringBuilder message = new StringBuilder(LogMessages.CHANGE_DIRECTION);
            message.append(newValue).append(".");
            logger.log(message.toString());
            updateLogs();
        });
    }

    public void sort() {
        try {
            Sorting<Integer> numberSorting = new NumberSorting(direction.get());
            String[] strInputArray = inputArray.get().split(" ");
            Integer[] numbers = new Integer[strInputArray.length];
            for (int i = 0; i < strInputArray.length; i++) {
                numbers[i] = Integer.parseInt(strInputArray[i]);
            }
            String result = Arrays.toString(numberSorting.sort(numbers));
            sortedArray.set(result.substring(1, result.length() - 1));
            StringBuilder message = new StringBuilder(LogMessages.SORTED);
            message.append(Arrays.toString(strInputArray))
                    .append(" to ")
                    .append("[" + sortedArray.getValue() + "] ")
                    .append(directionProperty().get());
            logger.log(message.toString());
        } catch (NumberFormatException ex) {
            error.set("Bad format");
            sortedArray.set("");
            logger.log(LogMessages.INCORRECT_INPUT);
        } finally {
            updateLogs();
        }
    }

    private void onInput(final String newValue) {
        if (!newValue.matches("^-?\\d+(\\s-?\\d+)*\\s?$")) {
            sortButtonDisabled.set(true);
            error.set("Incorrect Input");
        } else {
            sortButtonDisabled.set(false);
            error.set("");
        }
    }

    public StringProperty inputArrayProperty() {
        return inputArray;
    }

    public StringProperty sortedArrayProperty() {
        return sortedArray;
    }

    public final String getSortedArray() {
        return sortedArray.get();
    }

    public StringProperty errorProperty() {
        return error;
    }

    public final String getError() {
        return error.get();
    }

    public ObjectProperty<ObservableList<Direction>> directionsProperty() {
        return directions;
    }

    public final ObservableList<Direction> getDirections() {
        return directions.get();
    }

    public ObjectProperty<Direction> directionProperty() {
        return direction;
    }

    public BooleanProperty sortButtonDisabledProperty() {
        return sortButtonDisabled;
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final String getLogs() {
        return logs.get();
    }

    public final List<String> getLog() {
        return logger.getLog();
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        String record = new String("");
        for (String log : fullLog) {
            record += log + "\n";
        }
        logs.set(record);
    }
}

final class LogMessages {
    public static final String SORTED = "Sorted following: ";
    public static final String CHANGE_DIRECTION = "Direction swapped to: ";
    public static final String INCORRECT_INPUT = "Incorrect input. ";

    private LogMessages() {
    }
}

