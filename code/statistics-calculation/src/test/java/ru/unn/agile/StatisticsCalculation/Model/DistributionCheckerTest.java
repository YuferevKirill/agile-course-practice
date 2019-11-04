package ru.unn.agile.StatisticsCalculation.Model;

import org.junit.Test;
import ru.unn.agile.StatisticsCalculation.model.DistributionChecker;

public class DistributionCheckerTest {
    @Test(expected = IllegalArgumentException.class)
    public void throwWhenCalculateExpectedValueForEmptyValuesAndProbs() {
        Integer[] values = {};
        Double[] probabilities = {};
        DistributionChecker checker = new DistributionChecker();

        checker.validate(values, probabilities);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenCalculateExpectedValueForEmptyValues() {
        Integer[] values = {};
        Double[] probabilities = {1.0};
        DistributionChecker checker = new DistributionChecker();

        checker.validate(values, probabilities);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenCalculateExpectedValueForEmptyProbs() {
        Integer[] values = {1};
        Double[] probabilities = {};
        DistributionChecker checker = new DistributionChecker();

        checker.validate(values, probabilities);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenCalculateExpectedValueForNullValuesAndProbs() {
        Integer[] values = null;
        Double[] probabilities = null;
        DistributionChecker checker = new DistributionChecker();

        checker.validate(values, probabilities);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenCalculateExpectedValueForNullValues() {
        Integer[] values = null;
        Double[] probabilities = {1.0};
        DistributionChecker checker = new DistributionChecker();

        checker.validate(values, probabilities);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenCalculateExpectedValueForNullProbs() {
        Integer[] values = {1};
        Double[] probabilities = null;
        DistributionChecker checker = new DistributionChecker();

        checker.validate(values, probabilities);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenCalculateExpectedValueForNotEqualsValsAndProbs() {
        Integer[] values = {1, 2, 3};
        Double[] probabilities = {0.1, 0.9};
        DistributionChecker checker = new DistributionChecker();

        checker.validate(values, probabilities);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenCalculateExpectedValueForValuesWithNull() {
        Integer[] values = {1, null, 3};
        Double[] probabilities = {0.1, 0.8, 0.1};
        DistributionChecker checker = new DistributionChecker();

        checker.validate(values, probabilities);
    }

    @Test(expected = IllegalArgumentException.class)
    public void throwWhenCalculateExpectedValueForProbsWithNull() {
        Integer[] values = {1, 2, 3};
        Double[] probabilities = {0.1, null, 0.1};
        DistributionChecker checker = new DistributionChecker();

        checker.validate(values, probabilities);
    }
}
