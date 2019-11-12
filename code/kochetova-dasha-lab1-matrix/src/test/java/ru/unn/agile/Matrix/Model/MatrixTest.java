package ru.unn.agile.Matrix.Model;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import ru.unn.agile.Matrix.model.Matrix;

import static org.junit.Assert.*;


public class MatrixTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private double delta = 0.001;

    @Test
    public void canCreateMatrix() {
        var matrix = new Matrix(3);
        assertNotNull(matrix);
    }

    @Test
    public void cantCreateMatrixWithZeroSize() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Trying to create a matrix with zero size");

        var matrix = new Matrix(0);
    }

    @Test
    public void cantCreateMatrixWithNegativeSize() {
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Trying to create a matrix with negative zero");

        var matrix = new Matrix(-3);
    }

    @Test
    public void canGetSizeFromMatrix() {
        int matrixSize = 3;
        var matrix = new Matrix(matrixSize);
        assertEquals(matrixSize, matrix.getSize());
    }

    @Test
    public void checkGetSizeWithNewInit() {
        int firstSize = 4;
        int secondSize = 12;

        var matrix = new Matrix(firstSize);
        assertEquals(firstSize, matrix.getSize());
        matrix = new Matrix(secondSize);
        assertEquals(secondSize, matrix.getSize());
    }

    @Test
    public void canInitMatrixByArray() {
        int matrixSize = 2;
        var matrix = new Matrix(matrixSize);
        double[] array = new double[] {2.5, 1.6, 4.3, 7.8};
        matrix.initByArray(array);
        for (int i = 0; i < matrixSize; ++i) {
            for (int j = 0; j < matrixSize; ++j) {
                double expected = array[i * matrixSize + j];
                double actual = matrix.getValue(i, j);
                assertEquals(expected, actual, delta);
            }
        }
    }

    @Test
    public void cantInitMatrixByArrayWithMoreValues() {
        thrown.expect(IndexOutOfBoundsException.class);
        thrown.expectMessage("Array size was more then matrix size");

        var matrix = new Matrix(2);
        double[] array = new double[] {2.5, 1.6, 4.3, 7.8, 3.6};
        matrix.initByArray(array);
    }

    @Test
    public void canInitMatrixByArrayWithLessValues() {
        int matrixSize = 2;
        var matrix = new Matrix(matrixSize);
        double[] array = new double[] {2.5, 1.6, 4.3};
        matrix.initByArray(array);

        assertEquals(array[0], matrix.getValue(0, 0), delta);
        assertEquals(array[1], matrix.getValue(0, 1), delta);
        assertEquals(array[2], matrix.getValue(1, 0), delta);
        assertEquals(0, matrix.getValue(1, 1), delta);
    }

    @Test
    public void canGetValueFromMatrix() {
        var matrix = new Matrix(3);
        assertEquals(0, matrix.getValue(2, 2), delta);

        matrix.initByArray(new double[] {1, 1, 1, 0.5});
        assertEquals(0.5, matrix.getValue(1, 0), delta);
    }

    /*@Test
    public void addMatricesWithSameSize() {
        var matrix = new Matrix(4);
    }*/
}
