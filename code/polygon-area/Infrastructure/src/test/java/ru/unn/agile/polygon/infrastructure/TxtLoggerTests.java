package ru.unn.agile.polygon.infrastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TxtLoggerTests {

    private static final String LOG_FILE_PATH = "./TxtLogger_Tests-lab3_polygon_area_calc.log";
    private static final String DATE_TIME_FORMAT_REGEX =
            "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} >> .*";
    private TxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new TxtLogger(LOG_FILE_PATH);
    }

    @After
    public void tearDown() {
        txtLogger = null;
    }

    @Test
    public void isLogNotNull() {
        assertNotNull(txtLogger.getLog());
    }

    @Test
    public void isLogMessageAdded() {
        String testMessage = "Test log message";
        txtLogger.log(testMessage);
        assertTrue(txtLogger.getLog().get(0).contains(testMessage));
    }

    @Test
    public void areSeveralLogMessagesAdded() {

        String message1 = "Test log message 1";
        String message2 = "Test log message 2";
        String message3 = "Test log message 3";

        txtLogger.log(message1);
        txtLogger.log(message2);
        txtLogger.log(message3);

        assertEquals(3, txtLogger.getLog().size());

    }

    @Test
    public void doesLogMessageHaveDateTimeFormat() {
        String testMessage = "Test log message";
        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);
        assertTrue(message.matches(DATE_TIME_FORMAT_REGEX));
    }

}
