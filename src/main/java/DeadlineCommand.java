/**
 * Adds a new deadline task with its due date and time.
 */
public final class DeadlineCommand extends Command {
    /** Description supplied for the deadline. */
    private final String description;
    /** Parsed due date and optional time. */
    private final DateTimeUtil.ParsedDateTime by;

    /** Creates a command with a deadline description and due date. */
    public DeadlineCommand(String description, DateTimeUtil.ParsedDateTime by) {
        this.description = description;
        this.by = by;
    }

    /** Adds the deadline task. */
    @Override
    public void execute(DobbyLogic logic) {
        logic.createDeadline(description, by);
    }
}
