package ru.unn.agile.fractioncalculator.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class FractionTest {

    @Test
    public void canCreateFraction() {
        var fraction = new Fraction(1, 2);
        assertNotNull(fraction);
    }

    @Test
    public void canCreateFractionWithZeroNumerator() {
        var fraction = new Fraction(0, 1);
        assertNotNull(fraction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void canNotCreateFractionWithZeroDenominator() {
        new Fraction(1, 0);
    }

    @Test
    public void canGetNumerator() {
        var fraction = new Fraction(2, 1);
        assertEquals(2, fraction.getNumerator());
    }

    @Test
    public void canGetDenominator() {
        var fraction = new Fraction(2, 1);
        assertEquals(1, fraction.getDenominator());
    }

    @Test
    public void canEquals() {
        var fractionOne = new Fraction(2, 4);
        var fractionTwo = new Fraction(2, 4);
        assertEquals(fractionOne, fractionTwo);
    }

    @Test
    public void canEqualsWithDifferentFractions() {
        var fractionOne = new Fraction(2, 4);
        var fractionTwo = new Fraction(1, 3);
        assertNotEquals(fractionOne, fractionTwo);
    }

    @Test
    public void canEqualsSameFractions() {
        var fractionOne = new Fraction(10, 5);
        var fractionTwo = new Fraction(2, 1);
        assertEquals(fractionOne, fractionTwo);
    }

    @Test
    public void canEqualsWithObject() {
        var fraction = new Fraction(1, 1);
        assertNotEquals(fraction, new Object());
    }

    @Test
    public void canCheckNegativeWithNegativeNumerator() {
        assertTrue(new Fraction(-1, 2).isNegative());
    }

    @Test
    public void canCheckNegativeWithNegativeDenominator() {
        assertTrue(new Fraction(1, -2).isNegative());
    }

    @Test
    public void canCheckNegativeWithNegativeNumeratorAndDenominator() {
        assertFalse(new Fraction(-1, -2).isNegative());
    }
}
