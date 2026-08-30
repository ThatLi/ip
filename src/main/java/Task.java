/**
 * General Task class to be stored in DobbyLogic.java
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Initiate a task with its description
     * @param str Description
     */
    public Task(String str) {
        this.description = str;
        this.isDone = false;
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

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.description;
    }
}
