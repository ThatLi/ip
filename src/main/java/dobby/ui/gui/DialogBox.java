package dobby.ui.gui;

import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Displays a chat message beside an avatar.
 */
public class DialogBox extends HBox {
    private static final double AVATAR_SIZE = 100.0;

    /**
     * Creates a right-aligned chat message.
     *
     * @param message Message to display.
     * @param image Avatar representing the speaker.
     */
    private DialogBox(String message, Image image) {
        Label text = new Label(message);
        ImageView displayPicture = new ImageView(image);

        text.setWrapText(true);
        displayPicture.setFitWidth(AVATAR_SIZE);
        displayPicture.setFitHeight(AVATAR_SIZE);
        setAlignment(Pos.TOP_RIGHT);
        getChildren().addAll(text, displayPicture);
    }

    /** Flips the dialog so Dobby's avatar appears on the left. */
    private void flip() {
        ObservableList<Node> children = FXCollections.observableArrayList(getChildren());
        Collections.reverse(children);
        getChildren().setAll(children);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog for a user message.
     *
     * @param message Message to display.
     * @param image User avatar.
     * @return right-aligned user dialog.
     */
    public static DialogBox getUserDialog(String message, Image image) {
        return new DialogBox(message, image);
    }

    /**
     * Creates a dialog for a Dobby response.
     *
     * @param message Message to display.
     * @param image Dobby avatar.
     * @return left-aligned Dobby dialog.
     */
    public static DialogBox getDobbyDialog(String message, Image image) {
        DialogBox dialogBox = new DialogBox(message, image);
        dialogBox.flip();
        return dialogBox;
    }
}
