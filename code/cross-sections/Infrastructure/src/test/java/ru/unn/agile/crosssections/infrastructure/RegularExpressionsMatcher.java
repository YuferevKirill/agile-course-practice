package ru.unn.agile.crosssections.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegularExpressionsMatcher extends BaseMatcher {
    private final String string;

    private RegularExpressionsMatcher(final String regex) {
        this.string = regex;
    }

    public boolean matches(final Object o) {
        if (o instanceof String) {
            return ((String) o).matches(string);
        }
        return false;
    }

    public void describeTo(final Description description) {
        if (description != null) {
            description.appendText("regex = ");
            description.appendText(string);
        }
    }

    @SuppressWarnings(value = "unchecked")
    static Matcher<? super String> matchesPattern(final String regex) {
        return (Matcher<? super String>) new RegularExpressionsMatcher(regex);
    }
}
