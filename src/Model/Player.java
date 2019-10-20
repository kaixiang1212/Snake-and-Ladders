package Model;

import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Player {

    private String playerName;
    private int position;

    Player(String playerName) {
        this.playerName = playerName;
    }

    /**
     * Get player's name
     * @return player's name
     */
    public String getPlayerName(){
        return playerName;
    }

    /**
     * Set player's position
     * @param position player's position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Get player's position
     * @return player's current position
     */
    public int getPosition(){
        return position;
    }
}
