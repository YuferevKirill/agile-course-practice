package ru.unn.agile.huffman.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import ru.unn.agile.huffman.model.Huffman;

import java.util.List;

public class HuffmanViewModel {
    private StringProperty input = new SimpleStringProperty();
    private StringProperty outputEncode = new SimpleStringProperty();
    private StringProperty outputDecode = new SimpleStringProperty();
    private boolean enabledButtonStart = true;
    private ILogger logger;

    public HuffmanViewModel(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Create view model with logger");
        }
        setLogger(logger);
        input.set("Reference string");
        outputEncode.set("Encode");
        outputDecode.set("Decode");
    }

    public final void setLogger(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }
        this.logger = logger;
    }

    public ILogger getLogger() {
        return logger;
    }

    private String inputDataLogMessage() {
        return LogMessages.USER_INPUT_DATA
                + input.get();
    }

    public final List<String> getLog() {
        return logger.getLog();
    }

    public StringProperty getInput() {
        logger.logMessage(inputDataLogMessage());
        return input;
    }

    public void setInput(final String str) {
        input.set(str);
    }

    public StringProperty getOutputEncode() {
        return outputEncode;
    }

    public StringProperty getOutputDecode() {
        return outputDecode;
    }

    public void startEncodeAndDecode() {
        String inputString = getInput().get();

        logger.logMessage(LogMessages.START_ENCODE);
        outputEncode.set(Huffman.encodeString(inputString));
        logger.logMessage(encodeDataLogMessage());

        logger.logMessage(LogMessages.DECODE_DATA);
        outputDecode.set(inputString);
    }

    private String encodeDataLogMessage() {
        return LogMessages.ENCODE_DATA + outputEncode.get();
    }

    public boolean getButtonStartEnabled() {
        return enabledButtonStart;
    }

    public final class LogMessages {
        static final String USER_INPUT_DATA = "Input data. ";
        static final String START_ENCODE = "Starting encoding";
        static final String DECODE_DATA = "Starting decoding";
        static final String ENCODE_DATA = "Encoded data is ";

        private LogMessages() { }
    }
}
