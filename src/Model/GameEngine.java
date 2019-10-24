package Model;

import javafx.util.Pair;

import java.util.ArrayList;

public class GameEngine {

    private ArrayList<Player> players;
    private Player currentPlayer;
    private int currentPlayerNum;
    private Dice dice;
    private Board gameboard;
    private boolean finished;

    public GameEngine(){
        players = new ArrayList<>();
        gameboard = new Board(10, 10);
        dice = new Dice();
    }
    
    public GameEngine(Board gameboard){
        players = new ArrayList<>();
        this.gameboard = gameboard;
        dice = new Dice();
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
        updatePosition(player, gameboard.getMinPos());
        updateState();
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
    public int getPosition(Player player){
        return player.getPosition();
    }
    
    /**
     * Update current player's position
     * @param pos: player's current position
     */
	public int updatePosition(Player player, int pos) {
		if(pos > 0) {
			pos = Math.min(pos, gameboard.getMaxPos());
			player.setPosition(pos);
		} else {
			pos = Math.max(pos, gameboard.getMinPos());
			player.setPosition(pos);
		}
		return pos;
	}
	
	/**
     * Invoked when dice button is clicked
     * @return current player and dice number
     */
	public Pair<Player, Integer> rollDice(){
        int result = dice.roll();
        Player curr = this.currentPlayer;
        System.out.println(currentPlayer.getPlayerName() + " rolled " + result);
        System.out.println(currentPlayer.getPlayerName() + " moves from " + currentPlayer.getPosition() + " to " + updatePosition(currentPlayer, currentPlayer.getPosition()+result));

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
    public void nextPlayer(){
        currentPlayerNum = (currentPlayerNum + 1) % getPlayerNum();
        currentPlayer = players.get(currentPlayerNum);
        System.out.println("\n" + currentPlayer.getPlayerName() + "'s turn:");
    }
	
	/**
	 * Checks whether the game is finished
	 * @return true if finished, false otherwise
	 */
	public boolean isFinished() {
		updateState();
		return finished;
	}
	
	/**
	 * Updates the state of the game. This does:
	 * - Checks if player lands on snake, and changes their position
	 * - Checks if player lands on ladder, and changes their position
	 * - Checks if player lands on end position, and updates finished flag
	 */
	public void updateState() {
		for(Player currPlayer : players) {
			int currPos = currPlayer.getPosition();
			if(currPos == gameboard.getMaxPos()) {
				finished = true;
				return;
			} else if (gameboard.isSnake(currPos) != null) {
				System.out.print(currentPlayer.getPlayerName() + " gets eaten by a snake and moves from " + currentPlayer.getPosition() + " to ");
				currPlayer.setPosition(gameboard.isSnake(currPos).getTail());
				System.out.println(currentPlayer.getPlayerName());
				updateState();
			} else if (gameboard.isLadder(currPos) != null) {
				System.out.print(currentPlayer.getPlayerName() + " climbs up a ladder and moves from " + currentPlayer.getPosition() + " to ");
				currPlayer.setPosition(gameboard.isLadder(currPos).getTop());
				System.out.println(currentPlayer.getPlayerName());
				updateState();
			}
		}
		finished = false;
	}
	
	public int getCurrentPlayerNum() {
		return currentPlayerNum; 
	}

}
