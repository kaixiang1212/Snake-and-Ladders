package Controller;

import Model.GameEngine;
import Model.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.*;

public class DiceController {

    @FXML
    private Button button;
    @FXML
    private AnchorPane diceInterface;
    @FXML
    private ImageView diceImage;
    @FXML
    private Text text;
    @FXML
    private Text message;
    @FXML
    private ImageView playerToken;
    @FXML
    private Button menuButton;

    private GameEngine players;
    private MusicController musicController;
    private AnimationController animationController;
    private BoardController boardController;

    private final Image[] diceFace;
    private int currentPos;
    private int destination;
    private int diceResult;
    private boolean isPaused;

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
        animationController = new AnimationController(players, this);
        animationController.getAnimation().start();
    }

    /**
     * Configuration for Dice Controller
     * called by BoardController to communicate with board
     *
     * @param engine Game Engine
     */
    void config(GameEngine engine, BoardController boardController) {
        this.players = engine;
        this.boardController = boardController;
        animationController.setEngine(engine);
        setCurrentPlayerToken();
        Player player = players.getCurrentPlayer();
        StringBuilder sb = new StringBuilder();
        message.setText((sb.append("\n").append(player.getPlayerName()).append("'s turn:\n").toString()));
    }
    
    /**
     * Called when the 'roll' button is clicked
     */
    @FXML
    private void rollButtonClicked() {
        musicController.playRollDice();
        text.setText("");
        animationController.setSpinning(true);
        button.setText("Stop");
        button.setOnAction(event -> stopButtonClicked());
        diceImage.setOnMouseClicked(mouseEvent -> stopButtonClicked());
    }
    
    /**
     * Called when the 'stop' button is clicked
     */
    @FXML
	public void stopButtonClicked() {
        musicController.clear();
        animationController.setSpinning(false);
        button.setDisable(true);
        diceImage.setDisable(true);   

        Player currentPlayer = players.getCurrentPlayer();
        diceResult = getDiceRolled();
        text.setText(currentPlayer.getPlayerName() + " rolled " + diceResult);
        musicController.playThrowDice();
        
    	if(!players.isFinished()) {
            currentPos = getCurrentPos();
            destination = currentPos+diceResult;
            destination = getDestination();        
            animationController.setPlayerMoving(true);
    	}
        
    }

    /**
     * Called when the 'menu' button is clicked
     */
    @FXML
	public void menuButtonClicked() {
    	if (isPaused == false) {
            isPaused = true;
            button.setDisable(true);
            diceImage.setDisable(true);
            boardController.showMenu();
    	} else {
            boardController.hideMenu();
            if(!players.isFinished()) {
	            diceImage.setDisable(false);
	            button.setDisable(false);
            }
            isPaused = false;
    	}
    }
    
    
    /**
     * Called in-between player turns to prepare for the next player roll
     */
    public void prepareNextTurn() {
    	animationController.setSpinning(false);
    	animationController.setPlayerMoving(false);
    	players.updateState();
    	Player currPlayer = players.getCurrentPlayer();
    	StringBuilder sb = new StringBuilder();
    	if (players.isFinished()) {
            sb.append(currPlayer.getPlayerName()).append(" has won the game! Congratulations!\n");
            message.setText((sb.toString()));
            return;
        }
    	
    	button.setDisable(false);
        diceImage.setDisable(false);

    	if (diceResult == 6) {
            musicController.playRolled6();
            sb.append(currPlayer.getPlayerName().concat(" roll again"));
        } else {
        	players.nextPlayer();
        	currPlayer = players.getCurrentPlayer();
        }
    	
    	sb.append("\n");
    	sb.append(players.getCurrentPlayer().getPlayerName()).append("'s turn:\n");
    	message.setText(sb.toString());
    	
        button.setText("Start Rolling");
        diceImage.setOnMouseClicked(mouseEvent -> rollButtonClicked());
        button.setOnAction(event -> rollButtonClicked());
        players.clearConsole();
        setCurrentPlayerToken();
        text.setText("");
        animationController.setPoison(currPlayer.getPoisonStatus());

    }
    
    /**
     * Returns the current player's current position on the board
     * @return current player's current board position
     */
    public int getCurrentPos() {
    	Player currentPlayer = players.getCurrentPlayer();
    	return players.getBoard().getPosition(currentPlayer.getX(), currentPlayer.getY());
    }
    
    /**
     * Returns the destined position of the current player on the board
     * @return destination of current player
     */
    public int getDestination() {
    	destination = Math.max(destination, players.getBoard().getMinPos());
        destination = Math.min(destination, players.getBoard().getMaxPos());
    	return destination;
    }

    /**
     * Draw the given number on Dice
     *
     * @param dieFace Number to be drawn
     */
    public void draw(int dieFace) {
        Image image = this.diceFace[dieFace - 1];
        diceImage.setImage(image);
    }
    
    /**
     * Updates the current player's token to the correct player
     */
    private void setCurrentPlayerToken() {
        int token = players.getCurrentPlayerToken();
        playerToken.setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/token" + token + ".png"))));
    }

    /**
     * Get last randomly rolled dice image as result
     * @return dice result between 1 - 6
     */
    private int getDiceRolled(){
        Image lastRolled = diceImage.getImage();
        for(int i=0; i<6;i++){
            if (lastRolled == diceFace[i]) return i+1;
        }
        return -1;
    }

}