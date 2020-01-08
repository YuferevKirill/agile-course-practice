package ru.unn.agile.interpolationsearch.model;

import java.util.NoSuchElementException;

public final class InterpolationSearch {
    private InterpolationSearch() {
    }

     public static <T extends Comparable<T>> int find(final Integer[] array, final Integer value) {
        if (!isSortedArray(array)) {
            throw new IllegalArgumentException("Array is not sorted");
        }
        return search(array, value);
    }

    private static boolean isSortedArray(final Integer[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                return false;
            }
        }
        return true;
    }

     private static int search(final Integer[] array, final Integer value) {
        int start = 0, end = array.length - 1;
        while (start <= end && value >= array[start] && value <= array[end]) {
            int offset = Math.max(getSearchOffset(array, start, end, value), 0);
            int pos = start + offset;
            if (array[pos].equals(value)) {
                return pos;
            }
            if (array[pos] < value) {
                start = pos + 1;
            }
        }
        throw new NoSuchElementException("Cannot find element '" + value + "' in array.");
    }

     private static int getSearchOffset(final Integer[] array, final int startIndex,
                                final int endIndex, final int findElement) {
        int range = array[endIndex] - array[startIndex];
        if (range == 0) {
            range = 1;
        }
        return (((endIndex - startIndex) / range) * (findElement - array[endIndex]));
    }
}
