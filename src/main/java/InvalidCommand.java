/**
 * Reports a command error identified during parsing.
 *
 * <p>This class is public so that {@code Parser}, which will belong to the
 * {@code dobby.parser} package, can create invalid commands after the package
 * reorganization.</p>
 */
public final class InvalidCommand extends Command {
    /** Message explaining why the input was invalid. */
    private final String message;

    /** Creates a command that reports one error message. */
    public InvalidCommand(String message) {
        this.message = message;
    }

    /** Displays the parsing error. */
    @Override
    public void execute(DobbyLogic logic) {
        logic.showMessage(message);
    }
}
