package ru.unn.agile.Matrix.infrastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TxtLoggerTests {

    private static final String LOG_NAME = "./TxtLogger_Tests-lab3_matrix.log";
    private TxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new TxtLogger(LOG_NAME);
    }

    @Test
    public void canCreateLogger() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "very important log message";
        txtLogger.log(testMessage);
        assertTrue(txtLogger.getLog().get(0).contains(testMessage));
    }

    @Test
    public void canAddMoreThanOneMessage() {

        String message1 = "very important log message 1";
        String message2 = "very important log message 2";

        txtLogger.log(message1);
        txtLogger.log(message2);

        assertEquals(2, txtLogger.getLog().size());

    }
}
