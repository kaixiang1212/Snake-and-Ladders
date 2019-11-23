package View;

import Controller.PlayerCustomizationController;
import Model.Board.BoardType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerCustomizationScreen {

	private static String screenTitle;
	private static int numPlayer;
    private static BoardType boardType;

    public PlayerCustomizationScreen(int nPlayer, BoardType type){
        screenTitle = "Player Customisation";
        numPlayer = nPlayer;
        boardType = type;
    }

    public static void start(){	
        try {
        	FXMLLoader fxmlLoader = new FXMLLoader(PlayerCustomizationScreen.class.getResource("fxml/PlayerCustomization.fxml"));
        	Parent root = fxmlLoader.load();
        	PlayerCustomizationController playerCustomizationController = fxmlLoader.getController();
        	playerCustomizationController.config(numPlayer, boardType);
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
