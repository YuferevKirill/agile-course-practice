package ru.unn.agile.queue.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    @Before
    public void setUp() {
        FakeLogger fakeLogger = new FakeLogger();
        viewModel = new ViewModel(fakeLogger);
    }

    @After
    public void deletingViewModel() {
        viewModel = null;
    }

    @Test
    public void canCreateViewModelWithoutLogger() {
        ViewModel viewModel = new ViewModel();

        assertNotNull(viewModel);
    }

    @Test
    public void canCreateViewModelWithLogger() {
        FakeLogger logger = new FakeLogger();
        ViewModel viewModelLogged = new ViewModel(logger);

        assertNotNull(viewModelLogged);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwsWhenCreateViewModelWithNullLogger() {
        ViewModel viewModel = new ViewModel(null);
    }

    @Test
    public void canInitDefaultQueueInputValue() {
        assertEquals("", viewModel.getQueueElement());
    }

    @Test
    public void canInitDefaultResult() {
        assertEquals("", viewModel.getQueueResult());
    }

    @Test
    public void canInitDefaultStatus() {
        assertEquals("", viewModel.getQueueStatus());
    }

    @Test
    public void canInitDefaultTextLogger() {
        assertEquals("", viewModel.getTextLogger());
    }


    @Test
    public void canSetElementInputValue() {
        String expectedValue = "2";

        viewModel.setQueueInputElement("2");
        var actualValue = viewModel.getQueueElement();

        assertEquals(expectedValue, actualValue);
    }

    @Test
    public void canGetElementInputProperty() {
        viewModel.setQueueInputElement("1");

        assertEquals(viewModel.getQueueElement(), viewModel.queueElementProperty().get());
    }

    @Test
    public void canGetResultProperty() {
        viewModel.setQueueResult("1,2,3");

        assertEquals(viewModel.getQueueResult(), viewModel.queueResultProperty().get());
    }

    @Test
    public void isStatusPushElementWhenPushElementToQueue() {
        String expectedStatusValue = "Push element: 3.0";

        viewModel.setQueueInputElement("3");
        viewModel.pushProcess();
        var actualStatusValue = viewModel.getQueueResult();

        assertEquals(expectedStatusValue, actualStatusValue);
    }

    @Test
    public void isStatusPushElementChangesWhenPushMoreThenOneElementToQueue() {
        String expectedStatusValue = "Push element: 5.0";

        viewModel.setQueueInputElement("3");
        viewModel.pushProcess();
        viewModel.setQueueInputElement("5");
        viewModel.pushProcess();
        var actualStatusValue = viewModel.getQueueResult();

        assertEquals(expectedStatusValue, actualStatusValue);
    }

    @Test
    public void isStatusPopElementWhenPopElementFromQueue() {
        String expectedStatusValue = "Pop element: 3.0";

        viewModel.setQueueInputElement("3");
        viewModel.pushProcess();
        viewModel.popProcess();
        var actualStatusValue = viewModel.getQueueResult();

        assertEquals(expectedStatusValue, actualStatusValue);
    }

    @Test
    public void isStatusQueueIsEmptyWhenPopElementFromEmptyQueue() {
        String expectedStatusValue = "Queue is empty.";

        viewModel.popProcess();
        var actualStatusValue = viewModel.getQueueResult();

        assertEquals(expectedStatusValue, actualStatusValue);
    }

    @Test
    public void isStatusQueueClearedWhenClearQueueWithElements() {
        String expectedStatusValue = "Queue cleared";

        viewModel.setQueueInputElement("3");
        viewModel.pushProcess();
        viewModel.clearProcess();
        var actualStatusValue = viewModel.getQueueResult();

        assertEquals(expectedStatusValue, actualStatusValue);
    }

    @Test
    public void isStatusQueueClearedWhenClearQueueWithoutElements() {
        String expectedStatusValue = "Queue cleared";

        viewModel.clearProcess();
        var actualStatusValue = viewModel.getQueueResult();

        assertEquals(expectedStatusValue, actualStatusValue);
    }

    @Test
    public void isStatusHeadIsWhenGetHeadFromQueueWithElements() {
        String expectedStatusValue = "Head is: 3.0";

        viewModel.setQueueInputElement("3");
        viewModel.pushProcess();
        viewModel.getHeadProcess();
        var actualStatusValue = viewModel.getQueueResult();

        assertEquals(expectedStatusValue, actualStatusValue);
    }

    @Test
    public void isStatusQueueIsEmptyWhenGetHeadFromQueueWithoutElements() {
        String expectedStatusValue = "Queue is empty.";

        viewModel.getHeadProcess();
        var actualStatusValue = viewModel.getQueueResult();

        assertEquals(expectedStatusValue, actualStatusValue);
    }

    @Test
    public void isStatusTailIsWhenGetTailFromQueueWithElements() {
        String expectedStatusValue = "Tail is: 3.0";

        viewModel.setQueueInputElement("3");
        viewModel.pushProcess();
        viewModel.getTailProcess();
        var actualStatusValue = viewModel.getQueueResult();

        assertEquals(expectedStatusValue, actualStatusValue);
    }

    @Test
    public void isStatusQueueIsEmptyWhenGetTailFromQueueWithoutElements() {
        String expectedStatusValue = "Queue is empty.";

        viewModel.getTailProcess();
        var actualStatusValue = viewModel.getQueueResult();

        assertEquals(expectedStatusValue, actualStatusValue);
    }

    @Test
    public void isLogEmptyWhenStartup() {
        assertEquals(0, viewModel.getLog().size());
    }

    @Test
    public void isLogContainsInfoAboutPushedElement() {
        String inputElement = "1.0";
        viewModel.setQueueInputElement(inputElement);
        viewModel.pushProcess();

        String logMessage = viewModel.getLog().get(0);
        String expectedMessage = "Pushed " + inputElement + " to queue";
        assertTrue(logMessage.matches(".*" + expectedMessage + ".*"));
    }

    @Test
    public void isLogContainsInfoAboutPushingEmptyElement() {
        String inputElement = "";
        viewModel.setQueueInputElement(inputElement);
        viewModel.pushProcess();

        String logMessage = viewModel.getLog().get(0);
        String expectedMessage = "Pushing element is empty";
        assertTrue(logMessage.matches(".*" + expectedMessage + ".*"));
    }

    @Test
    public void isLogContainsInfoAboutPushingIncorrectElement() {
        String inputElement = "test";
        viewModel.setQueueInputElement(inputElement);
        viewModel.pushProcess();

        String logMessage = viewModel.getLog().get(0);
        String expectedMessage = "Pushing element test has incorrect format";
        assertTrue(logMessage.matches(".*" + expectedMessage + ".*"));
    }

    @Test
    public void isLogContainsInfoAboutPoppedElement() {
        String inputElement = "1";
        viewModel.setQueueInputElement(inputElement);
        viewModel.pushProcess();
        viewModel.popProcess();

        String logMessage = viewModel.getLog().get(1);
        String expectedMessage = "Popped 1.0 from queue";
        assertTrue(logMessage.matches(".*" + expectedMessage + ".*"));
    }

    @Test
    public void isLogContainsInfoAboutPopFromEmptyQueue() {
        viewModel.popProcess();

        String logMessage = viewModel.getLog().get(0);
        String expectedMessage = "Impossible to pop from empty queue";
        assertTrue(logMessage.matches(".*" + expectedMessage + ".*"));
    }

    @Test
    public void isLogContainsInfoAboutQueueClearing() {
        String inputElement = "3";
        viewModel.setQueueInputElement(inputElement);
        viewModel.pushProcess();
        viewModel.clearProcess();

        String logMessage = viewModel.getLog().get(1);
        String expectedMessage = "Queue was cleared";
        assertTrue(logMessage.matches(".*" + expectedMessage + ".*"));
    }

    @Test
    public void isLogContainsInfoAboutGettingHead() {
        String inputElement = "3";
        viewModel.setQueueInputElement(inputElement);
        viewModel.pushProcess();
        viewModel.getHeadProcess();

        String logMessage = viewModel.getLog().get(1);
        String expectedMessage = "Head element 3.0 was received";
        assertTrue(logMessage.matches(".*" + expectedMessage + ".*"));
    }

    @Test
    public void isLogContainsInfoAboutGettingHeadFromEmptyQueue() {
        viewModel.getHeadProcess();

        String logMessage = viewModel.getLog().get(0);
        String expectedMessage = "Impossible to get head from empty queue";
        assertTrue(logMessage.matches(".*" + expectedMessage + ".*"));
    }

    @Test
    public void isLogContainsInfoAboutGettingTail() {
        String firstInputElement = "3";
        viewModel.setQueueInputElement(firstInputElement);
        viewModel.pushProcess();
        String secondInputElement = "5";
        viewModel.setQueueInputElement(secondInputElement);
        viewModel.pushProcess();
        viewModel.getTailProcess();

        String logMessage = viewModel.getLog().get(2);
        String expectedMessage = "Tail element 5.0 was received";
        assertTrue(logMessage.matches(".*" + expectedMessage + ".*"));
    }

    @Test
    public void isLogContainsInfoAboutGettingTailFromEmptyQueue() {
        viewModel.getTailProcess();

        String logMessage = viewModel.getLog().get(0);
        String expectedMessage = "Impossible to get tail from empty queue";
        assertTrue(logMessage.matches(".*" + expectedMessage + ".*"));
    }

    protected void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }
}
