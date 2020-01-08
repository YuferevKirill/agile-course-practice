package ru.unn.agile.depositcalculator.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.unn.agile.depositcalculator.infrastructure.Logger;
import ru.unn.agile.depositcalculator.model.CapitalizationPeriod;
import ru.unn.agile.depositcalculator.model.DepositTimeType;
import ru.unn.agile.depositcalculator.viewmodel.ViewModel;

public class DepositCalculator {

    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtPercentage;
    @FXML
    private TextField txtStartSum;
    @FXML
    private ComboBox<DepositTimeType> cmbPeriod;
    @FXML
    private ComboBox<CapitalizationPeriod> cmbCapit;
    @FXML
    private TextArea txtResult;
    @FXML
    private TextArea areaLog;
    @FXML
    private Button btnCalc;

    @FXML
    void initialize() {
        viewModel.setLogger(new Logger("./TxtLogger.log"));
        txtPercentage.textProperty().bindBidirectional(viewModel.percentProperty());
        txtStartSum.textProperty().bindBidirectional(viewModel.startSumProperty());
        txtResult.textProperty().bindBidirectional(viewModel.resultProperty());

        cmbPeriod.valueProperty().bindBidirectional(viewModel.periodProperty());
        cmbCapit.valueProperty().bindBidirectional(viewModel.capitalizationProperty());

        areaLog.textProperty().bindBidirectional(viewModel.getLogsProperty());
        txtPercentage.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                viewModel.onPercentageFocusChanged();
            }
        });
        txtStartSum.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                viewModel.onSumFocusChanged();
            }
        });

        btnCalc.setOnAction(event -> viewModel.calculate());
    }
}
