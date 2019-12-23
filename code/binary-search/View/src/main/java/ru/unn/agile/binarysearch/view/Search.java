package ru.unn.agile.binarysearch.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.unn.agile.binarysearch.infrastructure.TxtLogger;
import ru.unn.agile.binarysearch.viewmodel.ViewModel;

public class Search {

    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField arrayInput;
    @FXML
    private TextField elementInput;
    @FXML
    private Button buttonSearch;
    @FXML
    private Label labelStatus;
    @FXML
    private Label labelResult;
    @FXML
    private TextArea textArea;

    @FXML
    void initialize() {

        viewModel.setLogger(new TxtLogger("./TxtLogger-lab3.log"));

        final ChangeListener<Boolean> focusChangeListener = new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> observable,
                                final Boolean oldValue, final Boolean newValue) {
                viewModel.onFocusChanged(oldValue, newValue);
            }
        };

        arrayInput.textProperty().bindBidirectional(viewModel.arrayInputProperty());
        arrayInput.focusedProperty().addListener(focusChangeListener);

        elementInput.textProperty().bindBidirectional(viewModel.elementInputProperty());
        elementInput.focusedProperty().addListener(focusChangeListener);

        labelStatus.textProperty().bindBidirectional(viewModel.statusProperty());
        labelResult.textProperty().bindBidirectional(viewModel.resultProperty());
        textArea.textProperty().bindBidirectional(viewModel.logProperty());

        buttonSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.search();
            }
        });
    }
}
