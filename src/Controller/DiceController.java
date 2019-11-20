package Controller;

import java.util.ArrayList;

import Model.GameEngine;
import Model.Item;
import Model.Player;
import Model.Item.ItemType;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
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
    private GridPane inventory;
    @FXML
    private GridPane effects;

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
        this.diceFaceAlt = new Image[6];
        diceFaceAlt[0] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice1alt.png")));
        diceFaceAlt[1] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice2alt.png")));
        diceFaceAlt[2] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice3alt.png")));
        diceFaceAlt[3] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice4alt.png")));
        diceFaceAlt[4] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice5alt.png")));
        diceFaceAlt[5] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice6alt.png")));
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
        text.setText("\n");
        spawnItemChance = GameEngine.getPlayerNum()*10;
        isPaused = false;
        rollButton.setDefaultButton(true);
        new AnimationController(this, this.boardController);
        configEffectsTooltip();
        setActiveEffects();
        setInventory();
    }
    
    /**
     * Called when the 'roll' button is clicked
     */
    @FXML
    private void rollButtonClicked() {
    	MusicController.playRollDice();
        text.setText("\n");
        AnimationController.setSpinning(true);
        menuButton.setDisable(true);
        inventory.setDisable(true);
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
    	inventory.setDisable(true);
        diceImage.setDisable(true);  
        menuButton.setDisable(false);


        Player currentPlayer = GameEngine.getCurrentPlayer();
        diceResult = getDiceRolled(); 
        if(currentPlayer.isDoubleRoll()) {
        	diceResult *= 2;
        }
        text.setText(currentPlayer.getPlayerName() + " rolled " + diceResult + "\n");
        if(currentPlayer.isRollBack()) {
        	diceResult *= -1;
        }
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
            inventory.setDisable(true);
            effects.setDisable(true);
            boardController.showMenu();
    	} else {
            boardController.hideMenu();
            MusicController.playBack();
            if(!GameEngine.isFinished()) {
	            diceImage.setDisable(false);
	            rollButton.setDisable(false);
	            inventory.setDisable(false);
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
    	
    	if(GameEngine.getCurrentPlayer().isDoubleRoll()) {
    		GameEngine.getCurrentPlayer().setDoubleRoll(false);
    		diceResult /= 2;
    	}
		if(GameEngine.getCurrentPlayer().isRollBack()) {
			GameEngine.getCurrentPlayer().setRollBack(false);
			diceResult *= -1;
		}
    	
    	if (diceResult == 6) {
    		MusicController.playRolled6();
            sb.append(GameEngine.getCurrentPlayer().getPlayerName().concat(" roll again"));
        } else if(GameEngine.getCurrentPlayer().isExtraRoll()) {
        	MusicController.playRolled6();
        	sb.append(GameEngine.getCurrentPlayer().getPlayerName().concat(" roll again"));
        	GameEngine.getCurrentPlayer().setExtraRoll(false);
        } else {
        	GameEngine.getCurrentPlayer().updatePoison();
			GameEngine.getCurrentPlayer().updateShield();
			GameEngine.getCurrentPlayer().updateSnakeImmunity();
			GameEngine.decrementExpiry();
        	GameEngine.setNextPlayer();
        	draw(diceResult, GameEngine.getCurrentPlayer().getPoisonStatus());
        }
    	
    	sb.append("\n");
    	sb.append(GameEngine.getCurrentPlayer().getPlayerName()).append("'s turn:\n");
    	message.setText(sb.toString());
    	
    	rollButton.setText("Start Rolling");
        diceImage.setOnMouseClicked(mouseEvent -> rollButtonClicked());
        rollButton.setOnAction(event -> rollButtonClicked());
        GameEngine.clearConsole();
        setCurrentPlayerToken();
        text.setText("\n");
        
        // Clean up expired items
        boardController.cleanExpiredItems();
        
        // Chance to randomly spawn an item
    	if(Math.random() < (float)spawnItemChance/100f)
    		boardController.spawnItem();
        
    	GameEngine.updateState();
    	highlightCurrentPlayer();
    	setActiveEffects();
    	setInventory();
        rollButton.setDisable(false);
        diceImage.setDisable(false);
        inventory.setDisable(false);
    }
    
    /**
     * Returns the current player's current position on the board
     * @return current player's current board position
     */
    public int getCurrentPos() {
    	Player currentPlayer = GameEngine.getCurrentPlayer();
    	if(currentPlayer.isRollBack())
    		currentPlayer = GameEngine.getLeadingPlayer();
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
    		Tooltip tooltip = new Tooltip(player.getPlayerName());
    		tooltip.setStyle("-fx-font-size: 16");
    		Tooltip.install(token, tooltip);
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
            if (lastRolled == diceFace[i] || lastRolled == diceFaceAlt[i]) {
            	return i+1;
            }
        }
        return -1;
    }
    
    private void configEffectsTooltip() {
    	for(Node node : effects.getChildren()) {
    		if(!node.isVisible())
        		continue;
    		ImageView image = (ImageView) node;
        	int i = effects.getChildren().indexOf(node);
        	String text;
        	switch(i) {
    	    	case 0:
    	    		text = Item.getDescriptions()[ItemType.EXTRAROLL.ordinal()];
    	    		break;
    	    	case 1:
    	    		text = "You are poisoned. Your rolls are halved." + "\n(Turns remaining: " + (GameEngine.getCurrentPlayer().getTurnsPoisoned()-1) + ")";
    	    		break;
    	    	case 2:
    	    		text = Item.getDescriptions()[ItemType.SHIELD.ordinal()] + "\n(Turns remaining: " + (GameEngine.getCurrentPlayer().getTurnsShielded()-1) + ")";
    	    		break;
    	    	case 3:
    	    		text = Item.getDescriptions()[ItemType.ROLLBACK.ordinal()];
    	    		break;
    	    	case 4:
    	    		text = Item.getDescriptions()[ItemType.DOUBLE.ordinal()];
    	    		break;
    	    	case 5:
    	    		text = Item.getDescriptions()[ItemType.ANTIDOTE.ordinal()] + "\n(Turns remaining: " + (GameEngine.getCurrentPlayer().getTurnsImmune()-1) + ")";
    	    		break;
    	    	default:
    	    		text = "";
        	}
    		Tooltip tooltip = new Tooltip(text);
    		tooltip.setStyle("-fx-font-size: 16");
        	Tooltip.install(image, tooltip);
    	}
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
    	configEffectsTooltip();
    }
    
    public void setInventory() {
    	inventory.getChildren().clear();
    	Player currPlayer = GameEngine.getCurrentPlayer();
    	int i = 0;
    	int x = 0;
    	int y = 0;
    	for(Item item : currPlayer.getItems()) {
    		ImageView view = item.getImage();
    		view.setPreserveRatio(true);
    		view.setFitHeight(45);
    		view.setOnMouseClicked(new EventHandler<MouseEvent>()
            {
                @Override
                public void handle(MouseEvent e) {
                	itemClicked(item);
                }
            });
    		inventory.add(view, x, y);
    		String message = item.getName() + ":\n" + item.getDescription();
    		Tooltip tooltip = new Tooltip(message);
    		tooltip.setStyle("-fx-font-size: 16");
    		Tooltip.install(view, tooltip);
    		i++;
    		x = i%4;
    		y = i/4;
    	}
    }
    
    private void itemClicked(Item item) {
    	Player targetPlayer;
    	Player player = GameEngine.getCurrentPlayer();
		switch(item.getItemType()) {
			case SKIPTURN:
				targetPlayer = GameEngine.getNextPlayer();
				if(targetPlayer != null && targetPlayer.isSkipped()) {
					text.setText("Could not use item!\n" + item.getName() + " is already activated.");
				} else if(targetPlayer != null && !targetPlayer.isShield()) {
					targetPlayer.setSkipped(true);
					player.useItem(item);
					text.setText(item.getName() + " activated!" + "\n" + targetPlayer.getPlayerName() + " skipped.");
				} else {
					text.setText("Could not use item! Target player is shielded or it's you.");
				}
				break;
			case EXTRAROLL:
				if(player.isExtraRoll()) {
					text.setText("Could not use item!\n" + item.getName() + " is already activated.");
				} else {
					player.setExtraRoll(true);
					player.useItem(item);
					text.setText(item.getName() + " activated!\n");
				}
				break;
			case POISON:
				ArrayList<Player> targetPlayers = GameEngine.getNextNearestPlayers();
				int i = 0;
				for(Player target : targetPlayers) {
					if(!target.isShield()) {
						target.setPoison(3);
						player.useItem(item);
						text.setText(item.getName() + " activated!"  + "\n" + target.getPlayerName() + " poisoned.");
						i++;
					}
				}
				if(i == 0)
					text.setText("Could not use item! Target player is shielded or it's you.");
				break;
			case SHIELD:
				player.setShield(3);
				player.useItem(item);
				text.setText(item.getName() + " activated!\n");
				break;
			case ROLLBACK:
				targetPlayer = GameEngine.getLeadingPlayer();
				if(targetPlayer != null && targetPlayer != player && !targetPlayer.isShield()) {
					player.setRollBack(true);
					player.useItem(item);
					text.setText(item.getName() + " activated!" + "\nRolling " + targetPlayer.getPlayerName() + " back.");
				} else if (targetPlayer != null && player.isRollBack()) {
					text.setText("Could not use item!\n" + item.getName()+ " is already activated.");
				}
				else {
					text.setText("Could not use item! Target player is shielded or it's you.");
				}
				break;
			case DOUBLE:
				if(player.isDoubleRoll()) {
					text.setText("Could not use item!\n" + item.getName() + " is already activated.");
				} else {
					player.setDoubleRoll(true);
					player.useItem(item);
					text.setText(item.getName() + " activated!\n");
				}
				break;
			case SWAP:
				targetPlayer = GameEngine.getLeadingPlayer();
				if(targetPlayer != null && targetPlayer != player && !targetPlayer.isShield()) {
					GameEngine.swapPlayers(player, targetPlayer);
					player.useItem(item);
					text.setText(item.getName() + " activated!" + "\nSwapped positions with " + targetPlayer.getPlayerName());
				} else {
					text.setText("Could not use item! Target player is shielded or it's you.");
				}
				break;
			case ANTIDOTE:
				player.setSnakeImmunity(2);
				player.useItem(item);
				text.setText(item.getName() + " activated!\n");
				if(player.getPoisonStatus())
					text.setText(item.getName() + " activated!\nYou have been healed.");
				player.setPoison(0);
				break;
			case REVERSE:
				GameEngine.setReverse(!GameEngine.isReverse());
				player.useItem(item);
				text.setText(item.getName() + " activated!\n");
				break;
		}
    	setInventory();
    	setActiveEffects();
    	draw(getDiceRolled(), GameEngine.getCurrentPlayer().getPoisonStatus());
    }
    
}