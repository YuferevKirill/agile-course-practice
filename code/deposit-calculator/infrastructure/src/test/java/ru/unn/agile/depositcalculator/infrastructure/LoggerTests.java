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

public class LoggerTests {
    private static final String FILENAME = "./LoggerTests.log";
    private Logger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new Logger(FILENAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(txtLogger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            var fileReader = new FileReader(FILENAME);
            new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't created!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        txtLogger.log("Test message");
        var pattern = ".*Test message$";
        assertThat(txtLogger.getLog().get(0), matchesPattern(pattern));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] messages = {"Test message 1", "Test message 2"};

        txtLogger.log(messages[0]);
        txtLogger.log(messages[1]);

        var actualMessages = txtLogger.getLog();
        assertThat(actualMessages.get(0), matchesPattern(".*" + messages[0] + "$"));
        assertThat(actualMessages.get(1), matchesPattern(".*" + messages[1] + "$"));
    }

    @Test
    public void checkIfLogContainsDateAndTime() {
        txtLogger.log("Test message");
        var pattern = "^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} > .*";
        assertThat(txtLogger.getLog().get(0), matchesPattern(pattern));
    }
}
