package ru.unn.agile.range.model;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class RangeTest {

    @Test
    public void containsIntegerValue() {
        Range range = new Range("[10,11)");

        assertTrue(range.containsValue(10));
    }

    @Test
    public void notContainsIntegerValue() {
        Range range = new Range("[10,11)");

        assertFalse(range.containsValue(11));
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenInitRangeIncorrectly() {
        new Range("[11,11)");
    }

    @Test
    public void containsSetOfIntegers() {
        Range range = new Range("[10,15)");

        assertTrue(range.containsSet(new int[]{10, 11, 13}));
    }

    @Test
    public void notContainsSetWhenOneElementContains() {
        Range range = new Range("[10,15)");

        assertFalse(range.containsSet(new int[]{10, 16}));
    }

    @Test
    public void notContainsSetOfIntegers() {
        Range range = new Range("[10,15)");

        assertFalse(range.containsSet(new int[]{9, 16}));
    }

    @Test
    public void canGetAllPointsWhenMissedPoints() {
        Range range = new Range("[10,15)");

        assertNotEquals(Arrays.toString(new int[]{10, 14}), Arrays.toString(range.getAllPoints()));
    }

    @Test
    public void canGetAllPointsWhenExtraPoints() {
        Range range = new Range("(10,15)");

        assertNotEquals(Arrays.toString(new int[]{10, 11, 12, 13, 14, 15}),
                Arrays.toString(range.getAllPoints()));
    }

    @Test
    public void canGetAllPoints() {
        Range range = new Range("[10,15)");

        assertEquals(Arrays.toString(new int[]{10, 11, 12, 13, 14}),
                Arrays.toString(range.getAllPoints()));
    }

    @Test
    public void cantContainRangeWithLargerEndElement() {
        Range range = new Range("[10,15)");

        assertFalse(range.containsRange(new Range("[9,16]")));
    }

    @Test
    public void cantContainRangeWithLesserStartElement() {
        Range range = new Range("[10,15)");

        assertFalse(range.containsRange(new Range("[9,13]")));
    }

    @Test
    public void cantContainLargerRange() {
        Range range = new Range("[10,15)");

        assertFalse(range.containsRange(new Range("[11,16]")));
    }

    @Test
    public void canContainLesserRange() {
        Range range = new Range("[10,15)");

        assertTrue(range.containsRange(new Range("[11,13]")));
    }

    @Test
    public void cantGetEndPointsWhenNonInclusiveBoundaries() {
        Range range = new Range("(10,15)");

        assertNotEquals(Arrays.toString(new int[]{10, 15}), Arrays.toString(range.endPoints()));
    }

    @Test
    public void cantGetEndPointsWhenInclusiveBoundaries() {
        Range range = new Range("[10,15]");

        assertNotEquals(Arrays.toString(new int[]{11, 14}), Arrays.toString(range.endPoints()));
    }

    @Test
    public void cantGetEndPointsWhenEndBoundaryIsInclusive() {
        Range range = new Range("(10,15]");

        assertNotEquals(Arrays.toString(new int[]{11, 14}), Arrays.toString(range.endPoints()));
    }

    @Test
    public void cantGetEndPointsWhenStartBoundaryIsInclusive() {
        Range range = new Range("[10,15)");

        assertNotEquals(Arrays.toString(new int[]{11, 14}), Arrays.toString(range.endPoints()));
    }

    @Test
    public void canGetEndPointsWhenStartBoundaryIsInclusive() {
        Range range = new Range("[10,15)");

        assertEquals(Arrays.toString(new int[]{10, 14}), Arrays.toString(range.endPoints()));
    }

    @Test
    public void canGetEndPointsWhenInclusiveBoundaries() {
        Range range = new Range("[10,15]");

        assertEquals(Arrays.toString(new int[]{10, 15}), Arrays.toString(range.endPoints()));
    }

    @Test
    public void canGetEndPointsWhenNonInclusiveBoundaries() {
        Range range = new Range("(10,15)");

        assertEquals(Arrays.toString(new int[]{11, 14}), Arrays.toString(range.endPoints()));
    }

    @Test
    public void cantOverlapsWithRangeWithInclusiveStartBoundary() {
        Range range1 = new Range("[10,15)");
        Range range2 = new Range("[15,20)");

        assertFalse(range1.overlapsRange(range2));
    }

    @Test
    public void cantOverlapsWithRangeWithNonInclusiveStartBoundary() {
        Range range1 = new Range("[10,15]");
        Range range2 = new Range("(15,20)");

        assertFalse(range1.overlapsRange(range2));
    }

    @Test
    public void cantOverlapsWithRangeWithNonInclusiveEndBoundary() {
        Range range1 = new Range("[10,15]");
        Range range2 = new Range("(5,10)");

        assertFalse(range1.overlapsRange(range2));
    }

    @Test
    public void cantOverlapsWithRangeWhenOverlapsIsMissing() {
        Range range1 = new Range("[11,15]");
        Range range2 = new Range("(5,9)");

        assertFalse(range1.overlapsRange(range2));
    }

    @Test
    public void canOverlapsWithRangeWhenOverlapsIsPresent() {
        Range range1 = new Range("[11,15]");
        Range range2 = new Range("(9,19)");

        assertTrue(range1.overlapsRange(range2));
    }

    @Test
    public void canOverlapsWithSameRange() {
        Range range1 = new Range("[11,20]");
        Range range2 = new Range("[11,20]");

        assertTrue(range1.overlapsRange(range2));
    }

    @Test
    public void canEqualsWithSameRange() {
        Range range1 = new Range("[11,20]");
        Range range2 = new Range("[11,20]");

        assertEquals(range1, range2);
    }

    @Test
    public void canEqualsWithRangeWithNonInclusiveBoundaries() {
        Range range1 = new Range("[11,20]");
        Range range2 = new Range("(10,21)");

        assertEquals(range1, range2);
    }

    @Test
    public void cantEqualsWithAnotherRange() {
        Range range1 = new Range("[11,20]");
        Range range2 = new Range("(11,20)");

        assertNotEquals(range1, range2);
    }

}
