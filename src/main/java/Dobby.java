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
        DobbyLogic dobby = new DobbyLogic();
        String line = DobbyUtil.LINE;

        // Initialize chatbot
        print(line);
        print(DobbyUtil.BANNER);
        print("> Dobby says hi!");
        print("> Dobby is ready to take orders.");
        print(line);

        // Take user inputs
        System.out.print("Tell Dobby: ");
        while (scanner.hasNextLine()) {
            String input = scanner.nextLine();
            if (dobby.isBye(input)) {
                break;
            }
            print(line);
            dobby.listen(input);
            print(line);
            System.out.print("Tell Dobby: ");
        }

        // Exit chatbot
        print(line);
        print("> Dobby says goodbye to master!");
        print(line);
    }

    private static void print(String str) {
        DobbyUtil.print(str);
    }
}
