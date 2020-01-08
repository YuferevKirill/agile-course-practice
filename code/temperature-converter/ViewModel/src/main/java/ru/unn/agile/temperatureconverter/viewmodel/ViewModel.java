package ru.unn.agile.temperatureconverter.viewmodel;

import ru.unn.agile.temperatureconverter.model.TemperatureConverter;
import ru.unn.agile.temperatureconverter.model.CelsiusTemperature;
import ru.unn.agile.temperatureconverter.model.NewtonTemperature;
import ru.unn.agile.temperatureconverter.model.FahrenheitTemperature;
import ru.unn.agile.temperatureconverter.model.KelvinTemperature;
import ru.unn.agile.temperatureconverter.model.Temperature;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ViewModel {
    private String fromTemperature;

    private String resultTemperature;
    private String statusText;
    private ListOfTemperatures listTemperaturesFrom;
    private ListOfTemperatures listTemperaturesTo;
    private boolean isConvertButtonEnabled;
    private boolean isErrorMessageDisplayed;

    private ILogger logger;

    private String patternInput = "-?(\\d+)(\\.(\\d+))?";

    public enum ListOfTemperatures {
        CELSIUS("Celsius"),
        FAHRENHEIT("Fahrenheit"),
        KELVIN("Kelvin"),
        NEWTON("Newton");
        private final String temperature;

        ListOfTemperatures(final String temperature) {
            this.temperature = temperature;
        }
        public String toString() {
            return temperature;
        }
    }

    public String getFromTemperature() {
        return fromTemperature;
    }

    public String getResultTemperature() {
        return resultTemperature;
    }

    public double getDoubleResult() {
        return Double.parseDouble(resultTemperature);
    }

    public String getStatusText() {
        return statusText;
    }

    public ListOfTemperatures getFrom() {
        return listTemperaturesFrom;
    }

    public ListOfTemperatures getTo() {
        return listTemperaturesTo;
    }

    public List<String> getLog() {
        return logger.getLogMessage();
    }

    public boolean isConvertButtonEnabled() {
        return isConvertButtonEnabled;
    }

    public boolean isErrorMessageDisplayed() {
        return isErrorMessageDisplayed;
    }

    public ViewModel(final ILogger logger) {
        if (logger == null) {
            throw new IllegalArgumentException("Logger parameter can't be null");
        }

        this.logger = logger;
        resultTemperature = "";
        fromTemperature = "";
        listTemperaturesFrom = ListOfTemperatures.CELSIUS;
        logger.addLog("Input updated: from temperature = " + listTemperaturesFrom);
        listTemperaturesTo = ListOfTemperatures.CELSIUS;
        logger.addLog("Input updated: to temperature = " + listTemperaturesTo);
        statusText = "";
        isConvertButtonEnabled = false;
    }

    public void setFromTemperature(final String fromTemperature) {
        if (!fromTemperature.equals(this.fromTemperature)) {
            this.fromTemperature = fromTemperature;
            logger.addLog("Input updated: value of from temperature = " + fromTemperature);
        }
    }

    public void setFrom(final ListOfTemperatures listTemperaturesFrom) {
        if (this.listTemperaturesFrom != listTemperaturesFrom) {
            this.resultTemperature = "";
            this.listTemperaturesFrom = listTemperaturesFrom;
            logger.addLog("Input updated: from temperature = " + listTemperaturesFrom);
        }
    }

    public void setTo(final ListOfTemperatures listTemperaturesTo) {
        if (this.listTemperaturesTo != listTemperaturesTo) {
            this.resultTemperature = "";
            this.listTemperaturesTo = listTemperaturesTo;
            logger.addLog("Input updated: to temperature = " + listTemperaturesTo);
        }
    }

    private boolean matchInput(final String line, final String pattern) {
        Pattern p  = Pattern.compile(pattern);

        if (line.isEmpty()) {
            return false;
        }

        Matcher m = p.matcher(line);
        if (m.matches()) {
            statusText = "";
            return true;
        } else {
            statusText = "Error. Please enter correct temperature";
            return false;
        }
    }

    public boolean checkTemperature(final String fromTemperature, final ListOfTemperatures from) {
        try {
            if (from.equals(ListOfTemperatures.CELSIUS)) {
                CelsiusTemperature fromClass = new CelsiusTemperature(fromTemperature);
            } else if (getFrom().equals(ListOfTemperatures.FAHRENHEIT)) {
                FahrenheitTemperature fromClass = new FahrenheitTemperature(fromTemperature);
            } else if (getFrom().equals(ListOfTemperatures.KELVIN)) {
                KelvinTemperature fromClass = new KelvinTemperature(fromTemperature);
            } else {
                NewtonTemperature fromClass = new NewtonTemperature(fromTemperature);
            }
        } catch (IllegalArgumentException e) {
            if (statusText.isEmpty()) {
                statusText = e.getMessage();
            }
            return false;
        }
        return true;
    }

    public void processInput() {
        boolean validationInput = matchInput(getFromTemperature(), patternInput);
        boolean validationWithAbsoluteZero = checkTemperature(getFromTemperature(), getFrom());
        if (validationInput && validationWithAbsoluteZero) {
            isConvertButtonEnabled = true;
            isErrorMessageDisplayed = false;
        } else {
            isConvertButtonEnabled = false;
            isErrorMessageDisplayed = true;
            logger.addLog("Error is displayed: " + statusText);
        }
    }

    public void calculate() {
        logger.addLog("Calculate");
        processInput();
        if (isConvertButtonEnabled()) {
            resultTemperature = "";
            TemperatureConverter converter = new TemperatureConverter();
            Temperature fromClass;
            Temperature toClass;

            if (getFrom().equals(ListOfTemperatures.CELSIUS)) {
                fromClass = new CelsiusTemperature(fromTemperature);
            } else if (getFrom().equals(ListOfTemperatures.FAHRENHEIT)) {
                fromClass = new FahrenheitTemperature(fromTemperature);
            } else if (getFrom().equals(ListOfTemperatures.KELVIN)) {
                fromClass = new KelvinTemperature(fromTemperature);
            } else {
                fromClass = new NewtonTemperature(fromTemperature);
            }

            if (getTo().equals(ListOfTemperatures.CELSIUS)) {
                toClass = converter.convert(fromClass, CelsiusTemperature.class);
            } else if (getTo().equals(ListOfTemperatures.FAHRENHEIT)) {
                toClass = converter.convert(fromClass, FahrenheitTemperature.class);
            } else if (getTo().equals(ListOfTemperatures.KELVIN)) {
                toClass = converter.convert(fromClass, KelvinTemperature.class);
            } else {
                toClass = converter.convert(fromClass, NewtonTemperature.class);
            }

            resultTemperature = Double.toString(toClass.getValue());
            logger.addLog("From: " + getFrom()
                    + "; To: " + getTo()
                    + "; Initial temperature = " + getFromTemperature()
                    + "; Result temperature = " + getResultTemperature());
        }
    }
}
