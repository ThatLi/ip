package dobby.ui.gui;

import dobby.Dobby;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Displays Dobby's JavaFX user interface.
 */
public class Main extends Application {
    private final Image userImage = new Image(getClass().getResourceAsStream("/images/DaUser.png"));
    private final Image dobbyImage = new Image(getClass().getResourceAsStream("/images/DaDobby.png"));
    private final Dobby dobby = new Dobby();

    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;

    /**
     * Creates and displays the primary application window.
     *
     * @param stage Primary stage supplied by JavaFX.
     */
    @Override
    public void start(Stage stage) {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        Button sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        configureLayout(stage, mainLayout, scrollPane, dialogContainer, userInput, sendButton);
        sendButton.setOnAction(event -> handleUserInput());
        userInput.setOnAction(event -> handleUserInput());
        dialogContainer.heightProperty().addListener(observable -> scrollPane.setVvalue(1.0));

        stage.setScene(new Scene(mainLayout));
        stage.show();
    }

    /** Adds the user's message and Dobby's response, then clears the input field. */
    private void handleUserInput() {
        String input = userInput.getText();
        String response = dobby.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDobbyDialog(response, dobbyImage));
        userInput.clear();
    }

    /** Configures the fixed-size layout used by the initial GUI prototype. */
    private void configureLayout(Stage stage, AnchorPane mainLayout, ScrollPane scrollPane,
            VBox dialogContainer, TextField userInput, Button sendButton) {
        stage.setTitle("Dobby");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);
        scrollPane.setPrefSize(385.0, 535.0);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);
        userInput.setPrefWidth(325.0);
        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);
        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);
        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);
    }
}
