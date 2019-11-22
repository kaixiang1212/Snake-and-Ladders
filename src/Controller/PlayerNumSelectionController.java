
package Controller;

import View.BoardSelectionScreen;
import View.PlayerCustomizationScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;

import java.io.IOException;

import Model.GameEngine;
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
    @FXML
    private Button togglePowerups;
    @FXML
    private Button dynamicSnakes;
    
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
    	if(DiceController.isPowerupsEnabled()) {
    		togglePowerups.setText("Power-Ups: ON");
    	} else {
    		togglePowerups.setText("Power-Ups: OFF");
    	}
    	if(GameEngine.isDynamicSnakes()) {
    		dynamicSnakes.setText("Dynamic Snakes: ON");
    	} else {
    		dynamicSnakes.setText("Dynamic Snakes: OFF");
    	}
    	Tooltip t = new Tooltip("Enable or disable power-ups appearing randomly in-game");
    	t.setStyle("-fx-font-size: 16");
    	Tooltip.install(togglePowerups, t);
    	Tooltip t2 = new Tooltip("Enable or disable snake effects.\n(Poisonous snakes, shifting snakes, ...etc)");
    	t2.setStyle("-fx-font-size: 16");
    	Tooltip.install(dynamicSnakes, t2);
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
    
    @FXML
    private void togglePowerupsClicked() {
    	DiceController.setPowerupsEnabled(!DiceController.isPowerupsEnabled());
    	if(DiceController.isPowerupsEnabled()) {
    		togglePowerups.setText("Power-Ups: ON");
    		MusicController.playSwitch();
    	} else {
    		togglePowerups.setText("Power-Ups: OFF");
    		MusicController.playBack();
    	}
    }
    
    @FXML
    private void dynamicSnakesClicked() {
    	GameEngine.setDynamicSnakes(!GameEngine.isDynamicSnakes());
    	if(GameEngine.isDynamicSnakes()) {
    		dynamicSnakes.setText("Dynamic Snakes: ON");
    		MusicController.playSwitch();
    	} else {
    		dynamicSnakes.setText("Dynamic Snakes: OFF");
    		MusicController.playBack();
    	}
    }

}
