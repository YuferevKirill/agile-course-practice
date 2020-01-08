package ru.unn.agile.primenumber.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    @After
    public void tearDown() {
        viewModel = null;
    }

    public void setExternalViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new ViewModel(new FakeLogger());
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreateViewModelWithNullLogger() {
        new ViewModel(null);
    }

    @Test
    public void logsIsEmptyInTheBeginning() {
        List<String> logs = viewModel.getLog();

        assertTrue(logs.isEmpty());
    }

    @Test
    public void checkStartElemDefaultValue() {
        assertEquals("", viewModel.startElemProperty().get());
    }

    @Test
    public void checkEndElemDefaultValue() {
        assertEquals("", viewModel.endElemProperty().get());
    }

    @Test
    public void checkOutputFieldDefaultValues() {
        assertEquals("", viewModel.outputProperty().get());
    }

    @Test
    public void checkDefaultStatus() {
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsWaitingWhenCalculateWithEmptyFields() {
        viewModel.findPrimaryNums();
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void containsLogMessageWithTime() {
        viewModel.startElemProperty().set("1");

        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogTemplates.INPUT_WAS_UPDATED + ".*"));
    }

    @Test
    public void containsLogMessageFindWasPressed() {
        viewModel.findPrimaryNums();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogTemplates.FIND_WAS_PRESSED + ".*"));
    }

    @Test
    public void statusIsReadyWhenFieldsAreFill() {
        viewModel.startElemProperty().set("1");
        viewModel.endElemProperty().set("10");

        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void containsLogMessageInputWasUpdatedWhenStartElemWasUpdated() {
        String startElem = "1";
        viewModel.startElemProperty().set(startElem);
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogTemplates.INPUT_WAS_UPDATED + startElem + ".*"));
    }

    @Test
    public void containsLogMessageInputWasUpdatedWhenEndElemWasUpdated() {
        String endElem = "10";
        viewModel.startElemProperty().set(endElem);
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogTemplates.INPUT_WAS_UPDATED + endElem + ".*"));
    }

    @Test
    public void canReportBadFormat() {
        viewModel.startElemProperty().set("a");
        viewModel.endElemProperty().set("b");

        assertEquals(Status.BAD_FORMAT.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void containsLogMessageBadFormat() {
        viewModel.startElemProperty().set("a");
        String message = viewModel.getLog().get(1);

        assertTrue(message.matches(".*" + LogTemplates.INCORRECT_INPUT + ".*"));
    }

    @Test
    public void statusIsWaitingIfValuesAreNotSet() {
        assertEquals(Status.WAITING.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledInitially() {
        assertTrue(viewModel.findBtnDisabledProperty().get());
    }

    @Test
    public void findButtonIsDisabledWhenReportBadFormat() {
        viewModel.startElemProperty().set("null");
        viewModel.endElemProperty().set("NaN");

        assertTrue(viewModel.findBtnDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsDisabledWithIncompleteInput() {
        viewModel.startElemProperty().set("1");

        assertTrue(viewModel.findBtnDisabledProperty().get());
    }

    @Test
    public void calculateButtonIsEnabledWithCorrectInput() {
        viewModel.startElemProperty().set("1");
        viewModel.endElemProperty().set("4");

        assertFalse(viewModel.findBtnDisabledProperty().get());
    }

    @Test
    public void operationAddHasCorrectResult() {
        viewModel.startElemProperty().set("1");
        viewModel.endElemProperty().set("4");

        viewModel.findPrimaryNums();

        assertEquals("[2, 3]", viewModel.outputProperty().get());
    }

    @Test
    public void containsLogMessageResultWasUpdated() {
        viewModel.startElemProperty().set("1");
        viewModel.endElemProperty().set("4");

        viewModel.findPrimaryNums();
        String message = viewModel.getLog().get(6);

        assertTrue(message.matches(".*" + LogTemplates.RESULT_WAS_UPDATED + "\\[2, 3\\]" + ".*"));
    }

    @Test
    public void canSetSuccessMessage() {
        viewModel.startElemProperty().set("1");
        viewModel.endElemProperty().set("2");

        viewModel.findPrimaryNums();

        assertEquals(Status.SUCCESS.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void statusIsReadyWhenSetProperValues() {
        viewModel.startElemProperty().set("1");
        viewModel.endElemProperty().set("2");

        assertEquals(Status.READY.toString(), viewModel.statusProperty().get());
    }

    @Test
    public void containsLogMessageStatusWasUpdated() {
        viewModel.startElemProperty().set("1");
        viewModel.endElemProperty().set("4");

        String message = viewModel.getLog().get(3);

        assertTrue(message.matches(".*" + LogTemplates.STATUS_WAS_UPDATED
                + Status.READY.toString() + ".*"));
    }

}
