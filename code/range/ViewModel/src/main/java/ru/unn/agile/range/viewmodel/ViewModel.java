package ru.unn.agile.range.viewmodel;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.unn.agile.range.model.Range;
import ru.unn.agile.range.model.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static ru.unn.agile.range.model.Utils.*;

public class ViewModel {

    private BooleanProperty btnGetEndPointsDisabled = new SimpleBooleanProperty();
    private BooleanProperty btnGetAllPointsDisabled = new SimpleBooleanProperty();
    private BooleanProperty btnContainsDisabled = new SimpleBooleanProperty();
    private BooleanProperty btnEqualsDisabled = new SimpleBooleanProperty();
    private BooleanProperty btnOverlapsDisabled = new SimpleBooleanProperty();
    private StringProperty txtInput = new SimpleStringProperty();
    private StringProperty txtRange = new SimpleStringProperty();
    private StringProperty txtResult = new SimpleStringProperty();
    private SortingLogger logger;
    private Range range;
    private final String yes = "Yes";
    private final String no = "No";


    public final void setLogger(final SortingLogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public ViewModel() {
        init();
    }

    public ViewModel(final SortingLogger logger) {
        setLogger(logger);
        init();
    }

    private void init() {
        btnGetEndPointsDisabled.setValue(true);
        btnGetAllPointsDisabled.setValue(true);
        btnOverlapsDisabled.setValue(true);
        btnEqualsDisabled.setValue(true);
        btnContainsDisabled.setValue(true);

        txtResult.setValue("");
        txtRange.setValue("");
        txtInput.setValue("");

        txtRange.addListener((observable, oldValue, newValue) -> {
            setRange(newValue);
        });
        txtInput.addListener((observable, oldValue, newValue) -> {
            setInput(newValue);
        });
    }

    public BooleanProperty isGetEndPointsButtonDisabled() {
        return btnGetEndPointsDisabled;
    }

    public BooleanProperty isGetAllPointsButtonDisabled() {
        return btnGetAllPointsDisabled;
    }

    public BooleanProperty isContainsButtonDisabled() {
        return btnContainsDisabled;
    }

    public BooleanProperty isEqualsButtonDisabled() {
        return btnEqualsDisabled;
    }

    public BooleanProperty isOverlapsButtonDisabled() {
        return btnOverlapsDisabled;
    }

    public StringProperty getTxtRange() {
        return txtRange;
    }

    public StringProperty getTxtResult() {
        return txtResult;
    }

    public StringProperty getTxtInput() {
        return txtInput;
    }

    public void containsInput() {
        String input = txtInput.get();
        logger.log(Actions.CONTAINS_CHECK + range + " and " + input);
        if (isInteger(input)) {
            if (range.containsValue(Integer.parseInt(input))) {
                txtResult.setValue(yes);
            } else {
                txtResult.setValue(no);
            }
        }
        if (isIntegerSet(input)) {
            if (range.containsSet(Arrays.stream(input.substring(1, input.length() - 1).split(","))
                    .map(String::trim).mapToInt(Integer::parseInt).toArray())) {
                txtResult.setValue(yes);
            } else {
                txtResult.setValue(no);
            }
        }
        if (isRange(input)) {
            if (range.containsRange(new Range(input))) {
                txtResult.setValue(yes);
            } else {
                txtResult.setValue(no);
            }
        }
    }

    public void overlapsRange() {
        String input = txtInput.get();
        logger.log(Actions.OVERLAPS_CHECK + range + " and " + input);
        if (isRange(input)) {
            if (range.overlapsRange(new Range(input))) {
                txtResult.setValue(yes);
            } else {
                txtResult.setValue(no);
            }
        }
    }

    public void equalsRange() {
        String input = txtInput.get();
        logger.log(Actions.EQUALS_CHECK + range + " and " + input);
        if (isRange(input)) {
            if (range.equals(new Range(input))) {
                txtResult.setValue(yes);
            } else {
                txtResult.setValue(no);
            }
        }
    }

    public void getAllPoints() {
        logger.log(Actions.ALL_POINTS_CHECK + range);
        txtResult.setValue(Arrays.toString(range.getAllPoints()));
    }

    public void getEndPoints() {
        logger.log(Actions.END_POINTS_CHECK + range);
        txtResult.setValue(Arrays.toString(range.endPoints()));
    }

    public final List<String> getLog() {
        List<String> log;
        try {
            log = logger.getLog();
        } catch (NullPointerException ex) {
            log = new ArrayList<String>();
        }
        return log;
    }

    private void setRange(final String input) {
        logger.log(Actions.NEW_RANGE + input);
        var isCorrectInput = Utils.isRange(input);

        if (isCorrectInput) {
            this.range = new Range(input);
        } else {
            logger.log(Actions.INCORRECT_INPUT + input);
        }

        btnGetAllPointsDisabled.setValue(!isCorrectInput);
        btnGetEndPointsDisabled.setValue(!isCorrectInput);
        disableAllButtonsConnectedWithInput();
    }

    private void setInput(final String input) {
        logger.log(Actions.NEW_INPUT + input);
        disableAllButtonsConnectedWithInput();

        if (btnGetEndPointsDisabled.get()) {
            return;
        }

        if (isInteger(input) || isIntegerSet(input)) {
            setEnable(btnContainsDisabled);
        } else if (isRange(input)) {
            setEnable(btnContainsDisabled);
            setEnable(btnOverlapsDisabled);
            setEnable(btnEqualsDisabled);
        }
    }

    private void disableAllButtonsConnectedWithInput() {
        setDisable(btnContainsDisabled);
        setDisable(btnOverlapsDisabled);
        setDisable(btnEqualsDisabled);
    }

    private void setDisable(final BooleanProperty booleanProperty) {
        booleanProperty.setValue(true);
    }

    private void setEnable(final BooleanProperty booleanProperty) {
        booleanProperty.setValue(true);
    }
}
