package ru.unn.agile.datastructure.set.infrastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertThat;
import static ru.unn.agile.datastructure.set.infrastructure.RegexMatcher.matchesPattern;

public class TxtLoggerTest {
    private static final String FILE_NAME = "./TxtLogger_Tests-lab3.log";
    private TxtLogger logger;

    @Before
    public void setUp() {
        logger = new TxtLogger(FILE_NAME);
    }

    @After
    public void tearDown() {
        logger = null;
    }

    @Test
    public void canLogMessage() {
        var message = "message";

        logger.log(message);

        assertThat(logger.getLog().get(0), matchesPattern(".*" + message + "$"));
    }

    @Test
    public void canLogSeveralMessages() {
        var messages = new ArrayList<String>() {{
            add("message 1");
            add("message 2");
            add("message 3");
        }};
        messages.forEach(logger::log);

        var log = logger.getLog();
        for (int i = 0; i < log.size(); i++) {
            assertThat(log.get(0), matchesPattern(".*" + messages.get(0) + "$"));
        }
    }
}
