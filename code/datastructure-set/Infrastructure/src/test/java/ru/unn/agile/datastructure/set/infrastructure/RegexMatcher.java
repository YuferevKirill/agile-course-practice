package ru.unn.agile.datastructure.set.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegexMatcher extends BaseMatcher {
    private final String regexStr;

    private RegexMatcher(final String regex) {
        this.regexStr = regex;
    }

    public boolean matches(final Object o) {
        if (o instanceof String) {
            return ((String) o).matches(regexStr);
        }
        return false;
    }

    public void describeTo(final Description description) {
        if (description != null) {
            description.appendText("regex = ");
            description.appendText(regexStr);
        }
    }

    @SuppressWarnings(value = "unchecked")
    static Matcher<? super String> matchesPattern(final String regex) {
        return (Matcher<? super String>) new RegexMatcher(regex);
    }
}
