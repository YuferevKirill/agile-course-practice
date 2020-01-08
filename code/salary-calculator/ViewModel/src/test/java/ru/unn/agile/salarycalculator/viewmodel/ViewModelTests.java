package ru.unn.agile.salarycalculator.viewmodel;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.salarycalculator.viewmodel.ViewModel.Status;

import static org.junit.Assert.*;

public class ViewModelTests {
    private final double delta = 0.01;
    private ViewModel viewModel;

    public void setExternalViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUpEmptyExample() {
        ILogger fakeLogger = new FakeTextLogger();
        viewModel = new ViewModel(fakeLogger);
    }

    @After
    public void deleteExample() {
        viewModel = null;
    }

    @Test
    public void checkStatusInBegin() {
        ILogger logger = new FakeTextLogger();
        viewModel = new ViewModel(logger);
        assertEquals(Status.COUNT_WAITING, viewModel.getStatus());
    }


    @Test
    public void checkStatusWhenReadyCalculate() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("145");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("2014");
        viewModel.checkCountFields();

        assertEquals(Status.READY_CALCULATE, viewModel.getStatus());
    }

    @Test
    public void checkStatusCash() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("145");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("2014");

        viewModel.checkCountFields();
        viewModel.calculateSalary();

        assertEquals(Status.CASH, viewModel.getStatus());
    }

    @Test
    public void isLengthCharactersSalaryNotCorrect() {
        viewModel.setWorkedHours("145");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("2014");

        viewModel.setSalary("1000000000000000000000000");

        viewModel.checkCountFields();

        assertEquals(Status.BAD_SALARY_FORMAT_NUMBERS, viewModel.getStatus());
    }

    @Test
    public void checkStatusWhenOneOfCountFieldEmpty() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("145");
        viewModel.setCountMonth("5");

        viewModel.setCountYear("");

        viewModel.checkCountFields();

        assertEquals(Status.BAD_YEAR_FORMAT, viewModel.getStatus());
    }

    @Test
    public void checkStatusWhenCountInputWithChar() {
        viewModel.setSalary("10000");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("2014");

        viewModel.setWorkedHours("a");

        viewModel.checkCountFields();

        assertEquals(Status.BAD_WORKED_HOURS_FORMAT, viewModel.getStatus());
    }

    @Test
    public void checkStatusWhenCountInputWithIncorrectMonth() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("145");
        viewModel.setCountYear("2014");

        viewModel.setCountMonth("50");

        viewModel.checkCountFields();

        assertEquals(Status.BAD_MONTH_FORMAT, viewModel.getStatus());
    }

    @Test
    public void checkStatusWhenCountInputWithIncorrectYear() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("145");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("19191");

        viewModel.checkCountFields();

        assertEquals(Status.BAD_YEAR_FORMAT, viewModel.getStatus());
    }

    @Test
    public void checkResultWithNormalParameters() {
        viewModel.setSalary("18000");
        viewModel.setWorkedHours("185");
        viewModel.setCountMonth("10");
        viewModel.setCountYear("2019");

        viewModel.checkCountFields();

        viewModel.calculateSalary();

        assertEquals("15830.2", viewModel.getResult());
    }

    @Test
    public void checkResultWithOvertime() {
        viewModel.setSalary("18000");
        viewModel.setCountMonth("10");
        viewModel.setCountYear("2019");

        viewModel.setWorkedHours("200");

        viewModel.checkCountFields();

        viewModel.calculateSalary();

        assertEquals("18383.5", viewModel.getResult());
    }

    @Test
    public void checkResultWithLessTime() {
        viewModel.setSalary("18000");
        viewModel.setCountMonth("10");
        viewModel.setCountYear("2019");
        viewModel.setWorkedHours("160");
        viewModel.checkCountFields();

        viewModel.calculateSalary();

        assertEquals("13617.4", viewModel.getResult());
    }


    @Test
    public void checkResultWithNegativeWorkedHours() {
        viewModel.setSalary("10000");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("2014");


        viewModel.setWorkedHours("-144");

        viewModel.checkCountFields();

        assertEquals(Status.BAD_WORKED_HOURS_FORMAT, viewModel.getStatus());
    }

    @Test
    public void checkResultWithNegativeSalary() {
        viewModel.setWorkedHours("145");
        viewModel.setCountMonth("5");
        viewModel.setCountYear("2014");

        viewModel.setSalary("-10000");

        viewModel.checkCountFields();

        assertEquals(Status.BAD_SALARY_FORMAT_SIGN, viewModel.getStatus());
    }

    @Test
    public void checkStatusAndButtonWhenIncorrectDate() {
        viewModel.setSalary("10000");
        viewModel.setWorkedHours("145");
        viewModel.setCountYear("2014");

        viewModel.setCountMonth("35");
        viewModel.checkCountFields();

        assertEquals(Status.BAD_MONTH_FORMAT, viewModel.getStatus());
    }
}
