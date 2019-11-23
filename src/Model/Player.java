package Model;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.json.JSONException;

import Controller.MusicController;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends Entity {

    private String playerName;
    private int token;
	private int turnsPoisoned;
    private int turnsShielded;
    private int turnsImmune;
    private ArrayList<Item> items;
    private Stats stats;

    // Player effects
    private boolean isPoisoned;
    private boolean skipped;
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
        items = new ArrayList<>();
        this.turnsPoisoned = 0;
        this.isPoisoned = false;
        turnsShielded = 0;
        turnsImmune = 0;
        extraRoll = false;
        shield = false;
        rollBack = false;
        doubleRoll = false;
        snakeImmunity = false;
        try {
			stats = new Stats(playerName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
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
    	if(turns > 0) {
    		this.isPoisoned = true;
    		stats.incrementTimesPoisoned();
    	} else {
    		isPoisoned = false;
    		turns = 0;
    	}
    	this.turnsPoisoned = turns;
    }

    public void updatePoison() {
    	if (this.isPoisoned) {
    		if (turnsPoisoned > 0) {
    			turnsPoisoned--;
    		}
    		if (turnsPoisoned <= 0){
    			this.isPoisoned = false;
    			turnsPoisoned = 0;
    		}
    	}
    }

    public boolean getPoisonStatus() {
    	return this.isPoisoned;
    }

    public ArrayList<Item> getItems() {
    	return items;
    }

    public void pickupItem(Item item) {
    	items.add(item);
    	stats.incrementItemsCollected(1);
    }

    public void useItem(Item item) {
    	items.remove(item);
    	stats.incrementItemsUsed(1);
        MusicController.playItemActivate();
    }

    public void useItem(int index) {
    	items.remove(index);
    	stats.incrementItemsUsed(1);
        MusicController.playItemActivate();
    }

    public boolean isSkipped() {
    	return skipped;
    }

    public void setSkipped(boolean skipped) {
    	this.skipped = skipped;
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

    public void setShield(int turns) {
    	if(turns < 0) turns = 0;
    	if(turns > 0) {
    		shield = true;
    	} else {
    		shield = false;
    	}
    	turnsShielded = turns;
    }

    public void updateShield() {
    	if (shield) {
    		if (turnsShielded > 0) {
    			turnsShielded--;
    		}
    		if (turnsShielded <= 0) {
    			shield = false;
    			turnsShielded = 0;
    		}
    	}
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

	public void setSnakeImmunity(int turns) {
    	if(turns < 0) turns = 0;
    	if(turns > 0) {
    		snakeImmunity = true;
    	} else {
    		snakeImmunity = false;
    	}
    	turnsImmune = turns;
    }

	public void updateSnakeImmunity() {
    	if (snakeImmunity) {
    		if (turnsImmune > 0) {
    			turnsImmune--;
    		}
    		if(turnsImmune <= 0){
    			snakeImmunity = false;
    			turnsImmune = 0;
    		}
    	}
    }

	public void clearEffects() {
		isPoisoned = false;
	    skipped = false;
		extraRoll = false;
	    shield = false;
	    rollBack = false;
	    doubleRoll = false;
	    snakeImmunity = false;
	}

    public int getTurnsPoisoned() {
		return turnsPoisoned;
	}

	public int getTurnsShielded() {
		return turnsShielded;
	}

	public int getTurnsImmune() {
		return turnsImmune;
	}
	
	public Stats getStats() {
		return stats;
	}

}
