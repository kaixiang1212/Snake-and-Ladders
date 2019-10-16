package Model;

import java.util.ArrayList;

public abstract class Player<E> {

    private String playerName;
    private int position;
    private ArrayList<String> powerUps;

    Player(String playerName) {
        this.playerName = playerName;
        this.powerUps = new ArrayList<>();
    }

    public abstract PlayerMoves getMove();

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
     * Get player's position
     * @return player's current position
     */
    public int getPosition(){
        return position;
    }
}
