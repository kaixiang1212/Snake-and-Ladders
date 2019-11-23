package Controller;

import java.util.ArrayList;

import Model.GameEngine;
import Model.Item;
import Model.Player;
import javafx.application.Platform;
import Model.Item.ItemType;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.*;

public class DiceController {

    @FXML
	private ImageView diceImage;
    @FXML
	private ImageView nextPlayer1;
	@FXML
	private ImageView nextPlayer2;
	@FXML
	private ImageView nextPlayer3;
	@FXML
	private ImageView currentPlayer;
	@FXML
	private ImageView activeEffect0;
	@FXML
	private ImageView activeEffect1;
	@FXML
	private ImageView activeEffect2;
	@FXML
	private ImageView activeEffect3;
	@FXML
	private ImageView inventory1;
	@FXML
	private ImageView inventory2;
	@FXML
	private ImageView inventory3;
	@FXML
	private ImageView inventory4;
	@FXML
	private ImageView inventory5;
	@FXML
	private ImageView inventory6;
	@FXML
	private Button menuButton;
	@FXML
	private Button helpButton;
	@FXML
	private Text message;
	@FXML
	private Text text;
	@FXML
	private Text itemLabel1;
	@FXML
	private Text itemLabel2;
	@FXML
	private Text itemLabel3;
	@FXML
	private Text itemLabel4;
	@FXML
	private Text itemLabel5;
	@FXML
	private Text itemLabel6;
	@FXML
    private VBox effectBox;

    private BoardController boardController;

	private Image extraRoll;
	private Image poisonStatus;
	private Image shield;
	private Image rollBack;
	private Image doubleRoll;
	private Image snakeImmunity;

    private final Image[] diceFace;
    private final Image[] diceFaceAlt;
    private int currentPos;
    private int destination;
    private int diceResult;
    private boolean isPaused;
    private boolean rolling;
    private static int spawnItemChance;		// Chance of an item spawning each turn in percentage
    private static boolean powerupsEnabled = true;
    private ArrayList<ImageView> inventory;
    private ArrayList<ImageView> activeEffect;
    private ArrayList<ImageView> playerQueue;
    private ArrayList<Text> itemLabel;


    public DiceController() {
        this.diceFace = new Image[6];
        diceFace[0] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice1.png")));
        diceFace[1] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice2.png")));
        diceFace[2] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice3.png")));
        diceFace[3] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice4.png")));
        diceFace[4] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice5.png")));
        diceFace[5] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice6.png")));
        rolling = false;
        
        this.diceFaceAlt = new Image[6];
        diceFaceAlt[0] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice1alt.png")));
        diceFaceAlt[1] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice2alt.png")));
        diceFaceAlt[2] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice3alt.png")));
        diceFaceAlt[3] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice4alt.png")));
        diceFaceAlt[4] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice5alt.png")));
        diceFaceAlt[5] = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/dice6alt.png")));

		extraRoll = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/items/effect1.png")));
		poisonStatus = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/items/effect2.png")));
		shield = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/items/effect3.png")));
		rollBack = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/items/effect4.png")));
		doubleRoll = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/items/effect5.png")));
		snakeImmunity = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/items/item0.png")));

        MusicController.initDice();
        new AnimationController(this, boardController);
        AnimationController.getAnimation().start();
    }


	/**
     * Configuration for Dice Controller
     * called by BoardController to communicate with board
     *
     */
    void config(BoardController boardController) {
		initInventory();
		initActiveEffect();
		initQueue();
        this.boardController = boardController;
        updateToken();
        Player player = GameEngine.getCurrentPlayer();
        StringBuilder sb = new StringBuilder();
        message.setText((sb.append(player.getPlayerName()).append("\n").toString()));
        text.setText("\n");
        spawnItemChance = GameEngine.getPlayerNum()*10;
        isPaused = false;
        Tooltip t = new Tooltip("Click to roll");
        t.setStyle("-fx-font-size: 16");
        Tooltip.install(diceImage, t);
        new AnimationController(this, this.boardController);
        GameEngine.getServer().setDiceController(this);
        configEffectsTooltip();
        setActiveEffects();
        setInventory();

		extraRoll = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/items/effect1.png")));
		poisonStatus = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/items/effect2.png")));
		shield = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/items/effect3.png")));
		rollBack = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/items/effect4.png")));
		doubleRoll = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/items/effect5.png")));
		snakeImmunity = new Image(String.valueOf(getClass().getClassLoader().getResource("asset/items/item0.png")));

		message.setText("\n");

	}

    /**
     * Called when the 'roll' button is clicked
     */
    @FXML
    public void rollButtonClicked() {
    	if (isPaused) return;
    	if (AnimationController.isPlayerMoving()) return;
        if (AnimationController.isSpinning()) return;
        rolling = true;
		diceImage.setFitWidth(100);
		diceImage.setFitHeight(100);
		MusicController.playRollDice();
        text.setText("\n");
        AnimationController.setSpinning(true);
        menuButton.setDisable(true);
        setDisableInventory(true);
        diceImage.setOnMouseClicked(mouseEvent -> stopButtonClicked());
        Tooltip t = new Tooltip("Click to stop");
        t.setStyle("-fx-font-size: 16");
        Tooltip.install(diceImage, t);
        GameEngine.getCurrentPlayer().getStats().incrementNumDiceRolled(1);
    }

    /**
     * Called when the 'stop' button is clicked
     */
	public void stopButtonClicked() {
		if (isPaused) return;
	    if (!AnimationController.isSpinning()) return;
    	MusicController.clear();
		diceImage.setFitWidth(130);
		diceImage.setFitHeight(130);
    	AnimationController.setSpinning(false);
		setDisableInventory(true);
        diceImage.setDisable(true);
        menuButton.setDisable(false);


        Player currentPlayer = GameEngine.getCurrentPlayer();
        diceResult = getDiceRolled();
        currentPlayer.getStats().incrementTotalDiceResults(diceResult);
        if(currentPlayer.isDoubleRoll()) {
        	diceResult *= 2;
        }
        text.setText("\n" + currentPlayer.getPlayerName() + " rolled " + diceResult);
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
            diceImage.setDisable(true);
			setDisableInventory(true);
			effectBox.setDisable(true);
            boardController.showMenu();
    	} else {
            boardController.hideMenu();
            MusicController.playBack();
            if(!GameEngine.isFinished()) {
	            diceImage.setDisable(false);
				setDisableInventory(false);
	            effectBox.setDisable(false);
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
    		MusicController.playVictory();
            sb.append(GameEngine.getCurrentPlayer().getPlayerName()).append(" has won the game! Congratulations!\n");
            message.setText((sb.toString()));
            GameEngine.getCurrentPlayer().getStats().incrementGamesWon(1);
            for(Player player : GameEngine.getPlayers()) {
            	player.getStats().incrementGamesPlayed(1);
            	int playerPos = GameEngine.getBoard().getPosition(player.getX(), player.getY());
            	player.getStats().incrementTotalFinishTile(playerPos);
            	try {
					player.getStats().exportStats();
				} catch (Exception e) {
					e.printStackTrace();
				}
            	player.getStats().printStats();
				System.out.println(",");
            }
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

		String msg = "";
    	if (diceResult == 6) {
    		MusicController.playRolled6();
			msg = GameEngine.getCurrentPlayer().getPlayerName().concat(" roll again");
        } else if(GameEngine.getCurrentPlayer().isExtraRoll()) {
        	MusicController.playRolled6();
			msg = GameEngine.getCurrentPlayer().getPlayerName().concat(" roll again");
        	GameEngine.getCurrentPlayer().setExtraRoll(false);
        } else {
        	GameEngine.getCurrentPlayer().updatePoison();
			GameEngine.getCurrentPlayer().updateShield();
			GameEngine.getCurrentPlayer().updateSnakeImmunity();
			GameEngine.decrementExpiry();
        	GameEngine.setNextPlayer();
        	draw(diceResult, GameEngine.getCurrentPlayer().getPoisonStatus());
        }
		sb.append(GameEngine.getCurrentPlayer().getPlayerName());
		sb.append("\n" + msg);
    	message.setText(sb.toString());

        diceImage.setOnMouseClicked(mouseEvent -> rollButtonClicked());
//        Tooltip t = new Tooltip("Click to roll");
//        t.setStyle("-fx-font-size: 16");
//        Tooltip.install(diceImage, t);
        GameEngine.clearConsole();
        updateToken();
        text.setText("\n");

        // Clean up expired items
        boardController.cleanExpiredItems();

        // Chance to randomly spawn an item
    	if((Math.random() < (float)spawnItemChance/100f) && powerupsEnabled)
    		boardController.spawnItem();

    	GameEngine.updateState();
    	updateToken();
    	setActiveEffects();
    	setInventory();
        diceImage.setDisable(false);
        setDisableInventory(false);
    }

    /**
     * Returns the current player's current position on the board
     * @return current player's current board position
     */
    public int getCurrentPos() {
    	Player currentPlayer = GameEngine.getCurrentPlayer();
    	if (currentPlayer.isRollBack())
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
    	if (poisoned) {
        	image = this.diceFaceAlt[dieFace - 1];
        } else {
        	image = this.diceFace[dieFace - 1];
        }

        diceImage.setImage(image);
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

    public int getCurrentPlayerNum(){
        return GameEngine.getCurrentPlayerNum();
    }

    private void configEffectsTooltip() {
    	for (ImageView effectImageView : activeEffect) {
    		Image effectImage = effectImageView.getImage();
    		String text = "";
    		if (effectImage == extraRoll){
    			text = Item.getDescriptions()[ItemType.EXTRAROLL.ordinal()];
    		} else if (effectImage == poisonStatus){
    			text = "You are poisoned. Your rolls are halved." + "\n(Turns remaining: " + (GameEngine.getCurrentPlayer().getTurnsPoisoned()-1) + ")";
    		} else if (effectImage == shield) {
				text = Item.getDescriptions()[ItemType.SHIELD.ordinal()] + "\n(Turns remaining: " + (GameEngine.getCurrentPlayer().getTurnsShielded() - 1) + ")";
			} else if (effectImage == rollBack) {
				text = Item.getDescriptions()[ItemType.ROLLBACK.ordinal()];
			} else if (effectImage == doubleRoll) {
				text = Item.getDescriptions()[ItemType.DOUBLE.ordinal()];
			} else if (effectImage == snakeImmunity) {
				text = Item.getDescriptions()[ItemType.ANTIDOTE.ordinal()] + "\n(Turns remaining: " + (GameEngine.getCurrentPlayer().getTurnsImmune() - 1) + ")";
			}
    		Tooltip tooltip = new Tooltip(text);
    		tooltip.setStyle("-fx-font-size: 16");
        	Tooltip.install(effectImageView, tooltip);
    	}
    }

    private void setActiveEffects() {
    	Player currPlayer = GameEngine.getCurrentPlayer();
    	clearActiveEffect();
    	int i = 0;
    	if (currPlayer.isExtraRoll()) {
			activeEffect.get(i).setImage(extraRoll);
			i++;
		} if (currPlayer.getPoisonStatus()){
			activeEffect.get(i).setImage(poisonStatus);
			i++;
    	} if (currPlayer.isShield()){
			activeEffect.get(i).setImage(shield);
			i++;
    	} if (currPlayer.isRollBack()){
			activeEffect.get(i).setImage(rollBack);
			i++;
    	} if (currPlayer.isDoubleRoll()){
			activeEffect.get(i).setImage(doubleRoll);
			i++;
    	} if (currPlayer.isSnakeImmunity()){
			activeEffect.get(i).setImage(snakeImmunity);
    	}
    	configEffectsTooltip();
    }

    void setInventory() {
    	clearInventory();
    	Player currPlayer = GameEngine.getCurrentPlayer();
    	int i = 0;
    	for (Item item: currPlayer.getItems()){
			ImageView itemImage = inventory.get(i);
			itemImage.setImage(item.getImage().getImage());
			itemImage.setOnMouseClicked(mouseEvent -> itemClicked(item));
			String message = item.getName() + ":\n" + item.getDescription();
//			Tooltip tooltip = new Tooltip(message);
//			tooltip.setStyle("-fx-font-size: 16");
//			Tooltip.install(itemImage, tooltip);
			i++;
		}
    }

    private void itemClicked(Item item) {
    	Player targetPlayer;
    	Player player = GameEngine.getCurrentPlayer();
		switch(item.getItemType()) {
			case SKIPTURN:
				targetPlayer = GameEngine.getNextPlayer();
				if (targetPlayer != null && targetPlayer.isSkipped()) {
					text.setText("Could not use item!\n" + item.getName() + " is already activated.");
				} else if(targetPlayer != null && !targetPlayer.isShield()) {
					targetPlayer.setSkipped(true);
					player.useItem(item);
					text.setText(item.getName() + " activated!" + "\n" + targetPlayer.getPlayerName() + " skipped.");
				} else {
					text.setText("Could not use item!\n Target player is shielded or it's you.");
				}
				break;
			case EXTRAROLL:
				if(player.isExtraRoll()) {
					text.setText("Could not use item!\n" + item.getName() + " is already activated.");
				} else {
					player.setExtraRoll(true);
					player.useItem(item);
					text.setText("\n"+ item.getName() + " activated!");
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
					text.setText("Could not use item!\n Target player is shielded or it's you.");
				break;
			case SHIELD:
				player.setShield(3);
				player.useItem(item);
				text.setText("\n"+ item.getName() + " activated!");
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
					text.setText("Could not use item!\n Target player is shielded or it's you.");
				}
				break;
			case DOUBLE:
				if(player.isDoubleRoll()) {
					text.setText("Could not use item!\n" + item.getName() + " is already activated.");
				} else {
					player.setDoubleRoll(true);
					player.useItem(item);
					text.setText("\n"+ item.getName() + " activated!");
				}
				break;
			case SWAP:
				targetPlayer = GameEngine.getLeadingPlayer();
				if (targetPlayer != null && targetPlayer != player && !targetPlayer.isShield()) {
					GameEngine.swapPlayers(player, targetPlayer);
					player.useItem(item);
					text.setText(item.getName() + " activated!" + "\nSwapped positions with " + targetPlayer.getPlayerName());
				} else {
					text.setText("Could not use item!\n Target player is shielded or it's you.");
				}
				break;
			case ANTIDOTE:
				player.setSnakeImmunity(2);
				player.useItem(item);
				text.setText("\n" + item.getName() + " activated!");
				if(player.getPoisonStatus())
					text.setText(item.getName() + " activated!\nYou have been healed.");
				player.setPoison(0);
				break;
			case REVERSE:
				GameEngine.setReverse(!GameEngine.isReverse());
				player.useItem(item);
				text.setText("\n"+ item.getName() + " activated!");
				break;
		}
		MusicController.playSwitch();
		updateToken();
    	setInventory();
    	setActiveEffects();
    	draw(getDiceRolled(), GameEngine.getCurrentPlayer().getPoisonStatus());
    }

    public static void setPowerupsEnabled(boolean powerups) {
    	powerupsEnabled = powerups;
    }
    
    public static boolean isPowerupsEnabled() {
    	return powerupsEnabled;
    }

    private void initInventory(){
		inventory = new ArrayList<>();
		inventory.add(inventory1);
		inventory.add(inventory2);
		inventory.add(inventory3);
		inventory.add(inventory4);
		inventory.add(inventory5);
		inventory.add(inventory6);

		for (ImageView imageView : inventory){
			imageView.toFront();
		}

		itemLabel = new ArrayList<>();
		itemLabel.add(itemLabel1);
		itemLabel.add(itemLabel2);
		itemLabel.add(itemLabel3);
		itemLabel.add(itemLabel4);
		itemLabel.add(itemLabel5);
		itemLabel.add(itemLabel6);

		for (Text text : itemLabel){
			text.toFront();
		}
	}

    private void setDisableInventory(boolean bool){
    	for (ImageView item : inventory){
    		item.setDisable(bool);
    		if (bool) item.setOpacity(0.4);
    		else item.setOpacity(1);
		}
		for (Text text : itemLabel){
			if (bool) text.setOpacity(0.4);
			else text.setOpacity(1);
		}
	}

	private void clearInventory(){
		for (ImageView item : inventory){
			item.setImage(null);
			item.setOnMouseClicked(mouseEvent -> {});
		}
	}

	private void initActiveEffect() {
    	activeEffect = new ArrayList<>();
		activeEffect.add(activeEffect0);
		activeEffect.add(activeEffect1);
		activeEffect.add(activeEffect2);
		activeEffect.add(activeEffect3);
		clearActiveEffect();
	}

	private void clearActiveEffect(){
		for (ImageView item : activeEffect){
			item.setImage(null);
		}
	}

	private void initQueue(){
		playerQueue = new ArrayList<>();
		playerQueue.add(nextPlayer1);
		playerQueue.add(nextPlayer2);
		playerQueue.add(nextPlayer3);
		clearQueue();
	}

	private void clearQueue(){
    	for(ImageView token : playerQueue){
    		token.setImage(null);
		}
	}

	private void updateToken(){
    	ArrayList<Player> sequence = GameEngine.getPlayerSequence();
    	int i = 0;
    	for (Player player: sequence) {
    		if (i == 0){
    			currentPlayer.setImage(player.getImage().getImage());
			} else {
    			playerQueue.get(i-1).setImage(player.getImage().getImage());
			}
			i++;
		}
    	clearActiveEffect();
    	setActiveEffects();
	}

	public void useItem(int number){
    	if (isPaused) return;
    	Player currPlayer = GameEngine.getCurrentPlayer();
    	ArrayList<Item> items = currPlayer.getItems();
    	if (items.size() < number) return;
    	Item item = items.get(number-1);
    	if (item != null) itemClicked(item);
	}
}
