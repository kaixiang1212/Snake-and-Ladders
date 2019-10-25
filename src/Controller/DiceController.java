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
    public Button button;
    @FXML
    private ImageView diceImage;
    @FXML
    private Text text;

    private Stage stage;
    private GameEngine players;
    private final Image[] diceFace;

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
        text.setText("");
        animation.start();
        button.setText("Stop");
        button.setOnAction(event -> stopButtonClicked());
    }

    @FXML
    private void stopButtonClicked(){
        animation.stop();
        button.setText("Start Rolling");

        Player currentPlayer = players.getCurrentPlayer();
        int diceResult = players.rollDice();
        text.setText(currentPlayer.getPlayerName() + " rolled " + diceResult);
        draw(diceResult);

        if (players.isFinished()) {
            System.out.println(currentPlayer.getPlayerName() + " has won the game! Congratulations!");
            // TODO: Handle endgame
            return;
        } else if (diceResult == 6){
            System.out.println(currentPlayer.getPlayerName() + " roll again");
            System.out.println("\n" + currentPlayer.getPlayerName() + "'s turn:");
        } else {
            players.nextPlayer();
        }

        button.setOnAction(event -> rollButtonClicked());
    }

    private void draw(int dieFace){
        Image image = this.diceFace[dieFace-1];
        diceImage.setImage(image);
    }


    private final int maxFrame = 1000;
    private int frame = 0;
    /**
     * Randomise Dice face for infinite frames
     */
    private AnimationTimer animation = new AnimationTimer() {
        public void handle( long time ) {
            int diceFrame = (int)(Math.random()*6) + 1;
            draw(diceFrame);
            frame++;
            if (frame == maxFrame){
                stopButtonClicked();
                frame = 0;
            }
        }
    };

}
