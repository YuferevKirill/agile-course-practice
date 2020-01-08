package ru.unn.agile.queue.infrastructure;

import org.junit.Before;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import static org.junit.Assert.*;

public class TxtLoggerTests {
    private static final String LOGGER_FILENAME = "./TxtLoggerTests.log";
    private TxtLogger logger;

    @Before
    public void setUp() {
        logger = new TxtLogger(LOGGER_FILENAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(logger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(LOGGER_FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + LOGGER_FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canLogOneMessage() {
        String message = "test";

        logger.log(message);

        String logMessage = logger.getLog().get(0);
        assertTrue(logMessage.matches(".*" + message + "$"));
    }

    @Test
    public void canLogSeveralMessages() {
        String[] messages = {"info", "warning", "error"};

        logger.log(messages[0]);
        logger.log(messages[1]);
        logger.log(messages[2]);

        List<String> logMessages = logger.getLog();
        for (int i = 0; i < logMessages.size(); i++) {
            String actualMessage = logMessages.get(i);
            assertTrue(actualMessage.matches(".*" + messages[i] + "$"));
        }
    }

    @Test
    public void isDateAndTimeAddedToMessageCorrectly() {
        String message = "test";

        logger.log(message);

        String logMessage = logger.getLog().get(0);
        var format = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3} > .*";
        assertTrue(logMessage.matches(format));
    }

}
