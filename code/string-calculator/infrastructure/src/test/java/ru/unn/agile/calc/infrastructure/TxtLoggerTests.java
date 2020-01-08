package ru.unn.agile.calc.infrastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.junit.Assert.*;

public class TxtLoggerTests {
    private static final String FILENAME = "./TxtLogger_Tests-lab3_string_calculator.log";
    public static final String FILE_NOT_FOUND_MESSAGE = "File not found.";
    private TxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new TxtLogger(FILENAME);
    }

    @After
    public void tearDown() {
        txtLogger = null;
    }

    @Test
    public void isLogFileCreated() {
        BufferedReader reader;

        try {
            reader = new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException exception) {
            fail(FILE_NOT_FOUND_MESSAGE);
        }
    }

    @Test
    public void isMessageLogged() {
        String testMessage = "Test message";
        txtLogger.log(testMessage);

        assertTrue(txtLogger.getLog().get(0).matches(".*" + testMessage + "$"));
    }

    @Test
    public void areSeveralMessagesLogged() {
        String message1 = "Test message 1";
        String message2 = "Test message 2";
        String message3 = "Test message 3";
        txtLogger.log(message1);
        txtLogger.log(message2);
        txtLogger.log(message3);

        List<String> actualMessages = txtLogger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertEquals(3, actualMessages.size());
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test message";
        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);
        assertTrue(message.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3} >> .*"));
    }
}

