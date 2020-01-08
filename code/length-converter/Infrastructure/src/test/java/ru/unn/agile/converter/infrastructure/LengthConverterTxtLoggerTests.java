package ru.unn.agile.converter.infrastructure;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.unn.agile.converter.infrastructure.LengthConverterRegexMatcher.matchesPattern;

public class LengthConverterTxtLoggerTests {
    private static final String LOG_FILE_NAME = "./TxtLogger_Tests-lab3.log";
    private LengthConverterTxtLogger logger;

    @Before
    public void setUp() {
        logger = new LengthConverterTxtLogger(LOG_FILE_NAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(logger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(LOG_FILE_NAME));
        } catch (FileNotFoundException e) {
            fail("File " + LOG_FILE_NAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteSingleLogMessage() {
        String testMessage = "Test message";

        logger.log(testMessage);

        String message = logger.getLog().get(0);
        assertThat(message, matchesPattern(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteMoreThenOneLogMessage() {
        String[] messages = {"Test message 1", "Test message 2"};

        logger.log(messages[0]);
        logger.log(messages[1]);

        List<String> actualMessages = logger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertThat(actualMessages.get(i), matchesPattern(".*" + messages[i] + "$"));
        }
    }

    @Test
    public void doesLogFileContainDateAndTime() {
        String message = "Test message";

        logger.log(message);

        String messageFromLog = logger.getLog().get(0);
        assertThat(messageFromLog, matchesPattern(
                "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*"));
    }
}
