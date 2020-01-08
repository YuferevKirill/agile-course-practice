package ru.unn.agile.calc.view;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import ru.unn.agile.calc.infrastructure.TxtLogger;
import ru.unn.agile.calc.viewmodel.StringCalcViewModel;

public class StringCalcView {

    private final PseudoClass errorClass = PseudoClass.getPseudoClass("error");
    private final String tooltip = "Allowed symbols: 0-9,+,-,*,/,.";

    @FXML
    private StringCalcViewModel viewModel;
    @FXML
    private TextField expressionTf;
    @FXML
    private Label resultLbl;
    @FXML
    private Button calcBtn;

    @FXML
    void initialize() {
        viewModel.setLogger(new TxtLogger("./TxtLogger-lab3.log"));

        expressionTf.textProperty().bindBidirectional(viewModel.expressionTfProperty());
        expressionTf.textProperty().addListener((observable, oldValue, newValue) -> {
                    expressionTf.textProperty().setValue(newValue.replaceAll("\\s", ""));
                    setErrorBorder(!viewModel.isExpressionValid());
                }
        );
        expressionTf.tooltipProperty().setValue(new Tooltip(tooltip));
        expressionTf.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                viewModel.onExpressionTfFocusChanged();
            }
        });

        resultLbl.textProperty().bindBidirectional(viewModel.resultLblProperty());

        calcBtn.disableProperty().bindBidirectional(viewModel.calculationDisabledProperty());

        calcBtn.setOnAction(e -> viewModel.calculate());
    }

    private void setErrorBorder(final boolean active) {
        expressionTf.pseudoClassStateChanged(errorClass, active);
    }
}
