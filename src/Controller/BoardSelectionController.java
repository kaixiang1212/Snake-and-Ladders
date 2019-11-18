
package Controller;

import View.PlayerNumSelectionScreen;
import View.StartGameScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

public class BoardSelectionController {
	
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


    public BoardSelectionController(){
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

    @FXML
    private void nextButtonClicked(){
        MusicController.playNext();
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
        new PlayerNumSelectionScreen(board);
        PlayerNumSelectionScreen.start();
    }

    @FXML
    private void backButtonClicked() throws IOException {
    	MusicController.playBack();
        StartGameScreen.start();
    }

}
