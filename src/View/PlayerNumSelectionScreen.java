package View;

import Controller.PlayerNumSelectionController;
import Model.Board.BoardType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerNumSelectionScreen {

    private static String screenTitle;
    private static BoardType boardType;

    public PlayerNumSelectionScreen(BoardType type) {
        screenTitle = "Player Selection";
        boardType = type;
    }

    public static void start() {
        
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(PlayerNumSelectionScreen.class.getResource("fxml/playerNumberSelection.fxml"));
            Parent root = fxmlLoader.load();
            ((PlayerNumSelectionController)fxmlLoader.getController()).config(boardType);
            Stage stage = StartGameScreen.getStage();
            stage.setTitle(screenTitle);
            Scene sc = new Scene(root);
            stage.setScene(sc);
            stage.show();
            sc.getRoot().requestFocus();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}