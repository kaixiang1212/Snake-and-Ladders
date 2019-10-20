package Model;

import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Player {

    private String playerName;
    private int position;
    private ArrayList<String> powerUps;

    Player(String playerName) {
        this.playerName = playerName;
        this.powerUps = new ArrayList<>();
    }

    /**
     * Get player's name
     * @return player's name
     */
    public String getPlayerName(){
        return playerName;
    }

    // TODO :: Still unsure how power up works
    public void addPowerUp(String powerUp){
        this.powerUps.add(powerUp);
    }

    /**
     * Set player's position
     * @param position player's position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Get Player's power up
     * @return Player's power up
     */
    public ArrayList<String> getPowerUps(){
        return this.powerUps;
    }

    /**
     * Get player's position
     * @return player's current position
     */
    public int getPosition(){
        return position;
    }
}
