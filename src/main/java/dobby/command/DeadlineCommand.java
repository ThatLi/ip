package dobby.command;

import dobby.logic.DobbyLogic;
import dobby.task.Recurrence;
import dobby.util.DateTimeUtil;

/**
 * Adds a new deadline task with its due date and time.
 */
public final class DeadlineCommand extends Command {
    /** Description supplied for the deadline. */
    private final String description;
    /** Parsed due date and optional time. */
    private final DateTimeUtil.ParsedDateTime by;
    /** Interval at which the deadline repeats. */
    private final Recurrence recurrence;

    /**
     * Creates a command with a deadline description, due date, and recurrence.
     *
     * @param description deadline description
     * @param by parsed due date and optional time
     * @param recurrence interval at which the deadline repeats
     */
    public DeadlineCommand(String description, DateTimeUtil.ParsedDateTime by, Recurrence recurrence) {
        this.description = description;
        this.by = by;
        this.recurrence = recurrence;
    }

    /** Adds the deadline task. */
    @Override
    public String execute(DobbyLogic logic) {
        return logic.createDeadline(description, by, recurrence);
    }
}
