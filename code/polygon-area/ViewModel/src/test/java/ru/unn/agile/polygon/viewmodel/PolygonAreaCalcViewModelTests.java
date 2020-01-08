package ru.unn.agile.polygon.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.polygon.model.Point;

import java.util.List;

import static org.junit.Assert.*;
import static ru.unn.agile.polygon.viewmodel.LogMessages.*;

public class PolygonAreaCalcViewModelTests {
    private final double eps = 1.0E-10;

    private PolygonAreaCalcViewModel viewModel;

    protected void setTestViewModel(final PolygonAreaCalcViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        viewModel = new PolygonAreaCalcViewModel(new FakeLogger());
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void isAddingValidInput() {
        addPoint("-261.55", "2.645");
        assertTrue(new Point(-261.55, 2.645).getX() == viewModel.getPointList().get(0).getX()
                && new Point(-261.55, 2.645).getY() == viewModel.getPointList().get(0).getY());
    }

    @Test(expected = IllegalArgumentException.class)
    public void isNotAddedInvalidInput() {
        addPoint("-26vrt1.55", "2..645");
    }

    @Test(expected = IllegalArgumentException.class)
    public void isNotAddedEmptyInput() {
        addPoint("", "");
    }

    @Test
    public void cantCalcAreaWhenPointListIsEmpty() {
        viewModel.calculateArea();
        assertNull(viewModel.getResult());
    }

    @Test
    public void canCalcAreaOfTriangle() {
        calculateTriangleArea();
        assertEquals(0.5, Double.parseDouble(viewModel.getResult()), eps);
    }

    @Test
    public void isAddPointButtonDisabledForEmptyInput() {
        setCoordinates("", "");
        assertTrue(viewModel.isAddingNewPointDisabled());
    }

    @Test
    public void isAddPointButtonDisabledForInvalidXInput() {
        setCoordinates("256..1", "23");
        assertTrue(viewModel.isAddingNewPointDisabled());
    }

    @Test
    public void isAddPointButtonDisabledForInvalidYInput() {
        setCoordinates("23", "--235...5");
        assertTrue(viewModel.isAddingNewPointDisabled());
    }

    @Test
    public void isFormInputsEmptyAfterAddingNewPoint() {
        addPoint("-36.516", "-62.52");
        assertTrue(viewModel.xProperty().get().isEmpty()
                && viewModel.yProperty().get().isEmpty()
        );
    }

    @Test
    public void canNotCalculateAreaForLessThanThreePointsPolygon() {
        addPoint("1", "0");
        addPoint("0", "0");
        viewModel.calculateArea();
        assertEquals("A polygon must have at least three vertices", viewModel.getResult());
    }

    @Test
    public void canNotCalculateAreaForSelfIntersectingPolygon() {
        calculateAreaForSelfIntersectingPolygon();
        assertEquals("Sides of polygon must not intersect", viewModel.getResult());
    }

    @Test(expected = IllegalArgumentException.class)
    public void canNotInstantiateViewModelWithNullLogger() {
        viewModel = new PolygonAreaCalcViewModel(null);
    }

    @Test
    public void logAreEmptyOnInit() {
        assertTrue(getLog().isEmpty());
    }

    @Test
    public void logContainsCorrespondingMessageAfterNewPointAdded() {
        Point p = new Point(1, 0);
        addPoint(p);
        String pointAddedMessage = String.format(POINT_ADDED, p.getX(), p.getY());
        assertTrue(!getLog().isEmpty() && getLog().get(0).contains(pointAddedMessage));
    }

    @Test
    public void logContainsCorrespondingMessageAfterCalculationStarted() {
        calculateTriangleArea();
        assertTrue(!getLog().isEmpty() && getLog().get(3).contains(CALCULATION_STARTED));
    }

    @Test
    public void logContainsCorrespondingMessageAfterSuccessfulCalculation() {
        calculateTriangleArea();
        String message = getLog().get(4);
        assertTrue(message.contains(CALCULATION_COMPLETED));
    }

    @Test
    public void logContainsCorrespondingMessageAfterCalculationFailed() {
        calculateAreaForSelfIntersectingPolygon();
        String message = getLog().get(5);
        assertTrue(message.contains(CALCULATION_FAILED));
    }

    private void addPoint(final Point p) {
        setCoordinates(String.valueOf(p.getX()), String.valueOf(p.getY()));
        viewModel.addPoint();
    }

    private void addPoint(final String x, final String y) {
        setCoordinates(x, y);
        viewModel.addPoint();
    }

    private void calculateTriangleArea() {
        viewModel.clearPointList();
        addPoint("1", "0");
        addPoint("0", "0");
        addPoint("0", "1");
        viewModel.calculateArea();
    }

    private void calculateAreaForSelfIntersectingPolygon() {
        viewModel.clearPointList();
        addPoint("1", "1");
        addPoint("0", "0");
        addPoint("1", "0");
        addPoint("0", "1");
        viewModel.calculateArea();
    }

    private void setCoordinates(final String x, final String y) {
        viewModel.xProperty().set(x);
        viewModel.yProperty().set(y);
    }

    private List<String> getLog() {
        return viewModel.getLog();
    }
}
