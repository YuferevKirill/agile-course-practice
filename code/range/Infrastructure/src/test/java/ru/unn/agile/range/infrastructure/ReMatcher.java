package ru.unn.agile.range.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class ReMatcher extends BaseMatcher {
    private final String reString;

    public ReMatcher(final String regex) {
        this.reString = regex;
    }

    public static Matcher<? super String> matchesPattern(final String regularExpression) {
        ReMatcher matcher = new ReMatcher(regularExpression);
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>)   matcher;
        return castedMatcher;
    }

    public void describeTo(final Description description) {
        description.appendText("match regular string = ");
        description.appendText(reString);
    }

    public boolean matches(final Object o) {
        return ((String) o).matches(reString);
    }
}
