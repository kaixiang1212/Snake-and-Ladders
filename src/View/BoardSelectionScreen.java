package View;

import Controller.BoardSelectionController;
import Controller.PlayerNumSelectionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardSelectionScreen {

    private Stage stage;
    private String screenTitle;
    private FXMLLoader fxmlLoader;

    public BoardSelectionScreen(Stage stage) {
        this.stage = stage;
        this.screenTitle = "Board Selection";
        this.fxmlLoader = new FXMLLoader(getClass().getResource("fxml/boardSelection.fxml"));
    }

    public void start() {
        stage.setTitle(screenTitle);
        try {
            Parent root = fxmlLoader.load();
            ((BoardSelectionController)fxmlLoader.getController()).setStage(stage);
            Scene sc = new Scene(root);
            stage.setScene(sc);
            stage.show();
            sc.getRoot().requestFocus();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}