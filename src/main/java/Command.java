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
