package ru.unn.agile.currencyconverter.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegexpMatcher extends BaseMatcher {
    private final String regexp;

    public RegexpMatcher(final String regexp) {
        this.regexp = regexp;
    }

    public boolean matches(final Object object) {
        return ((String) object).matches(regexp);
    }

    public void describeTo(final Description description) {
        description.appendText("matches regex = ");
        description.appendText(regexp);
    }

    public static Matcher<? super String> matchesPattern(final String regexp) {
        RegexpMatcher matcher = new RegexpMatcher(regexp);
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>) matcher;
        return castedMatcher;
    }
}
