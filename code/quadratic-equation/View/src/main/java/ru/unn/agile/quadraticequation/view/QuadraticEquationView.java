package ru.unn.agile.quadraticequation.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.unn.agile.quadraticequation.infrastructure.QuadraticEquationTxtLogger;
import ru.unn.agile.quadraticequation.viewmodel.QuadraticEquationViewModel;

public class QuadraticEquationView {
    @FXML
    private QuadraticEquationViewModel viewModel;
    @FXML
    private Button btnCalc;
    @FXML
    private TextField txtCoeffA;
    @FXML
    private TextField txtCoeffB;
    @FXML
    private TextField txtCoeffC;
    @FXML
    private Label lblResult;
    @FXML
    private Label lblError;

    @FXML
    void initialize() {
        viewModel.setLogger(
                new QuadraticEquationTxtLogger("./QuadraticEquationViewModel_with_TxtLogger.log"));
        btnCalc.disableProperty().bindBidirectional(viewModel.isCalculateButtonDisabled());
        txtCoeffA.textProperty().bindBidirectional(viewModel.getTxtCoeffAProperty());
        txtCoeffB.textProperty().bindBidirectional(viewModel.getTxtCoeffBProperty());
        txtCoeffC.textProperty().bindBidirectional(viewModel.getTxtCoeffCProperty());
        lblResult.textProperty().bindBidirectional(viewModel.getTxtResultProperty());
        lblError.textProperty().bindBidirectional(viewModel.getTxtErrorProperty());
        btnCalc.setOnAction(event -> {
            viewModel.calculate();
        });
    }
}
