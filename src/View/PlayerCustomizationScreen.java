package View;

import Controller.PlayerCustomizationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerCustomizationScreen {
    private static Stage stage;
    private static FXMLLoader fxmlLoader;
    private static String screenTitle;
    private static int numPlayer;

    public PlayerCustomizationScreen(Stage s, int nPlayer){
        stage = s;
        screenTitle = "Player Customisation";
        numPlayer = nPlayer;
        fxmlLoader = new FXMLLoader(getClass().getResource("fxml/PlayerCustomization.fxml"));
    }

    public static void start(){
        stage.setTitle(screenTitle);
        try {
            Parent root = fxmlLoader.load();
            PlayerCustomizationController playerCustomizationController = fxmlLoader.getController();
            playerCustomizationController.setStage(stage);
            playerCustomizationController.setPlayers(numPlayer);

            Scene sc = new Scene(root);
            stage.setScene(sc);
            stage.show();
            sc.getRoot().requestFocus();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
