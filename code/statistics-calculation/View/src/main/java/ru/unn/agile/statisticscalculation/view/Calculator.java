package ru.unn.agile.statisticscalculation.view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import ru.unn.agile.statisticscalculation.infrastructure.TextLogger;
import ru.unn.agile.statisticscalculation.viewmodel.ViewModel;
import ru.unn.agile.statisticscalculation.viewmodel.Operation;
import ru.unn.agile.statisticscalculation.viewmodel.TableElement;

public class Calculator {
    @FXML
    private ViewModel viewModel;

    @FXML
    private TextField textfieldNewValue;
    @FXML
    private TextField textfieldNewProbability;
    @FXML
    private TextField textfieldParameter;

    @FXML
    private ComboBox<Operation> comboBoxOperation;

    @FXML
    private AnchorPane anchorPaneSettings;

    @FXML
    private Button buttonUpdate;
    @FXML
    private Button buttonDelete;
    @FXML
    private Button buttonCalculate;

    @FXML
    private TableView<TableElement> tableViewData;
    @FXML
    private TableColumn<TableElement, String> columnValue;
    @FXML
    private TableColumn<TableElement, String> columnProbability;

    @FXML
    void initialize() {
        viewModel.setLoggerToViewModel(new TextLogger("./TextLogger-StatisticsCalculation.log"));
        textfieldNewValue.textProperty().bindBidirectional(viewModel.newValueProperty());
        textfieldNewProbability.textProperty().bindBidirectional(
                viewModel.newProbabilityProperty());
        textfieldParameter.textProperty().bindBidirectional(
                viewModel.operationParameterProperty());

        comboBoxOperation.valueProperty().bindBidirectional(viewModel.operationProperty());

        columnValue.setCellValueFactory(
                new PropertyValueFactory<>("value"));
        columnProbability.setCellValueFactory(
                new PropertyValueFactory<>("probability"));

        buttonUpdate.setOnAction(new EventHandler<>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.updateTableElement();
                tableViewData.setItems(viewModel.getListData());
                tableViewData.getSelectionModel().clearSelection();
            }
        });

        buttonDelete.setOnAction(new EventHandler<>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.deleteTableElement(tableViewData.getSelectionModel().getFocusedIndex());
                tableViewData.setItems(viewModel.getListData());
                tableViewData.getSelectionModel().clearSelection();
            }
        });

        tableViewData.setOnMousePressed(new EventHandler<>() {
            @Override
            public void handle(final MouseEvent event) {
                viewModel.setSelectedElement(tableViewData.getSelectionModel().getFocusedIndex());
            }
        });

        tableViewData.setOnMouseClicked(new EventHandler<>() {
            @Override
            public void handle(final MouseEvent event) {
                Node source = event.getPickResult().getIntersectedNode();

                // move up through the node hierarchy until a TableRow or scene root is found
                while (source != null && !(source instanceof TableRow)) {
                    source = source.getParent();
                }

                // clear selection on click anywhere but on a filled row
                if (source == null || (source instanceof TableRow
                        && ((TableRow) source).isEmpty())) {
                    viewModel.resetSelectedElement();
                    tableViewData.getSelectionModel().clearSelection();
                }
            }
        });

        comboBoxOperation.setOnAction(new EventHandler<>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.updateOperation();
            }
        });

        buttonCalculate.setOnAction(new EventHandler<>() {
            @Override
            public void handle(final ActionEvent event) {
                viewModel.calculate();
            }
        });
    }
}
