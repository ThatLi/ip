/**
 * Displays every task currently recorded by Dobby.
 */
public final class ListCommand extends Command {
    /** Displays the task list. */
    @Override
    public String execute(DobbyLogic logic) {
        return logic.showTasks();
    }
}
