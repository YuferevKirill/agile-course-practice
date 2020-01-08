package ru.unn.agile.interpolationsearch.model;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

public class InterpolationSearchTest {
    @Test
    public void canSearchForOneElement() {
        final Integer[] array = {
            1
        };
        final Integer value = 1;

        final Integer res = InterpolationSearch.find(array, value);

        final Integer exp = 0;
        assertEquals(exp, res);
    }

    @Test
    public void canSearchForTwoElement() {
        final Integer[] array = {
            1, 2
        };
        final Integer value = 2;

        final Integer res = InterpolationSearch.find(array, value);

        final Integer exp = 1;
        assertEquals(exp, res);
    }

    @Test
    public void canSearchForThreeElement() {
        final Integer[] array = {
                1, 2, 3
        };
        final Integer value = 2;

        final Integer res = InterpolationSearch.find(array, value);

        final Integer exp = 1;
        assertEquals(exp, res);
    }


    @Test
    public void canSearchForNegativeTwoElement() {
        final Integer[] array = {
            -12, -11
        };
        final Integer value = -11;

        final Integer res = InterpolationSearch.find(array, value);

        final Integer exp = 1;
        assertEquals(exp, res);
    }

    @Test
    public void canSearchForMixedElements() {
        final Integer[] array = {
            -123, -13, 567, 1024
        };
        final Integer value = 567;

        final Integer res = InterpolationSearch.find(array, value);

        final Integer exp = 2;
        assertEquals(exp, res);
    }

    @Test
    public void canSearchForLastZeroElement() {
        final Integer[] array = {
            -45, -44, -33, -12, -11, 0
        };
        final Integer value = 0;

        final Integer res = InterpolationSearch.find(array, value);

        final Integer exp = 5;
        assertEquals(exp, res);
    }

    @Test
    public void canSearchForSymmetricElements() {
        final Integer[] array = {
            -52, -51, -15, 15, 51, 52
        };
        final Integer value = 15;

        final Integer res = InterpolationSearch.find(array, value);

        final Integer exp = 3;
        assertEquals(exp, res);
    }

    @Test
    public void canSearchForSymmetricZeroElements() {
        final Integer[] array = {
            -7, 0, 0, 0, 7
        };
        final Integer value = 7;

        final Integer res = InterpolationSearch.find(array, value);

        final Integer exp = 4;
        assertEquals(exp, res);
    }

    @Test
    public void canSearchForIdenticalZeroElements() {
        final Integer[] array = {
            0, 0, 0, 0, 0, 0
        };
        final Integer value = 0;

        Integer res = InterpolationSearch.find(array, value);

        final Integer exp = 0;
        assertEquals(exp, res);
    }

    @Test(expected = NoSuchElementException.class)
    public void cantSearchElement() {
        final Integer[] array = {
            -3, 0, 4, 10, 42
        };
        final Integer value = 6;

        InterpolationSearch.find(array, value);
    }

    @Test(expected = IllegalArgumentException.class)
    public void arrayIsNotSorted() {
        final Integer[] array = {
            -3, 0, 4, 10, 4
        };
        final Integer value = 6;

        InterpolationSearch.find(array, value);
    }

    @Test
    public void canSearchInBigArray() {
        final int maxSize = 10000000;
        Integer[] array = new Integer[maxSize];
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }
        final Integer value = maxSize - 1;

        final Integer res = InterpolationSearch.find(array, value);

        final Integer exp = value;
        assertEquals(exp, res);
    }
}
