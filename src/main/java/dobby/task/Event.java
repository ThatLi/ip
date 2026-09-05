package dobby.task;

import java.time.LocalDateTime;

import dobby.util.DateTimeUtil;

/** A task that occurs between a start and end date, optionally with times. */
public class Event extends Task {
    private final LocalDateTime from;
    private final boolean hasStartTime;
    private final LocalDateTime to;
    private final boolean hasEndTime;

    /** Creates an event with parsed start and end dates and times. */
    public Event(String str, LocalDateTime from, boolean hasStartTime, LocalDateTime to, boolean hasEndTime) {
        super(str, "E");
        this.from = from;
        this.hasStartTime = hasStartTime;
        this.to = to;
        this.hasEndTime = hasEndTime;
    }

    /** Returns this event in the format used by the task data file. */
    @Override
    public String toFileString() {
        return super.toFileString() + " | " + DateTimeUtil.formatForStorage(from, hasStartTime)
                + " | " + DateTimeUtil.formatForStorage(to, hasEndTime);
    }

    /** Returns a displayable representation of this event. */
    @Override
    public String toString() {
        return super.toString() + " (from: " + DateTimeUtil.formatForDisplay(from, hasStartTime)
                + " to: " + DateTimeUtil.formatForDisplay(to, hasEndTime) + ")";
    }
}
