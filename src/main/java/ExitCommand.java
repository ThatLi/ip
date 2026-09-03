/**
 * Ends the Dobby application loop.
 */
public final class ExitCommand extends Command {
    /** This command has no task-list work to perform before exit. */
    @Override
    public void execute(DobbyLogic logic) {
        // The application loop handles the exit state after parsing this command.
    }

    /** Returns that Dobby should exit after this command. */
    @Override
    public boolean isExit() {
        return true;
    }
}
