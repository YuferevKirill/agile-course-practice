package ru.unn.agile.arabicromanconverter.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.unn.agile.arabicromanconverter.infrastructure.FileLogger;
import ru.unn.agile.arabicromanconverter.viewmodel.ViewModel;

public class ArabicRomanConverter {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField inputField;
    @FXML
    private TextField outputField;
    @FXML
    private Label lblError;
    @FXML
    private Label lblConvert;
    @FXML
    private Button btnConvert;
    @FXML
    private Button btnSwap;

    @FXML
    void initialize() {
        viewModel.setLogger(new FileLogger("./TxtLogger-lab3.log"));
        inputField.textProperty().bindBidirectional(viewModel.getInput());
        outputField.textProperty().bindBidirectional(viewModel.getOutput());
        lblConvert.textProperty().bindBidirectional(viewModel.getConvert());
        lblError.textProperty().bindBidirectional(viewModel.getError());
        btnConvert.disableProperty().bindBidirectional(viewModel.isConvertButtonDisabled());
        btnConvert.setOnAction(event -> viewModel.convert());
        btnSwap.setOnAction(event -> viewModel.swap());

    }
}
