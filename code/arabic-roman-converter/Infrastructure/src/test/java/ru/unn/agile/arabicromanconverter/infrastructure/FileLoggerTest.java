package ru.unn.agile.arabicromanconverter.infrastructure;

import static org.hamcrest.MatcherAssert.assertThat;
import static ru.unn.agile.arabicromanconverter.infrastructure.RegexpMatcher.matches;
import org.junit.Test;
import ru.unn.agile.arabicromanconverter.viewmodel.ILogger;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;

public class FileLoggerTest {
    private static final String FILENAME = "./FileLogger_Tests-lab3.log";

    @Test
    public void canCreateLogger() {
        ILogger logger = new FileLogger(FILENAME);
        assertNotNull(logger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        ILogger logger = new FileLogger(FILENAME);
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        ILogger logger = new FileLogger(FILENAME);
        String testMessage = "Test message";

        logger.log(testMessage);

        String message = logger.readLog().get(0);
        assertThat(message, matches(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        ILogger logger = new FileLogger(FILENAME);
        String[] messages = {"Test message 1", "Test message 2"};

        logger.log(messages[0]);
        logger.log(messages[1]);

        List<String> actualMessages = logger.readLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertThat(actualMessages.get(i), matches(".*" + messages[i] + "$"));
        }
    }

    @Test
    public void canWriteLogWithDateAndTime() {
        ILogger logger = new FileLogger(FILENAME);
        String logMessage = "hello";

        logger.log(logMessage);

        String message = logger.readLog().get(0);
        assertThat(message, matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2} ~ .*"));
    }
}
