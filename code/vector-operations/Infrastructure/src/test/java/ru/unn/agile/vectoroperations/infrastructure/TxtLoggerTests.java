package ru.unn.agile.vectoroperations.infrastructure;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TxtLoggerTests {
    private static final String NAME_FILE = "./ViewModelTxt.log";
    private TxtLogger textLogger;

    @Before
    public void beforeStart() {
        textLogger = new TxtLogger(NAME_FILE);
    }

    @After
    public void tearDown() {
        textLogger = null;
    }

    @Test
    public void canCreateLoggerWithFileName() {
        assertNotNull(textLogger);
    }

    @Test
    public void canCreateLogFileOnDisk() {
        try {
            new BufferedReader(new FileReader(NAME_FILE));
        } catch (FileNotFoundException exp) {
            fail("File " + NAME_FILE + " wasn't found!");
        }
    }

    @Test
    public void canWriteOneSimpleLogMessage() {
        String testMessage = "Yo yo yo! 1483 to the 3 to the 3 to the 6 to the 9,"
                             + " representing the ABQ";

        textLogger.log(testMessage);

        String logMessage = textLogger.getLog().get(0);
        assertTrue(logMessage.matches(".*" + testMessage + "$"));
    }

    @Test
    public void canWriteTwoLogMessages() {
        String testMessageOne = "Yo yo yo! 1483 to the 3 to the 3 to the 6 to the 9,"
                                + " representing the ABQ";
        String testMessageTwo = "What up! Leave at the tone";
        int firstMessageIdx = 0;
        int secondMessageIdx = 1;
        textLogger.log(testMessageOne);
        textLogger.log(testMessageTwo);

        List<String> logMessages = textLogger.getLog();
        String logMessageOne = logMessages.get(firstMessageIdx);
        String logMessageTwo = logMessages.get(secondMessageIdx);
        assertTrue(logMessageOne.matches(".*" + testMessageOne + "$"));
        assertTrue(logMessageTwo.matches(".*" + testMessageTwo + "$"));
    }
}
