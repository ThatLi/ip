/**
 * General Task class to be stored in DobbyLogic.java
 */
public class Task {
    protected String description;
    protected boolean isDone;
    protected String type = " ";

    /**
     * Initiate a task with its description
     * @param str Description
     */
    public Task(String str) {
        this.description = str;
        this.isDone = false;
    }

    /**
     * Initiate a task with description and task type
     * @param str Description
     * @param type Type of task
     */
    public Task(String str, String type) {
        this(str);
        this.type = type;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markNotDone() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (this.isDone ? "X" : " ");
    }

    public String getType() {
        return this.type;
    }

    /**
     * Returns this task in the format used by the task data file.
     *
     * @return task type, status, and description separated by pipes
     */
    public String toFileString() {
        return this.type + " | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }

    @Override
    public String toString() {
        return DobbyUtil.encloseBracket(this.getType()) +
                DobbyUtil.encloseBracket(this.getStatusIcon()) + " " +
                this.description;
    }
}
