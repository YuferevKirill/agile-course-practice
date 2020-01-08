package ru.unn.agile.interpolationsearch.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class TxtLoggerTests {
    private static final String FILE_NAME = "./TxtLoggerTests.log";

    private TxtLogger txtLogger;

    @Before
    public void setUp() throws IOException {
        txtLogger = new TxtLogger(FILE_NAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canCreateLogFileOnDisk() throws FileNotFoundException {
        new BufferedReader(new FileReader(FILE_NAME));
    }

    @Test
    public void dateAndTimeContainInLog() throws IOException {
        String testMessage = "Test message for check";

        txtLogger.addingLog(testMessage);

        String logMessage = txtLogger.getLoggingList().get(0);
        assertTrue(logMessage.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }

    @Test
    public void canWriteOneLogMessage() throws IOException {
        String testMessage = "Test message #1";

        txtLogger.addingLog(testMessage);

        String logMessage = txtLogger.getLoggingList().get(0);
        assertTrue(logMessage.matches(".*" + logMessage + "$"));
    }

    @Test
    public void canWriteSeveralLogMessages() throws IOException {
        String[] testMessagesString = {"Test message #1, Test message #2"};

        for (String testMessage : testMessagesString) {
            txtLogger.addingLog(testMessage);
        }

        List<String> logMessages = txtLogger.getLoggingList();
        for (String logMessage : logMessages) {
            assertTrue(logMessage.matches(".*" + logMessage + "$"));
        }
    }
}
