package ru.unn.agile.primenumber.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegularExpressionMatcher extends BaseMatcher {
    private final String regularExpression;

    public RegularExpressionMatcher(final String regularExpression) {
        this.regularExpression = regularExpression;
    }

    public void describeTo(final Description description) {
        description.appendText("matches regular expression = ");
        description.appendText(regularExpression);
    }

    public boolean matches(final Object o) {
        return ((String) o).matches(regularExpression);
    }

    public static Matcher<? super String> matches(final String regex) {
        RegularExpressionMatcher matcher = new RegularExpressionMatcher(regex);
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>)   matcher;
        return castedMatcher;
    }
}
