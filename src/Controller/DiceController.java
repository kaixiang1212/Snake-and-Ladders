package Controller;

import Model.GameEngine;
import Model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;

public class DiceController {

    @FXML
    public Button rollButton;
    @FXML
    private ImageView diceImage;
    @FXML
    private Text text;

    private Stage stage;
    private GameEngine players;

    public DiceController(Stage stage, GameEngine players){
        this.players = players;
        this.stage = stage;
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void rollButtonClicked(){
    	rollButton.setDisable(true);
        Image image;
        Pair<Player, Integer> result = players.rollDice();
        Player currentPlayer = result.getKey();
        int dice = result.getValue();
        switch (dice){
            case 1:
                image = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice1.png")));
                break;
            case 2:
                image = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice2.png")));
                break;
            case 3:
                image = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice3.png")));
                break;
            case 4:
                image = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice4.png")));
                break;
            case 5:
                image = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice5.png")));
                break;
            case 6:
                image = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice6.png")));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + result);
        }
        diceImage.setImage(image);
        text.setText(currentPlayer.getPlayerName() + " rolled " + dice);
        if(players.isFinished()) {
        	System.out.println(currentPlayer.getPlayerName() + " has won the game! Congratulations!");
        	return;
        }
        if (dice == 6){
        	System.out.println(currentPlayer.getPlayerName() + " roll again");
        	System.out.println("\n" + currentPlayer.getPlayerName() + "'s turn:");
        } else {
        	players.nextPlayer();
        }
        
        rollButton.setDisable(false);
    }
    
}
