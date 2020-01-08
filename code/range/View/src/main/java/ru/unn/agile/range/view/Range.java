package ru.unn.agile.range.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.unn.agile.range.infrastructure.TxtLogger;
import ru.unn.agile.range.viewmodel.ViewModel;

public class Range {
    @FXML
    private ViewModel viewModel;
    @FXML
    private Button btnContains;
    @FXML
    private Button btnOverlaps;
    @FXML
    private Button btnEquals;
    @FXML
    private Button btnGetEndPoints;
    @FXML
    private Button btnGetAllPoints;
    @FXML
    private TextField txtResult;
    @FXML
    private TextField txtRange;
    @FXML
    private TextField txtInput;

    @FXML
    void initialize() {
        viewModel.setLogger(new TxtLogger("./TxtLogger-lab3.log"));
        btnContains.disableProperty()
                .bindBidirectional(viewModel.isContainsButtonDisabled());
        btnOverlaps.disableProperty()
                .bindBidirectional(viewModel.isOverlapsButtonDisabled());
        btnEquals.disableProperty()
                .bindBidirectional(viewModel.isEqualsButtonDisabled());
        btnGetAllPoints.disableProperty()
                .bindBidirectional(viewModel.isGetAllPointsButtonDisabled());
        btnGetEndPoints.disableProperty()
                .bindBidirectional(viewModel.isGetEndPointsButtonDisabled());

        txtInput.textProperty().bindBidirectional(viewModel.getTxtInput());
        txtRange.textProperty().bindBidirectional(viewModel.getTxtRange());
        txtResult.textProperty().bindBidirectional(viewModel.getTxtResult());

        btnContains.setOnAction(event -> viewModel.containsInput());
        btnOverlaps.setOnAction(event -> viewModel.overlapsRange());
        btnEquals.setOnAction(event -> viewModel.equalsRange());
        btnGetEndPoints.setOnAction(event -> viewModel.getEndPoints());
        btnGetAllPoints.setOnAction(event -> viewModel.getAllPoints());
    }
}
