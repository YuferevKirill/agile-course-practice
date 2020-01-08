package ru.unn.agile.fractioncalculator.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.unn.agile.fractioncalculator.viewmodel.ViewModel;

public class FractionCalculator {

    @FXML private ViewModel viewModel;

    @FXML private TextField firstFraction;
    @FXML private TextField secondFraction;

    @FXML private Label resultLabel;

    @FXML private Button plusButton;
    @FXML private Button minusButton;
    @FXML private Button multipleButton;
    @FXML private Button divideButton;

    @FXML
    void initialize() {
        firstFraction.textProperty().bindBidirectional(viewModel.firstFractionProperty());
        secondFraction.textProperty().bindBidirectional(viewModel.secondFractionProperty());
        resultLabel.textProperty().bindBidirectional(viewModel.firstFractionProperty());

        plusButton.setOnAction(actionEvent -> viewModel.calculateSum());
        minusButton.setOnAction(actionEvent -> viewModel.calculateMinus());
        multipleButton.setOnAction(actionEvent -> viewModel.calculateMultiple());
        divideButton.setOnAction(actionEvent -> viewModel.calculateDivide());
    }
}
