package de.mem89.imdb.test.matcher;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVRecord;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

import java.util.Arrays;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class HasRowValues extends TypeSafeMatcher<CSVRecord> {
    @NonNull
    private String[] values;

    @Override
    protected boolean matchesSafely(CSVRecord item) {
        if (item.size() != values.length) {
            return false;
        }

        for (int i = 0; i < values.length; i++) {
            if (!values[i].equals(item.get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        String objectClass = CSVRecord.class.getSimpleName();
        String valueList = Arrays.stream(values).collect(Collectors.joining(", "));
        description.appendText(String.format("a %s with values=[%s]", objectClass,valueList));
    }

    public static HasRowValues hasRowValues(String... values) {
        return new HasRowValues(values);
    }
}
