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

    /**
     * Reconstructs a task from one line in the task data file.
     *
     * @param line saved task data
     * @return the reconstructed task
     * @throws IllegalArgumentException if the line does not match the save format
     */
    public static Task fromFileString(String line) {
        String[] fields = line.split(" \\| ", -1);
        if (fields.length < 3 || fields[2].isBlank()
                || !(fields[1].equals("0") || fields[1].equals("1"))) {
            throw new IllegalArgumentException("Invalid saved task: " + line);
        }

        Task task;
        switch (fields[0]) {
        case "T":
            if (fields.length != 3) {
                throw new IllegalArgumentException("Invalid saved todo: " + line);
            }
            task = new ToDo(fields[2]);
            break;
        case "D":
            if (fields.length != 4 || fields[3].isBlank()) {
                throw new IllegalArgumentException("Invalid saved deadline: " + line);
            }
            task = new Deadline(fields[2], fields[3]);
            break;
        case "E":
            if (fields.length != 5 || fields[3].isBlank() || fields[4].isBlank()) {
                throw new IllegalArgumentException("Invalid saved event: " + line);
            }
            task = new Event(fields[2], fields[3], fields[4]);
            break;
        default:
            throw new IllegalArgumentException("Unknown saved task type: " + fields[0]);
        }

        if (fields[1].equals("1")) {
            task.markDone();
        }
        return task;
    }

    @Override
    public String toString() {
        return DobbyUtil.encloseBracket(this.getType()) +
                DobbyUtil.encloseBracket(this.getStatusIcon()) + " " +
                this.description;
    }
}
