package ru.unn.agile.vectoroperations;

import org.junit.Test;
import ru.unn.agile.vectoroperations.model.Vector;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.*;

public class VectorTest {

    @Test
    public void canCreateVectorWithPositiveCoordinates() {
        Vector vect = new Vector(1, 2, 3);
        assertNotNull(vect);
    }

    @Test
    public void canCreateVectorWithNegativeCoordinates() {
        Vector vect = new Vector(-1, -2, -3);
        assertNotNull(vect);
    }

    @Test
    public void canCreateTwoEqualVectorsWithPositiveCoordinates() {
        Vector v1 = new Vector(1, 2, 3);
        Vector v2 = new Vector(1, 2, 3);
        assertEquals(v1, v2);
    }

    @Test
    public void canCreateTwoEqualVectorsWithNegativeCoordinates() {
        Vector v1 = new Vector(-1, -2, -3);
        Vector v2 = new Vector(-1, -2, -3);
        assertEquals(v1, v2);
    }

    @Test
    public void canFindTheLengthNormWithPositiveCoordinates() {
        Vector vect = new Vector(1, 2, 3);
        double tmp = 3.7416573867739413;
        double len = Vector.getLengthNorm(vect);
        assertEquals(len, tmp);
    }

    @Test
    public void canFindTheLengthNormWithNegativeCoordinates() {
        Vector vect = new Vector(-1, -2, -3);
        double tmp = 3.7416573867739413;
        double len = Vector.getLengthNorm(vect);
        assertEquals(len, tmp);
    }

    @Test
    public void canDoScalarMulterenceTwoVectorsWithPositiveCoordinates() {
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(2, 2, 2);
        double actRes = 6;
        double expRes = Vector.getScalarMult(v2, v1);
        assertEquals(expRes, actRes);
    }

    @Test()
    public void canDoScalarMultTwoVectorsWithNegativeCoordinates() {
        Vector v1 = new Vector(-1, -1, -1);
        Vector v2 = new Vector(-2, -2, -2);
        double actRes = 6;
        double expRes = Vector.getScalarMult(v2, v1);
        assertEquals(expRes, actRes);
    }

    @Test
    public void canDoScalarMultTwoVectorsWithDifferentSignCoordinates() {
        Vector v1 = new Vector(-1, 1, 1);
        Vector v2 = new Vector(2, -2, 2);
        double actRes = -2;
        double expRes = Vector.getScalarMult(v2, v1);
        assertEquals(expRes, actRes);
    }

    @Test
    public void canDoVectMulterenceTwoVectorsWithPositiveCoordinates() {
        Vector v1 = new Vector(1, 1, 1);
        Vector v2 = new Vector(2, 2, 2);
        Vector actRes = new Vector(0, 0, 0);
        Vector expRes = Vector.getVectMult(v2, v1);
        assertEquals(expRes, actRes);
    }

    @Test
    public void canDoVectMultTwoVectorsWithNegativeCoordinates() {
        Vector v1 = new Vector(-1, -1, -1);
        Vector v2 = new Vector(-2, -2, -2);
        Vector actRes = new Vector(0, 0, 0);
        Vector expRes = Vector.getVectMult(v2, v1);
        assertEquals(expRes, actRes);
    }

    @Test
    public void canDoVectMultTwoVectorsWithDifferentSignCoordinates() {
        Vector v1 = new Vector(-1, 1, 1);
        Vector v2 = new Vector(2, -2, 2);
        Vector actRes = new Vector(-4, 4, 0);
        Vector expRes = Vector.getVectMult(v2, v1);
        assertEquals(expRes, actRes);
    }


    @Test
    public void canCalculateNormalizedMixedVector() {
        Vector v1 = new Vector(5, -5, 5);
        Vector actualResult = Vector.getNormalizedVector(v1);
        double vectorNorm = 8.66;
        double expectedX = v1.getX() / vectorNorm;
        double expectedY = v1.getY() / vectorNorm;
        double expectedZ = v1.getZ() / vectorNorm;
        Vector expectedResult = new Vector(expectedX, expectedY, expectedZ);
        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void canGetX() {
        Vector v1 = new Vector(-1, 1, 1);
        double actualRes = v1.getX();
        double expectedRes = -1.0;
        assertEquals(actualRes, expectedRes);
    }

    @Test
    public void canGetY() {
        Vector v1 = new Vector(-1, 1, 1);
        double actualRes = v1.getY();
        double expectedRes = 1.0;
        assertEquals(actualRes, expectedRes);
    }

    @Test
    public void canGetZ() {
        Vector v1 = new Vector(-1, 1, 1);
        double actualRes = v1.getZ();
        double expectedRes = 1.0;
        assertEquals(actualRes, expectedRes);
    }
}
