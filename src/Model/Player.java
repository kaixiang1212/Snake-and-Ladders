package Model;

public class Player extends Entity {

    private String playerName;
    // private int position;
    private int token;
    private int turnsPoisoned;
    private boolean isPoisoned;

    public Player(String playerName, int token, int x, int y) {
    	super(x, y, Type.PLAYER);
    	this.playerName = playerName;
        this.token = token;
        this.turnsPoisoned = 0;
        this.isPoisoned = false;
    }

    /**
     * Get player's name
     * @return player's name
     */
    public String getPlayerName(){
        return playerName;
    }
    
    /**
     * Get player's position
     * @return player's current position
     */
//    public int getPosition(){
//        return position;
//    }
    
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
     * Set player's absolute position
     * @param position player's absolute position
     */
//    public void setPosition(int position) {
//        this.position = position;
//    }
    
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
    
    
    
}