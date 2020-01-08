package ru.unn.agile.currencyconverter.infrastructure;

import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.fail;
import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.unn.agile.currencyconverter.infrastructure.RegexpMatcher.matchesPattern;

public class TxtLoggerTests {
    private static final String FILENAME = "./TestLogger.log";
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
    public void canCreateLogFileOnDisk() {
        try {
            Files.write(Paths.get(FILENAME), "Fake Logger".getBytes());
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void canWriteLogMessage() {
        String expectedMessage = "Test message";

        txtLogger.log(expectedMessage);

        String actualMessage = txtLogger.getLog().get(0);
        assertThat(actualMessage, matchesPattern(".*" + expectedMessage + "$"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        String[] expectedMessages = {"Test message 1", "Test message 2"};

        txtLogger.log(expectedMessages[0]);
        txtLogger.log(expectedMessages[1]);

        List<String> actualMessages = txtLogger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertThat(actualMessages.get(i), matchesPattern(".*" + expectedMessages[i] + "$"));
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        String expectedMessage = "Test message";

        txtLogger.log(expectedMessage);

        String actualMessage = txtLogger.getLog().get(0);
        String regexp = "^\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3}\\] .*";
        assertThat(actualMessage, matchesPattern(regexp));
    }
}
