package ru.unn.agile.triangle.model;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public final class Formatter {

    private Formatter() { }

    public static String format(final Object object) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols decimalFormatSymbols = decimalFormat.getDecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        decimalFormat.setDecimalFormatSymbols(decimalFormatSymbols);
        decimalFormat.setMaximumFractionDigits(2);
        return decimalFormat.format(object);
    }
}
