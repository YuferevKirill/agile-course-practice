package ru.unn.agile.complexnumbercalculator.infrastructure;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static junit.framework.TestCase.assertNotNull;

public class TxtLoggerTests {
    private TxtLogger txtLogger;
    private static final String FILENAME = "./TxtLoggerTests-complex-number-calculator.log";
    private static final ICalendar CALENDAR = new FakeCalendar();

    @Before
    public void setUp() {
        txtLogger = new TxtLogger(FILENAME, CALENDAR);
    }

    @Test
    public void canCreateLogger() {

        assertNotNull(txtLogger);
    }

    @Test
    public void canCreateLogFile() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't create!");
        }
    }

    @Test
    public void canWriteSomeLogMessage() {
        String messageToLog = "Log message 1";
        txtLogger.addToLog(messageToLog);

        String message = txtLogger.getLog().get(0);

        assertThat(message, containsString(messageToLog));
    }

    @Test
    public void isLogContainTime() {
        String messageToLog = "Log message 2";

        txtLogger.addToLog(messageToLog);

        String message = txtLogger.getLog().get(0);
        assertThat(message, containsString("2019-12-22 20:33:51"));
    }
}
