package ru.unn.agile.crosssections.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ru.unn.agile.crosssections.infrastructure.FileLogger;
import ru.unn.agile.crosssections.viewmodel.ViewModel;

public class CrossChecker {
    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField txtFstSectionFinishX;
    @FXML
    private TextField txtFstSectionFinishY;
    @FXML
    private TextField txtFstSectionStartX;
    @FXML
    private TextField txtFstSectionStartY;
    @FXML
    private TextField txtSndSectionStartX;
    @FXML
    private TextField txtSndSectionStartY;
    @FXML
    private TextField txtSndSectionFinishX;
    @FXML
    private TextField txtSndSectionFinishY;
    @FXML
    private Button btnCheck;

    @FXML
    void initialize() {
        viewModel.setLogger(new FileLogger("./FileLogger.log"));
        txtFstSectionFinishX.textProperty().bindBidirectional(
                viewModel.fstSectionStartXProperty());
        txtFstSectionFinishY.textProperty().bindBidirectional(
                viewModel.fstSectionStartYProperty());
        txtFstSectionStartX.textProperty().bindBidirectional(
                viewModel.fstSectionFinishXProperty());
        txtFstSectionStartY.textProperty().bindBidirectional(
                viewModel.fstSectionFinishYProperty());
        txtSndSectionStartX.textProperty().bindBidirectional(
                viewModel.sndSectionStartXProperty());
        txtSndSectionStartY.textProperty().bindBidirectional(
                viewModel.sndSectionStartYProperty());
        txtSndSectionFinishX.textProperty().bindBidirectional(
                viewModel.sndSectionFinishXProperty());
        txtSndSectionFinishY.textProperty().bindBidirectional(
                viewModel.sndSectionFinishYProperty());

        btnCheck.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.check();
            }
        });
    }
}
