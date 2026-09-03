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
        DobbyLogic dobby = new DobbyLogic();
        Ui ui = new Ui();

        // Initialize chatbot
        ui.showWelcome();

        // Take user inputs
        ui.showPrompt();
        String input;
        while ((input = ui.readCommand()) != null) {
            Command command = Parser.parse(input);
            if (command != null && command.isExit()) {
                break;
            }
            ui.showLine();
            if (command != null) {
                command.execute(dobby);
            } else {
                dobby.listen(input);
            }
            ui.showLine();
            ui.showPrompt();
        }

        // Exit chatbot
        ui.showGoodbye();
    }
}
