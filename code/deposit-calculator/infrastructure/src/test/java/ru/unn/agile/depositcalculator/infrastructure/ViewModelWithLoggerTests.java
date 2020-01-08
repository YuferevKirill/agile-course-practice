package ru.unn.agile.depositcalculator.infrastructure;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.unn.agile.depositcalculator.infrastructure.RegexMatcher.matchesPattern;

public class ViewModelWithLoggerTests {
    private static final String FILENAME = "./ViewModelWithLoggerTests.log";
    private Logger logger;

    @Before
    public void setUp() {
        logger = new Logger(FILENAME);
    }

    @Test
    public void canCreateLogger() {
        assertNotNull(logger);
    }

    @Test
    public void canCreateLogFileOnStorage() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        var testMessage = "Test message";
        logger.log(testMessage);
        var pattern = ".*" + testMessage + "$";
        assertThat(logger.getLog().get(0), matchesPattern(pattern));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] messages = {"Test message 1", "Test message 2"};

        logger.log(messages[0]);
        logger.log(messages[1]);

        var actualMessages = logger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            var pattern = ".*" + messages[i] + "$";
            assertThat(actualMessages.get(i), matchesPattern(pattern));
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        logger.log("Test message");
        var pattern = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*";
        assertThat(logger.getLog().get(0), matchesPattern(pattern));
    }
}
