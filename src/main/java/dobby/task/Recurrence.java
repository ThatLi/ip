package dobby.task;

import java.util.Locale;

import dobby.exception.DobbyException;

/** Describes how often a deadline or event repeats. */
public enum Recurrence {
    NONE(""),
    DAY("day"),
    WEEK("week"),
    MONTH("month"),
    YEAR("year");

    private final String interval;

    Recurrence(String interval) {
        this.interval = interval;
    }

    /**
     * Converts a user-supplied recurrence interval into its enum value.
     *
     * @param text recurrence interval such as {@code week}
     * @return the matching recurrence
     * @throws DobbyException if the interval is unsupported
     */
    public static Recurrence parse(String text) throws DobbyException {
        assert text != null : "A recurrence interval must come from parsed input or saved data";

        try {
            return valueOf(text.trim().toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new DobbyException("Dobby needs a recurrence of day, week, month, or year.", e);
        }
    }

    /** Returns whether this value represents a repeating task. */
    public boolean isRecurring() {
        return this != NONE;
    }

    /** Returns the recurrence interval used in commands and display text. */
    @Override
    public String toString() {
        return interval;
    }
}
