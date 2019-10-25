package Controller;

import Model.GameEngine;
import Model.Player;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class DiceController {

    @FXML
    public Button rollButton;
    @FXML
    private ImageView diceImage;
    @FXML
    private Text text;

    private Stage stage;
    private GameEngine players;
    private final Image[] diceFace;
    private int diceResult;
    private Player currentPlayer;

    public DiceController(Stage stage, GameEngine players){
        this.players = players;
        this.stage = stage;
        this.diceFace = new Image[6];
        diceFace[0] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice1.png")));
        diceFace[1] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice2.png")));
        diceFace[2] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice3.png")));
        diceFace[3] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice4.png")));
        diceFace[4] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice5.png")));
        diceFace[5] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice6.png")));
    }

    public void setStage(Stage stage){
        this.stage = stage;
    }

    @FXML
    private void rollButtonClicked(){
    	rollButton.setDisable(true);
    	currentPlayer = players.getCurrentPlayer();
        diceResult = players.rollDice();
        animation.start();

        if (players.isFinished()) {
        	System.out.println(currentPlayer.getPlayerName() + " has won the game! Congratulations!");
        	return;
        }
        if (diceResult == 6){
        	System.out.println(currentPlayer.getPlayerName() + " roll again");
        	System.out.println("\n" + currentPlayer.getPlayerName() + "'s turn:");
        } else {
        	players.nextPlayer();
        }
    }

    private void draw(int dieFace){
        Image image = this.diceFace[dieFace-1];
        diceImage.setImage(image);
    }

    private int frame = 0;

    /**
    Randomise Dice face for 30 frames
     */
    private AnimationTimer animation = new AnimationTimer() {
        public void handle( long time ) {
            int diceFrame = (int)(Math.random()*6) + 1;
            draw(diceFrame);
            frame++;
            if (frame == 30) {
                animation.stop();
                draw(diceResult);
                text.setText(currentPlayer.getPlayerName() + " rolled " + diceResult);
                rollButton.setDisable(false);
                frame = 0;
            }
        }
    };

}
