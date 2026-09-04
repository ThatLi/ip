import java.io.IOException;

/**
 * Applies task operations and coordinates persistence for Dobby commands.
 */
public final class DobbyLogic {
    /** Tasks recorded during this session. */
    private final TaskList tasks = new TaskList();
    /** Stores the task list between sessions. */
    private final Storage storage = new Storage();

    /** Loads tasks saved by an earlier Dobby session. */
    public DobbyLogic() {
        loadTasks();
    }

    /** Displays all recorded tasks in numbered order. */
    public void showTasks() {
        StringBuilder result = new StringBuilder();
        result.append("> Dobby show ").append(tasks.size()).append(" tasks:\n");
        for (int index = 0; index < tasks.size(); index++) {
            result.append(index + 1).append(". ").append(tasks.get(index)).append("\n");
        }
        print(result.toString());
    }

    /** Displays a message supplied by a command. */
    public void showMessage(String message) {
        print(message);
    }

    /** Changes one task's completion status and reports the result. */
    public void changeTaskStatus(int taskNumber, boolean isDone) {
        if (taskNumber > tasks.size() || taskNumber <= 0) {
            print("> Dobby is confused. Dobby can't find task " + taskNumber);
            return;
        }
        Task task = tasks.get(taskNumber - 1);
        if (isDone) {
            task.markDone();
            saveTasks();
            print("> Dobby will mark this as done!");
        } else {
            task.markNotDone();
            saveTasks();
            print("> Dobby will mark this as not done!");
        }
        print("   " + task);
    }

    /** Removes one task, saves the updated list, and reports the result. */
    public void deleteTask(int taskNumber) {
        if (taskNumber > tasks.size() || taskNumber <= 0) {
            print("> Dobby is confused. Dobby can't find task " + taskNumber);
            return;
        }
        Task task = tasks.remove(taskNumber - 1);
        saveTasks();
        print("> Dobby has removed this task. Now Dobby only see " + tasks.size() + " tasks!");
        print("  " + task);
    }

    /** Adds a todo task. */
    public void createToDo(String description) {
        tasks.add(new ToDo(description));
        saveTasks();
        print("> Dobby noted a new Todo: " + description);
    }

    /** Adds a deadline task. */
    public void createDeadline(String description, DateTimeUtil.ParsedDateTime by) {
        tasks.add(new Deadline(description, by.getValue(), by.hasTime()));
        saveTasks();
        print("> Dobby noted a new Deadline: " + description + " by "
                + DateTimeUtil.formatForDisplay(by.getValue(), by.hasTime()));
    }

    /** Adds an event task. */
    public void createEvent(String description, DateTimeUtil.ParsedDateTime from, DateTimeUtil.ParsedDateTime to) {
        tasks.add(new Event(description, from.getValue(), from.hasTime(), to.getValue(), to.hasTime()));
        saveTasks();
        print("> Dobby noted a new Event: " + description + " from "
                + DateTimeUtil.formatForDisplay(from.getValue(), from.hasTime()) + " to "
                + DateTimeUtil.formatForDisplay(to.getValue(), to.hasTime()));
    }

    /** Saves tasks and reports an error only if writing fails. */
    private void saveTasks() {
        try {
            storage.save(tasks.asList());
        } catch (IOException | SecurityException e) {
            print("> Dobby could not save the task list.");
        }
    }

    /** Loads tasks and reports only invalid records or file-reading failures. */
    private void loadTasks() {
        try {
            Storage.LoadResult loadResult = storage.load();
            for (Task task : loadResult.getTasks()) {
                tasks.add(task);
            }
            if (loadResult.getInvalidTaskCount() > 0) {
                print("> Dobby skipped " + loadResult.getInvalidTaskCount() + " invalid saved task(s).");
            }
        } catch (IOException | SecurityException e) {
            print("> Dobby could not load the task list.");
        }
    }

    /** Prints a Dobby message. */
    private void print(String message) {
        DobbyUtil.print(message);
    }
}
