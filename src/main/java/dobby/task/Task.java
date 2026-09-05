package dobby.task;

import java.util.Locale;

import dobby.exception.DobbyException;
import dobby.util.DateTimeUtil;
import dobby.util.DobbyUtil;

/** Represents a task recorded by Dobby. */
public class Task {
    protected String description;
    protected boolean isDone;
    protected String type = " ";

    /**
     * Creates a task with its description.
     *
     * @param description task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Creates a task with its description and type.
     *
     * @param description task description
     * @param type task type
     */
    public Task(String description, String type) {
        this(description);
        this.type = type;
    }

    /** Marks this task as complete. */
    public void markDone() {
        isDone = true;
    }

    /** Marks this task as incomplete. */
    public void markNotDone() {
        isDone = false;
    }

    /** Returns the completion-status icon used in task displays. */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /** Returns the task type icon used in task displays. */
    public String getType() {
        return type;
    }

    /**
     * Returns whether this task's description contains the given text, ignoring letter case.
     *
     * @param keyword text to look for
     * @return whether the description contains the text
     */
    public boolean hasDescriptionContaining(String keyword) {
        return description.toLowerCase(Locale.ROOT).contains(keyword.toLowerCase(Locale.ROOT));
    }

    /**
     * Returns this task in the format used by the task data file.
     *
     * @return task type, status, and description separated by pipes
     */
    public String toFileString() {
        return type + " | " + (isDone ? "1" : "0") + " | " + description;
    }

    /**
     * Reconstructs a task from one line in the task data file.
     *
     * @param line saved task data
     * @return the reconstructed task
     * @throws DobbyException if the line does not match the save format
     */
    public static Task fromFileString(String line) throws DobbyException {
        String[] fields = line.split(" \\| ", -1);
        if (fields.length < 3 || fields[2].isBlank()
                || !(fields[1].equals("0") || fields[1].equals("1"))) {
            throw new DobbyException("Invalid saved task: " + line);
        }

        Task task;
        switch (fields[0]) {
        case "T":
            if (fields.length != 3) {
                throw new DobbyException("Invalid saved todo: " + line);
            }
            task = new ToDo(fields[2]);
            break;
        case "D":
            if (fields.length != 4 || fields[3].isBlank()) {
                throw new DobbyException("Invalid saved deadline: " + line);
            }
            DateTimeUtil.ParsedDateTime deadlineDateTime = parseSavedDate(fields[3], line);
            task = new Deadline(fields[2], deadlineDateTime.getValue(), deadlineDateTime.hasTime());
            break;
        case "E":
            if (fields.length != 5 || fields[3].isBlank() || fields[4].isBlank()) {
                throw new DobbyException("Invalid saved event: " + line);
            }
            DateTimeUtil.ParsedDateTime startDateTime = parseSavedDate(fields[3], line);
            DateTimeUtil.ParsedDateTime endDateTime = parseSavedDate(fields[4], line);
            task = new Event(fields[2], startDateTime.getValue(), startDateTime.hasTime(),
                    endDateTime.getValue(), endDateTime.hasTime());
            break;
            default:
                throw new DobbyException("Unknown saved task type: " + fields[0]);
        }
        if (fields[1].equals("1")) {
            task.markDone();
        }
        return task;
    }

    /** Parses a date from saved data while preserving the context of the invalid record. */
    private static DateTimeUtil.ParsedDateTime parseSavedDate(String dateText, String line) throws DobbyException {
        try {
            return DateTimeUtil.parse(dateText);
        } catch (DobbyException e) {
            throw new DobbyException("Invalid saved date: " + line, e);
        }
    }

    @Override
    public String toString() {
        return DobbyUtil.encloseBracket(getType())
                + DobbyUtil.encloseBracket(getStatusIcon()) + " "
                + description;
    }
}
