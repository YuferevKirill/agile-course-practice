package ru.unn.agile.quadraticequation.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static ru.unn.agile.quadraticequation.infrastructure.ExtendedRegexMatcher.matches;

public class QuadraticEquationTxtLoggerTests {

    private static final String LOG_FILENAME = "./QuadraticEquationViewModel_with_TxtLogger.log";
    private QuadraticEquationTxtLogger textLogger;

    @Before
    public void setUp() {
        textLogger = new QuadraticEquationTxtLogger(LOG_FILENAME);
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(textLogger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(LOG_FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + LOG_FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        String testLog = "Test log";
        textLogger.log(testLog);
        String message = textLogger.getLog().get(0);
        assertThat(message, matches(".*" + testLog + "$"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] testLogs = new String[]{"Test log 1", "Test log 2"};
        for (String testLog: testLogs) {
            textLogger.log(testLog);
        }

        List<String> actualMessages = textLogger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertThat(actualMessages.get(i), matches(".*" + testLogs[i] + "$"));
        }
    }

    @Test
    public void doesLogContainDate() {
        String testMessage = "Test message";

        textLogger.log(testMessage);

        String message = textLogger.getLog().get(0);
        assertThat(message,
                matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{3} > .*"));
    }
}
