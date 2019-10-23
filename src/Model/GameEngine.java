package Model;

import javafx.util.Pair;

import java.util.ArrayList;

public class GameEngine {

    private ArrayList<Player> players;
    private Player currentPlayer;
    private int currentPlayerNum;
    private Dice dice = new Dice();

    public GameEngine(){
        players = new ArrayList<>();
    }

    /**
     * Add a player into the game
     * @param player A player object to be added into the game
     */
    public void addPlayer(Player player){
        if (currentPlayer == null) {
            currentPlayerNum = 0;
            currentPlayer = player;
        }
        players.add(player);
    }

    /**
     * Get the number of players in the game
     * @return number of players
     */
    public int getPlayerNum(){
        return players.size();
    }

    /**
     * Get current player's position
     * @return current player's position
     */
    public int getPosition(){
        return currentPlayer.getPosition();
    }

    /**
     * Update current player's position
     * @param position player's current position
     */
    public void updatePosition(int position){
        currentPlayer.setPosition(position);
    }

    /**
     * Invoked when dice button is clicked
     * @return current player and dice number
     */
    public Pair<Player, Integer> rollDice(){
        int result = dice.roll();
        Player curr = this.currentPlayer;
        if (result == 6){
            return new Pair<>(curr, result);
        }
        nextPlayer();
        return new Pair<>(curr, result);
    }

    /**
     * Get current player
     * @return current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Iterate over the next player
     */
    private void nextPlayer(){
        currentPlayerNum = (currentPlayerNum + 1) % getPlayerNum();
        currentPlayer = players.get(currentPlayerNum);
    }

}
