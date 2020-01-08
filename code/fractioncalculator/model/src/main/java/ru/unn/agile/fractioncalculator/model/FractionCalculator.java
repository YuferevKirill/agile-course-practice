package ru.unn.agile.fractioncalculator.model;

import java.util.Arrays;
import java.util.Objects;

public final class FractionCalculator {

    private FractionCalculator() { }

    public static Fraction reduce(final Fraction fraction) {
        checkFractionsNotNull(fraction);
        var tmpNumerator = Math.abs(fraction.getNumerator());
        var tmpDenominator = Math.abs(fraction.getDenominator());
        while (tmpNumerator != 0 && tmpDenominator != 0) {
            if (tmpNumerator > tmpDenominator) {
                tmpNumerator %= tmpDenominator;
            } else {
                tmpDenominator %= tmpNumerator;
            }
        }
        var nod = tmpNumerator + tmpDenominator;
        if (fraction.getDenominator() < 0 && fraction.getNumerator() < 0) {
            nod *= -1;
        }
        return new Fraction(
                fraction.getNumerator() / nod,
                fraction.getDenominator() / nod
        );
    }

    public static Fraction multiple(final Fraction first, final Fraction second) {
        checkFractionsNotNull(first, second);
        return new Fraction(
                first.getNumerator() * second.getNumerator(),
                first.getDenominator() * second.getDenominator()
        );
    }

    public static Fraction divide(final Fraction first, final Fraction second) {
        checkFractionsNotNull(first, second);
        var reverseSecond = new Fraction(second.getDenominator(), second.getNumerator());
        return multiple(first, reverseSecond);
    }

    public static Fraction sum(final Fraction first, final Fraction second) {
        checkFractionsNotNull(first, second);
        return new Fraction(
                first.getNumerator() * second.getDenominator()
                        + second.getNumerator() * first.getDenominator(),
                first.getDenominator() * second.getDenominator()
        );
    }

    public static Fraction minus(final Fraction first, final Fraction second) {
        checkFractionsNotNull(first, second);
        var negativeSecond = new Fraction(-second.getNumerator(), second.getDenominator());
        return sum(first, negativeSecond);
    }

    private static void checkFractionsNotNull(final Fraction... fractions)
            throws NullPointerException {
        Arrays.stream(Objects.requireNonNull(fractions)).forEach(Objects::requireNonNull);
    }
}
