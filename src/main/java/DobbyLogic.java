import java.util.ArrayList;
import java.util.List;

/**
 * Evaluate user input
 */
public final class DobbyLogic {
    enum Command {
        LIST
    }
    List<Task> tasks = new ArrayList<>();
    int taskCount = 0;

    public void listen(String input) {
        Command command;

        try {
            command = Command.valueOf(input.toUpperCase());
        } catch (IllegalArgumentException e) {
            this.tasks.add(new Task(input));
            this.taskCount++;
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
        return input.equals("bye");
    }

    /**
     * Print all tasks as numbered list
     */
    private void doList() {
        StringBuilder res = new StringBuilder();
        res.append("Dobby show " + Integer.toString(this.taskCount) + " tasks:\n");
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