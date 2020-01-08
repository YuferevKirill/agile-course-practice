package ru.unn.agile.converter.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class LengthConverterRegexMatcher extends BaseMatcher {
    private final String regex;

    public LengthConverterRegexMatcher(final String regex) {
        this.regex = regex;
    }

    public void describeTo(final Description description) {
        description.appendText("matches regex = ");
        description.appendText(regex);
    }

    public boolean matches(final Object o) {
        return ((String) o).matches(regex);
    }

    public static Matcher<? super String> matchesPattern(final String regex) {
        LengthConverterRegexMatcher matcher = new LengthConverterRegexMatcher(regex);
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>)   matcher;
        return castedMatcher;
    }
}
