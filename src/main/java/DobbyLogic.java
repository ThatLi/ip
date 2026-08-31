import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        EVENT
    }
    private static final Map<String, Command> COMMANDS = new HashMap<>();
    static {
        for (Command command : Command.values()) {
            COMMANDS.put(command.name().toLowerCase(Locale.ROOT), command);
        }
    }

    /** Store tasks recorded since the start of program */
    private final List<Task> tasks = new ArrayList<>();

    /**
     * Call appropriate methods or add task to input, depending on user command in input
     * @param input User command or task to be added
     */
    public void listen(String input) {
        // Split input into array of words, removing all spaces
        String[] inputs = input.split("\\s+");
        // whitespaces-only case
        if (inputs.length == 0) {
            print("> Dobby couldn't hear you. Dobby want you to speak louder!");
            return;
        }
        Command command = COMMANDS.get(inputs[0].toLowerCase(Locale.ROOT));


        // instantiate tasks
        if (command == null) {
            this.tasks.add(new Task(input));
            print("> Dobby noted: " + input);
            return;
        }

        switch (command) {
            case LIST:
                this.doList();
                break;

            case MARK, UNMARK:
                this.doMark(inputs, command);
                break;

            case TODO, DEADLINE, EVENT:
                this.createTask(inputs, command);
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
        for (Task t : this.tasks) {
            count++;
            res.append(count).append(". ").append(t).append("\n");
        }
        print(res.toString());
    }

    /**
     * Parse Mark & Unmark command, calls respective marking command
     * @param inputs [MARK/ UNMARK, int index]
     */
    private void doMark(String[] inputs, Command command) {
        // Check valid command arguments
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
            print("> Dobby will mark this as done!");
            print("   " + t);
        } else {
            t.markNotDone();
            print("> Dobby will mark this as not done!");
            print("   " + t);
        }
    }

    public void createTask(String[] inputs, Command command) {
        int len = inputs.length - 1;

        // Take Strings from inputs[1:]
        String[] descriptions = new String[len];
        System.arraycopy(inputs, 1, descriptions, 0, len);
        String description = String.join(" ", descriptions);

        switch (command) {
            case TODO:
                this.createToDo(description);
                break;

            case DEADLINE:
                this.createDeadline(description);
                break;

            case EVENT:
                this.createEvent(description);
                break;
        }
    }

    public void createToDo(String str) {
        this.tasks.add(new ToDo(str));
    }

    public void createDeadline(String str) {
        this.tasks.add(new Deadline(str, ""));
    }

    public void createEvent(String str) {
        this.tasks.add(new Event(str, "", ""));
    }

    private void print(String str) {
        DobbyUtil.print(str);
    }
}