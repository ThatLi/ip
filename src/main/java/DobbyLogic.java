import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.io.IOException;
import java.time.format.DateTimeParseException;

/**
 * Evaluate user input
 */
public final class DobbyLogic {
    /** Initialize dictionary of commands */
    enum Command {
        LIST,
        MARK,
        UNMARK,
        TODO,
        DEADLINE,
        EVENT,
        DELETE
    }
    private static final Map<String, Command> COMMANDS = new HashMap<>();
    static {
        for (Command command : Command.values()) {
            COMMANDS.put(command.name().toLowerCase(Locale.ROOT), command);
        }
    }

    /** Store tasks recorded since the start of program */
    private final TaskList tasks = new TaskList();
    /** Writes the current task list to the application's data file. */
    private final Storage storage = new Storage();

    /** Loads the task list saved by an earlier chatbot session. */
    public DobbyLogic() {
        loadTasks();
    }

    /**
     * Call appropriate methods or add task to input, depending on user command in input
     * @param input User command or task to be added
     */
    public void listen(String input) {
        // Split input into array of words, removing all spaces
        if (input.trim().isEmpty()) {
            print("> Dobby couldn't hear you. Dobby want you to speak louder!");
            return;
        }
        String[] inputs = input.trim().split("\\s+");
        Command command = COMMANDS.get(inputs[0].toLowerCase(Locale.ROOT));


        // Old way to instantiate tasks
        if (command == null) {
//            this.tasks.add(new Task(input));
//            print("> Dobby noted: " + input);
            print(" > Dobby asks is this a Todo, Deadline, or Event?");
            return;
        }

        switch (command) {
            case LIST:
                if (inputs.length == 1) {
                    this.doList();
                } else {
                    print("> Dobby is confused. Dobby think you meant 'list'");
                }
                break;

            case MARK, UNMARK:
                this.doMark(inputs, command);
                break;

            case TODO, DEADLINE, EVENT:
                this.createTask(inputs, command);
                break;

            case DELETE:
                this.doDelete(inputs, command);
                break;
        }
    }

    public boolean isBye(String input) {
        return input.equalsIgnoreCase("bye");
    }

    /**
     * Print all tasks as numbered list
     */
    private void doList() {
        StringBuilder res = new StringBuilder();
        res.append("> Dobby show ").append(this.tasks.size()).append(" tasks:\n");
        int count = 0;
        for (int index = 0; index < this.tasks.size(); index++) {
            count++;
            res.append(count).append(". ").append(this.tasks.get(index)).append("\n");
        }
        print(res.toString());
    }

    /**
     * Parse Mark & Unmark command, calls respective marking command
     * @param inputs [MARK/ UNMARK, int index]
     */
    private void doMark(String[] inputs, Command command) {
        // Check valid command arguments -- could be custom Exception?
        if (inputs.length != 2) {
            print("> Dobby is confused. Dobby think you meant '" + inputs[0] + " <Task number>'");
            return;
        }

        // Check valid index
        int i; // input task number have offset of +1, so -1 to get correct index
        try {
            i = Integer.parseInt(inputs[1]);
        } catch (NumberFormatException e) {
            print("> Dobby is confused. Dobby expected a task number");
            return;
        }
        if (i > this.tasks.size() || i <= 0) {
            print("> Dobby is confused. Dobby can't find task " + i);
            return;
        }

        // Toggle done status
        Task t = this.tasks.get(i - 1);
        if (command == Command.MARK) {
            t.markDone();
            saveTasks();
            print("> Dobby will mark this as done!");
            print("   " + t);
        } else {
            t.markNotDone();
            saveTasks();
            print("> Dobby will mark this as not done!");
            print("   " + t);
        }
    }


    private void doDelete(String[] inputs, Command command) {
        // Check valid command arguments -- could be custom Exception?
        if (inputs.length != 2) {
            print("> Dobby is confused. Dobby think you meant '" + inputs[0] + " <Task number>'");
            return;
        }

        // Check valid index
        int i; // input task number have offset of +1, so -1 to get correct index
        try {
            i = Integer.parseInt(inputs[1]);
        } catch (NumberFormatException e) {
            print("> Dobby is confused. Dobby expected a task number");
            return;
        }
        if (i > this.tasks.size() || i <= 0) {
            print("> Dobby is confused. Dobby can't find task " + i);
            return;
        }

        // Delete task
        Task t = this.tasks.get(i - 1);
        this.tasks.remove(i - 1);
        saveTasks();
        print("> Dobby has removed this task. Now Dobby only see " + this.tasks.size() + " tasks!");
        print("  " + t);
    }
    /** (generated by Codex)
     * Creates a task from a tokenized command. Deadline descriptions end at '/by';
     * Event descriptions end at '/from', and their star times end at '/to'.
     *
     * @param inputs tokenized user input
     * @param command type of task to create
     */
    public void createTask(String[] inputs, Command command) {
        switch (command) {
            case TODO:
                if (inputs.length == 1) {
                    print("> Dobby is confused. Dobby think you meant 'todo <description>'");
                    return;
                }
                this.createToDo(joinTokens(inputs, 1, inputs.length));
                break;

            case DEADLINE:
                createDeadlineFromInput(inputs);
                break;

            case EVENT:
                createEventFromInput(inputs);
                break;

            default:
                // Shouldn't reach here
                throw new IllegalArgumentException("Unsupported task command: " + command);
        }
    }

    /** Creates a deadline after extracting its description and due date. */
    private void createDeadlineFromInput(String[] inputs) {
        int byIndex = this.findMarker(inputs, "/by", 1);
        if (byIndex == -1 || byIndex == 1 || byIndex == inputs.length - 1) {
            print("> Dobby is confused. Dobby think you meant 'deadline <description> /by <date/time>'");
            return;
        }
        try {
            DateTimeUtil.ParsedDateTime by = DateTimeUtil.parse(this.joinTokens(inputs, byIndex + 1, inputs.length));
            createDeadline(this.joinTokens(inputs, 1, byIndex), by);
        } catch (DateTimeParseException e) {
            print("> Dobby needs a valid date: yyyy-MM-dd, optionally followed by HHmm.");
        }
    }

    /** Creates an event after extracting its description, start time, and end time. */
    private void createEventFromInput(String[] inputs) {
        int fromIndex = this.findMarker(inputs, "/from", 1);
        int toIndex = this.findMarker(inputs, "/to", fromIndex + 1);
        if (fromIndex == -1 || toIndex == -1 || fromIndex == 1
                || fromIndex + 1 == toIndex || toIndex == inputs.length - 1) {
            print("> Dobby is confused. Dobby think you meant "
                    + "'event <description> /from <date/time> /to <date/time>'");
            return;
        }
        try {
            DateTimeUtil.ParsedDateTime from = DateTimeUtil.parse(this.joinTokens(inputs, fromIndex + 1, toIndex));
            DateTimeUtil.ParsedDateTime to = DateTimeUtil.parse(this.joinTokens(inputs, toIndex + 1, inputs.length));
            createEvent(this.joinTokens(inputs, 1, fromIndex), from, to);
        } catch (DateTimeParseException e) {
            print("> Dobby needs valid dates: yyyy-MM-dd, optionally followed by HHmm.");
        }
    }

    /** Returns the index of a marker, or -1 when it is absent. */
    private int findMarker(String[] inputs, String marker, int startIndex) {
        for (int i = Math.max(0, startIndex); i < inputs.length; i++) {
            if (marker.equalsIgnoreCase(inputs[i])) {
                return i;
            }
        }
        return -1;
    }

    /** Joins tokens in the half-open range [start, end) with spaces. */
    private String joinTokens(String[] inputs, int start, int end) {
        String[] selectedTokens = new String[end - start];
        System.arraycopy(inputs, start, selectedTokens, 0, selectedTokens.length);
        return String.join(" ", selectedTokens);
    }

    public void createToDo(String str) {
        this.tasks.add(new ToDo(str));
        saveTasks();
        print("> Dobby noted a new Todo: " + str);
    }

    /** Adds a deadline with its due date/time. */
    public void createDeadline(String description, DateTimeUtil.ParsedDateTime by) {
        this.tasks.add(new Deadline(description, by.getValue(), by.hasTime()));
        saveTasks();
        print("> Dobby noted a new Deadline: " + description + " by "
                + DateTimeUtil.formatForDisplay(by.getValue(), by.hasTime()));
    }

    /** Adds an event with its start and end date/time. */
    public void createEvent(String description, DateTimeUtil.ParsedDateTime from, DateTimeUtil.ParsedDateTime to) {
        this.tasks.add(new Event(description, from.getValue(), from.hasTime(), to.getValue(), to.hasTime()));
        saveTasks();
        print("> Dobby noted a new Event: " + description + " from "
                + DateTimeUtil.formatForDisplay(from.getValue(), from.hasTime()) + " to "
                + DateTimeUtil.formatForDisplay(to.getValue(), to.hasTime()));
    }

    /** Saves tasks and reports an error only if the file cannot be written. */
    private void saveTasks() {
        try {
            storage.save(tasks.asList());
        } catch (IOException | SecurityException e) {
            print("> Dobby could not save the task list.");
        }
    }

    /** Loads saved tasks and reports an error only if the file cannot be read. */
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

    private void print(String str) {
        DobbyUtil.print(str);
    }
}
