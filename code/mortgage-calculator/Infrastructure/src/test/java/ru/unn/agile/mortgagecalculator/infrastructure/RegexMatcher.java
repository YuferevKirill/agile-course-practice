package ru.unn.agile.mortgagecalculator.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegexMatcher extends BaseMatcher {
    private final String regex;

    private RegexMatcher(final String regex) {
        this.regex = regex;
    }

    @Override
    public boolean matches(final Object o) {
        return ((String) o).matches(regex);
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("matches regex = ");
        description.appendText(regex);
    }

    static Matcher<? super String> matchesPattern(final String regex) {
        RegexMatcher regexMatcher = new RegexMatcher(regex);
        @SuppressWarnings(value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>) regexMatcher;
        return castedMatcher;
    }
}
