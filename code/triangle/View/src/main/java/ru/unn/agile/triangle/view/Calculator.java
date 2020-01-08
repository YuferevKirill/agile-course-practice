package ru.unn.agile.triangle.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import ru.unn.agile.triangle.infrastructure.TriangleTxtLogger;
import ru.unn.agile.triangle.model.Triangle.Operation;
import ru.unn.agile.triangle.viewmodel.TriangleViewModel;

public class Calculator {
    @FXML
    private TriangleViewModel viewModel;
    @FXML
    private TextField txtAx;
    @FXML
    private TextField txtAy;
    @FXML
    private TextField txtBx;
    @FXML
    private TextField txtBy;
    @FXML
    private TextField txtCx;
    @FXML
    private TextField txtCy;
    @FXML
    private ComboBox<Operation> cbOperation;
    @FXML
    private Button btnCalc;

    @FXML
    void initialize() {
        viewModel.setLogger(new TriangleTxtLogger("./TxtLogger-lab3.log"));

        final ChangeListener<Boolean> focusChangeListener = new ChangeListener<>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onFocusChanged(oldValue, newValue);
            }
        };

        txtAx.textProperty().bindBidirectional(viewModel.axProperty());
        txtAx.focusedProperty().addListener(focusChangeListener);

        txtAy.textProperty().bindBidirectional(viewModel.ayProperty());
        txtAy.focusedProperty().addListener(focusChangeListener);

        txtBx.textProperty().bindBidirectional(viewModel.bxProperty());
        txtBx.focusedProperty().addListener(focusChangeListener);

        txtBy.textProperty().bindBidirectional(viewModel.byProperty());
        txtBy.focusedProperty().addListener(focusChangeListener);

        txtCx.textProperty().bindBidirectional(viewModel.cxProperty());
        txtCx.focusedProperty().addListener(focusChangeListener);

        txtCy.textProperty().bindBidirectional(viewModel.cyProperty());
        txtCy.focusedProperty().addListener(focusChangeListener);

        cbOperation.valueProperty().bindBidirectional(viewModel.operationProperty());

        cbOperation.valueProperty().addListener(new ChangeListener<Operation>() {
            @Override
            public void changed(final ObservableValue<? extends Operation> observable,
                                final Operation oldValue,
                                final Operation newValue) {
                viewModel.onOperationChanged(oldValue, newValue);
            }
        });

        btnCalc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });
    }
}
