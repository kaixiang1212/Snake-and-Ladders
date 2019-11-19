package Controller;

import Model.GameEngine;
import Model.Item;
import Model.Player;
import Model.Item.ItemType;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    private HBox hbox;
    @FXML
    private Button menuButton;
    @FXML
    private Button inventoryButton;
    @FXML
    private GridPane effects;
    @FXML
    private Text description;

    private BoardController boardController;

    private final Image[] diceFace;
    private final Image[] diceFaceAlt;
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
        this.diceFaceAlt = new Image[3];
        diceFaceAlt[0] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice1alt.png")));
        diceFaceAlt[1] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice2alt.png")));
        diceFaceAlt[2] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice3alt.png")));
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
        setTurnTokens();
        highlightCurrentPlayer();
        Player player = GameEngine.getCurrentPlayer();
        StringBuilder sb = new StringBuilder();
        message.setText((sb.append("\n").append(player.getPlayerName()).append("'s turn:\n").toString()));
        spawnItemChance = GameEngine.getPlayerNum()*10;
        isPaused = false;
        rollButton.setDefaultButton(true);
        new AnimationController(this, this.boardController);
        description.setText("\n\n");
        setActiveEffects();
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
    		MusicController.playSwitch();
    		rollButton.setDisable(true);
            diceImage.setDisable(true);
            inventoryButton.setDisable(true);
            effects.setDisable(true);
            boardController.showMenu();
    	} else {
            boardController.hideMenu();
            MusicController.playBack();
            if(!GameEngine.isFinished()) {
	            diceImage.setDisable(false);
	            rollButton.setDisable(false);
	            inventoryButton.setDisable(false);
	            effects.setDisable(false);
            }
    	}
    }
    
    @FXML
    public void inventoryButtonClicked() {
    	MusicController.playSwitch();
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
        AnimationController.setPoison(GameEngine.getCurrentPlayer().getPoisonStatus());
        
        // Clean up expired items
        boardController.cleanExpiredItems();
        
        // Chance to randomly spawn an item
    	if(Math.random() < (float)spawnItemChance/100f)
    		boardController.spawnItem();
        
    	highlightCurrentPlayer();
    	setActiveEffects();
    	
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
    public void draw(int dieFace, boolean poisoned) {
        Image image;
    	if(poisoned) {
        	image = this.diceFaceAlt[dieFace - 1];
        } else {
        	image = this.diceFace[dieFace - 1];
        }
    	
        diceImage.setImage(image);
    }
    
    /**
     * Updates the current player's token to the correct player
     */
    private void setCurrentPlayerToken() {
        playerToken.setImage(GameEngine.getCurrentPlayer().getImage().getImage());
    }
    
    private void setTurnTokens() {
    	int i = 0;
    	for(Player player : GameEngine.getPlayers()) {
    		ImageView token = (ImageView) hbox.getChildren().get(i);
    		token.setImage(player.getImage().getImage());
    		i++;
    	}
    }
    
    private void highlightCurrentPlayer() {
    	int i = 0;
    	for(Node node : hbox.getChildren()) {
    		ImageView token = (ImageView) node;
    		ColorAdjust saturation = new ColorAdjust();
    		if(i == GameEngine.getCurrentPlayerNum()) {
    			saturation.setSaturation(0.0);
    		} else {
    			saturation.setSaturation(-0.9);
    		}
    		token.setEffect(saturation);
    		i++;
    	}
    }

    /**
     * Get last randomly rolled dice image as result
     * @return dice result between 1 - 6
     */
    private int getDiceRolled(){
        Image lastRolled = diceImage.getImage();
        for(int i = 0; i < 6; i++){
            if (lastRolled == diceFace[i]) {
            	return i+1;
            } else if(i < 3 && lastRolled == diceFaceAlt[i]) {
            	return i+1;
            }
        }
        return -1;
    }
    
    @FXML
    private void setEffectDescription(MouseEvent e) {
    	Node node = (Node) e.getSource();
    	if(!node.isVisible())
    		return;
    	int i = effects.getChildren().indexOf(node);
    	String text = "";
    	switch(i) {
	    	case 0:
	    		text = Item.getDescriptions()[ItemType.EXTRAROLL.ordinal()];
	    		text += "\n";
	    		break;
	    	case 1:
	    		text = "You are poisoned. Your rolls are halved.\n";
	    		break;
	    	case 2:
	    		text = Item.getDescriptions()[ItemType.SHIELD.ordinal()];
	    		text += "\n";
	    		break;
	    	case 3:
	    		text = Item.getDescriptions()[ItemType.ROLLBACK.ordinal()];
	    		text += "\n";
	    		break;
	    	case 4:
	    		text = Item.getDescriptions()[ItemType.DOUBLE.ordinal()];
	    		text += "\n\n";
	    		break;
	    	case 5:
	    		text = Item.getDescriptions()[ItemType.ANTIDOTE.ordinal()];
	    		break;
	    	default:
	    		text = "\n\n";
    	}
    	description.setText(text);
    }
    
    @FXML
    private void clearItemDescription() {
    	description.setText("\n\n");
    }
    
    private void setActiveEffects() {
    	Player currPlayer = GameEngine.getCurrentPlayer();
    	for(Node node : effects.getChildren()) {
    		node.setVisible(false);
    	}
    	if(currPlayer.isExtraRoll())
    		effects.getChildren().get(0).setVisible(true);
    	if(currPlayer.getPoisonStatus())
    		effects.getChildren().get(1).setVisible(true);
    	if(currPlayer.isShield())
    		effects.getChildren().get(2).setVisible(true);
    	if(currPlayer.isRollBack())
    		effects.getChildren().get(3).setVisible(true);
    	if(currPlayer.isDoubleRoll())
    		effects.getChildren().get(4).setVisible(true);
    	if(currPlayer.isSnakeImmunity())
    		effects.getChildren().get(5).setVisible(true);
    }
    
    
}