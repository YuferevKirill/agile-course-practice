package ru.unn.agile.quadraticequation.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.unn.agile.ComplexNumber.model.ComplexNumber;
import ru.unn.agile.quadraticequation.model.QuadraticEquation;

import java.util.List;

public class QuadraticEquationViewModel {
    private BooleanProperty btnCalcDisabledProp = new SimpleBooleanProperty();
    private StringProperty txtCoeffAProp = new SimpleStringProperty();
    private StringProperty txtCoeffBProp = new SimpleStringProperty();
    private StringProperty txtCoeffCProp = new SimpleStringProperty();
    private StringProperty txtResultProp = new SimpleStringProperty();
    private StringProperty getTxtErrorProp = new SimpleStringProperty();
    private final StringProperty logs = new SimpleStringProperty();
    private QuadraticEquationILogger logger;

    public QuadraticEquationViewModel() {
        init();
    }

    public QuadraticEquationViewModel(final QuadraticEquationILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
        init();
    }

    private void init() {
        btnCalcDisabledProp.setValue(true);
        txtCoeffAProp.setValue("");
        txtCoeffBProp.setValue("");
        txtCoeffCProp.setValue("");
        txtResultProp.setValue("");
        getTxtErrorProp.setValue("");

        txtCoeffAProp.addListener((observable, oldValue, newValue) -> {
            inputValue(txtCoeffAProp, newValue);
        });
        txtCoeffBProp.addListener((observable, oldValue, newValue) -> {
            inputValue(txtCoeffBProp, newValue);
        });
        txtCoeffCProp.addListener((observable, oldValue, newValue) -> {
            inputValue(txtCoeffCProp, newValue);
        });
    }

    private void inputValue(final StringProperty property, final String newValue) {
        property.setValue(newValue);
        btnCalcDisabledProp.setValue(!areTxtFieldsNotEmpty() || !canConvertCoeffsToDouble());
        getTxtErrorProp.setValue("");
    }

    public BooleanProperty isCalculateButtonDisabled() {
        return btnCalcDisabledProp;
    }

    public StringProperty getTxtCoeffAProperty() {
        return txtCoeffAProp;
    }

    public StringProperty getTxtCoeffBProperty() {
        return txtCoeffBProp;
    }

    public StringProperty getTxtCoeffCProperty() {
        return txtCoeffCProp;
    }

    public StringProperty getTxtResultProperty() {
        return txtResultProp;
    }

    private boolean areTxtFieldsNotEmpty() {
        return !getTxtCoeffAProperty().get().isEmpty()
                && !getTxtCoeffBProperty().get().isEmpty()
                && !getTxtCoeffCProperty().get().isEmpty();
    }

    public void calculate() {
        QuadraticEquation qe;
        try {
            qe = new QuadraticEquation(Double.parseDouble(txtCoeffAProp.get()),
                    Double.parseDouble(txtCoeffBProp.get()),
                    Double.parseDouble(txtCoeffCProp.get()));
        } catch (IllegalArgumentException e) {
            getTxtErrorProp.setValue("Incorrect Input Data");
            return;
        }
        ComplexNumber[] result = qe.solve();
        String res = formatResult(result);
        String message = "Calculate for: " + "A = " + txtCoeffAProp.get()
                + " B = " + txtCoeffBProp.get()
                + " C = " + txtCoeffCProp.get()
                + " Result: " + res;
        logger.log(message);
        updateLogs();
        setTxtResultProp(res);
    }

    private String formatResult(final ComplexNumber[] result) {
        StringBuilder res = new StringBuilder();
        res.append("X1 = ").append(result[0]);
        if (result.length > 1) {
            res.append("; X2 = ").append(result[1]);
        }

        return res.toString();
    }

    private void setTxtResultProp(final String result) {
        txtResultProp.setValue(result);
    }

    private boolean canConvertCoeffsToDouble() {
        try {
            Double.parseDouble(txtCoeffAProp.get());
            Double.parseDouble(txtCoeffBProp.get());
            Double.parseDouble(txtCoeffCProp.get());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public StringProperty getTxtErrorProperty() {
        return getTxtErrorProp;
    }

    public List<String> getLog() {
        return logger.getLog();
    }

    public void setLogger(final QuadraticEquationILogger logger) {
        this.logger = logger;
    }

    public final String getLogs() {
        return logs.get();
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        StringBuilder record = new StringBuilder("");
        for (String log : fullLog) {
            record.append(log).append("\n");
        }
        logs.set(record.toString());
    }
}
