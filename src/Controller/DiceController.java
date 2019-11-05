package Controller;

import Model.Dice;
import Model.GameEngine;
import Model.Player;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.*;

public class DiceController {

    @FXML
    public Button button;
    @FXML
    public AnchorPane diceInterface;
    @FXML
    private ImageView diceImage;
    @FXML
    private Text text;
    @FXML
    private Text message;
    @FXML
    private ImageView playerToken;

    private GameEngine players;
    private Dice dice;
    private final Image[] diceFace;
    private boolean isSpinning;
    private boolean playerIsMoving;

    private MusicController musicController;

    public DiceController() {
        this.diceFace = new Image[6];
        diceFace[0] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice1.png")));
        diceFace[1] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice2.png")));
        diceFace[2] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice3.png")));
        diceFace[3] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice4.png")));
        diceFace[4] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice5.png")));
        diceFace[5] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice6.png")));
        musicController = new MusicController();
        musicController.initDice();
        dice = new Dice();
        animation.start();
        isSpinning = false;
        playerIsMoving = false;
    }

    /**
     * Configuration for Dice Controller
     * called by Board Controller to communicate with board
     *
     * @param engine Game Engine
     */
    void config(GameEngine engine) {
        this.players = engine;
        setCurrentPlayerToken();
        Player player = players.getCurrentPlayer();
        StringBuilder sb = new StringBuilder();
        message.setText(sb.append("\n").append(player.getPlayerName()).append("'s turn:\n").toString());
    }

    @FXML
    private void rollButtonClicked() {
        musicController.playRollDice();
        text.setText("");
        //animation.start();
        isSpinning = true;
        button.setText("Stop");
        button.setOnAction(event -> stopButtonClicked());
        diceImage.setOnMouseClicked(mouseEvent -> stopButtonClicked());
    }

    @FXML
    private void stopButtonClicked() {
        musicController.clear();
        //animation.stop();
        isSpinning = false;
        button.setDisable(true);
        diceImage.setDisable(true);
        

        Player currentPlayer = players.getCurrentPlayer();
        int diceResult = dice.roll();
        text.setText(currentPlayer.getPlayerName() + " rolled " + diceResult);
        musicController.playThrowDice();
        draw(diceResult);

    	if(!players.isFinished()) {
    		
            currentPos = players.getBoard().getPosition(currentPlayer.getX(), currentPlayer.getY());
            destination = currentPos+diceResult;
            playerToMove = currentPlayer; 
            dieRolled = diceResult;
            playerIsMoving = true;
            
    	}

        
    }

    /**
     * Draw the given number on Dice
     *
     * @param dieFace Number to be drawn
     */
    private void draw(int dieFace) {
        Image image = this.diceFace[dieFace - 1];
        diceImage.setImage(image);
    }

    //private final int maxFrame = 1000;
    private int frame = 0;
    private int currentPos;
    private int destination;
    private Player playerToMove;
    private int dieRolled;

    /**
     * Randomise Dice face for 1000 frames
     * after 1000 frames it automatically stops
     */
    private AnimationTimer animation = new AnimationTimer() {
        public void handle(long time) {
        	if (isSpinning) {
        		int diceFrame = (int) (Math.random() * 6) + 1;
        		draw(diceFrame);
            	frame++;
            	
        	} else if (playerIsMoving) {
        		
        		if (currentPos == destination) {
        			
        	        StringBuilder sb = new StringBuilder();
        			
        			if (players.isFinished()) {
        	            sb.append(playerToMove.getPlayerName()).append(" has won the game! Congratulations!\n");
        	            message.setText(sb.toString());
        	            return;
        	        }

        	        if (dieRolled == 6) {
        	            musicController.playRolled6();
        	            sb.append(playerToMove.getPlayerName()).append(" roll again\n");
        	        } else {
        	            sb.append("\n");
        	            playerToMove = players.nextPlayer();
        	        }

        	        sb.append(playerToMove.getPlayerName()).append("'s turn:\n");

        	        button.setDisable(false);
        	        diceImage.setDisable(false);
        	        button.setText("Start Rolling");
        	        diceImage.setOnMouseClicked(mouseEvent -> rollButtonClicked());
        	        button.setOnAction(event -> rollButtonClicked());

        	        message.setText(sb.toString());
        	        players.clearConsole();
        	        setCurrentPlayerToken();
        			players.updateState();
        			playerIsMoving = false;
        			
        		} else if (frame >= 8 && currentPos <= destination) {
        			
        			players.updatePosition(playerToMove, currentPos + 1);
        			currentPos++;
        			frame = 0;

        		}
        		draw(dieRolled);
        		frame++;
        		
        	}
        }
    };

    private void setCurrentPlayerToken() {
        int token = players.getCurrentPlayerToken();
        playerToken.setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/token" + token + ".png"))));
    }

}