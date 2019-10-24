package Model;


public class Player {

    private String playerName;
    private int position;
    private char token;

    public Player(String playerName, char token) {
        this.playerName = playerName;
        this.token = token;
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
    public int getPosition(){
        return position;
    }
    
    /**
     * Get the player's token (symbol)
     * @return player's token
     */
    public char getPlayerToken() {
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
    public void setPosition(int position) {
        this.position = position;
    }
    
    /**
     * Set the player's token
     * @param player token
     */
    public void setToken(char token) {
    	this.token = token;
    }
    
}
