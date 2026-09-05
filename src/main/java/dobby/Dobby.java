package dobby;

import dobby.command.Command;
import dobby.logic.DobbyLogic;
import dobby.parser.Parser;
import dobby.ui.cli.Ui;

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
        ui.showMessage(dobby.getStartupMessage());
        ui.showWelcome();

        // Take user inputs
        ui.showPrompt();
        String input;
        while ((input = ui.readCommand()) != null) {
            Command command = Parser.parse(input);
            if (command.isExit()) {
                break;
            }
            ui.showLine();
            ui.showMessage(command.execute(dobby));
            ui.showLine();
            ui.showPrompt();
        }

        // Exit chatbot
        ui.showGoodbye();
    }
}
