/**
 * Marks a specified task as incomplete.
 */
public final class UnmarkCommand extends Command {
    /** One-based number of the task to unmark. */
    private final int taskNumber;

    /**
     * Creates a command for one task number.
     *
     * @param taskNumber one-based task number
     */
    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /** Marks the selected task as incomplete. */
    @Override
    public void execute(DobbyLogic logic) {
        logic.changeTaskStatus(taskNumber, false);
    }
}
