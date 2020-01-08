package ru.unn.agile.currencyconverter.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.unn.agile.currencyconverter.infrastructure.TxtLogger;
import ru.unn.agile.currencyconverter.model.CurrencyPair;
import ru.unn.agile.currencyconverter.viewmodel.CurrencyConverterViewModel;

public class CurrencyConverterView {
    @FXML
    private CurrencyConverterViewModel viewModel;
    @FXML
    private TextField txtInputCurrency;
    @FXML
    private Label lblError;
    @FXML
    private TextField txtOutputCurrency;
    @FXML
    private ComboBox<CurrencyPair> cbCurrencyPair;
    @FXML
    private Button btnConvert;
    @FXML
    private TextArea logArea;

    @FXML
    void initialize() {
        txtInputCurrency.textProperty().bindBidirectional(viewModel.getInputCurrency());
        lblError.textProperty().bindBidirectional(viewModel.getError());
        txtOutputCurrency.textProperty().bindBidirectional(viewModel.getOutputCurrency());
        cbCurrencyPair.valueProperty().bindBidirectional(viewModel.getCurrencyPair());
        btnConvert.disableProperty().bindBidirectional(viewModel.isConvertButtonDisabled());
        btnConvert.setOnAction(event -> viewModel.convert());
        viewModel.setLogger(new TxtLogger("./OperationLog.log"));
        logArea.textProperty().bindBidirectional(viewModel.getLog());
    }

}
