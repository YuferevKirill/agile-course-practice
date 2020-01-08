package ru.unn.agile.vectoroperations.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.unn.agile.vectoroperations.infrastructure.TxtLogger;
import ru.unn.agile.vectoroperations.model.Vector.Operation;
import ru.unn.agile.vectoroperations.viewmodel.ViewModel;

public class VectorOperations {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField textX0;
    @FXML
    private TextField textY0;
    @FXML
    private TextField textZ0;
    @FXML
    private TextField textX1;
    @FXML
    private TextField textY1;
    @FXML
    private TextField textZ1;
    @FXML
    private ComboBox<Operation> comboBoxOperations;
    @FXML
    private Button buttonCalculate;

    @FXML
    void initialize() {
        viewModel.setLogger(new TxtLogger("./ViewModelTxt.log"));
        textX0.textProperty().bindBidirectional(viewModel.x0Property());
        textY0.textProperty().bindBidirectional(viewModel.y0Property());
        textZ0.textProperty().bindBidirectional(viewModel.z0Property());
        textX1.textProperty().bindBidirectional(viewModel.x1Property());
        textY1.textProperty().bindBidirectional(viewModel.y1Property());
        textZ1.textProperty().bindBidirectional(viewModel.z1Property());

        comboBoxOperations.valueProperty().bindBidirectional(viewModel.opProperty());

        buttonCalculate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });
    }
}
