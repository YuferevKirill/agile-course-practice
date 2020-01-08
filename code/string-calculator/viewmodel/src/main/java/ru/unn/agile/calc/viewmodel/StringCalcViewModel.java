package ru.unn.agile.calc.viewmodel;

import ru.unn.agile.calc.model.StringCalc;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class StringCalcViewModel {

    private static final Pattern ALLOWED_SYMBOLS = Pattern.compile("[\\d-+*/.\\s]");
    private static final String ERROR_MESSAGE = "Error. Check your input";

    private final StringProperty logs = new SimpleStringProperty();
    private final SimpleStringProperty expressionTf = new SimpleStringProperty();
    private final SimpleStringProperty resultLbl = new SimpleStringProperty();
    private final SimpleBooleanProperty calculationDisabled = new SimpleBooleanProperty();

    private final StringCalc calculator = new StringCalc();
    private ILogger logger;

    public StringCalcViewModel() {
        init();
    }

    public StringCalcViewModel(final ILogger logger) {
        setLogger(logger);
        init();
    }

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    private void init() {
        expressionTf.set("");
        resultLbl.set("");
        expressionTf.addListener((observable, oldValue, newValue) ->
                    expressionTf.setValue(newValue.replaceAll("\\s", ""))
        );
        BooleanBinding canCalculateBoolBinding = new BooleanBinding() {
            {
                super.bind(expressionTf);
            }
            @Override
            protected boolean computeValue() {
                return isExpressionValid();
            }
        };
        calculationDisabled.bind(canCalculateBoolBinding.not());
    }

    public void calculate() {
        if (!isCalculationDisabled()) {
            StringBuilder message = new StringBuilder(LogMessages.CALCULATE_WAS_PRESSED);
            message.append("\"")
                    .append(expressionTf.getValue())
                    .append("\"");
            logger.log(message.toString());
            updateLogs();

            try {
                String calcResult = String.valueOf(calculator.result(getExpression()));
                resultLbl.setValue((calcResult));

                logger.log(LogMessages.CALCULATION_WAS_SUCCESSFUL);
                updateLogs();
            } catch (Exception e) {
                resultLbl.setValue(ERROR_MESSAGE);
                logger.log(LogMessages.CALCULATION_WAS_UNSUCCESSFUL);
                updateLogs();
            }
        }
    }

    public boolean isExpressionValid() {
        String exprText = getExpression();
        return !(exprText.isEmpty() || hasRestrictedSymbols(exprText));
    }

    private boolean hasRestrictedSymbols(final String input) {
        return input.chars()
                .mapToObj(c -> String.valueOf((char) c))
                .anyMatch(symbol -> !ALLOWED_SYMBOLS.matcher(symbol).matches());
    }

    public StringProperty expressionTfProperty() {
        return expressionTf;
    }

    private String getExpression() {
        return expressionTf.get().replaceAll("\\s", "");
    }

    public void onExpressionTfFocusChanged() {
        String message = LogMessages.EDITING_FINISHED;
        if (isExpressionValid()) {
            message += LogMessages.EXPRESSION_IS_VALID;
        } else {
            message += LogMessages.EXPRESSION_IS_INVALID;
        }

        logger.log(message);
        updateLogs();
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        String record = new String("");
        for (String log : fullLog) {
            record += log + "\n";
        }
        logs.set(record);
    }

    public StringProperty resultLblProperty() {
        return resultLbl;
    }

    private boolean isCalculationDisabled() {
        return calculationDisabled.get();
    }

    public BooleanProperty calculationDisabledProperty() {
        return calculationDisabled;
    }

    public StringProperty logsProperty() {
        return logs;
    }

    public final String getLogs() {
        return logs.get();
    }

    public final List<String> getLog() {
        if (logger != null) {
            return logger.getLog();
        }
        return Collections.emptyList();
    }
}

final class LogMessages {
    public static final String CALCULATE_WAS_PRESSED = "Calculating ";
    public static final String CALCULATION_WAS_SUCCESSFUL = "Calculation completed.";
    public static final String CALCULATION_WAS_UNSUCCESSFUL = "Calculation failed. Incorrect input";
    public static final String EDITING_FINISHED = "Updated input. ";
    public static final String EXPRESSION_IS_VALID = "Expression is valid.";
    public static final String EXPRESSION_IS_INVALID = "Expression is invalid.";

    private LogMessages() { }
}
