package ru.unn.agile.triangle.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class TriangleTests {

    private final double delta = 0.01;
    private final Triangle testTriangle;
    private final Triangle testNegativeTriangle;

    public TriangleTests() {

        this.testTriangle = new Triangle(
                new Point(0, 0),
                new Point(0, 1),
                new Point(1, 0));

        this.testNegativeTriangle = new Triangle(
                new Point(-2, -4),
                new Point(0, -1),
                new Point(-1, 0));
    }

    @Test
    public void canCreatePoint() {
        int x = 0;
        int y = 1;
        Point point = new Point(x, y);
        assertNotNull(point);
    }

    @Test
    public void canCreatePointNegative() {
        int x = -2;
        int y = -4;
        Point point = new Point(x, y);
        assertNotNull(point);
    }

    @Test
    public void canCreateTriangle() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 1);
        Point p3 = new Point(1, 0);
        Triangle triangle = new Triangle(p1, p2, p3);
        assertNotNull(triangle);
    }

    @Test
    public void canCreateTriangleNegative() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, -1);
        Point p3 = new Point(-1, 0);
        Triangle triangle = new Triangle(p1, p2, p3);
        assertNotNull(triangle);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cantCreateOnePlacePointTriangle() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 0);
        Point p3 = new Point(0, 0);
        Triangle triangle = new Triangle(p1, p2, p3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void cantCreateOneLinePointTriangle() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(0, 1);
        Point p3 = new Point(0, 2);
        Triangle triangle = new Triangle(p1, p2, p3);
    }

    @Test
    public void canGetSide() {
        double expectedSide = 1;
        double actualSide = testTriangle.getSideABLength();
        assertEquals(expectedSide, actualSide, delta);
    }

    @Test
    public void canGetSideNegative() {
        double expectedSide = 3.6;
        double actualSide = testNegativeTriangle.getSideABLength();
        assertEquals(expectedSide, actualSide, delta);
    }

    @Test
    public void canCalculatePerimeter() {
        double perimeter = testTriangle.getPerimeter();
        assertEquals(3.41, perimeter, delta);
    }

    @Test
    public void canCalculatePerimeterNegative() {
        double perimeter = testNegativeTriangle.getPerimeter();
        assertEquals(9.14, perimeter, delta);
    }

    @Test
    public void canCalculateArea() {
        double area = testTriangle.getArea();
        assertEquals(0.5, area, delta);
    }

    @Test
    public void canCalculateAreaNegative() {
        double area = testNegativeTriangle.getArea();
        assertEquals(2.5, area, delta);
    }

    @Test
    public void canCalculateAngle() {
        ArrayList<String> expectedAngles = new ArrayList<String>(
                Arrays.asList("90", "45", "45"));

        List angles = testTriangle.calculateAngles();
        List<String> actualAngles = new ArrayList<String>();
        for (Object o : angles) {
            actualAngles.add(Formatter.format(o));
        }
        assertEquals(expectedAngles, actualAngles);
    }

    @Test
    public void canCalculateAngleNegative() {
        ArrayList<String> expectedAngles = new ArrayList<String>(
                Arrays.asList("19.65", "101.31", "59.04"));

        List angles = testNegativeTriangle.calculateAngles();
        List<String> actualAngles = new ArrayList<String>();
        for (Object o : angles) {
            actualAngles.add(Formatter.format(o));
        }
        assertEquals(expectedAngles, actualAngles);
    }

    @Test
    public void checkToString() {
        String expectedString = "A(0.0,0.0), B(0.0,1.0), C(1.0,0.0)";

        String actualString = testTriangle.toString();
        assertEquals(expectedString, actualString);
    }

    @Test
    public void checkToStringNegative() {
        String expectedString = "A(-2.0,-4.0), B(0.0,-1.0), C(-1.0,0.0)";

        String actualString = testNegativeTriangle.toString();
        assertEquals(expectedString, actualString);
    }

    @Test
    public void canGetBisectorA() {
        String expectedBisector = "0.71";

        double actualBisector = testTriangle.getBisectorA();
        assertEquals(expectedBisector, Formatter.format(actualBisector));
    }

    @Test
    public void canGetBisectorB() {
        String expectedBisector = "1.08";

        double actualBisector = testTriangle.getBisectorB();
        assertEquals(expectedBisector, Formatter.format(actualBisector));
    }

    @Test
    public void canGetBisectorC() {
        String expectedBisector = "1.08";

        double actualBisector = testTriangle.getBisectorC();
        assertEquals(expectedBisector, Formatter.format(actualBisector));
    }

    @Test
    public void canGetBisectorANegative() {
        String expectedBisector = "3.79";

        double actualBisector = testNegativeTriangle.getBisectorA();
        assertEquals(expectedBisector, Formatter.format(actualBisector));
    }

    @Test
    public void canGetBisectorBNegative() {
        String expectedBisector = "1.29";

        double actualBisector = testNegativeTriangle.getBisectorB();
        assertEquals(expectedBisector, Formatter.format(actualBisector));
    }

    @Test
    public void canGetBisectorCNegative() {
        String expectedBisector = "1.83";

        double actualBisector = testNegativeTriangle.getBisectorC();
        assertEquals(expectedBisector, Formatter.format(actualBisector));
    }

    @Test
    public void canGetMedianA() {
        String expectedMedian = "0.71";

        double actualMedian = testTriangle.getMedianA();
        assertEquals(expectedMedian, Formatter.format(actualMedian));
    }

    @Test
    public void canGetMedianB() {
        String expectedMedian = "1.12";

        double actualMedian = testTriangle.getMedianB();
        assertEquals(expectedMedian, Formatter.format(actualMedian));
    }

    @Test
    public void canGetMedianC() {
        String expectedMedian = "1.12";

        double actualMedian = testTriangle.getMedianC();
        assertEquals(expectedMedian, Formatter.format(actualMedian));
    }

    @Test
    public void canGetMedianANegative() {
        String expectedMedian = "3.81";

        double actualMedian = testNegativeTriangle.getMedianA();
        assertEquals(expectedMedian, Formatter.format(actualMedian));
    }

    @Test
    public void canGetMedianBNegative() {
        String expectedMedian = "1.8";

        double actualMedian = testNegativeTriangle.getMedianB();
        assertEquals(expectedMedian, Formatter.format(actualMedian));
    }

    @Test
    public void canGetMedianCNegative() {
        String expectedMedian = "2.5";

        double actualMedian = testNegativeTriangle.getMedianC();
        assertEquals(expectedMedian, Formatter.format(actualMedian));
    }

    @Test
    public void canGetHeightA() {
        String expectedHeight = "0.71";

        double actualHeight = testTriangle.getHeightA();
        assertEquals(expectedHeight, Formatter.format(actualHeight));
    }

    @Test
    public void canGetHeightB() {
        String expectedHeight = "1";

        double actualHeight = testTriangle.getHeightB();
        assertEquals(expectedHeight, Formatter.format(actualHeight));
    }

    @Test
    public void canGetHeightC() {
        String expectedHeight = "1";

        double actualHeight = testTriangle.getHeightC();
        assertEquals(expectedHeight, Formatter.format(actualHeight));
    }

    @Test
    public void canGetHeightANegative() {
        String expectedHeight = "3.54";

        double actualHeight = testNegativeTriangle.getHeightA();
        assertEquals(expectedHeight, Formatter.format(actualHeight));
    }

    @Test
    public void canGetHeightBNegative() {
        String expectedHeight = "1.21";

        double actualHeight = testNegativeTriangle.getHeightB();
        assertEquals(expectedHeight, Formatter.format(actualHeight));
    }

    @Test
    public void canGetHeightCNegative() {
        String expectedHeight = "1.39";

        double actualHeight = testNegativeTriangle.getHeightC();
        assertEquals(expectedHeight, Formatter.format(actualHeight));
    }

    @Test
    public void canGetMedianIntersection() {
        String[] expectedCoordinates = {"0.33", "0.33"};

        Point centroid = testTriangle.getMedianIntersection();

        String[] actualCoordinates = {
                Formatter.format(centroid.getX()),
                Formatter.format(centroid.getY())};
        assertArrayEquals(expectedCoordinates, actualCoordinates);
    }

    @Test
    public void canGetMedianIntersectionNegative() {
        String[] expectedCoordinates = {"-1", "-1.67"};

        Point centroid = testNegativeTriangle.getMedianIntersection();

        String[] actualCoordinates = {
                Formatter.format(centroid.getX()),
                Formatter.format(centroid.getY())};
        assertArrayEquals(expectedCoordinates, actualCoordinates);
    }

}
