package dobby.task;

import java.time.LocalDateTime;

import dobby.util.DateTimeUtil;

/** A task that occurs between a start and end date, optionally with times. */
public class Event extends Task {
    private final LocalDateTime from;
    private final boolean hasStartTime;
    private final LocalDateTime to;
    private final boolean hasEndTime;
    private final Recurrence recurrence;

    /**
     * Creates an event with parsed start and end dates and times.
     *
     * @param description event description
     * @param from start date and time
     * @param hasStartTime whether the start date includes a time
     * @param to end date and time
     * @param hasEndTime whether the end date includes a time
     */
    public Event(String description, LocalDateTime from, boolean hasStartTime,
                 LocalDateTime to, boolean hasEndTime) {
        this(description, from, hasStartTime, to, hasEndTime, Recurrence.NONE);
    }

    /**
     * Creates an event with its parsed dates, times, and recurrence.
     *
     * @param description event description
     * @param from start date and time
     * @param hasStartTime whether the start date includes a time
     * @param to end date and time
     * @param hasEndTime whether the end date includes a time
     * @param recurrence interval at which the event repeats
     */
    public Event(String description, LocalDateTime from, boolean hasStartTime,
                 LocalDateTime to, boolean hasEndTime, Recurrence recurrence) {
        super(description, "E");
        assert recurrence != null : "An event recurrence must be specified";

        this.from = from;
        this.hasStartTime = hasStartTime;
        this.to = to;
        this.hasEndTime = hasEndTime;
        this.recurrence = recurrence;
    }

    /** Returns this event in the format used by the task data file. */
    @Override
    public String toFileString() {
        String recurrenceField = recurrence.isRecurring() ? " | " + recurrence : "";
        return super.toFileString() + " | " + DateTimeUtil.formatForStorage(from, hasStartTime)
                + " | " + DateTimeUtil.formatForStorage(to, hasEndTime) + recurrenceField;
    }

    /** Returns a displayable representation of this event. */
    @Override
    public String toString() {
        String recurrenceText = recurrence.isRecurring() ? ", every " + recurrence : "";
        return super.toString() + " (from: " + DateTimeUtil.formatForDisplay(from, hasStartTime)
                + " to: " + DateTimeUtil.formatForDisplay(to, hasEndTime) + recurrenceText + ")";
    }
}
