/**
 * Represents one parsed instruction that can be performed by Dobby.
 */
public abstract class Command {
    /**
     * Performs this instruction using the application's task logic.
     *
     * @param logic application logic that owns the task list and command results
     */
    public abstract void execute(DobbyLogic logic);

    /**
     * Returns whether this instruction ends the application.
     *
     * @return {@code true} when Dobby should exit
     */
    public boolean isExit() {
        return false;
    }
}

/** Reports a command error identified during parsing. */
final class InvalidCommand extends Command {
    /** Message explaining why the input was invalid. */
    private final String message;

    /** Creates a command that reports one error message. */
    InvalidCommand(String message) {
        this.message = message;
    }

    /** Displays the parsing error. */
    @Override
    public void execute(DobbyLogic logic) {
        logic.showMessage(message);
    }
}
