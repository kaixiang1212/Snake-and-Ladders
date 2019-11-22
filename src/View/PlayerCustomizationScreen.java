package View;

import Controller.PlayerCustomizationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerCustomizationScreen {

	private static String screenTitle;
	private static int numPlayer;
    private static int boardNum;

    public PlayerCustomizationScreen(int nPlayer, int board){
        screenTitle = "Player Customisation";
        numPlayer = nPlayer;
        boardNum = board;
    }

    public static void start(){	
        try {
        	FXMLLoader fxmlLoader = new FXMLLoader(PlayerCustomizationScreen.class.getResource("fxml/PlayerCustomization.fxml"));
        	Parent root = fxmlLoader.load();
        	PlayerCustomizationController playerCustomizationController = fxmlLoader.getController();
            playerCustomizationController.setPlayers(numPlayer);
            playerCustomizationController.setBoard(boardNum);
            Stage stage = StartGameScreen.getStage();
            stage.setTitle(screenTitle);
            Scene sc = new Scene(root);
            playerCustomizationController.setStage(stage);
            stage.setScene(sc);
            stage.show();
            sc.getRoot().requestFocus();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
