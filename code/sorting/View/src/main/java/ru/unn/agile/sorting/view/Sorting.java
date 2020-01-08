package ru.unn.agile.sorting.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.unn.agile.sorting.model.api.Direction;
import ru.unn.agile.sorting.viewmodel.ViewModel;
import ru.unn.agile.sorting.infrastructure.TxtLogger;

public class Sorting {
    @FXML
    private ViewModel viewModel;
    @FXML
    private Button btnSort;
    @FXML
    private TextField txtArray;
    @FXML
    private ComboBox<Direction> cbDirection;

    @FXML
    void initialize() {
        viewModel.setLogger(new TxtLogger("./TxtLogger-lab3.log"));
        txtArray.textProperty().bindBidirectional(viewModel.inputArrayProperty());
        cbDirection.valueProperty().bindBidirectional(viewModel.directionProperty());
        btnSort.disableProperty().bindBidirectional(viewModel.sortButtonDisabledProperty());
        btnSort.setOnAction(event -> viewModel.sort());
    }
}
