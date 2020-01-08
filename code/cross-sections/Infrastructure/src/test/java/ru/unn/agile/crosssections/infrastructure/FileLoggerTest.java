package ru.unn.agile.crosssections.infrastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertThat;
import static ru.unn.agile.crosssections.infrastructure.RegularExpressionsMatcher.matchesPattern;

public class FileLoggerTest {
    private static final String FILE_NAME = "./FileLogger.log";
    private FileLogger logger;

    @Before
    public void setUp() {
        logger = new FileLogger(FILE_NAME);
    }

    @After
    public void tearDown() {
        logger = null;
    }

    @Test
    public void canWriteLogMessage() {
        var log = "action";

        logger.log(log);

        assertThat(logger.getLog().get(0), matchesPattern(".*" + log + "$"));
    }

    @Test
    public void canWriteSeveralLogMessages() {
        var logs = new ArrayList<String>() {{
            add("action 1");
            add("action 2");
            add("action 3");
        }};
        logs.forEach(logger::log);

        var log = logger.getLog();
        for (int i = 0; i < log.size(); i++) {
            assertThat(log.get(0), matchesPattern(".*" + logs.get(0) + "$"));
        }
    }
}
