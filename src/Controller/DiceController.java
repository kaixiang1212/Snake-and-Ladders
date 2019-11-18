package Controller;

import Model.GameEngine;
import Model.Player;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.*;

public class DiceController {
	
    @FXML
    private Button rollButton;
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

    private BoardController boardController;

    private final Image[] diceFace;
    private int currentPos;
    private int destination;
    private int diceResult;
    private boolean isPaused;
    private int spawnItemChance;		// Chance of an item spawning each turn in percentage

    public DiceController() {
        this.diceFace = new Image[6];
        diceFace[0] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice1.png")));
        diceFace[1] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice2.png")));
        diceFace[2] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice3.png")));
        diceFace[3] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice4.png")));
        diceFace[4] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice5.png")));
        diceFace[5] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice6.png")));
        MusicController.initDice();
        new AnimationController(this, boardController);
        AnimationController.getAnimation().start();
    }

    /**
     * Configuration for Dice Controller
     * called by BoardController to communicate with board
     *
     * @param engine Game Engine
     */
    void config(BoardController boardController) {
        this.boardController = boardController;
        setCurrentPlayerToken();
        Player player = GameEngine.getCurrentPlayer();
        StringBuilder sb = new StringBuilder();
        message.setText((sb.append("\n").append(player.getPlayerName()).append("'s turn:\n").toString()));
        spawnItemChance = GameEngine.getPlayerNum()*10;
        isPaused = false;
        rollButton.setDefaultButton(true);
        new AnimationController(this, this.boardController);
    }
    
    /**
     * Called when the 'roll' button is clicked
     */
    @FXML
    private void rollButtonClicked() {
    	MusicController.playRollDice();
        text.setText("");
        AnimationController.setSpinning(true);
        menuButton.setDisable(true);
        rollButton.setText("Stop");
        rollButton.setOnAction(event -> stopButtonClicked());
        diceImage.setOnMouseClicked(mouseEvent -> stopButtonClicked());
    }
    
    /**
     * Called when the 'stop' button is clicked
     */
    @FXML
	public void stopButtonClicked() {
    	MusicController.clear();
    	AnimationController.setSpinning(false);
    	rollButton.setDisable(true);
        diceImage.setDisable(true);  
        menuButton.setDisable(false);


        Player currentPlayer = GameEngine.getCurrentPlayer();
        diceResult = getDiceRolled();
        text.setText(currentPlayer.getPlayerName() + " rolled " + diceResult);
        MusicController.playThrowDice();
        
    	if(!GameEngine.isFinished()) {
            currentPos = getCurrentPos();
            destination = currentPos+diceResult;
            destination = Math.max(destination, GameEngine.getBoard().getMinPos());
            if(destination > GameEngine.getBoard().getMaxPos()) {
            	prepareNextTurn();
            	return;
            }
            AnimationController.setPlayerMoving(true);
    	}
    }

    /**
     * Called when the 'menu' button is clicked
     */
    @FXML
	public void menuButtonClicked() {
    	isPaused = !isPaused;
    	if (isPaused) {
    		rollButton.setDisable(true);
            diceImage.setDisable(true);
            boardController.showMenu();
    	} else {
            boardController.hideMenu();
            if(!GameEngine.isFinished()) {
	            diceImage.setDisable(false);
	            rollButton.setDisable(false);
            }
    	}
    }
    
    
    /**
     * Called in-between player turns to prepare for the next player roll
     */
    public void prepareNextTurn() {
    	AnimationController.setSpinning(false);
    	AnimationController.setPlayerMoving(false);
    	GameEngine.updateState();
    	StringBuilder sb = new StringBuilder();
    	if (GameEngine.isFinished()) {
            sb.append(GameEngine.getCurrentPlayer().getPlayerName()).append(" has won the game! Congratulations!\n");
            message.setText((sb.toString()));
            return;
        }

    	if (diceResult == 6) {
    		MusicController.playRolled6();
            sb.append(GameEngine.getCurrentPlayer().getPlayerName().concat(" roll again"));
        } else {
        	GameEngine.nextPlayer();
        }
    	
    	sb.append("\n");
    	sb.append(GameEngine.getCurrentPlayer().getPlayerName()).append("'s turn:\n");
    	message.setText(sb.toString());
    	
    	rollButton.setText("Start Rolling");
        diceImage.setOnMouseClicked(mouseEvent -> rollButtonClicked());
        rollButton.setOnAction(event -> rollButtonClicked());
        GameEngine.clearConsole();
        setCurrentPlayerToken();
        text.setText("");
        
        // Clean up expired items
        boardController.cleanExpiredItems();
        
        // Chance to randomly spawn an item
    	if(Math.random() < (float)spawnItemChance/100f)
    		boardController.spawnItem();
        
        
        rollButton.setDisable(false);
        diceImage.setDisable(false);
    }
    
    /**
     * Returns the current player's current position on the board
     * @return current player's current board position
     */
    public int getCurrentPos() {
    	Player currentPlayer = GameEngine.getCurrentPlayer();
    	return GameEngine.getBoard().getPosition(currentPlayer.getX(), currentPlayer.getY());
    }
    
    /**
     * Returns the destined position of the current player on the board
     * @return destination of current player
     */
    public int getDestination() {
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
        int token = GameEngine.getCurrentPlayerToken();
        playerToken.setImage(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/token" + token + ".png"))));
    }

    /**
     * Get last randomly rolled dice image as result
     * @return dice result between 1 - 6
     */
    private int getDiceRolled(){
        Image lastRolled = diceImage.getImage();
        for(int i = 0; i < 6; i++){
            if (lastRolled == diceFace[i]) return i+1;
        }
        return -1;
    }
    
    
}