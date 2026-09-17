package dobby.ui.gui;

import javafx.geometry.Pos;
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
    public DialogBox(String message, Image image) {
        Label text = new Label(message);
        ImageView displayPicture = new ImageView(image);

        text.setWrapText(true);
        displayPicture.setFitWidth(AVATAR_SIZE);
        displayPicture.setFitHeight(AVATAR_SIZE);
        setAlignment(Pos.TOP_RIGHT);
        getChildren().addAll(text, displayPicture);
    }
}
