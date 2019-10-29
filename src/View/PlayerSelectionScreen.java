package View;

import Controller.PlayerSelectionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerSelectionScreen {

    private Stage stage;
    private String screenTitle;
    private FXMLLoader fxmlLoader;

    public PlayerSelectionScreen(Stage stage) {
        this.stage = stage;
        this.screenTitle = "Player Selection";
        this.fxmlLoader = new FXMLLoader(getClass().getResource("fxml/PlayerSelection.fxml"));
    }

    public void start() {
        stage.setTitle(screenTitle);
        fxmlLoader.setController(new PlayerSelectionController(stage));
        try {
            Parent root = fxmlLoader.load();
            Scene sc = new Scene(root);
            stage.setScene(sc);
            stage.show();
            sc.getRoot().requestFocus();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}