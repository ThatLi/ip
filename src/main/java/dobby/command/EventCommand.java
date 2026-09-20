package dobby.command;

import dobby.logic.DobbyLogic;
import dobby.task.Recurrence;
import dobby.util.DateTimeUtil;

/**
 * Adds a new event task with start and end dates and times.
 */
public final class EventCommand extends Command {
    /** Description supplied for the event. */
    private final String description;
    /** Parsed start date and optional time. */
    private final DateTimeUtil.ParsedDateTime from;
    /** Parsed end date and optional time. */
    private final DateTimeUtil.ParsedDateTime to;
    /** Interval at which the event repeats. */
    private final Recurrence recurrence;

    /**
     * Creates a command with event details and recurrence.
     *
     * @param description event description
     * @param from parsed start date and optional time
     * @param to parsed end date and optional time
     * @param recurrence interval at which the event repeats
     */
    public EventCommand(String description, DateTimeUtil.ParsedDateTime from, DateTimeUtil.ParsedDateTime to,
                        Recurrence recurrence) {
        this.description = description;
        this.from = from;
        this.to = to;
        this.recurrence = recurrence;
    }

    /** Adds the event task. */
    @Override
    public String execute(DobbyLogic logic) {
        return logic.createEvent(description, from, to, recurrence);
    }
}
