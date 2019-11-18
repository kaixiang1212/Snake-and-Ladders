package View;

import Controller.PlayerNumSelectionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerNumSelectionScreen {

    private Stage stage;
    private String screenTitle;
    private FXMLLoader fxmlLoader;
    private int boardNum;

    public PlayerNumSelectionScreen(Stage stage, int board) {
        this.stage = stage;
        this.screenTitle = "Player Selection";
        this.boardNum = board;
        this.fxmlLoader = new FXMLLoader(getClass().getResource("fxml/playerNumberSelection.fxml"));
    }

    public void start() {
        stage.setTitle(screenTitle);
        try {
            Parent root = fxmlLoader.load();
            ((PlayerNumSelectionController )fxmlLoader.getController()).setStage(stage);
            ((PlayerNumSelectionController )fxmlLoader.getController()).setBoard(boardNum);
            Scene sc = new Scene(root);
            stage.setScene(sc);
            stage.show();
            sc.getRoot().requestFocus();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}