package ru.unn.agile.primenumber.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.unn.agile.primenumber.infrastructure.ActionLogger;
import ru.unn.agile.primenumber.viewmodel.ViewModel;

public class PrimeNumber {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField startElement;
    @FXML
    private TextField endElement;
    @FXML
    private Button btnOk;
    @FXML
    private TextArea outputField;

    @FXML
    void initialize() {
        viewModel.setLogger(new ActionLogger("./TxtLogger-lab3.log"));
        startElement.textProperty().bindBidirectional(viewModel.startElemProperty());
        endElement.textProperty().bindBidirectional(viewModel.endElemProperty());
        outputField.textProperty().bindBidirectional(viewModel.outputProperty());

        btnOk.setOnAction(event -> viewModel.findPrimaryNums());
    }
}
