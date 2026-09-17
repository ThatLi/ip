package dobby.ui.gui;

import dobby.Dobby;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controls the main Dobby chat window defined in FXML.
 */
public class MainWindow extends AnchorPane {
    private final Image userImage = new Image(getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image dobbyImage = new Image(getClass().getResourceAsStream("/images/DobbyProfile.jpg"));

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;

    private Dobby dobby;

    /** Configures listeners after FXML fields have been injected. */
    @FXML
    public void initialize() {
        dialogContainer.heightProperty().addListener(observable -> scrollPane.setVvalue(1.0));
    }

    /**
     * Supplies the application facade used to process user commands.
     *
     * @param dobby Dobby application facade.
     */
    public void setDobby(Dobby dobby) {
        this.dobby = dobby;
    }

    /** Adds the user's message and Dobby's response, then clears the input field. */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = dobby.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDobbyDialog(response, dobbyImage));
        userInput.clear();
    }
}
