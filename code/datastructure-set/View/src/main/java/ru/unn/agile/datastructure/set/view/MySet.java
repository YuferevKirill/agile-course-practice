package ru.unn.agile.datastructure.set.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ru.unn.agile.datastructure.set.infrastructure.TxtLogger;
import ru.unn.agile.datastructure.set.viewmodel.ViewModel;

public class MySet {

    private ViewModel viewModel;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnRetain;
    @FXML
    private Button btnClear;
    @FXML
    private Button btnContains;
    @FXML
    private Button btnIsEmpty;
    @FXML
    private TextField txtEnteredSet;
    @FXML
    private TextField txtCurrentSet;
    @FXML
    private Label lblContains;
    @FXML
    private Label lblIsEmpty;

    @FXML
    void initialize() {
        viewModel = new ViewModel(new TxtLogger("./TxtLogger-lab3.log"));
        btnAdd.disableProperty().bindBidirectional(viewModel.isAddButtonDisabled());
        btnRemove.disableProperty().bindBidirectional(viewModel.isRemoveButtonDisabled());
        btnRetain.disableProperty().bindBidirectional(viewModel.isRetainButtonDisabled());
        btnContains.disableProperty().bindBidirectional(viewModel.isContainsButtonDisabled());

        txtEnteredSet.textProperty().bindBidirectional(viewModel.getTxtEnteredItemsProp());
        txtCurrentSet.textProperty().bindBidirectional(viewModel.getTxtCurrentSetProp());
        lblContains.textProperty().bindBidirectional(viewModel.getTxtContainsItemProp());
        lblIsEmpty.textProperty().bindBidirectional(viewModel.getTxtIsEmptyItemProp());

        btnAdd.setOnAction(event -> viewModel.addEnteredItems());
        btnRemove.setOnAction(event -> viewModel.removeEnteredItems());
        btnRetain.setOnAction(event -> viewModel.retainEnteredItems());
        btnClear.setOnAction(event -> viewModel.clearCurrentItems());
        btnContains.setOnAction(event -> viewModel.containsEnteredItems());
        btnIsEmpty.setOnAction(event -> viewModel.isCurrentSetEmpty());
    }
}
