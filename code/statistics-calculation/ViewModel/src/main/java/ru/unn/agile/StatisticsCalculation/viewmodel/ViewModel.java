package ru.unn.agile.statisticscalculation.viewmodel;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import ru.unn.agile.statisticscalculation.model.DiscreteRandomVariable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

enum InputDataStatus {
    WAITING("Enter data"),
    READY("Input data is correct"),
    BAD_FORMAT("Input data error");

    private final String name;

    InputDataStatus(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}

enum DataStatus {
    WAITING("Enter data"),
    READY("Data is correct"),
    BAD_FORMAT("Data normalization error");

    private final String name;

    DataStatus(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}

enum OperationStatus {
    WAITING_OPERATION("Choose an operation"),
    WAITING_DATA("Waiting correct data"),
    READY("Press 'Calculate'"),
    WAITING_PARAMETER("Enter parameter"),
    BAD_FORMAT("Error in parameter"),
    SUCCESS("Success");

    private final String name;

    OperationStatus(final String name) {
        this.name = name;
    }

    public String toString() {
        return name;
    }
}

final class LogMessages {
    public static final String CALCULATE_WAS_PRESSED = "Calculate. ";
    public static final String OPERATION_WAS_CHANGED = "Operation was changed to ";
    public static final String UPDATE_TABLE = "Updated input. ";
    public static final String DELETE_ELEMENT_IN_TABLE = "Deleted element. ";
    public static final String SELECTED_ELEMENT_IN_TABLE = "Selected element in table. ";

    private LogMessages() { }
}

public class ViewModel {
    private final StringProperty newValue = new SimpleStringProperty();
    private final StringProperty newProbability = new SimpleStringProperty();
    private final StringProperty operationParameter = new SimpleStringProperty();

    private final StringProperty result = new SimpleStringProperty();
    private final StringProperty operationStatus = new SimpleStringProperty();
    private final StringProperty dataStatus = new SimpleStringProperty();
    private final StringProperty inputDataStatus = new SimpleStringProperty();

    private final BooleanProperty calculationDisabled = new SimpleBooleanProperty();
    private final BooleanProperty deleteDisabled = new SimpleBooleanProperty();
    private final BooleanProperty updateDisabled = new SimpleBooleanProperty();
    private final BooleanProperty enterParameterVisible = new SimpleBooleanProperty();
    private final BooleanProperty isOperationParameterCorrect = new SimpleBooleanProperty();

    private final IntegerProperty selectedListIndex = new SimpleIntegerProperty();

    public StringProperty logsProperty() {
        return logs;
    }

    private final StringProperty logs = new SimpleStringProperty();

    private final ObjectProperty<ObservableList<Operation>> operations =
            new SimpleObjectProperty<>(FXCollections.observableArrayList(Operation.values()));
    private final ObjectProperty<Operation> operation = new SimpleObjectProperty<>();

    private final ObservableList<TableElement> listData = FXCollections.observableArrayList();
    private final List<UpdateDataChangeListener> updateDataChangedListeners = new ArrayList<>();
    private static final int NOT_SELECTED = -1;
    private static final String EMPTY = "";
    private DiscreteRandomVariable discreteRandomVariable;

    private ILogger logger;

    public final void setLoggerToViewModel(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    // FXML needs default c-tor for binding
    public ViewModel() {
        init();
    }

    public ViewModel(final ILogger logger) {
        setLoggerToViewModel(logger);
        init();
    }

    public void init() {
        setInputFieldsToEmpty();
        operationParameter.set(EMPTY);

        selectedListIndex.set(NOT_SELECTED);

        result.set(EMPTY);
        operationStatus.set(OperationStatus.WAITING_DATA.toString());
        dataStatus.set(DataStatus.WAITING.toString());
        inputDataStatus.set(InputDataStatus.WAITING.toString());
        operation.set(null);

        operationParameter.addListener(
                (observable, oldValue, newValue) -> {
                    operationParameter.set(newValue);
                    try {
                        if (Integer.parseInt(operationParameterProperty().get()) > 0) {
                            isOperationParameterCorrect.set(true);
                        }
                    } catch (NumberFormatException nfe) {
                        isOperationParameterCorrect.set(false);
                    }
                    operationStatus.set(calculateOperationStatus().toString());
                });

        BooleanBinding couldUpdateData = new BooleanBinding() {
            {
                super.bind(newValue, newProbability);
            }

            @Override
            protected boolean computeValue() {
                return calculateInputDataStatus() == InputDataStatus.READY;
            }
        };
        updateDisabled.bind(couldUpdateData.not());

        BooleanBinding couldDelete = new BooleanBinding() {
            {
                super.bind(newValue, newProbability);
            }

            @Override
            protected boolean computeValue() {
                return !(newValue.isEmpty().get() && newProbability.isEmpty().get());
            }
        };
        deleteDisabled.bind(couldDelete.not());

        BooleanBinding couldCalculate = new BooleanBinding() {
            {
                super.bind(operationStatus, dataStatus);
            }

            @Override
            protected boolean computeValue() {
                return calculateDataStatus() == DataStatus.READY
                        && calculateOperationStatus() == OperationStatus.READY;
            }
        };
        calculationDisabled.bind(couldCalculate.not());

        BooleanBinding couldEnterParameter = new BooleanBinding() {
            {
                super.bind(operation, operationStatus);
            }

            @Override
            protected boolean computeValue() {
                return calculateDataStatus() == DataStatus.READY
                        && (operation.get() == Operation.RAW_MOMENT
                        || operation.get() == Operation.CENTRAL_MOMENT);
            }
        };
        enterParameterVisible.bind(couldEnterParameter);

        final List<StringProperty> fields = new ArrayList<>() {
            {
                add(newValue);
                add(newProbability);
                add(operationParameter);
            }
        };

        for (StringProperty field : fields) {
            final UpdateDataChangeListener listener = new UpdateDataChangeListener();
            field.addListener(listener);
            updateDataChangedListeners.add(listener);
        }
    }

    public StringProperty newValueProperty() {
        return newValue;
    }

    public StringProperty newProbabilityProperty() {
        return newProbability;
    }

    public StringProperty operationParameterProperty() {
        return operationParameter;
    }


    public StringProperty resultProperty() {
        return result;
    }

    public final String getResult() {
        return result.get();
    }

    public StringProperty operationStatusProperty() {
        return operationStatus;
    }

    public final String getOperationStatus() {
        return operationStatus.get();
    }

    public StringProperty dataStatusProperty() {
        return dataStatus;
    }

    public final String getDataStatus() {
        return dataStatus.get();
    }

    public StringProperty inputDataStatusProperty() {
        return inputDataStatus;
    }

    public final String getInputDataStatus() {
        return inputDataStatus.get();
    }

    public ObservableList<TableElement> getListData() {
        return listData;
    }

    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }

    public final boolean isCalculationDisabled() {
        return calculationDisabled.get();
    }

    public BooleanProperty deleteDisabledProperty() {
        return deleteDisabled;
    }

    public final boolean isDeleteDisabled() {
        return deleteDisabled.get();
    }

    public BooleanProperty updateDisabledProperty() {
        return updateDisabled;
    }

    public final boolean isUpdateDisabled() {
        return updateDisabled.get();
    }

    public BooleanProperty enterParameterVisibleProperty() {
        return enterParameterVisible;
    }

    public final boolean isEnterParameterVisible() {
        return enterParameterVisible.get();
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
    public final String getLogs() {
        return logs.get();
    }
    public void updateTableElement() {
        inputDataStatus.set(calculateInputDataStatus().toString());
        if (calculateInputDataStatus() == InputDataStatus.READY) {
            if (selectedListIndex.get() >= 0) {
                listData.set(selectedListIndex.get(),
                        new TableElement(newValue.getValue(), newProbability.getValue()));
            } else {
                listData.add(new TableElement(newValue.getValue(), newProbability.getValue()));
            }

            StringBuilder message = new StringBuilder(LogMessages.UPDATE_TABLE);
            message.append("Value = ").append(newValue.get())
                    .append("; Probability = ").append(newProbability.getValue())
                    .append(" Operation: ").append(getOperationStatus()).append(".");
            logger.addLog(message.toString());
            updateLogstoView();

            setInputFieldsToEmpty();
            inputDataStatus.set(calculateInputDataStatus().toString());
        }
        dataStatus.set(calculateDataStatus().toString());
        operationStatus.set(calculateOperationStatus().toString());
    }

    public void deleteTableElement(final int focusedIndex) {
        if (focusedIndex >= 0 && focusedIndex < listData.size()) {
            StringBuilder message = new StringBuilder(LogMessages.DELETE_ELEMENT_IN_TABLE);
            message.append("Value = ").append(listData.get(focusedIndex).getValue())
                    .append("; Probability = ").append(listData.get(focusedIndex).getProbability())
                    .append(" Operation: ").append(getOperationStatus()).append(".");
            logger.addLog(message.toString());
            updateLogstoView();
            listData.remove(focusedIndex);
        }
        setInputFieldsToEmpty();
        inputDataStatus.set(calculateInputDataStatus().toString());
        dataStatus.set(calculateDataStatus().toString());
        operationStatus.set(calculateOperationStatus().toString());
    }

    public void setSelectedElement(final int focusedIndex) {
        selectedListIndex.set(focusedIndex);
        if (selectedListIndex.get() != NOT_SELECTED) {
            newValue.set(listData.get(selectedListIndex.get()).getValue());
            newProbability.set(listData.get(selectedListIndex.get()).getProbability());
            inputDataStatus.set(calculateInputDataStatus().toString());
        }
        StringBuilder message = new StringBuilder(LogMessages.SELECTED_ELEMENT_IN_TABLE);
        message.append("Index in table: ").append(selectedListIndex.get())
                .append("; Value = ").append(listData.get(selectedListIndex.get()).getValue())
                .append("; Probability = ")
                .append(listData.get(selectedListIndex.get()).getProbability())
                .append(".");
        logger.addLog(message.toString());
        updateLogstoView();
    }

    public void updateOperation() {
        operationStatus.set(calculateOperationStatus().toString());
        StringBuilder message = new StringBuilder(LogMessages.OPERATION_WAS_CHANGED);
        message.append(operation.get().toString()).append(".");
        logger.addLog(message.toString());
        updateLogstoView();
    }

    public void calculate() {
        try {
            Double operationResult = operation.get().apply(
                    discreteRandomVariable, operationParameter.get());
            operationStatus.set(OperationStatus.SUCCESS.toString());
            result.set(operationResult.toString());

            StringBuilder message = new StringBuilder(LogMessages.CALCULATE_WAS_PRESSED);
            message.append("Operation = ").append(operation.get().toString())
                    .append("; Operation parameter = ").append(operationParameter.get())
                    .append("; Values = ")
                    .append(Arrays.toString(discreteRandomVariable.getValues()))
                    .append("; Probabilities = ")
                    .append(Arrays.toString(discreteRandomVariable.getProbabilities()))
                    .append("; Result = ").append(operationResult)
                    .append(".");
            logger.addLog(message.toString());
            updateLogstoView();

        } catch (IllegalArgumentException exception) {
            result.set(exception.toString());
        }
    }

    public void resetSelectedElement() {
        selectedListIndex.set(NOT_SELECTED);
        setInputFieldsToEmpty();
        inputDataStatus.set(calculateInputDataStatus().toString());
    }

    private InputDataStatus calculateInputDataStatus() {
        if (newValueProperty().get().isEmpty() || newProbabilityProperty().get().isEmpty()) {
            return InputDataStatus.WAITING;
        }
        try {
            if (!newValueProperty().get().isEmpty()) {
                Double.parseDouble(newValueProperty().get());
            }
            if (!newProbabilityProperty().get().isEmpty()) {
                Double.parseDouble(newProbabilityProperty().get());
                if (Double.parseDouble(newProbabilityProperty().get()) > 1.0) {
                    return InputDataStatus.BAD_FORMAT;
                }
            }
        } catch (NumberFormatException nfe) {
            return InputDataStatus.BAD_FORMAT;
        }

        return InputDataStatus.READY;
    }

    private DataStatus calculateDataStatus() {
        if (listData.isEmpty()) {
            return DataStatus.WAITING;
        }
        Number[] values = createArrayValuesFromList();
        Double[] probabilities = createArrayProbabilitiesFromList();
        try {
            discreteRandomVariable = new DiscreteRandomVariable(values, probabilities);
        } catch (IllegalArgumentException exception) {
            return DataStatus.BAD_FORMAT;
        }

        return DataStatus.READY;
    }

    private OperationStatus calculateOperationStatus() {
        if (calculateDataStatus() != DataStatus.READY) {
            return OperationStatus.WAITING_DATA;
        }
        if (calculateDataStatus() == DataStatus.READY
                && operation.get() == null) {
            return OperationStatus.WAITING_OPERATION;
        }
        if (operation.get() == Operation.EXPECTED_VALUE
                || operation.get() == Operation.DISPERSION) {
            return OperationStatus.READY;
        }
        if ((operation.get() == Operation.CENTRAL_MOMENT
                || operation.get() == Operation.RAW_MOMENT)
                && operationParameter.get().equals(EMPTY)) {
            return OperationStatus.WAITING_PARAMETER;
        }
        if (!isOperationParameterCorrect.get()) {
            return OperationStatus.BAD_FORMAT;
        }
        return OperationStatus.READY;
    }

    private Number[] createArrayValuesFromList() {
        Number[] values = new Number[listData.size()];
        int i = 0;
        for (TableElement element : listData) {
            values[i++] = Double.parseDouble(element.getValue());
        }

        return values;
    }

    private Double[] createArrayProbabilitiesFromList() {
        Double[] probabilities = new Double[listData.size()];
        int i = 0;
        for (TableElement element : listData) {
            probabilities[i++] = Double.parseDouble(element.getProbability());
        }

        return probabilities;
    }

    private void setInputFieldsToEmpty() {
        newValue.set(EMPTY);
        newProbability.set(EMPTY);
    }

    public List<String> getLog() {
        if (logger == null) {
            throw new IllegalArgumentException("Logger is null");
        }
        return logger.getLog();
    }

    private void updateLogstoView() {
        List<String> fullLog = logger.getLog();
        String record = new String("");
        for (String log : fullLog) {
            record += log + "\n";
        }
        logs.set(record);
    }

    private class UpdateDataChangeListener implements ChangeListener<String> {
        @Override
        public void changed(final ObservableValue<? extends String> observable,
                            final String oldValue, final String newValue) {
            inputDataStatus.set(calculateInputDataStatus().toString());
        }
    }
}


