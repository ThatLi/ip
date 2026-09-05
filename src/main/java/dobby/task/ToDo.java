package dobby.task;

/** A task without a date or time. */
public class ToDo extends Task {
    /**
     * Creates a todo task with the specified description.
     *
     * @param str description of the task
     */
    public ToDo(String str) {
        super(str, "T");
    }
}
