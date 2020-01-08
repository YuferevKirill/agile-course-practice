package ru.unn.agile.bitarray.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.unn.agile.bitarray.infrastructure.SimpleLogger;
import ru.unn.agile.bitarray.viewmodel.ViewModel;

public class BitArray {
    private ViewModel viewModel;

    @FXML
    private TextField inputBitArray;
    @FXML
    private TextField inputBit;

    @FXML
    private Label labelStatusArray;
    @FXML
    private Label labelStatusBit;
    @FXML
    private Label labelBitArray;

    @FXML
    private Button btnCreateBitArray;
    @FXML
    private Button btnSetBit;
    @FXML
    private Button btnUnsetBit;

    @FXML
    void initialize() {
        viewModel = new ViewModel(new SimpleLogger("./bit_array.log"));

        labelStatusArray.textProperty().bind(viewModel.fieldInputArrayStatusProperty());
        labelStatusBit.textProperty().bind(viewModel.fieldInputBitStatusProperty());

        labelBitArray.textProperty().bind(viewModel.fieldBitArrayProperty());

        inputBitArray.textProperty().bindBidirectional(viewModel.inputBitArrayProperty());
        inputBit.textProperty().bindBidirectional(viewModel.inputBitProperty());

        btnCreateBitArray.setOnAction(event -> viewModel.create());
        btnSetBit.setOnAction(event -> viewModel.setBit());
        btnUnsetBit.setOnAction(event -> viewModel.unsetBit());
    }
}
