package ru.unn.agile.huffman.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;

public class TxtLoggerTests {
    private static final String FILENAME = "./TxtLoggerTests-huffman-lab3.log";
    private TxtLogger logger;

    @Before
    public void setUp() {
        logger = new TxtLogger(FILENAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(logger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Simple test message";

        logger.logMessage(testMessage);

        String messageFromLogger = logger.getLog().get(0);
        assertTrue(messageFromLogger.contains(testMessage));
    }

    @Test
    public void canLogSeveralMessages() {
        String[] logMessages = {"First test message", "Second test message"};

        logger.logMessage(logMessages[0]);
        logger.logMessage(logMessages[1]);

        List<String> log = logger.getLog();

        for (int i = 0; i < log.size(); i++) {
            assertTrue(log.get(i).matches(".*" + logMessages[i]  + ".*"));
        }
    }
}
