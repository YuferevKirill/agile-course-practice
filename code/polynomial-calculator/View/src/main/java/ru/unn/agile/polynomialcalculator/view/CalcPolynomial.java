package ru.unn.agile.polynomialcalculator.view;

import javafx.beans.property.Property;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.unn.agile.polynomialcalculator.viewmodel.ViewModel;

public class CalcPolynomial {
    private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    private final String pointInputTooltip = "Only numbers allowed";

    @FXML
    private ViewModel viewModel;
    @FXML
    private TextField degreeTextField;
    @FXML
    private TextField coeffTextField;
    @FXML
    private Button addPolynomialButton;
    @FXML
    private Button calcSumPolynomialButton;
    @FXML
    private ListView<String> listPolinomials;
    @FXML
    private Label resultTextArea;

    @FXML
    void initialize() {
        initTextField(coeffTextField, viewModel.coeffProperty());
        initTextField(degreeTextField, viewModel.degreeProperty());

        viewModel.coeffProperty().addListener(obs ->
                setErrorBorder(coeffTextField, !viewModel.isPolynomialInputCorrect()));

        viewModel.degreeProperty().addListener(obs ->
                setErrorBorder(degreeTextField, !viewModel.isPolynomialInputCorrect()));
        addPolynomialButton.setOnAction(e -> viewModel.addPolynomial());
        listPolinomials.itemsProperty().bindBidirectional(viewModel.polynomialsProperty());
        calcSumPolynomialButton.setOnAction(e -> viewModel.calcPolynomialAdd());
        resultTextArea.textProperty().bindBidirectional(viewModel.resultProperty());
    }

    private void initTextField(final TextField textField,
                               final Property<String> property) {
        textField.textProperty().bindBidirectional(property);
        textField.tooltipProperty().setValue(new Tooltip(pointInputTooltip));
    }

    private void setErrorBorder(final TextField textField, final boolean active) {
        textField.pseudoClassStateChanged(errorClass, active);
    }

}
