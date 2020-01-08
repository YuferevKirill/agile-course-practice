package ru.unn.agile.sorting.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.sorting.model.api.Direction;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {

    private ViewModel viewModel;

    public void setExternalViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    private void setCorrectInput() {
        viewModel.inputArrayProperty().set("2 4 3");
        viewModel.directionProperty().set(Direction.ASC);
    }

    @Before
    public void setUp() {
        viewModel = new ViewModel(new SortingFakeLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetEmptyInputArray() {
        assertEquals("", viewModel.inputArrayProperty().get());
    }

    @Test
    public void canSetEmptyResultArray() {
        assertEquals("", viewModel.sortedArrayProperty().get());
    }

    @Test
    public void canSetDefaultDirection() {
        assertEquals(Direction.ASC, viewModel.directionProperty().get());
    }

    @Test
    public void canSetDefaultErrorMessage() {
        assertEquals("", viewModel.errorProperty().get());
    }

    @Test
    public void canSetDirection() {
        viewModel.directionProperty().set(Direction.DESC);
        assertEquals(Direction.DESC, viewModel.directionProperty().get());
    }

    @Test
    public void canShowErrorMessage() {
        viewModel.inputArrayProperty().set("qwerty");
        assertEquals("Incorrect Input", viewModel.errorProperty().get());
    }

    @Test
    public void isSortButtonDisabledByDefault() {
        assertEquals(true, viewModel.sortButtonDisabledProperty().get());
    }

    @Test
    public void isSortButtonDisabledAfterInputValue() {
        viewModel.inputArrayProperty().set("1");
        assertEquals(false, viewModel.sortButtonDisabledProperty().get());
    }

    @Test
    public void isSortButtonDisabledAfterIncorrectInput() {
        viewModel.inputArrayProperty().set("q");
        assertEquals(true, viewModel.sortButtonDisabledProperty().get());
    }

    @Test
    public void isSortButtonDisabledAfterNegativeValueInput() {
        viewModel.inputArrayProperty().set("-3");
        assertEquals(false, viewModel.sortButtonDisabledProperty().get());
    }

    @Test
    public void isSortButtonDisabledAfterDoubleValueInput() {
        viewModel.inputArrayProperty().set("2.5");
        assertEquals(true, viewModel.sortButtonDisabledProperty().get());
    }

    @Test
    public void isSortButtonDisableAfterDeletingValue() {
        viewModel.inputArrayProperty().set("1 2 4 3");
        viewModel.inputArrayProperty().set("");
        assertEquals(true, viewModel.sortButtonDisabledProperty().get());
    }

    @Test
    public void canSortNumbersDesc() {
        viewModel.directionProperty().set(Direction.DESC);
        viewModel.inputArrayProperty().set("2 1 3 6 9");
        viewModel.sort();
        assertEquals("9, 6, 3, 2, 1", viewModel.sortedArrayProperty().get());
    }

    @Test
    public void canSortNegativeNumbersAsc() {
        viewModel.inputArrayProperty().set("-2 -1 -5 -8");
        viewModel.sort();
        assertEquals("-8, -5, -2, -1", viewModel.sortedArrayProperty().get());
    }

    @Test
    public void canSortNegativeAndPositiveNumbersAsc() {
        viewModel.inputArrayProperty().set("5 -1 3 -6 12");
        viewModel.sort();
        assertEquals("-6, -1, 3, 5, 12", viewModel.sortedArrayProperty().get());
    }

    @Test
    public void canSortWrongValue() {
        viewModel.inputArrayProperty().set("55555555555555");
        viewModel.sort();
        assertEquals("Bad format", viewModel.errorProperty().get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
            new ViewModel(null);
    }

    @Test
    public void logIsEmptyInTheBeginning() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void logContainsProperMessageAfterSorting() {
        setCorrectInput();
        viewModel.sort();
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + LogMessages.SORTED + ".*"));
    }

    @Test
    public void logContainsInputArgumentsAfterSorting() {
        setCorrectInput();
        String inputStr = "2, 4, 3";
        viewModel.sort();

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + inputStr + ".*"
                + viewModel.directionProperty().get() + ".*"));
    }

    @Test
    public void canSeeDirectionChangeInLog() {
        setCorrectInput();

        viewModel.directionProperty().set(Direction.DESC);

        String message = viewModel.getLog().get(0);
        assertTrue(message.matches(".*" + LogMessages.CHANGE_DIRECTION + ".*"));
    }

    @Test
    public void canPutSeveralLogMessages() {
        setCorrectInput();

        viewModel.sort();
        viewModel.sort();

        assertEquals(2, viewModel.getLog().size());
    }

    @Test
    public void directionIsNotLoggedIfNotChanged() {
        viewModel.directionProperty().set(Direction.ASC);

        assertEquals(0, viewModel.getLog().size());
    }

}


