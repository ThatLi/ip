package dobby.task;

import java.time.LocalDateTime;

import dobby.util.DateTimeUtil;

/** A task that must be completed by a specific date, optionally with a time. */
public class Deadline extends Task {
    private final LocalDateTime by;
    private final boolean hasTime;

    /** Creates a deadline with its parsed due date and time. */
    public Deadline(String str, LocalDateTime by, boolean hasTime) {
        super(str, "D");
        this.by = by;
        this.hasTime = hasTime;
    }

    /** Returns this deadline in the format used by the task data file. */
    @Override
    public String toFileString() {
        return super.toFileString() + " | " + DateTimeUtil.formatForStorage(by, hasTime);
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + DateTimeUtil.formatForDisplay(by, hasTime) + ")";
    }
}
