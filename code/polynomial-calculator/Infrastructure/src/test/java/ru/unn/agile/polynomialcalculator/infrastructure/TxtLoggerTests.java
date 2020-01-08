package ru.unn.agile.polynomialcalculator.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class TxtLoggerTests {
    private static final String FILENAME = "./TxtLoggerTests-polynomial-calculator.log";
    private TxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new TxtLogger(FILENAME);
    }

    @Test
    public void canLogSeveralMessages() {
        String m1 = "0";
        String m2 = "1";
        String m3 = "2";

        txtLogger.addLog(m1);
        txtLogger.addLog(m2);
        txtLogger.addLog(m3);

        List<String> log = txtLogger.getLogMessage();

        for (int i = 0; i < log.size(); i++) {
            assertTrue(log.get(i).matches(".*" + Integer.toString(i) + ".*"));
        }
    }

    @Test
    public void canGetLogMessages() {
        String m = "Test";
        txtLogger.addLog(m);

        assertNotNull(txtLogger.getLogMessage());
    }

    @Test
    public void canLogOneMessage() {
        String m = "Test logger";

        txtLogger.addLog(m);

        List<String> log = txtLogger.getLogMessage();
        assertTrue(log.get(0).matches(".*" + "Test logger" + ".*"));
    }

    @Test
    public void doesLogContainDateAndTime() {
        String msg1 = "Test";
        txtLogger.addLog(msg1);

        List<String> log = txtLogger.getLogMessage();

        assertTrue(log.get(0).matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} << .*"));
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(txtLogger);
    }
}
