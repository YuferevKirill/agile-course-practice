package ru.unn.agile.temperatureconverter.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class TxtLoggerTests {
    private static final String FILENAME = "./TxtLoggerTests-temperature_converter.log";
    private TxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new TxtLogger(FILENAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canLogOneMessage() {
        String msg = "Test logger";

        txtLogger.addLog(msg);

        List<String> log = txtLogger.getLogMessage();
        assertTrue(log.get(0).matches(".*" + "Test logger" + ".*"));
    }

    @Test
    public void canLogSeveralMessages() {
        String msg1 = "Test logger 1";
        String msg2 = "Test logger 2";
        String msg3 = "Test logger 3";

        txtLogger.addLog(msg1);
        txtLogger.addLog(msg2);
        txtLogger.addLog(msg3);

        List<String> log = txtLogger.getLogMessage();

        for (int i = 0; i < log.size(); i++) {
            assertTrue(log.get(i).matches(".*" + "Test logger "  + ".*"));
        }
    }

    @Test
    public void canGetLogMessages() {
        String msg1 = "Test logger 1";
        txtLogger.addLog(msg1);

        assertNotNull(txtLogger.getLogMessage());
    }

    @Test
    public void doesLogContainDateAndTime() {
        String msg1 = "Test logger 1";
        txtLogger.addLog(msg1);

        List<String> log = txtLogger.getLogMessage();

        assertTrue(log.get(0).matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} >> .*"));
    }
}
