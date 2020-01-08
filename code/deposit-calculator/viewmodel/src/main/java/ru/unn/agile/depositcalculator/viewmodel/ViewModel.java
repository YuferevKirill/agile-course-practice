package ru.unn.agile.depositcalculator.viewmodel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ru.unn.agile.depositcalculator.model.Calculator;
import ru.unn.agile.depositcalculator.model.CapitalizationPeriod;
import ru.unn.agile.depositcalculator.model.DepositTimeType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ViewModel {

    private final Pattern pattern =
            Pattern.compile("^([0-9]*)(\\.)?([0-9]*)$", Pattern.CASE_INSENSITIVE);

    // region fields
    private final SimpleStringProperty percentProperty = new SimpleStringProperty();
    private final SimpleStringProperty startSumProperty = new SimpleStringProperty();
    private final SimpleStringProperty resultProperty = new SimpleStringProperty();
    private final SimpleStringProperty logsProperty = new SimpleStringProperty();
    private final ObjectProperty<ObservableList<DepositTimeType>> periods =
            new SimpleObjectProperty<>(
                    FXCollections.observableArrayList(DepositTimeType.values()));
    private final ObjectProperty<ObservableList<CapitalizationPeriod>> capitalizations =
            new SimpleObjectProperty<>(
                    FXCollections.observableArrayList(CapitalizationPeriod.values()));
    private final ObjectProperty<DepositTimeType> period = new SimpleObjectProperty<>();
    private final ObjectProperty<CapitalizationPeriod> capitalization =
            new SimpleObjectProperty<>();

    private ILogger logger;
    // endregion

    // region getters/setters

    public ObservableList<DepositTimeType> getPeriods() {
        return periods.get();
    }

    public ObservableList<CapitalizationPeriod> getCapitalizations() {
        return capitalizations.get();
    }

    public DepositTimeType getPeriod() {
        return period.get();
    }

    public ObjectProperty<DepositTimeType> periodProperty() {
        return period;
    }

    public void setPeriod(final DepositTimeType period) {
        this.period.set(period);
    }

    public CapitalizationPeriod getCapitalization() {
        return capitalization.get();
    }

    public ObjectProperty<CapitalizationPeriod> capitalizationProperty() {
        return capitalization;
    }

    public void setCapitalization(final CapitalizationPeriod capitalization) {
        this.capitalization.set(capitalization);
    }

    public String getPercentProperty() {
        return percentProperty.get();
    }

    public SimpleStringProperty percentProperty() {
        return percentProperty;
    }

    public void setPercentProperty(final String percentProperty) {
        this.percentProperty.set(percentProperty);
    }

    public String getStartSumProperty() {
        return startSumProperty.get();
    }

    public SimpleStringProperty startSumProperty() {
        return startSumProperty;
    }

    public void setStartSumProperty(final String startSumProperty) {
        this.startSumProperty.set(startSumProperty);
    }

    public String getResultProperty() {
        return resultProperty.get();
    }

    public SimpleStringProperty resultProperty() {
        return resultProperty;
    }

    public void setResultProperty(final String resultProperty) {
        this.resultProperty.set(resultProperty);
    }

    public final SimpleStringProperty getLogsProperty() {
        return logsProperty;
    }
    //endregion

    public ViewModel() {
        initialize();
    }

    public ViewModel(final ILogger logger) {
        setLogger(logger);
        initialize();
    }

    public void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger value can't be null");
        }
        this.logger = logger;
    }

    public void calculate() {
        if (getStartSumProperty().isEmpty()
                || getPercentProperty().isEmpty()
                || !getValidationStatus(getStartSumProperty())
                || !getValidationStatus(getPercentProperty())) {
            setResultProperty(LogMessages.VALIDATION_ERROR);
            return;
        }

        Calculator calculator = new Calculator();
        calculator.setPeriod(getPeriod(), 1);
        calculator.setPercent(Integer.parseInt(getPercentProperty()));
        calculator.setStartSum(Double.parseDouble(getStartSumProperty()));
        calculator.setCapitalizationPeriod(getCapitalization());
        double result = calculator.calculate();
        result = getCustomerFormat(result);
        setResultProperty(String.format("%s", result));

        log(LogMessages.CALCULATION_COMPLETED_LOG_MSG + result);
    }

    public void onPercentageFocusChanged() {
        log(LogMessages.PERCENTAGE_UPDATED_LOG_MSG + getPercentProperty());
    }

    public void onSumFocusChanged() {
        log(LogMessages.START_SUM_UPDATED_LOG_MSG + getStartSumProperty());
    }

    public List<String> getLogs() {
        if (logger == null) {
            return new ArrayList<>();
        }
        return logger.getLog();
    }

    private boolean getValidationStatus(final String value) {
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    private double getCustomerFormat(final double input) {
        BigDecimal bd = new BigDecimal(input);
        return bd.setScale(2, RoundingMode.UP).doubleValue();
    }

    private void setActualLogs() {
        var fullLog = getLogs();
        StringBuilder record = new StringBuilder();
        for (String log : fullLog) {
            record.append(log).append("\n");
        }
        logsProperty.set(record.toString());
    }

    private void log(final String message) {
        if (logger != null) {
            logger.log(message);
            setActualLogs();
        }
    }

    private void initialize() {
        period.addListener((observable, oldValue, newValue) -> {
            log(LogMessages.PERIOD_UPDATED_LOG_MSG + newValue);
        });
        capitalization.addListener((observable, oldValue, newValue) -> {
            log(LogMessages.CAPITALIZATION_UPDATED_LOG_MSG + newValue);
        });

        setCapitalization(CapitalizationPeriod.MONTH);
        setPeriod(DepositTimeType.DAY);
        setStartSumProperty("1000");
        setPercentProperty("8");
    }

    static final class LogMessages {
        private LogMessages() {
        }

        static final String VALIDATION_ERROR =
                "Fields should contains only number and values should be more or equal 0";
        static final String PERIOD_UPDATED_LOG_MSG = "Selected period updated: ";
        static final String CAPITALIZATION_UPDATED_LOG_MSG = "Selected capitalization updated: ";
        static final String START_SUM_UPDATED_LOG_MSG = "Start sum value updated: ";
        static final String PERCENTAGE_UPDATED_LOG_MSG = "Percentage value updated: ";
        static final String CALCULATION_COMPLETED_LOG_MSG = "Calculation completed: ";
    }
}
