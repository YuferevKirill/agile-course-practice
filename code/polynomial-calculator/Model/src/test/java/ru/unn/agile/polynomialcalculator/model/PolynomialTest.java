package ru.unn.agile.polynomialcalculator.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class PolynomialTest {

    private double delta = 1e-3;

    @Test
    public void canInitZeroPolynomial() {
        var polynomial = new Polynomial(0, 0);
        assertNotNull(polynomial);
    }

    @Test
    public void zeroPolynomialHasZeroDegree() {
        var polynomial = new Polynomial(0, 0);
        assertEquals(0, polynomial.getDegree());
    }

    @Test
    public void zeroPolynomialHasZeroCoef() {
        var polynomial = new Polynomial(0, 0);
        var degree = polynomial.getDegree();

        assertEquals(0, polynomial.getCoef(degree), delta);
    }

    @Test
    public void zeroPolynomialHasCorrectStringRepr() {
        var polynomial = new Polynomial(0, 0);

        assertEquals("0", polynomial.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void cannotCreatePolynomialWithNegativeDegree() {
        new Polynomial(0, -1);
    }

    @Test
    public void nonZeroDegreePolynomialWithZeroCoefHasCorrectStringRepr() {
        var polynomial = new Polynomial(0, 3);

        assertEquals("0", polynomial.toString());
    }

    @Test
    public void zeroDegreePolynomialWithNonZeroCoefHasCorrectStringRepr() {
        var polynomial = new Polynomial(3, 0);

        assertEquals("3.0", polynomial.toString());
    }

    @Test
    public void canCreatePolynomialWithNonZeroCoefAndDegree() {
        var polynomial = new Polynomial(1., 1);

        assertNotNull(polynomial);
    }

    @Test
    public void nonZeroPolynomialHasCorrectDegree() {
        var polynomial = new Polynomial(1., 1);

        assertEquals(1, polynomial.getDegree());
    }

    @Test
    public void nonZeroPolynomialHasCorrectCoef() {
        var polynomial = new Polynomial(1., 1);
        var degree = polynomial.getDegree();

        assertEquals(1., polynomial.getCoef(degree), delta);
    }

    @Test
    public void nonZeroPolynomialHasCorrectStringRepr() {
        var polynomial = new Polynomial(2.3, 2);

        assertEquals("2.3x^2", polynomial.toString());
    }

    @Test
    public void stringReprOfFirstDegreePolynomialOmitsDegreeSign() {
        var polynomial = new Polynomial(1.5, 1);

        assertEquals("1.5x", polynomial.toString());
    }

    @Test
    public void stringReprOfContainMinusSignWithNegativeCoef() {
        var polynomial = new Polynomial(-2.3, 2);

        assertEquals("- 2.3x^2", polynomial.toString());
    }

    @Test
    public void sumOfPolynomialsIsNotNull() {
        var p1 = new Polynomial(1.1, 2);
        var p2 = new Polynomial(1.4, 3);

        var p3 = p1.plus(p2);

        assertNotNull(p3);
    }

    @Test
    public void sumProducesCorrectCoefs() {
        var p1 = new Polynomial(1.1, 1);
        var p2 = new Polynomial(1.4, 1);

        var p3 = p1.plus(p2);

        assertEquals(2.5, p3.getCoef(1), delta);
    }

    @Test
    public void sumProducesCorrectDegree() {
        var p1 = new Polynomial(1.1, 1);
        var p2 = new Polynomial(1.4, 2);

        var p3 = p1.plus(p2);

        assertEquals(2, p3.getDegree());
    }

    @Test
    public void sumHasCorrectStringRepr() {
        var p1 = new Polynomial(1.1, 1);
        var p2 = new Polynomial(1.4, 2);

        var p3 = p1.plus(p2);

        assertEquals("1.4x^2 + 1.1x", p3.toString());
    }

    @Test
    public void minusOfPolynomialIsNotNull() {
        var p1 = new Polynomial(2.3, 1);
        var p2 = new Polynomial(1.8, 1);

        var p3 = p1.minus(p2);

        assertNotNull(p3);
    }

    @Test
    public void minusProducesCorrectCoefs() {
        var p1 = new Polynomial(2.1, 1);
        var p2 = new Polynomial(1.8, 1);

        var p3 = p1.minus(p2);

        assertEquals(0.3, p3.getCoef(1), delta);
    }

    @Test
    public void minusProducesCorrectDegree() {
        var p1 = new Polynomial(1.1, 1);
        var p2 = new Polynomial(1.4, 2);

        var p3 = p1.minus(p2);

        assertEquals(2, p3.getDegree());
    }

    @Test
    public void minusHasCorrectStringRepr() {
        var p1 = new Polynomial(1.1, 1);
        var p2 = new Polynomial(1.4, 2);

        var p3 = p1.minus(p2);

        assertEquals("- 1.4x^2 + 1.1x", p3.toString());
    }

    @Test
    public void polynomialReducesDegreeCorrectly() {
        var p1 = new Polynomial(1., 1);
        var p2 = new Polynomial(2.1, 2);

        var sum = p2.plus(p1);
        var p1Copy = sum.minus(p2);

        assertEquals(1, p1Copy.getDegree());
    }

    @Test
    public void polynomialHasCorrectStringReprAfterReduction() {
        var p1 = new Polynomial(1.2, 3);
        var p2 = new Polynomial(2, 2);
        var p3 = new Polynomial(3.9, 1);

        var p1Negative = new Polynomial(-1.2, 3);
        var result = p1.plus(p2).plus(p3).plus(p1Negative);

        assertEquals("2.0x^2 + 3.9x", result.toString());
    }

    @Test
    public void longPolynomialHasCorrectStringRepr() {
        var p0 = new Polynomial(3.0, 0);
        var p1 = new Polynomial(1.2, 1);
        var p2 = new Polynomial(2.3, 2);
        var p3 = new Polynomial(-4.1, 3);
        var p4 = new Polynomial(-1, 4);

        var polynomial = p4.plus(p3).minus(p2).plus(p1).plus(p0);

        assertEquals("- 1.0x^4 - 4.1x^3 - 2.3x^2 + 1.2x + 3.0", polynomial.toString());
    }

    @Test
    public void productOfPolynomialsIsNotNull() {
        var p1 = new Polynomial(1.2, 2);
        var p2 = new Polynomial(2., 3);

        var p3 = p1.multiply(p2);

        assertNotNull(p3);
    }

    @Test
    public void productOfPolynomialsHasCorrectDegree() {
        var p1 = new Polynomial(1.2, 2);
        var p2 = new Polynomial(2., 3);

        var p3 = p1.multiply(p2);

        assertEquals(5, p3.getDegree());
    }

    @Test
    public void productOfPolynomialsHasCorrectCoef() {
        var p1 = new Polynomial(1.2, 2);
        var p2 = new Polynomial(2., 3);

        var p3 = p1.multiply(p2);

        assertEquals(2.4, p3.getCoef(5), delta);
    }

    @Test
    public void productOfPolynomialsHasCorrectStringRepr() {
        var p1 = new Polynomial(1, 2);
        var p2 = new Polynomial(3, 1);
        var p3 = p1.plus(p2);

        var pr = new Polynomial(2, 3);
        var product = p3.multiply(pr);

        assertEquals("2.0x^5 + 6.0x^4", product.toString());
    }

    @Test
    public void samePolynomialsAreEqual() {
        var p1 = new Polynomial(3.2, 2);
        var p2 = new Polynomial(4.2, 3);

        assertEquals(p1.plus(p2), p2.plus(p1));
    }

    @Test
    public void polynomialEqualsWithSelf() {
        var p1 = new Polynomial(3.3, 2);

        assertEquals(p1, p1);
    }

    @Test
    public void polynomialsAreNotEqualWithOtherClasses() {
        var p1 = new Polynomial(3.3, 2);

        assertNotEquals(p1, new Object());
    }

    @Test
    public void differentCoefPolynomialsAreNotEqual() {
        var p1 = new Polynomial(2.3, 2);
        var p2 = new Polynomial(4.3, 2);

        assertNotEquals(p1, p2);
    }

    @Test
    public void differentDegreePolynomialsAreNotEqual() {
        var p1 = new Polynomial(2.3, 2);
        var p2 = new Polynomial(2.3, 4);

        assertNotEquals(p1, p2);
    }

    @Test
    public void composeOfPolynomialsIsNotNull() {
        var p1 = new Polynomial(2, 2);
        var p2 = new Polynomial(3, 4);

        var p3 = p1.compose(p2);

        assertNotNull(p3);
    }

    @Test
    public void composeOfPolynomialsHasCorrectDegree() {
        var p1 = new Polynomial(2, 2);
        var p2 = new Polynomial(3, 4);

        var p3 = p1.compose(p2);

        assertEquals(8, p3.getDegree());
    }

    @Test
    public void composeOfPolynomialsHasCorrectCoef() {
        var p1 = new Polynomial(2, 2);
        var p2 = new Polynomial(3, 4);

        var p3 = p1.compose(p2);
        assertEquals(18., p3.getCoef(8), delta);
    }

    @Test
    public void composeOfPolynomialsHasCorrectStringRepr() {
        var p1 = new Polynomial(2, 2);
        var p2 = new Polynomial(3, 4);
        var p3 = new Polynomial(4, 0);

        var composition = p1.compose(p2.plus(p3));

        assertEquals("18.0x^8 + 48.0x^4 + 32.0", composition.toString());
    }

    @Test
    public void differentiateOfPolynomialsIsNotNull() {
        var p1 = new Polynomial(2, 2);
        var p2 = new Polynomial(3, 4);

        var p3 = p1.plus(p2).differentiate();

        assertNotNull(p3);
    }

    @Test
    public void differentiateOfPolynomialsHasCorrectDegree() {
        var p1 = new Polynomial(2, 2);
        var p2 = new Polynomial(3, 4);

        var p3 = p1.plus(p2).differentiate();

        assertEquals(3, p3.getDegree());
    }

    @Test
    public void differentiateOfPolynomialsHasCorrectCoef() {
        var p1 = new Polynomial(2, 2);
        var p2 = new Polynomial(3, 4);

        var p3 = p1.plus(p2).differentiate();
        assertEquals(12., p3.getCoef(3), delta);
    }

    @Test
    public void differentiateOfPolynomialsHasCorrectStringRepr() {
        var p1 = new Polynomial(2, 2);
        var p2 = new Polynomial(3, 4);
        var p3 = new Polynomial(4, 0);

        var derivative = p1.plus(p2).plus(p3).differentiate();

        assertEquals("12.0x^3 + 4.0x", derivative.toString());
    }

    @Test
    public void differentiatesZeroDegreePolynomial() {
        var p1 = new Polynomial(3, 0);

        var derivative = p1.differentiate();

        assertEquals("0", derivative.toString());
    }

    @Test
    public void evaluateOfTrivialPolynomialCorrectly() {
        var polynomial = new Polynomial(5., 0);

        var x = 42.8;
        assertEquals(5., polynomial.evaluate(x), delta);
    }

    @Test
    public void evaluateOfComplexPolynomialCorrectly() {
        var p0 = new Polynomial(3.0, 0);
        var p1 = new Polynomial(1.3, 1);
        var p2 = new Polynomial(2.4, 2);
        var p3 = new Polynomial(-4.1, 3);
        var p4 = new Polynomial(3.1, 4);

        var polynomial = p4.plus(p3).minus(p2).plus(p1).plus(p0);

        var x = 2.;
        assertEquals(12.8, polynomial.evaluate(x), delta);
    }


}
