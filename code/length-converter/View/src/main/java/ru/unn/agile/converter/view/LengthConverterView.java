package ru.unn.agile.converter.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.unn.agile.converter.model.LengthType;
import ru.unn.agile.converter.viewmodel.LengthConverterViewModel;
import ru.unn.agile.converter.infrastructure.LengthConverterTxtLogger;

public class LengthConverterView {
    @FXML
    private LengthConverterViewModel viewModel;
    @FXML
    private TextField txtInput;
    @FXML
    private Label lblError;
    @FXML
    private TextField txtOutput;
    @FXML
    private ComboBox<LengthType> cbFromType;
    @FXML
    private ComboBox<LengthType> cbToType;
    @FXML
    private Button btnConvert;
    @FXML
    private Button btnSwap;

    @FXML
    void initialize() {
        viewModel.setLogger(new LengthConverterTxtLogger("./TxtLogger-lab3.log"));
        txtInput.textProperty().bindBidirectional(viewModel.getInput());
        lblError.textProperty().bindBidirectional(viewModel.getError());
        txtOutput.textProperty().bindBidirectional(viewModel.getOutput());
        cbFromType.valueProperty().bindBidirectional(viewModel.getFromType());
        cbToType.valueProperty().bindBidirectional(viewModel.getToType());
        btnConvert.disableProperty().bindBidirectional(viewModel.isConvertButtonDisabled());
        btnConvert.setOnAction(event -> viewModel.convert());
        btnSwap.setOnAction(event -> viewModel.swap());

    }

}
