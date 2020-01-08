package ru.unn.agile.Matrix.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class ViewModelTests {

    private static final double DELTA = 1e-3;
    private ViewModel viewModel;

    @Before
    public void setUp() {
        ILogger fakeLogger = new FakeTextLogger();
        viewModel = new ViewModel(fakeLogger);
    }

    public void setViewModel(final ViewModel matrixViewModel) {
        this.viewModel = matrixViewModel;
    }

    @Test
    public void canSetDefaultValuesToFirstMatrix() {
        assertEquals("", viewModel.getFirstMatrixProperties()[0].get());
    }

    @Test
    public void canSetDefaultValuesToSecondMatrix() {
        assertEquals("", viewModel.getSecondMatrixProperties()[0].get());
    }

    @Test (expected = NumberFormatException.class)
    public void canNotTransposeMatrixIfNotFullFilled() {
        setWrongInputData();
        viewModel.transposeFirstMatrix();
    }

    @Test
    public void canTransposeFirstMatrixIfFilledWithCorrectValues() {
        setCorrectInputData();
        viewModel.transposeFirstMatrix();
        assertEquals(4.0, Double.parseDouble(viewModel.getFirstMatrixProperties()[1].get()), DELTA);
    }


    @Test
    public void canTransposeSecondMatrixIfFilledWithCorrectValues() {
        setCorrectInputData();
        viewModel.transposeSecondMatrix();
        assertEquals(4.0, Double.parseDouble(viewModel.getSecondMatrixProperties()[1].get()),
                DELTA);
    }

    @Test (expected = NumberFormatException.class)
    public void canNotTransposeMatrixWithNotNumericValues() {
        setWrongInputData();
        viewModel.transposeSecondMatrix();
    }

    @Test
    public void canCompareMatrices() {
        setCorrectInputData();
        viewModel.compareMatrices();
        assertTrue(Boolean.parseBoolean(viewModel.getResult()));
    }
    @Test (expected = NumberFormatException.class)
    public void canCompareOnlyCorrectFilledMatrices() {
        setWrongInputData();
        viewModel.compareMatrices();
    }
    @Test (expected = NumberFormatException.class)
    public void canNotSumMatricesIfNotFilledWithCorrectArgs() {
        setWrongInputData();
        viewModel.sumMatrices();
    }

    @Test
    public void logIsEmptyAtTheBeginningOfWork() {
        List<String> log = viewModel.getLog();

        assertTrue(log.isEmpty());
    }

    @Test
    public void canSetLoggerInMatrixViewModel() {
        viewModel.setLogger(new FakeTextLogger());
        assertNotNull(viewModel.getLogger());
    }

    @Test
    public void canLogStartOfComparingMatrices() {
        setCorrectInputData();
        viewModel.compareMatrices();
        var logMessages = viewModel.getLog();
        var lastLogMessages = logMessages.get(logMessages.size() - 2);
        assertTrue(lastLogMessages.contains(ViewModel.LogMessages.START));
    }

    @Test
    public void canLogFinishOfComparingMatrices() {
        setCorrectInputData();
        viewModel.compareMatrices();
        var logMessages = viewModel.getLog();
        var lastLogMessages = logMessages.get(logMessages.size() - 1);
        assertTrue(lastLogMessages.contains(ViewModel.LogMessages.FINISH));
    }

    @Test
    public void canLogStartOfTransposeMatrix() {
        setCorrectInputData();
        viewModel.transposeFirstMatrix();
        var logMessages = viewModel.getLog();
        var encodedMessage = logMessages.get(logMessages.size() - 2);
        assertTrue(encodedMessage.contains(ViewModel.LogMessages.START));
    }

    @Test
    public void canLogFinishOfTransposeMatrix() {
        setCorrectInputData();
        viewModel.transposeFirstMatrix();
        var logMessages = viewModel.getLog();
        var lastLogMessages = logMessages.get(logMessages.size() - 1);
        assertTrue(lastLogMessages.contains(ViewModel.LogMessages.FINISH));
    }
    private void setCorrectInputData() {
        viewModel.setFirstMatrixProperties(new StringProperty[] {
                new SimpleStringProperty("1"),
                new SimpleStringProperty("2"),
                new SimpleStringProperty("3"),
                new SimpleStringProperty("4"),
                new SimpleStringProperty("5"),
                new SimpleStringProperty("6"),
                new SimpleStringProperty("7"),
                new SimpleStringProperty("8"),
                new SimpleStringProperty("9"),
        });
        viewModel.setSecondMatrixProperties(viewModel.getFirstMatrixProperties());
    }

    private void setWrongInputData() {
        viewModel.setFirstMatrixProperties(new StringProperty[] {
                new SimpleStringProperty("3"),
                new SimpleStringProperty("trash"),
                new SimpleStringProperty("1"),
                new SimpleStringProperty("f"),
                new SimpleStringProperty("fad"),
                new SimpleStringProperty(";;;"),
                new SimpleStringProperty("/r/n"),
                new SimpleStringProperty("1e"),
                new SimpleStringProperty("9="),
        });
        viewModel.setSecondMatrixProperties(viewModel.getFirstMatrixProperties());
    }
}
