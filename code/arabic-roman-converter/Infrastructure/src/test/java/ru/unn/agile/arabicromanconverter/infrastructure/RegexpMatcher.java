package ru.unn.agile.arabicromanconverter.infrastructure;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public class RegexpMatcher extends BaseMatcher {
    private final String regexp;

    public RegexpMatcher(final String regexp) {
        this.regexp = regexp;
    }

    public void describeTo(final Description description) {
        description.appendText("matches regexp = ");
        description.appendText(regexp);
    }

    public boolean matches(final Object o) {
        return ((String) o).matches(regexp);
    }

    public static Matcher<? super String> matches(final String regex) {
        RegexpMatcher matcher = new RegexpMatcher(regex);
        @SuppressWarnings (value = "unchecked")
        Matcher<? super String> castedMatcher = (Matcher<? super String>)   matcher;
        return castedMatcher;
    }
}
