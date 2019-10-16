package Model;

import java.util.ArrayList;

public class PlayerList {

    private ArrayList<Player> players;
    private Player currentPlayer;
    private int currentPlayerNum;
    private int playerNum;

    public PlayerList(){
        this.players = new ArrayList<>();
    }

    /**
     * Add a player into the game
     * @param player A player object to be added into the game
     */
    public void addPlayer(Player player){
        if (this.currentPlayer == null) {
            this.currentPlayerNum = 0;
            this.currentPlayer = player;
        }
        this.players.add(player);
        this.playerNum++;
    }

    /**
     * Get the number of players in the game
     * @return number of players
     */
    public int getPlayerNum(){
        return playerNum;
    }

    /**
     * Get current player's position
     * @return current player's position
     */
    public int getPosition(){
        return this.currentPlayer.getPosition();
    }

    /**
     * Update current player's position
     * @param position player's current position
     */
    public void updatePosition(int position){
        this.currentPlayer.setPosition(position);
    }

    /**
     * Get current player's move
     * @return current player's move
     */
    public PlayerMoves move(){
        return this.currentPlayer.getMove();
    }

    /**
     * Iterate over the next player
     */
    public void nextPlayer(){
        this.currentPlayerNum = (this.currentPlayerNum + 1) % this.playerNum;
        this.currentPlayer = players.get(this.currentPlayerNum);
    }
}
