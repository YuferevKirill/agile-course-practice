package ru.unn.agile.depositcalculator.viewmodel;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.unn.agile.depositcalculator.model.CapitalizationPeriod;
import ru.unn.agile.depositcalculator.model.DepositTimeType;

public class ViewModelTest {

    private ViewModel viewModel;

    protected void setViewModel(final ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Before
    public void setUp() {
        if (viewModel == null) {
            viewModel = new ViewModel(new FakeLogger());
        }
    }

    @After
    public void tearDown() {
        viewModel = null;
    }

    @Test
    public void canSetDefaultDepositPeriod() {
        Assert.assertEquals(DepositTimeType.DAY, viewModel.periodProperty().get());
    }

    @Test
    public void canSetDefaultCapitPeriod() {
        Assert.assertEquals(CapitalizationPeriod.MONTH, viewModel.capitalizationProperty().get());
    }

    @Test
    public void canSetDefaultStartSum() {
        Assert.assertEquals("1000", viewModel.getStartSumProperty());
    }

    @Test
    public void canSetDefaultPercent() {
        Assert.assertEquals("8", viewModel.getPercentProperty());
    }

    @Test
    public void canCalculate() {
        viewModel.calculate();
        Assert.assertEquals("1000.22", viewModel.getResultProperty());
    }

    @Test
    public void canValidateEmptyPercentage() {
        viewModel.setPercentProperty("");
        viewModel.calculate();
        Assert.assertEquals(ViewModel.LogMessages.VALIDATION_ERROR, viewModel.getResultProperty());
    }

    @Test
    public void canValidateEmptyStartSum() {
        viewModel.setStartSumProperty("");
        viewModel.calculate();
        Assert.assertEquals(ViewModel.LogMessages.VALIDATION_ERROR, viewModel.getResultProperty());
    }

    @Test
    public void canValidateNegativeStartSum() {
        viewModel.setStartSumProperty("-1");
        viewModel.calculate();
        Assert.assertEquals(ViewModel.LogMessages.VALIDATION_ERROR, viewModel.getResultProperty());
    }

    @Test
    public void canValidateNegativePercentage() {
        viewModel.setPercentProperty("-1");
        viewModel.calculate();
        Assert.assertEquals(ViewModel.LogMessages.VALIDATION_ERROR, viewModel.getResultProperty());
    }

    @Test
    public void canValidateNotNumberSymbolsPercentage() {
        viewModel.setPercentProperty("asdfasdf100-adsf.0");
        viewModel.calculate();
        Assert.assertEquals(ViewModel.LogMessages.VALIDATION_ERROR, viewModel.getResultProperty());
    }

    @Test
    public void canValidateNotNumberSymbolsStartSum() {
        viewModel.setStartSumProperty("asdfasdf100-adsf.0");
        viewModel.calculate();
        Assert.assertEquals(ViewModel.LogMessages.VALIDATION_ERROR, viewModel.getResultProperty());
    }

    @Test
    public void canSetLoggerToViewModel() {
        var viewModel = new ViewModel();
        viewModel.setLogger(new FakeLogger());
        Assert.assertNotNull(viewModel.getLogs());
    }

    @Test (expected = IllegalArgumentException.class)
    public void cannotCreateViewModelIfLoggerIsNull() {
        new ViewModel(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void cannotSetNullLoggerToViewModel() {
        viewModel.setLogger(null);
    }

    @Test
    public void loggerInitiallyContainsDefaultValues() {
        var logs = viewModel.getLogs();
        Assert.assertFalse(logs.isEmpty());
    }

    @Test
    public void canLogCapitalizationUpdatedValue() {
        viewModel.setCapitalization(CapitalizationPeriod.YEAR);
        var logs = viewModel.getLogs();
        var lastLog = logs.get(logs.size() - 1);
        Assert.assertTrue(lastLog.contains(ViewModel.LogMessages.CAPITALIZATION_UPDATED_LOG_MSG));
    }

    @Test
    public void canLogPeriodUpdatedValue() {
        viewModel.setPeriod(DepositTimeType.DAY);
        var logs = viewModel.getLogs();
        var lastLog = logs.get(logs.size() - 1);
        Assert.assertTrue(lastLog.contains(ViewModel.LogMessages.PERIOD_UPDATED_LOG_MSG));
    }

    @Test
    public void canLogStartSumValueChanged() {
        viewModel.setStartSumProperty("10000");
        viewModel.onSumFocusChanged();
        var logs = viewModel.getLogs();
        var lastLog = logs.get(logs.size() - 1);
        Assert.assertTrue(lastLog.contains(ViewModel.LogMessages.START_SUM_UPDATED_LOG_MSG));
    }

    @Test
    public void canLogPercentageValueChanged() {
        viewModel.setPercentProperty("21");
        viewModel.onPercentageFocusChanged();
        var logs = viewModel.getLogs();
        var lastLog = logs.get(logs.size() - 1);
        Assert.assertTrue(lastLog.contains(ViewModel.LogMessages.PERCENTAGE_UPDATED_LOG_MSG));
    }

    @Test
    public void canLogManyActions() {
        viewModel.setPercentProperty("21");
        viewModel.onPercentageFocusChanged();

        viewModel.setCapitalization(CapitalizationPeriod.YEAR);

        var logs = viewModel.getLogs();
        var logsCount = logs.size();
        Assert.assertTrue(logsCount > 1);
    }

    @Test
    public void canLogCalculationCompletion() {
        viewModel.setStartSumProperty("10000");
        viewModel.onSumFocusChanged();

        viewModel.setPercentProperty("21");
        viewModel.onPercentageFocusChanged();

        viewModel.setPeriod(DepositTimeType.DAY);

        viewModel.setCapitalization(CapitalizationPeriod.YEAR);

        viewModel.calculate();

        var logs = viewModel.getLogs();
        var lastLog = logs.get(logs.size() - 1);
        Assert.assertTrue(lastLog.contains(ViewModel.LogMessages.CALCULATION_COMPLETED_LOG_MSG));
    }
}
