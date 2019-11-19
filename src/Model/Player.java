package Model;

import java.util.ArrayList;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends Entity {

    private String playerName;
    private int token;
    private int turnsPoisoned;
    private ArrayList<Item> items;
    
    // Player effects
    private boolean isPoisoned;
	private boolean extraRoll;
    private boolean shield;
    private boolean rollBack;
    private boolean doubleRoll;
    private boolean snakeImmunity;
    
    public Player(String playerName, int token, int x, int y) {
    	super(x, y, Type.PLAYER);
    	this.playerName = playerName;
        this.token = token;
        image = new ImageView(new Image(String.valueOf(getClass().getClassLoader().getResource("asset/token" + token + ".png"))));
		image.setId("player" + token);
        items = new ArrayList<Item>();
        this.turnsPoisoned = 0;
        this.isPoisoned = false;
        extraRoll = false;
        shield = false;
        rollBack = false;
        doubleRoll = false;
        snakeImmunity = false;
    }

    /**
     * Get player's name
     * @return player's name
     */
    public String getPlayerName(){
        return playerName;
    }

    /**
     * Get the player's token
     * @return player's token
     */
    public int getPlayerToken() {
    	return token;
    }

    /**
     * Set the player's name
     * @param player name
     */
    public void setName(String playerName) {
    	this.playerName = playerName;
    }

    /**
     * Set the player's token
     * @param player token
     */
    public void setToken(char token) {
    	this.token = token;
    }

    public void setPoison(int turns) {
    	this.turnsPoisoned = turns;
    	this.isPoisoned = true;
    }

    public void updatePoison() {
    	if (this.isPoisoned) {
    		if (turnsPoisoned > 0) {
    			turnsPoisoned--;
    		} else {
    			this.isPoisoned = false;
    			turnsPoisoned = 0;
    		}
    	}
    }

    public boolean getPoisonStatus() {
    	return this.isPoisoned;
    }

    public void pickupItem(Item item) {
    	items.add(item);
    }

    public void useItem(Item item) {
    	items.remove(item);
    }

    public void useItem(int index) {
    	items.remove(index);
    }

    public boolean isExtraRoll() {
		return extraRoll;
	}

	public void setExtraRoll(boolean extraRoll) {
		this.extraRoll = extraRoll;
	}

	public boolean isShield() {
		return shield;
	}

	public void setShield(boolean shield) {
		this.shield = shield;
	}

	public boolean isRollBack() {
		return rollBack;
	}

	public void setRollBack(boolean rollBack) {
		this.rollBack = rollBack;
	}

	public boolean isDoubleRoll() {
		return doubleRoll;
	}

	public void setDoubleRoll(boolean doubleRoll) {
		this.doubleRoll = doubleRoll;
	}

	public boolean isSnakeImmunity() {
		return snakeImmunity;
	}

	public void setSnakeImmunity(boolean snakeImmunity) {
		this.snakeImmunity = snakeImmunity;
	}

}
