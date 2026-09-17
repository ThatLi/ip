package dobby.ui.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 * Displays Dobby's JavaFX user interface.
 */
public class Main extends Application {
    /**
     * Creates and displays the primary application window.
     *
     * @param stage Primary stage supplied by JavaFX.
     */
    @Override
    public void start(Stage stage) {
        Label helloWorld = new Label("Hello World!");
        Scene scene = new Scene(helloWorld);
        stage.setScene(scene);
        stage.show();
    }
}
