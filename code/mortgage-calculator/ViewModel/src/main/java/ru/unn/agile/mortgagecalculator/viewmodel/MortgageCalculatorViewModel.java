package ru.unn.agile.mortgagecalculator.viewmodel;

import javafx.beans.property.*;
import ru.unn.agile.mortgagecalculator.model.calculator.MortgageCalculator;
import ru.unn.agile.mortgagecalculator.model.calculator.MortgageWithAnnuityPaymentsCalculator;
import ru.unn.agile.mortgagecalculator.model.calculator.MortgageWithDifferentialPaymentsCalculator;
import ru.unn.agile.mortgagecalculator.model.parameters.MortgageParameters;
import ru.unn.agile.mortgagecalculator.model.parameters.PeriodType;
import ru.unn.agile.mortgagecalculator.model.parameters.commission.Commission;
import ru.unn.agile.mortgagecalculator.model.parameters.commission.FixedCommission;
import ru.unn.agile.mortgagecalculator.model.parameters.commission.PercentCommission;
import ru.unn.agile.mortgagecalculator.model.parameters.monthlycommission.FixedMonthlyCommission;
import ru.unn.agile.mortgagecalculator.model.parameters.monthlycommission.MonthlyCommission;
import ru.unn.agile.mortgagecalculator.model.parameters.monthlycommission.PercentAmountMonthlyCommission;
import ru.unn.agile.mortgagecalculator.model.report.MortgageMonthReport;
import ru.unn.agile.mortgagecalculator.model.report.MortgageReport;

import java.util.Collections;
import java.util.List;


public class MortgageCalculatorViewModel {
    private StringProperty apartmentPrice = new SimpleStringProperty();
    private StringProperty firstPayment = new SimpleStringProperty();
    private StringProperty loanPeriod = new SimpleStringProperty();
    private StringProperty loanPeriodType = new SimpleStringProperty();
    private StringProperty interestRate = new SimpleStringProperty();
    private StringProperty oneTimeCommissions = new SimpleStringProperty();
    private StringProperty oneTimeCommissionsType = new SimpleStringProperty();
    private StringProperty monthlyCommissions = new SimpleStringProperty();
    private StringProperty monthlyCommissionsType = new SimpleStringProperty();
    private StringProperty typeOfPayment = new SimpleStringProperty();
    private StringProperty result = new SimpleStringProperty();
    private final StringProperty logs = new SimpleStringProperty();

    private MortgageCalculatorILogger logger;

    public void setLogger(final MortgageCalculatorILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can not be null");
        }
        this.logger = logger;
    }

    public MortgageCalculatorViewModel() {
        init();
    }

    public MortgageCalculatorViewModel(final MortgageCalculatorILogger logger) {
        setLogger(logger);
        init();
    }

    private void init() {
        apartmentPrice.set("");
        firstPayment.set("");
        loanPeriod.set("");
        loanPeriodType.set("");
        interestRate.set("");
        oneTimeCommissions.set("");
        oneTimeCommissionsType.set("");
        monthlyCommissions.set("");
        monthlyCommissionsType.set("");
        typeOfPayment.set("");
        result.set("");

        apartmentPrice.addListener((observable, oldValue, newValue) -> {
            if (checkInput()) {
                result.set("");
            } else {
                result.set("Incorrect input");
            }
        });

        firstPayment.addListener((observable, oldValue, newValue) -> {
            if (checkInput()) {
                result.set("");
            } else {
                result.set("Incorrect input");
            }
        });

        loanPeriod.addListener((observable, oldValue, newValue) -> {
            if (checkInput()) {
                result.set("");
            } else {
                result.set("Incorrect input");
            }
        });

        interestRate.addListener((observable, oldValue, newValue) -> {
            if (checkInput()) {
                result.set("");
            } else {
                result.set("Incorrect input");
            }
        });

        oneTimeCommissions.addListener((observable, oldValue, newValue) -> {
            if (checkInput()) {
                result.set("");
            } else {
                result.set("Incorrect input");
            }
        });

        monthlyCommissions.addListener((observable, oldValue, newValue) -> {
            if (checkInput()) {
                result.set("");
            } else {
                result.set("Incorrect input");
            }
        });

        loanPeriodType.addListener((observable, oldValue, newValue) -> {
            StringBuilder message = new StringBuilder(LogMessages.LOAN_PERIOD_TYPE_WAS_CHANGED);
            message.append(newValue).append(".");
            logger.log(message.toString());
            updateLogs();
            onTypeChange();
        });

        typeOfPayment.addListener((observable, oldValue, newValue) -> {
            onTypeChange();
        });

        monthlyCommissionsType.addListener((observable, oldValue, newValue) -> {
            StringBuilder message =
                    new StringBuilder(LogMessages.MONTHLY_COMMISSIONS_TYPE_WAS_CHANGED);
            message.append(newValue).append(".");
            logger.log(message.toString());
            updateLogs();
            onTypeChange();
        });

        oneTimeCommissionsType.addListener((observable, oldValue, newValue) -> {
            StringBuilder message =
                    new StringBuilder(LogMessages.ONE_TIME_COMMISSIONS_TYPE_WAS_CHANGED);
            message.append(newValue).append(".");
            logger.log(message.toString());
            updateLogs();
            onTypeChange();
        });

    }

    private boolean checkDoubleInput(final String newValue) {
        if (newValue.equals("")) {
            return false;
        }
        try {
            double val = Double.parseDouble(newValue);
            if (val < 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean checkIntInput(final String newValue) {
        if (newValue.equals("")) {
            return false;
        }
        try {
            int val = Integer.parseInt(newValue);
            if (val < 0) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void onTypeChange() {
        result.set("");
    }

    public String getApartmentPrice() {
        return apartmentPrice.get();
    }

    public StringProperty apartmentPriceProperty() {
        return apartmentPrice;
    }

    public String getFirstPayment() {
        return firstPayment.get();
    }

    public StringProperty firstPaymentProperty() {
        return firstPayment;
    }

    public String getLoanPeriod() {
        return loanPeriod.get();
    }

    public StringProperty loanPeriodProperty() {
        return loanPeriod;
    }

    public String getLoanPeriodType() {
        return loanPeriodType.get();
    }

    public StringProperty loanPeriodTypeProperty() {
        return loanPeriodType;
    }

    public String getInterestRate() {
        return interestRate.get();
    }

    public StringProperty interestRateProperty() {
        return interestRate;
    }

    public String getOneTimeCommissions() {
        return oneTimeCommissions.get();
    }

    public StringProperty oneTimeCommissionsProperty() {
        return oneTimeCommissions;
    }

    public String getOneTimeCommissionsType() {
        return oneTimeCommissionsType.get();
    }

    public StringProperty oneTimeCommissionsTypeProperty() {
        return oneTimeCommissionsType;
    }

    public String getMonthlyCommissions() {
        return monthlyCommissions.get();
    }

    public StringProperty monthlyCommissionsProperty() {
        return monthlyCommissions;
    }

    public String getMonthlyCommissionsType() {
        return monthlyCommissionsType.get();
    }

    public StringProperty monthlyCommissionsTypeProperty() {
        return monthlyCommissionsType;
    }

    public String getTypeOfPayment() {
        return typeOfPayment.get();
    }

    public StringProperty typeOfPaymentProperty() {
        return typeOfPayment;
    }

    public String getResult() {
        return result.get();
    }

    public StringProperty resultProperty() {
        return result;
    }

    private boolean checkInput() {
        if (!checkDoubleInput(apartmentPrice.get())) {
            return false;
        }
        if (!checkDoubleInput(interestRate.get())) {
            return false;
        }
        if (!checkIntInput(loanPeriod.get())) {
            return false;
        }
        if (loanPeriodType.get().equals("")) {
            return false;
        }
        if (!checkDoubleInput(firstPayment.get())) {
            return false;
        }
        if (!checkDoubleInput(oneTimeCommissions.get())) {
            return false;
        }
        if (!checkDoubleInput(monthlyCommissions.get())) {
            return false;
        }
        if (monthlyCommissionsType.get().equals("")) {
            return false;
        }
        if (oneTimeCommissionsType.get().equals("")) {
            return false;
        }
        return !typeOfPayment.get().equals("");
    }

    private MortgageParameters createMortgageParametersCalculator() {
        MortgageParameters mortgageParameters = null;

        double amount = Double.parseDouble(apartmentPrice.get());
        double percent = Double.parseDouble(interestRate.get());
        int period = Integer.parseInt(loanPeriod.get());

        PeriodType periodType = loanPeriodType.get().equals("Year")
                ? PeriodType.YEAR : PeriodType.MONTH;

        double initialPayment = Double.parseDouble((firstPayment.get()));
        double fixedComissionAmount = Double.parseDouble((oneTimeCommissions.get()));
        double monthlyComissionAmount = Double.parseDouble((monthlyCommissions.get()));

        Commission fixedCommission = null;
        MonthlyCommission monthlyCommission = null;

        try {
            fixedCommission = oneTimeCommissionsType.get().equals("Percent")
                    ? new PercentCommission(fixedComissionAmount)
                    : new FixedCommission(fixedComissionAmount);
            monthlyCommission = monthlyCommissionsType.get().equals("Percent")
                    ? new PercentAmountMonthlyCommission(monthlyComissionAmount)
                    : new FixedMonthlyCommission(monthlyComissionAmount);

            mortgageParameters = new MortgageParameters(amount, percent, periodType, period);
            mortgageParameters.setInitialPayment(initialPayment);
        } catch (Exception e) {
            logger.log(LogMessages.INCORRECT_INPUT);
            updateLogs();
            result.set("Incorrect input");
            return null;
        }
        mortgageParameters.setCommission(fixedCommission);
        mortgageParameters.setMonthlyCommission(monthlyCommission);
        return mortgageParameters;
    }

    public void calculate() {
        if (!checkInput()) {
            result.set("Incorrect input");
            return;
        }

        MortgageParameters mortgageParameters = createMortgageParametersCalculator();
        if (mortgageParameters == null) {
            return;
        }

        MortgageCalculator calculator;
        if (typeOfPayment.get().equals("Annuity")) {
            calculator = new MortgageWithAnnuityPaymentsCalculator();
        } else {
            calculator = new MortgageWithDifferentialPaymentsCalculator();
        }

        MortgageReport report = calculator.calculate(mortgageParameters);
        MortgageMonthReport monthReport = report.getMonthReport(1);
        String resultString = "Final amount " + report.getFinalAmount() + "; "
                + "Overpayment " + report.getOverpayment()
                + "; With month payment " + monthReport.getPayment() + " for "
                + "" + mortgageParameters.getMonthsPeriod() + " months.";
        this.result.set(resultString);

        StringBuilder message = new StringBuilder(LogMessages.CALCULATE_WAS_PRESSED);
        message.append("Arguments: Apartment Price = ").append(apartmentPrice.get())
                .append("; First Payment = ").append(firstPayment.get())
                .append("; Interest Rate = ").append(interestRate.get())
                .append("; Loan Period = ").append(loanPeriod.get())
                .append("; Loan Period Type: ").append(loanPeriodType.get())
                .append("; Monthly Commissions = ").append(monthlyCommissions.get())
                .append("; Monthly Commissions Type: ").append(monthlyCommissionsType.get())
                .append("; One Time Commissions = ").append(oneTimeCommissions.get())
                .append("; One Time Commissions Type: ").append(oneTimeCommissionsType.get())
                .append("; Type Of Payment: ").append(typeOfPayment.get()).append(".");
        logger.log(message.toString());
        updateLogs();
    }

    public List<String> getLog() {
        if (logger != null) {
            return logger.getLog();
        }
        return Collections.emptyList();
    }

    public final String getLogs() {
        return logs.get();
    }

    public StringProperty logsProperty() {
        return logs;
    }

    private void updateLogs() {
        List<String> fullLog = logger.getLog();
        String record = new String("");
        for (String log : fullLog) {
            record += log + "\n";
        }
        logs.set(record);
    }

    final class LogMessages {
        public static final String CALCULATE_WAS_PRESSED = "Calculate. ";
        public static final String LOAN_PERIOD_TYPE_WAS_CHANGED =
                "Loan Period Type was changed to ";
        public static final String ONE_TIME_COMMISSIONS_TYPE_WAS_CHANGED =
                "One Time Commissions Type was changed to ";
        public static final String MONTHLY_COMMISSIONS_TYPE_WAS_CHANGED =
                "Monthly Commissions Type was changed to ";
        public static final String INCORRECT_INPUT = "Incorrect input. ";

        private LogMessages() {
        }
    }
}
