import java.util.ArrayList;
import java.util.List;

/**
 * Evaluate user input
 */
public final class DobbyLogic {
    enum Command {
        LIST
    }
    private final List<Task> tasks = new ArrayList<>();

    public void listen(String input) {
        Command command;

        try {
            command = Command.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.tasks.add(new Task(input));
            print("Dobby noted: " + input);
            return;
        }

        switch (command) {
            case LIST:
                this.doList();
                break;
        }
        return;
    }

    public boolean isBye(String input) {
        return input.equalsIgnoreCase("bye");
    }

    /**
     * Print all tasks as numbered list
     */
    private void doList() {
        StringBuilder res = new StringBuilder();
        res.append("Dobby show " + Integer.toString(this.tasks.size()) + " tasks:\n");
        int count = 0;
        for (Task t : this.tasks) {
            count++;
            res.append(Integer.toString(count) + ". " + t + "\n");
        }
        print(res.toString());
    }

    private void print(String str) {
        DobbyUtil.print(str);
    }
}