package ru.unn.agile.triangle.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;

public class TriangleTxtLoggerTests {
    private static final String FILENAME = "./TxtLogger_Tests-lab3.log";
    private TriangleTxtLogger txtLogger;

    @Before
    public void setUp() {
        txtLogger = new TriangleTxtLogger(FILENAME);
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
            fail("File " + FILENAME + " not found!");
        }
    }

    @Test
    public void canWriteSeveralLogMessages() {
        String[] messages = {"Test message 1", "Test message 2", "Test message 3"};

        txtLogger.log(messages[0]);
        txtLogger.log(messages[1]);

        List<String> actualMessage = txtLogger.getLog();
        for (int i = 0; i < actualMessage.size(); i++) {
            assertThat(actualMessage.get(i), actualMessage.get(i).matches(".*"
                    + messages[i] + "$"));
        }
    }

    @Test
    public void canWriteLogMessage() {
        String testMessage = "Test log message";

        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);
        assertThat(message, message.matches(".*" + testMessage + "$"));
    }

    @Test
    public void doesLogContainDateAndTime() {
        String testMessage = "Test log message";

        txtLogger.log(testMessage);

        String message = txtLogger.getLog().get(0);

    }
}
