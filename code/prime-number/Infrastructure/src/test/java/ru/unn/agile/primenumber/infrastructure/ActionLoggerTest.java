package ru.unn.agile.primenumber.infrastructure;

import static org.hamcrest.MatcherAssert.assertThat;
import static ru.unn.agile.primenumber.infrastructure.RegularExpressionMatcher.matches;
import org.junit.Test;
import ru.unn.agile.primenumber.viewmodel.Logger;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;

public class ActionLoggerTest {
    private static final String FILENAME = "./FileLogger_Tests-lab3.log";

    @Test
    public void canCreateLogger() {
        Logger logger = new ActionLogger(FILENAME);
        assertNotNull(logger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        Logger logger = new ActionLogger(FILENAME);
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }

    @Test
    public void canWriteLogMessage() {
        Logger logger = new ActionLogger(FILENAME);
        String testMessage = "Test message";

        logger.writeLog(testMessage);

        String message = logger.readLog().get(0);
        assertThat(message, matches(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteSeveralLogMessage() {
        Logger actionLogger = new ActionLogger(FILENAME);
        String[] messages = {"Test message 1", "Test message 2"};

        actionLogger.writeLog(messages[0]);
        actionLogger.writeLog(messages[1]);

        List<String> readLog = actionLogger.readLog();
        for (int i = 0; i < readLog.size(); i++) {
            assertThat(readLog.get(i), matches(".*" + messages[i] + "$"));
        }
    }

    @Test
    public void canWriteLogWithDateAndTime() {
        Logger logger = new ActionLogger(FILENAME);
        String logMessage = "hello";

        logger.writeLog(logMessage);

        String message = logger.readLog().get(0);
        assertThat(message, matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{3} ~~~ .*"));
    }
}
