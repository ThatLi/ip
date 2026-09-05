package dobby.task;

/** A task without a date or time. */
public class ToDo extends Task {
    public ToDo(String str) {
        super(str, "T");
    }
}
