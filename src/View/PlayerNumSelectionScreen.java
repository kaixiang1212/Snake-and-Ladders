package View;

import Controller.PlayerNumSelectionController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerNumSelectionScreen {

    
    private static Stage stage;
    private static String screenTitle;
    private static FXMLLoader fxmlLoader;
    private static int boardNum;

    public PlayerNumSelectionScreen(Stage s, int board) {
        stage = s;
        screenTitle = "Player Selection";
        fxmlLoader = new FXMLLoader(getClass().getResource("fxml/playerNumberSelection.fxml"));
        boardNum = board;
    }

    public static void start() {
        stage.setTitle(screenTitle);
        try {
            Parent root = fxmlLoader.load();
            ((PlayerNumSelectionController)fxmlLoader.getController()).setStage(stage);
            ((PlayerNumSelectionController)fxmlLoader.getController()).setBoard(boardNum);
            Scene sc = new Scene(root);
            stage.setScene(sc);
            stage.show();
            sc.getRoot().requestFocus();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}