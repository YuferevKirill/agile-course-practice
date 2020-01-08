package ru.unn.agile.complexnumbercalculator.view;

import ru.unn.agile.complexnumbercalculator.infrastructure.RealCalendar;
import ru.unn.agile.complexnumbercalculator.infrastructure.TxtLogger;
import ru.unn.agile.complexnumbercalculator.viewmodel.ViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

public final class CalculatorComplexNumbers {
    private JPanel mainPanel;
    private JPanel chooseOperationPanel;
    private JPanel enterDataPanel;
    private JPanel calculationPanel;
    private JLabel chooseLabel;
    private JComboBox<ViewModel.Operations> operationsComboBox;
    private JLabel enterDataLabel;
    private JButton calculateButton;
    private JTextField firstReTextField;
    private JTextField firstImTextField;
    private JTextField secondReTextField;
    private JTextField secondImTextField;
    private JTextField degreeTextField;
    private JLabel fisrtReLabel;
    private JLabel fisrtImLabel;
    private JLabel secondReLabel;
    private JLabel secondImLabel;
    private JLabel degreeLabel;
    private JPanel errorPanel;
    private JLabel errorLabel;
    private JPanel calculatePanel;
    private JLabel resultTextLabel;
    private JLabel resultLabel;
    private JList<String> logList;

    private ViewModel viewModel;

    public static void main(final String[] args) {
        JFrame frame = new JFrame("CalculatorComplexNumbers");

        RealCalendar calendar = new RealCalendar();
        TxtLogger logger =
                new TxtLogger("./CalculatorComplexNumbers.log", calendar);

        frame.setContentPane(new CalculatorComplexNumbers(new ViewModel(logger)).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private CalculatorComplexNumbers(final ViewModel viewModel) {
        this.viewModel = viewModel;
        backBind();
        loadOperations();
        hideUnnecessaryElements();
        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                bind();
                CalculatorComplexNumbers.this.viewModel.calculate();
                backBind();
            }
        });

        operationsComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                bind();
                CalculatorComplexNumbers.this.viewModel.hideUnnecessaryFields();
                hideUnnecessaryElements();
                backBind();
            }
        });

        KeyAdapter keyListener = new KeyAdapter() {
            public void keyReleased(final KeyEvent e) {
                bind();
                CalculatorComplexNumbers.this.viewModel.processEnterPress(e.getKeyCode());
                CalculatorComplexNumbers.this.viewModel.processInput();
                backBind();
            }
        };
        firstReTextField.addKeyListener(keyListener);
        firstImTextField.addKeyListener(keyListener);
        secondReTextField.addKeyListener(keyListener);
        secondImTextField.addKeyListener(keyListener);
        degreeTextField.addKeyListener(keyListener);
    }

    private void loadOperations() {
        ViewModel.Operations[] operations = ViewModel.Operations.values();
        operationsComboBox.setModel(new JComboBox<>(operations).getModel());
    }

    private void hideUnnecessaryElements() {
        if (!viewModel.isDegreeVisible()) {
            degreeTextField.setVisible(false);
            degreeLabel.setVisible(false);
        } else {
            degreeTextField.setVisible(true);
            degreeLabel.setVisible(true);
        }
        if (!viewModel.isSecondNumberVisible()) {
            secondReTextField.setVisible(false);
            secondReLabel.setVisible(false);
            secondImTextField.setVisible(false);
            secondImLabel.setVisible(false);
        } else {
            secondReTextField.setVisible(true);
            secondReLabel.setVisible(true);
            secondImTextField.setVisible(true);
            secondImLabel.setVisible(true);
        }
    }

    private void backBind() {
        calculateButton.setEnabled(viewModel.isCalculateButtonEnabled());
        resultLabel.setText(viewModel.getResult());
        errorLabel.setText(viewModel.getError());

        List<String> log = viewModel.getLog();
        String[] items = log.toArray(new String[log.size()]);
        logList.setListData(items);
    }

    private void bind() {
        viewModel.setFirstRe(firstReTextField.getText());
        viewModel.setFirstIm(firstImTextField.getText());
        viewModel.setSecondRe(secondReTextField.getText());
        viewModel.setSecondIm(secondImTextField.getText());
        viewModel.setDegree(degreeTextField.getText());
        viewModel.setOperations((ViewModel.Operations) operationsComboBox.getSelectedItem());
    }
}
