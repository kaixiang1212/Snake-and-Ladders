
package Controller;

import View.PlayerCustomizationScreen;
import View.StartGameScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class PlayerNumSelectionController {

    private Stage stage;

    @FXML
    private Label title;
    @FXML
    private ToggleGroup playerNumber;
    @FXML
    private Toggle player1;
    @FXML
    private Toggle player2;
    @FXML
    private Toggle player3;
    @FXML
    private Toggle player4;
    @FXML
    private Button backButton;
    @FXML
    private Button nextButton;

    private Toggle playerSelected;


    public PlayerNumSelectionController(){}

    public void numberClicked(){
        nextButton.setDisable(false);
        if (playerNumber.getSelectedToggle() == null){
            nextButtonClicked();
        }
        playerSelected = playerNumber.getSelectedToggle();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void nextButtonClicked(){
        int player = -1;
        if (player1 == playerSelected){
            player = 1;
        } else if (player2 == playerSelected){
            player = 2;
        } else if (player3 == playerSelected){
            player = 3;
        } else if (player4 == playerSelected) {
            player = 4;
        } else {
            return;
        }
        PlayerCustomizationScreen playerCustomizationScreen = new PlayerCustomizationScreen(stage, player);
        playerCustomizationScreen.start();
    }

    @FXML
    private void backButtonClicked() throws IOException {
        StartGameScreen startGameScreen = new StartGameScreen(stage);
        startGameScreen.start();
    }
}
