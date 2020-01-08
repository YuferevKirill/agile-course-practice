package ru.unn.agile.range.model;

public final class Utils {

    private Utils() {
    }

    public static boolean isInteger(final String input) {
        return input.matches("-?\\d+");
    }

    public static boolean isIntegerSet(final String input) {
        return input.matches("\\{-?\\d+(,-?\\d+)+?\\}");
    }

    public static boolean isRange(final String input) {
        return input.matches("[(|\\[]-?\\d+,-?\\d+[\\]|)]");
    }

}
