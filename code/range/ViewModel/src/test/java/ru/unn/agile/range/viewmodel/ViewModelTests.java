package ru.unn.agile.range.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {

    private ViewModel viewModel;
    private final String yes = "Yes";
    private final String no = "No";

    @Before
    public void setUp() {
        viewModel = new ViewModel(new SortingFakeLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    protected void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Test
    public void containsButtonIsDisabledByDefault() {
        assertTrue(viewModel.isContainsButtonDisabled().get());
    }

    @Test
    public void equalsButtonIsDisabledByDefault() {
        assertTrue(viewModel.isEqualsButtonDisabled().get());
    }

    @Test
    public void overlapsButtonIsDisabledByDefault() {
        assertTrue(viewModel.isOverlapsButtonDisabled().get());
    }

    @Test
    public void getAllPointsButtonIsDisabledByDefault() {
        assertTrue(viewModel.isGetAllPointsButtonDisabled().get());
    }

    @Test
    public void getEndPointsButtonIsDisabledByDefault() {
        assertTrue(viewModel.isGetEndPointsButtonDisabled().get());
    }

    @Test
    public void containsButtonIsDisabledWithIncorrectRange() {
        viewModel.getTxtRange().setValue("word");
        assertTrue(viewModel.isContainsButtonDisabled().get());
    }

    @Test
    public void logContainsActionIncorrectInput() {
        String input = "word";
        viewModel.getTxtRange().setValue(input);
        String message = viewModel.getLog().get(1);

        assertTrue(message.matches(".*" + Actions.INCORRECT_INPUT + input + ".*"));
    }

    @Test
    public void equalsButtonIsDisabledWithIncorrectRange() {
        viewModel.getTxtRange().setValue("word");
        assertTrue(viewModel.isEqualsButtonDisabled().get());
    }

    @Test
    public void overlapsButtonIsDisabledWithIncorrectRange() {
        viewModel.getTxtRange().setValue("word");
        assertTrue(viewModel.isOverlapsButtonDisabled().get());
    }

    @Test
    public void getAllPointsButtonIsDisabledWithIncorrectRange() {
        viewModel.getTxtRange().setValue("word");
        assertTrue(viewModel.isGetAllPointsButtonDisabled().get());
    }

    @Test
    public void getEndPointsButtonIsDisabledWithIncorrectRange() {
        viewModel.getTxtRange().setValue("word");
        assertTrue(viewModel.isGetEndPointsButtonDisabled().get());
    }

    @Test
    public void getAllPointsButtonIsEnabledWithCorrectRange() {
        viewModel.getTxtRange().setValue("[1,2]");
        assertFalse(viewModel.isGetAllPointsButtonDisabled().get());
    }

    @Test
    public void logContainsActionNewRange() {
        viewModel.getTxtRange().setValue("[1,2]");
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + Actions.NEW_RANGE + "\\[1,2\\]" + ".*"));
    }

    @Test
    public void getEndPointsButtonIsEnabledWithCorrectRange() {
        viewModel.getTxtRange().setValue("(-1,3)");
        assertFalse(viewModel.isGetEndPointsButtonDisabled().get());
    }

    @Test
    public void equalsButtonIsDisabledWithIncorrectInput() {
        viewModel.getTxtRange().setValue("(-1,3)");
        viewModel.getTxtInput().setValue("word");
        assertTrue(viewModel.isEqualsButtonDisabled().get());
    }

    @Test
    public void logContainsActionNewInput() {
        String input = "word";
        viewModel.getTxtInput().setValue(input);
        String message = viewModel.getLog().get(0);

        assertTrue(message.matches(".*" + Actions.NEW_INPUT + input + ".*"));
    }

    @Test
    public void overlapsButtonIsDisabledWithIncorrectInput() {
        viewModel.getTxtRange().setValue("(-1,3)");
        viewModel.getTxtInput().setValue("word");
        assertTrue(viewModel.isOverlapsButtonDisabled().get());
    }

    @Test
    public void containsButtonIsDisabledWithIncorrectInput() {
        viewModel.getTxtRange().setValue("(-1,3)");
        viewModel.getTxtInput().setValue("word");
        assertTrue(viewModel.isContainsButtonDisabled().get());
    }

    @Test
    public void equalsButtonIsDisabledWithIncorrectRangeAndCorrectInput() {
        viewModel.getTxtRange().setValue("word");
        viewModel.getTxtInput().setValue("(-1,3)");
        assertTrue(viewModel.isEqualsButtonDisabled().get());
    }

    @Test
    public void overlapsButtonIsDisabledWithIncorrectRangeAndCorrectInput() {
        viewModel.getTxtRange().setValue("word");
        viewModel.getTxtInput().setValue("(-1,3)");
        assertTrue(viewModel.isOverlapsButtonDisabled().get());
    }

    @Test
    public void containsButtonIsDisabledWithIncorrectRangeAndCorrectInput() {
        viewModel.getTxtRange().setValue("word");
        viewModel.getTxtInput().setValue("(-1,3)");
        assertTrue(viewModel.isContainsButtonDisabled().get());
    }

    @Test
    public void getAllPointsButtonReturnsCorrectResult() {
        viewModel.getTxtRange().setValue("[1,4]");

        viewModel.getAllPoints();

        assertEquals(Arrays.toString(new int[]{1, 2, 3, 4}), viewModel.getTxtResult().get());
    }

    @Test
    public void logContainsActionGetAllPoints() {
        viewModel.getTxtRange().setValue("[1,4]");
        viewModel.getAllPoints();
        String message = viewModel.getLog().get(1);

        assertTrue(message.matches(".*" + Actions.ALL_POINTS_CHECK + "\\[1,4\\]" + ".*"));
    }

    @Test
    public void getEndPointsButtonReturnsCorrectResult() {
        viewModel.getTxtRange().setValue("[1,4]");

        viewModel.getEndPoints();

        assertEquals(Arrays.toString(new int[]{1, 4}), viewModel.getTxtResult().get());
    }

    @Test
    public void logContainsActionGetEndPoints() {
        viewModel.getTxtRange().setValue("[1,4]");
        viewModel.getEndPoints();
        String message = viewModel.getLog().get(1);

        assertTrue(message.matches(".*" + Actions.END_POINTS_CHECK + "\\[1,4\\]" + ".*"));
    }

    @Test
    public void overlapsWithRangeWhenOverlapsIsPresent() {
        viewModel.getTxtRange().setValue("[11,15]");
        viewModel.getTxtInput().setValue("(9,19)");

        viewModel.overlapsRange();

        assertEquals(yes, viewModel.getTxtResult().get());
    }

    @Test
    public void logContainsActionOverlaps() {
        viewModel.getTxtRange().setValue("[11,15]");
        viewModel.getTxtInput().setValue("(9,19)");

        viewModel.overlapsRange();
        String message = viewModel.getLog().get(2);

        assertTrue(message
                .matches(".*" + Actions.OVERLAPS_CHECK + "\\[11,15\\] and \\(9,19\\)" + ".*"));
    }

    @Test
    public void cantOverlapsWithRangeWhenOverlapsIsMissing() {
        viewModel.getTxtRange().setValue("[11,15]");
        viewModel.getTxtInput().setValue("(5,9)");

        viewModel.overlapsRange();

        assertEquals(no, viewModel.getTxtResult().get());
    }

    @Test
    public void canEqualsWithRangeWithNonInclusiveBoundaries() {
        viewModel.getTxtRange().setValue("[11,20]");
        viewModel.getTxtInput().setValue("(10,21)");

        viewModel.equalsRange();

        assertEquals(yes, viewModel.getTxtResult().get());
    }

    @Test
    public void logContainsActionEquals() {
        viewModel.getTxtRange().setValue("[11,15]");
        viewModel.getTxtInput().setValue("(9,19)");

        viewModel.equalsRange();
        String message = viewModel.getLog().get(2);

        assertTrue(message
                .matches(".*" + Actions.EQUALS_CHECK + "\\[11,15\\] and \\(9,19\\)" + ".*"));
    }

    @Test
    public void cantEqualsWithAnotherRange() {
        viewModel.getTxtRange().setValue("[11,20]");
        viewModel.getTxtInput().setValue("(11,20)");

        viewModel.equalsRange();

        assertEquals(no, viewModel.getTxtResult().get());
    }

    @Test
    public void containsIntegerValue() {
        viewModel.getTxtRange().setValue("[10,11)");
        viewModel.getTxtInput().setValue("10");

        viewModel.containsInput();

        assertEquals(yes, viewModel.getTxtResult().get());
    }

    @Test
    public void logContainsActionContains() {
        String input = "10";
        viewModel.getTxtRange().setValue("[10,11)");
        viewModel.getTxtInput().setValue(input);

        viewModel.containsInput();
        String message = viewModel.getLog().get(2);

        assertTrue(message
                .matches(".*" + Actions.CONTAINS_CHECK + "\\[10,11\\) and " + input + ".*"));
    }

    @Test
    public void notContainsIntegerValue() {
        viewModel.getTxtRange().setValue("[10,11)");
        viewModel.getTxtInput().setValue("11");

        viewModel.containsInput();

        assertEquals(no, viewModel.getTxtResult().get());
    }

    @Test
    public void containsSetOfIntegers() {
        viewModel.getTxtRange().setValue("[10,15)");
        viewModel.getTxtInput().setValue("{10,11,13}");

        viewModel.containsInput();

        assertEquals(yes, viewModel.getTxtResult().get());
    }

    @Test
    public void notContainsSetWhenOneElementContains() {
        viewModel.getTxtRange().setValue("[10,15)");
        viewModel.getTxtInput().setValue("{10,16}");

        viewModel.containsInput();

        assertEquals(no, viewModel.getTxtResult().get());
    }

    @Test
    public void canContainLesserRange() {
        viewModel.getTxtRange().setValue("[10,15)");
        viewModel.getTxtInput().setValue("[11,13]");

        viewModel.containsInput();

        assertEquals(yes, viewModel.getTxtResult().get());
    }

    @Test
    public void cantContainLargerRange() {
        viewModel.getTxtRange().setValue("[10,15)");
        viewModel.getTxtInput().setValue("[11,16]");

        viewModel.containsInput();
        assertEquals(no, viewModel.getTxtResult().get());
    }

    @Test(expected = IllegalArgumentException.class)
    public void viewModelConstructorThrowsExceptionWithNullLogger() {
        new ViewModel(null);
    }

    @Test
    public void logIsEmptyIfNothingHappened() {
        List<String> log = viewModel.getLog();
        assertTrue(log.isEmpty());
    }
}
