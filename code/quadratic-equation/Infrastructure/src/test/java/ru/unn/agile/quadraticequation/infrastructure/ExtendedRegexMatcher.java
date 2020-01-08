package ru.unn.agile.quadraticequation.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class ExtendedRegexMatcher extends BaseMatcher {
    private final String regex;

    private ExtendedRegexMatcher(final String regex) {
        this.regex = regex;
    }

    @Override
    public boolean matches(final Object o) {
        return ((String) o).matches(regex);
    }

    @Override
    public void describeTo(final Description expansion) {
        expansion.appendText("matches regex = ");
        expansion.appendText(regex);
    }

    static Matcher<? super String> matches(final String regex) {
        ExtendedRegexMatcher matcher = new ExtendedRegexMatcher(regex);
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>)   matcher;
        return castedMatcher;
    }
}
