import java.util.Scanner;

/**
 * Starts the Dobby chatbot application.
 */
public class Dobby {
    /**
     * Displays a welcome banner when the chatbot starts.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = DobbyUtil.LINE;

        // Initialize chatbot
        print(line);
        print(DobbyUtil.BANNER);
        print("Dobby says hi!");
        print("Dobby is ready to take orders.");
        print(line);

        // Take user inputs
        String input = scanner.nextLine(); // could throw exception
        print(line);
        while (!DobbyLogic.isBye(input)) {
            print(input);
            print(line);
            input = scanner.nextLine();
        }

        // Exit chatbot
        print(line);
        print("Dobby says goodbye to master!");
        print(line);
    }

    // Class for Tasks:
    // will be stored in java collection
    // 2 states: done, not done
    // stores String Description
    // subtypes -> ToDos, Events, Deadlines
    // -> each stores String task, + deadline date, + start & end date
    // handle error
    // can be deleted

    private static void print(String str) {
        DobbyUtil.print(str);
    }
}
