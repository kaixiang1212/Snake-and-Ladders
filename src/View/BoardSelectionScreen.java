package View;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardSelectionScreen {

    private static String screenTitle;

    public BoardSelectionScreen() {
        screenTitle = "Board Selection";
    }

    public static void start() {
        try {
        	Parent root = FXMLLoader.load(BoardSelectionScreen.class.getResource("fxml/boardSelection.fxml"));
        	Scene sc = new Scene(root);
            Stage stage = StartGameScreen.getStage();
            stage.setTitle(screenTitle);
            stage.setScene(sc);
            stage.show();
            sc.getRoot().requestFocus();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}