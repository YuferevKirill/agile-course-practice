package ru.unn.agile.mortgagecalculator.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.unn.agile.mortgagecalculator.infrastructure.MortgageCalculatorTxtLogger;
import ru.unn.agile.mortgagecalculator.viewmodel.MortgageCalculatorViewModel;

public class MortgageCalculatorView {

    @FXML
    private MortgageCalculatorViewModel viewModel;
    @FXML
    private TextField apartmentPrice;
    @FXML
    private TextField firstPayment;
    @FXML
    private TextField loanPeriod;
    @FXML
    private TextField interestRate;
    @FXML
    private TextField monthlyCommissions;
    @FXML
    private TextField oneTimeCommissions;
    @FXML
    private TextArea result;
    @FXML
    private ChoiceBox<String> loanPeriodType;
    @FXML
    private ChoiceBox<String> monthlyCommissionType;
    @FXML
    private ChoiceBox<String> oneTimeCommissionType;
    @FXML
    private ChoiceBox<String> typeOfPayment;
    @FXML
    private Button calculate;
    @FXML
    private TextArea areaLog;

    @FXML
    void initialize() {
        viewModel.setLogger(new MortgageCalculatorTxtLogger("./MortgageCalculatorTxtLogger.log"));
        apartmentPrice.textProperty().bindBidirectional(viewModel.apartmentPriceProperty());
        firstPayment.textProperty().bindBidirectional(viewModel.firstPaymentProperty());
        loanPeriod.textProperty().bindBidirectional(viewModel.loanPeriodProperty());
        interestRate.textProperty().bindBidirectional(viewModel.interestRateProperty());
        monthlyCommissions.textProperty().bindBidirectional(viewModel.monthlyCommissionsProperty());
        oneTimeCommissions.textProperty().bindBidirectional(viewModel.oneTimeCommissionsProperty());
        result.textProperty().bindBidirectional(viewModel.resultProperty());
        loanPeriodType.valueProperty().bindBidirectional(viewModel.loanPeriodTypeProperty());
        monthlyCommissionType.valueProperty().bindBidirectional(
                viewModel.monthlyCommissionsTypeProperty());
        oneTimeCommissionType.valueProperty().bindBidirectional(
                viewModel.oneTimeCommissionsTypeProperty());
        typeOfPayment.valueProperty().bindBidirectional(viewModel.typeOfPaymentProperty());
        calculate.setOnAction(event -> viewModel.calculate());
        areaLog.textProperty().bindBidirectional(viewModel.logsProperty());
    }
}
