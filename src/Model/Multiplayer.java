package Model;

import javafx.scene.input.KeyEvent;

import java.util.ArrayList;

public class Multiplayer {

    private ArrayList<Player> players;
    private Player currentPlayer;
    private int currentPlayerNum;
    private int playerNum;
    private Board board;

    public Multiplayer(Board board){
        players = new ArrayList<>();
        this.board = board;
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
        playerNum++;
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
     */
    public void rollDice(){
        int result = board.rollDice(currentPlayer);
        currentPlayer.setPosition(result);
    }

    /**
     * Iterate over the next player
     */
    public void nextPlayer(){
        currentPlayerNum = (currentPlayerNum + 1) % playerNum;
        currentPlayer = players.get(currentPlayerNum);
    }
}
