
package Controller;

import View.PlayerCustomizationScreen;
import View.PlayerNumSelectionScreen;
import View.StartGameScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.IOException;

public class BoardSelectionController {

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

    private MusicController musicController;


    public BoardSelectionController(){
        musicController = new MusicController();
        musicController.initUI();
    }

    public void numberClicked(){
        musicController.clear();
        nextButton.setDisable(false);
        if (playerNumber.getSelectedToggle() == null){
            nextButtonClicked();
            return;
        }
        musicController.playSwitch();
        playerSelected = playerNumber.getSelectedToggle();
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void nextButtonClicked(){
        musicController.playNext();
        int board = -1;
        if (player1 == playerSelected){
            board = 1;
        } else if (player2 == playerSelected){
            board = 2;
        } else if (player3 == playerSelected){
            board = 3;
        } else if (player4 == playerSelected) {
            board = 4;
        } else {
            return;
        }
        PlayerNumSelectionScreen playerNumSelectionScreen = new PlayerNumSelectionScreen(stage, board);
        playerNumSelectionScreen.start();
    }

    @FXML
    private void backButtonClicked() throws IOException {
        musicController.playBack();
        StartGameScreen startGameScreen = new StartGameScreen(stage);
        startGameScreen.start();
    }

}
