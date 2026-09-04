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

    /** Creates a command with event details. */
    public EventCommand(String description, DateTimeUtil.ParsedDateTime from, DateTimeUtil.ParsedDateTime to) {
        this.description = description;
        this.from = from;
        this.to = to;
    }

    /** Adds the event task. */
    @Override
    public void execute(DobbyLogic logic) {
        logic.createEvent(description, from, to);
    }
}
