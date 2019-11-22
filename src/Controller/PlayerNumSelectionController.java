
package Controller;

import View.BoardSelectionScreen;
import View.PlayerCustomizationScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

import Model.Board.BoardType;

public class PlayerNumSelectionController {

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
    private BoardType boardType;
    
    public PlayerNumSelectionController(){
    	MusicController.initUI();
    }

    public void numberClicked(){
    	MusicController.clear();
        nextButton.setDisable(false);
        if (playerNumber.getSelectedToggle() == null){
            nextButtonClicked();
            return;
        }
        MusicController.playSwitch();
        playerSelected = playerNumber.getSelectedToggle();
    }

    public void config(BoardType type) {
    	boardType = type;
    }

    @FXML
    private void nextButtonClicked(){
    	MusicController.playNext();
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

        new PlayerCustomizationScreen(player, boardType);
        PlayerCustomizationScreen.start();

    }

    @FXML
    private void backButtonClicked() throws IOException {
        MusicController.playBack();
        BoardSelectionScreen.start();
    }

}
