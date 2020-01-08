package ru.unn.agile.interpolationsearch.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class ViewModelTests {
    private ViewModel viewModel;

    public void setOuterViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() throws IOException {
        viewModel = new ViewModel(new LogFaker());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    private void setNumber(final Integer n) {
        viewModel.numberProperty().set(n.toString());
    }

    private void addNumber(final Integer n) throws IOException {
        setNumber(n);
        viewModel.addNumber();
    }

    @Test
    public void isAddingValidInput() throws IOException {
        addNumber(1);
        assertEquals(Integer.valueOf(1), viewModel.getArray().get(0));
    }

    @Test
    public void isAddingMultipleValidInput() throws IOException {
        addNumber(1);
        addNumber(2);
        assertEquals(Integer.valueOf(1), viewModel.getArray().get(0));
        assertEquals(Integer.valueOf(2), viewModel.getArray().get(1));
    }

    @Test
    public void canDoSearch() throws IOException {
        addNumber(1);
        addNumber(2);
        addNumber(3);
        viewModel.searchValueProperty().set("2");
        viewModel.doSearch();
        assertEquals("1", viewModel.getResult());
    }

    @Test
    public void canHandleEmptyList() throws IOException {
        viewModel.searchValueProperty().set(Integer.toString(2));
        viewModel.doSearch();
        assertEquals("Empty list", viewModel.getResult());
    }

    @Test
    public void canHandleBadNumbers() throws IOException {
        viewModel.numberProperty().set("2.5");
        viewModel.addNumber();
        assertEquals("Incorrect number", viewModel.getResult());
    }

    @Test
    public void canHandleBadValues() throws IOException {
        addNumber(1);
        addNumber(2);
        viewModel.searchValueProperty().set("2.5");
        viewModel.doSearch();
        assertEquals("Incorrect value for search", viewModel.getResult());
    }

    @Test
    public void canHandleUnsortedArray() throws IOException {
        addNumber(2);
        addNumber(1);
        viewModel.searchValueProperty().set("2");
        viewModel.doSearch();
        assertEquals("Array is not sorted", viewModel.getResult());
    }

    @Test
    public void canHandleBadSearch() throws IOException {
        addNumber(1);
        addNumber(2);
        viewModel.searchValueProperty().set("3");
        viewModel.doSearch();
        assertEquals("Cannot find element '3' in array.", viewModel.getResult());
    }

    @Test
    public void canSetDefaultLog() throws IOException {
        var message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*Start"));
    }

    @Test
    public void correctLogWhenAddedElement() throws IOException {
        viewModel.numberProperty().set("2");
        viewModel.addNumber();

        var message = viewModel.getLog().get(1);

        assertTrue(message.matches(".*Element was added"));
    }

    @Test
    public void correctLogWhenElementIsIncorrect() throws IOException {
        viewModel.numberProperty().set("2.5");
        viewModel.addNumber();

        var message = viewModel.getLog().get(1);

        assertTrue(message.matches(".*Element is incorrect"));
    }

    @Test
    public void correctLogWhenSearchUnexistKey() throws IOException {
        addNumber(1);
        addNumber(2);
        viewModel.searchValueProperty().set("2");
        viewModel.doSearch();

        var message = viewModel.getLog().get(3);

        assertTrue(message.matches(".*Element is found"));
    }

    @Test
    public void correctLogWhenEmptyList() throws IOException {
        viewModel.searchValueProperty().set("2");
        viewModel.doSearch();

        var message = viewModel.getLog().get(1);

        assertTrue(message.matches(".*List is Empty"));
    }
}
