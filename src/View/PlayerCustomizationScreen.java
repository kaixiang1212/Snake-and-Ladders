package View;

import Controller.PlayerCustomizationController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerCustomizationScreen {
    private Stage stage;
    private FXMLLoader fxmlLoader;
    private String screenTitle;
    private int numPlayer;

    public PlayerCustomizationScreen(Stage stage, int numPlayer){
        this.stage = stage;
        this.screenTitle = "Player Customisation";
        this.numPlayer = numPlayer;
        this.fxmlLoader = new FXMLLoader(getClass().getResource("fxml/PlayerCustomization.fxml"));
    }

    public void start(){
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
