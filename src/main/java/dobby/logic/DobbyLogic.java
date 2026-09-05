package dobby.logic;

import java.io.IOException;

import dobby.storage.Storage;
import dobby.task.Deadline;
import dobby.task.Event;
import dobby.task.Task;
import dobby.task.TaskList;
import dobby.task.ToDo;
import dobby.util.DateTimeUtil;

/**
 * Applies task operations and coordinates persistence for Dobby commands.
 */
public final class DobbyLogic {
    /** Tasks recorded during this session. */
    private final TaskList tasks = new TaskList();
    /** Stores the task list between sessions. */
    private final Storage storage = new Storage();

    /** Message to show when loading saved tasks identifies a problem. */
    private final String startupMessage;

    /** Loads tasks saved by an earlier Dobby session. */
    public DobbyLogic() {
        startupMessage = loadTasks();
    }

    /** Returns all recorded tasks in numbered order. */
    public String showTasks() {
        StringBuilder result = new StringBuilder();
        result.append("> Dobby show ").append(tasks.size()).append(" tasks:\n");
        for (int index = 0; index < tasks.size(); index++) {
            result.append(index + 1).append(". ").append(tasks.get(index)).append("\n");
        }
        return result.toString();
    }

    /** Returns any message produced while loading saved tasks. */
    public String getStartupMessage() {
        return startupMessage;
    }

    /** Changes one task's completion status and returns the result. */
    public String changeTaskStatus(int taskNumber, boolean isDone) {
        if (taskNumber > tasks.size() || taskNumber <= 0) {
            return "> Dobby is confused. Dobby can't find task " + taskNumber;
        }
        Task task = tasks.get(taskNumber - 1);
        if (isDone) {
            task.markDone();
            return combineSaveStatus(saveTasks(), "> Dobby will mark this as done!\n   " + task);
        } else {
            task.markNotDone();
            return combineSaveStatus(saveTasks(), "> Dobby will mark this as not done!\n   " + task);
        }
    }

    /** Removes one task, saves the updated list, and returns the result. */
    public String deleteTask(int taskNumber) {
        if (taskNumber > tasks.size() || taskNumber <= 0) {
            return "> Dobby is confused. Dobby can't find task " + taskNumber;
        }
        Task task = tasks.remove(taskNumber - 1);
        return combineSaveStatus(saveTasks(), "> Dobby has removed this task. Now Dobby only see "
                + tasks.size() + " tasks!\n  " + task);
    }

    /** Adds a todo task and returns the result. */
    public String createToDo(String description) {
        tasks.add(new ToDo(description));
        return combineSaveStatus(saveTasks(), "> Dobby noted a new Todo: " + description);
    }

    /** Adds a deadline task and returns the result. */
    public String createDeadline(String description, DateTimeUtil.ParsedDateTime by) {
        tasks.add(new Deadline(description, by.getValue(), by.hasTime()));
        return combineSaveStatus(saveTasks(), "> Dobby noted a new Deadline: " + description + " by "
                + DateTimeUtil.formatForDisplay(by.getValue(), by.hasTime()));
    }

    /** Adds an event task and returns the result. */
    public String createEvent(String description, DateTimeUtil.ParsedDateTime from, DateTimeUtil.ParsedDateTime to) {
        tasks.add(new Event(description, from.getValue(), from.hasTime(), to.getValue(), to.hasTime()));
        return combineSaveStatus(saveTasks(), "> Dobby noted a new Event: " + description + " from "
                + DateTimeUtil.formatForDisplay(from.getValue(), from.hasTime()) + " to "
                + DateTimeUtil.formatForDisplay(to.getValue(), to.hasTime()));
    }

    /** Saves tasks and returns an error message only if writing fails. */
    private String saveTasks() {
        try {
            storage.save(tasks.asList());
            return "";
        } catch (IOException | SecurityException e) {
            return "> Dobby could not save the task list.";
        }
    }

    /** Loads tasks and returns only invalid-record or file-reading messages. */
    private String loadTasks() {
        try {
            Storage.LoadResult loadResult = storage.load();
            for (Task task : loadResult.getTasks()) {
                tasks.add(task);
            }
            if (loadResult.getInvalidTaskCount() > 0) {
                return "> Dobby skipped " + loadResult.getInvalidTaskCount() + " invalid saved task(s).";
            }
            return "";
        } catch (IOException | SecurityException e) {
            return "> Dobby could not load the task list.";
        }
    }

    /** Combines a persistence error with the successful operation message. */
    private String combineSaveStatus(String saveStatus, String successMessage) {
        return saveStatus.isEmpty() ? successMessage : saveStatus + "\n" + successMessage;
    }
}
