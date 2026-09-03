import java.util.Scanner;

/**
 * Handles Dobby's console input and output.
 */
public class Ui {
    /** Source of commands entered by the user. */
    private final Scanner scanner;

    /** Creates a UI that reads commands from standard input. */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /** Displays Dobby's welcome message. */
    public void showWelcome() {
        showLine();
        print(DobbyUtil.BANNER);
        print("> Dobby says hi!");
        print("> Dobby is ready to take orders.");
        showLine();
    }

    /** Displays the command prompt. */
    public void showPrompt() {
        System.out.print("Tell Dobby: ");
    }

    /** Reads the next command, or returns {@code null} after input ends. */
    public String readCommand() {
        return scanner.hasNextLine() ? scanner.nextLine() : null;
    }

    /** Displays the standard divider line. */
    public void showLine() {
        print(DobbyUtil.LINE);
    }

    /** Displays Dobby's farewell message. */
    public void showGoodbye() {
        showLine();
        print("> Dobby says goodbye to master!");
        showLine();
    }

    /** Prints one message followed by a line break. */
    private void print(String message) {
        DobbyUtil.print(message);
    }
}
