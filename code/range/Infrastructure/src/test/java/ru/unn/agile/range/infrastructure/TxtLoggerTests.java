package ru.unn.agile.range.infrastructure;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.unn.agile.range.infrastructure.ReMatcher.matchesPattern;

public class TxtLoggerTests {
    private static final String FILENAME = "./TxtLogger_Tests-lab3.log";
    private TxtLogger txtLogger;

    @Before
    public void initLogger() {
        txtLogger = new TxtLogger(FILENAME);
    }

    @Test
    public void canCreateLogger() {
        TxtLogger txtLogger = new TxtLogger(FILENAME);
        assertNotNull(txtLogger);
    }

    @Test
    public void canWriteAction() {
        String action = "Test action";

        txtLogger.log(action);

        String message = txtLogger.getLog().get(0);
        assertThat(message, matchesPattern(".*" + action + "$"));
    }

    @Test
    public void canWriteSomeActions() {
        String[] actions = {"Test action 1", "Test action 2"};

        txtLogger.log(actions[0]);
        txtLogger.log(actions[1]);

        List<String> actualMessages = txtLogger.getLog();
        for (int i = 0; i < actualMessages.size(); i++) {
            assertThat(actualMessages.get(i), matchesPattern(".*" + actions[i] + "$"));
        }
    }

    @Test
    public void doesLogContainDateAndTime() {
        String action = "Test actions";

        txtLogger.log(action);

        String message = txtLogger.getLog().get(0);
        assertThat(message, matchesPattern("^\\[\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\]: .*"));
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            fail("File " + FILENAME + " wasn't found!");
        }
    }
}
