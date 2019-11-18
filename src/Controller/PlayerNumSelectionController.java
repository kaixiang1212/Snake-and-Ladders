
package Controller;

import View.BoardSelectionScreen;
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
    
    private int boardNum;

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


    public PlayerNumSelectionController(){
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
    
    public void setBoard(int board) {
    	this.boardNum = board;
    }

    @FXML
    private void nextButtonClicked(){
        musicController.playNext();
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
        PlayerCustomizationScreen playerCustomizationScreen = new PlayerCustomizationScreen(stage, player, boardNum);
        playerCustomizationScreen.start();
    }

    @FXML
    private void backButtonClicked() throws IOException {
        musicController.playBack();
        BoardSelectionScreen boardSelectionScreen = new BoardSelectionScreen(stage);
        boardSelectionScreen.start();
    }

}
