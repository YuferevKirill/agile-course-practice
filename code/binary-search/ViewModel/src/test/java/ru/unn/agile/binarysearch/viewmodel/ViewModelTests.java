package ru.unn.agile.binarysearch.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    public void setOuterViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new ViewModel(new FakeLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canInittArrayValue() {

        assertEquals("", viewModel.getArrayInputProperty());
    }

    @Test
    public void canInitElementValue() {

        assertEquals("", viewModel.getElementInputProperty());
    }

    @Test
    public void canInitStatus() {

        assertEquals("", viewModel.getStatusProperty());
    }

    @Test
    public void canInitResult() {

        assertEquals("", viewModel.getResultProperty());
    }

    @Test
    public void canSetArrayInputValue() {

        int[] expected = new int[] {1, 2, 3};

        viewModel.setArrayInputProperty("1,2,3");

        assertArrayEquals(expected, viewModel.getBinarySearchArray());
    }

    @Test
    public void canSetElementInputValue() {

        String expected = "2";

        viewModel.setElementInputProperty("2");

        assertEquals(expected, viewModel.getElementInputProperty());
    }

    @Test
    public void canGetArrayInputProperty() {

        viewModel.setArrayInputProperty("1,2,3");

        assertEquals(viewModel.getArrayInputProperty(), viewModel.arrayInputProperty().get());
    }

    @Test
    public void canGetElementInputProperty() {

        viewModel.setElementInputProperty("1");

        assertEquals(viewModel.getElementInputProperty(), viewModel.elementInputProperty().get());
    }

    @Test
    public void canGetResultProperty() {

        viewModel.setArrayInputProperty("1,2,3");
        viewModel.setElementInputProperty("3");
        viewModel.search();

        assertEquals(viewModel.getResultProperty(), viewModel.resultProperty().get());
    }

    @Test
    public void canSearchExistingKey() {

        String expected = "Found key, index 1 ;";

        viewModel.setArrayInputProperty("1,2,3");
        viewModel.setElementInputProperty("2");
        viewModel.search();

        assertEquals(expected, viewModel.getResultProperty());
    }

    @Test
    public void canSearchNonExistingKey() {

        String expected = "Key not found ;";

        viewModel.setArrayInputProperty("1,2,3");
        viewModel.setElementInputProperty("4");
        viewModel.search();

        assertEquals(expected, viewModel.getResultProperty());
    }

    @Test
    public void canSearchKeyWithNonSortedArray() {

        String expected = "Array not sorted ;";

        viewModel.setArrayInputProperty("3,2,1");
        viewModel.setElementInputProperty("3");
        viewModel.search();

        assertEquals(expected, viewModel.getResultProperty());
    }

    @Test
    public void isStatusSetToBadArrayFormatWithIncorrectArrayInput() {
        viewModel.setArrayInputProperty("p,2,!,2_w");
        viewModel.setElementInputProperty("2");

        assertEquals(Status.BAD_ARRAY_FORMAT.toString(), viewModel.getStatusProperty());
    }

    @Test
    public void isStatusSetToBadElementFormatWithIncorrectElementInput() {
        viewModel.setArrayInputProperty("1,2,3");
        viewModel.setElementInputProperty("`");

        assertEquals(Status.BAD_ELEMENT_FORMAT.toString(), viewModel.getStatusProperty());
    }

    @Test
    public void isStatusSetToReadyWithCorrectElementInput() {
        viewModel.setArrayInputProperty("1,2,3");
        viewModel.setElementInputProperty("1");

        assertEquals(Status.READY.toString(), viewModel.getStatusProperty());
    }

    @Test
    public void isStatusSetToWaitingWithCorrectArrayAndEmptyElementInput() {
        viewModel.setArrayInputProperty("1,2,3");
        viewModel.setElementInputProperty("");

        assertEquals(Status.WAITING.toString(), viewModel.getStatusProperty());
    }

    @Test
    public void canCreateViewModelWithLogger() {
        var fakeLogger = new FakeLogger();
        ViewModel newViewModel = new ViewModel(fakeLogger);

        assertNotNull(newViewModel);
    }

    @Test
    public void canCreateEmptyViewModel() {
        ViewModel viewModel = new ViewModel();

        assertNotNull(viewModel);
    }

    @Test
    public void canSetDefaultLog() {
        assertEquals(0, viewModel.getLog().size());
    }

    @Test
    public void correctLogWhenSearchUnexistKey() {
        viewModel.setArrayInputProperty("1,2,3");
        viewModel.setElementInputProperty("4");

        viewModel.search();
        String message = viewModel.getLog().get(2);

        assertTrue(message.matches(".*" + "Result: Key not found" + ".*"));
    }

    @Test
    public void correctLogWhenSearchKeyInUnsortedArray() {
        viewModel.setArrayInputProperty("3,2,1");
        viewModel.setElementInputProperty("2");

        viewModel.search();
        String message = viewModel.getLog().get(2);

        assertTrue(message.matches(".*" + "Result: Array not sorted" + ".*"));
    }

    @Test
    public void logContainsProperMessageAfterSearch() {
        viewModel.setArrayInputProperty("1,2,3");
        viewModel.setElementInputProperty("3");

        viewModel.search();
        String message = viewModel.getLog().get(2);

        assertTrue(message.matches(".*" + "Result: " + ".*"));
    }

    @Test
    public void logContainsInputArrayAfterSearch() {
        viewModel.setArrayInputProperty("1,2,3");
        viewModel.setElementInputProperty("3");

        viewModel.search();
        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + viewModel.arrayInputProperty().get() + ".*"));
    }

    @Test
    public void logContainsInputElementAfterSearch() {
        viewModel.setArrayInputProperty("1,2,3");
        viewModel.setElementInputProperty("3");

        viewModel.search();
        String message = viewModel.getLog().get(1);
        assertTrue(message.matches(".*" + viewModel.elementInputProperty().get() + ".*"));
    }

    @Test
    public void canPutSeveralLogMessages() {
        viewModel.setArrayInputProperty("1,2,3");
        viewModel.setElementInputProperty("3");

        viewModel.search();
        viewModel.search();
        viewModel.search();

        assertEquals(5, viewModel.getLog().size());
    }
}
