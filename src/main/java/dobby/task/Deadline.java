package dobby.task;

import java.time.LocalDateTime;

import dobby.util.DateTimeUtil;

/** A task that must be completed by a specific date, optionally with a time. */
public class Deadline extends Task {
    private final LocalDateTime by;
    private final boolean hasTime;
    private final Recurrence recurrence;

    /**
     * Creates a deadline with its parsed due date and time.
     *
     * @param description deadline description
     * @param by due date and time
     * @param hasTime whether the due date includes a time
     */
    public Deadline(String description, LocalDateTime by, boolean hasTime) {
        this(description, by, hasTime, Recurrence.NONE);
    }

    /**
     * Creates a deadline with its parsed due date, time, and recurrence.
     *
     * @param description deadline description
     * @param by due date and time
     * @param hasTime whether the due date includes a time
     * @param recurrence interval at which the deadline repeats
     */
    public Deadline(String description, LocalDateTime by, boolean hasTime, Recurrence recurrence) {
        super(description, "D");
        assert recurrence != null : "A deadline recurrence must be specified";

        this.by = by;
        this.hasTime = hasTime;
        this.recurrence = recurrence;
    }

    /** Returns this deadline in the format used by the task data file. */
    @Override
    public String toFileString() {
        String recurrenceField = recurrence.isRecurring() ? " | " + recurrence : "";
        return super.toFileString() + " | " + DateTimeUtil.formatForStorage(by, hasTime) + recurrenceField;
    }

    /** Returns a displayable representation of this deadline. */
    @Override
    public String toString() {
        String recurrenceText = recurrence.isRecurring() ? ", every " + recurrence : "";
        return super.toString() + " (by: " + DateTimeUtil.formatForDisplay(by, hasTime) + recurrenceText + ")";
    }
}
