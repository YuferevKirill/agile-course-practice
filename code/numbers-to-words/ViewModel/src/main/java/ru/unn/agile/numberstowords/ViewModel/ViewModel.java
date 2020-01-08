package ru.unn.agile.numberstowords.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import ru.unn.agile.numberstowords.model.NumbersToWordsConverter;


public class ViewModel {
    private final StringProperty numberInput = new SimpleStringProperty();
    private final StringProperty textOutput = new SimpleStringProperty();
    private final StringProperty status = new SimpleStringProperty();

    public ViewModel() {
        numberInput.set("");
        textOutput.set("");
        status.set("");
    }

    public StringProperty numberInputProperty() {
        return numberInput;
    }

    public StringProperty textOutputProperty() {
        return textOutput;
    }

    public StringProperty statusProperty() {
        return status;
    }

    private static boolean isNumeric(final String strNum) {
        try {
            Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void convert() {
        if (numberInput.get().equals("")) {
            status.set(Status.EMPTY_INPUT.toString());
        } else if (!isNumeric(numberInput.get())) {
            status.set(Status.WRONG_INPUT.toString());
        } else {
            int number = Integer.parseInt(numberInput.get());
            status.set("");
            textOutput.set(NumbersToWordsConverter.toWord(number));
        }


    }
}

enum Status {
    EMPTY_INPUT("Please provide input data"),
    WRONG_INPUT("Please provide correct input data");

    private final String name;
    Status(final String name) {
        this.name = name;
    }
    public String toString() {
        return name;
    }
}
