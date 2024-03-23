package accesssentry;

import java.util.concurrent.TimeUnit;

/**
 * Enumeration representing various time units with their corresponding upper bounds.
 */
enum TimeUnitType {
    DAYS(TimeUnit.DAYS, 100),
    HOURS(TimeUnit.HOURS, 24),
    MINUTES(TimeUnit.MINUTES, 60),
    SECONDS(TimeUnit.SECONDS, 60);

    private final TimeUnit timeUnit;
    private final int upperBound;

    TimeUnitType(TimeUnit timeUnit, int upperBound) {
        this.timeUnit = timeUnit;
        this.upperBound = upperBound;
    }

    TimeUnit getTimeUnit() {
        return timeUnit;
    }

    int getUpperBound() {
        return upperBound;
    }
}
