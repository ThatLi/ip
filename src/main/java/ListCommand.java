/**
 * Displays every task currently recorded by Dobby.
 */
public final class ListCommand extends Command {
    /** Displays the task list. */
    @Override
    public void execute(DobbyLogic logic) {
        logic.showTasks();
    }
}
