package other;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.time.temporal.TemporalUnit;

/**
 * Created by tetiana.sviatska on 7/14/2015.
 */
public class DateMatcher extends TypeSafeMatcher<LocalDateTime> {

    private TemporalUnit unit;
    private LocalDateTime time;

    private DateMatcher(@NotNull LocalDateTime time, @Nullable TemporalUnit unit) {
        this.unit = unit;
        this.time = unit != null ? time.truncatedTo(unit) : time;
    }

    @Override
    protected boolean matchesSafely(LocalDateTime localDateTime) {
        return time.isEqual(localDateTime);
    }

    @Override
    public void describeTo(Description description) {
        description.appendValue(time);
    }

    @NotNull
    public static DateMatcher equals(@NotNull LocalDateTime time) {
        return equals(time, null);
    }

    @NotNull
    public static DateMatcher equals(@NotNull LocalDateTime time, @Nullable TemporalUnit unit) {
        return new DateMatcher(time, unit);
    }
}
