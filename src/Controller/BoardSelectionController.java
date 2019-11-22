
package Controller;

import View.PlayerNumSelectionScreen;
import View.StartGameScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

import java.io.IOException;

import Model.Board.BoardType;

public class BoardSelectionController {
	
    @FXML
    private Label title;
    @FXML
    private ToggleGroup boardType;
    @FXML
    private Toggle board1;
    @FXML
    private Toggle board2;
    @FXML
    private Toggle board3;
    @FXML
    private Toggle board4;
    @FXML
    private Button backButton;
    @FXML
    private Button nextButton;

    private Toggle boardSelected;


    public BoardSelectionController(){
        MusicController.initUI();
    }

    public void numberClicked(){
        MusicController.clear();
        nextButton.setDisable(false);
        if (boardType.getSelectedToggle() == null){
            nextButtonClicked();
            return;
        }
        MusicController.playSwitch();
        boardSelected = boardType.getSelectedToggle();
    }

    @FXML
    private void nextButtonClicked(){
        MusicController.playNext();
        BoardType type;
        if (board1 == boardSelected){
            type = BoardType.DEFAULT;
        } else if (board2 == boardSelected){
            type = BoardType.PLAIN;
        } else if (board3 == boardSelected){
            type = BoardType.SNAKELESS;
        } else if (board4 == boardSelected) {
            type = BoardType.LADDERLESS;
        } else {
            return;
        }
        new PlayerNumSelectionScreen(type);
        PlayerNumSelectionScreen.start();
    }

    @FXML
    private void backButtonClicked() throws IOException {
    	MusicController.playBack();
        StartGameScreen.start();
    }

}
