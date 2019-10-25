/**
 * @author Kai, Abdullah
 */

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
    
    /**
     * Default constructor generates a 10x10 board with some snakes and ladders
     */
    public GameEngine(){
        players = new ArrayList<>();
        gameboard = new Board(10, 10);
        gameboard.addSnake(new Snake(27, 5));
        gameboard.addSnake(new Snake(40, 3));
        gameboard.addSnake(new Snake(43, 18));
        gameboard.addSnake(new Snake(54, 31));
        gameboard.addSnake(new Snake(66, 45));
        gameboard.addSnake(new Snake(76, 58));
        gameboard.addSnake(new Snake(89, 53));
        gameboard.addSnake(new Snake(99, 41));
        gameboard.addLadder(new Ladder(4, 25));
        gameboard.addLadder(new Ladder(33, 49));
        gameboard.addLadder(new Ladder(42, 63));
        gameboard.addLadder(new Ladder(50, 69));
        gameboard.addLadder(new Ladder(62, 81));
        gameboard.addLadder(new Ladder(74, 92));
        dice = new Dice();
    }
    
    /**
     * This constructor is used to pass in a pre-made gameboard
     * @param gameboard: pre-made gameboard
     */
    public GameEngine(Board gameboard){
        players = new ArrayList<>();
        this.gameboard = gameboard;
        dice = new Dice();
    }

    /**
     * Add a player into the game
     * @param player: A player object to be added into the game
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
     * Get the current position of player
     * @param player: Specify player object to find their position
     * @return player's position
     */
    public int getPosition(Player player){
        return player.getPosition();
    }
    
    /**
     * Update player's position to pos
     * @param player: Player object to update their position
     * @param pos: player's new position
     * @return updated player position
     */
	private int updatePosition(Player player, int pos) {
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
     * Rolls a dice and updates player position accordingly
     * @return current player and dice number
     */
	public int rollDice(){
        int result = dice.roll();
        System.out.println(currentPlayer.getPlayerName() + " rolled " + result);
        int currPos = currentPlayer.getPosition();
        int newPos = updatePosition(currentPlayer, currPos+result);
        System.out.println(currentPlayer.getPlayerName() + " moves from " + currPos + " to " + newPos);
        updateState();
        printBoard();
        return result;
    }
	
    /**
     * Get current player
     * @return current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * Get index of current player
     * @return current player index
     */
	public int getCurrentPlayerNum() {
		return currentPlayerNum; 
	}
	
    /**
     * Set the next player in turn as current player, looping over the list of all players
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
				int newPos = updatePosition(currPlayer, gameboard.isSnake(currPos).getTail());
		        System.out.println(currPlayer.getPlayerName() + " gets eaten by a snake and moves back from " + currPos + " to " + newPos);
				updateState();
			} else if (gameboard.isLadder(currPos) != null) {
				int newPos = updatePosition(currPlayer, gameboard.isLadder(currPos).getTop());
		        System.out.println(currPlayer.getPlayerName() + " climbs a ladder moves up from " + currPos + " to " + newPos);
				updateState();
			}
		}
		finished = false;
	}
	
	public void printBoard() {
		int[][] grid = gameboard.getGrid();
		int width = gameboard.getWidth();
		int height = gameboard.getHeight();
		for(int y = height-1; y >= 0; y--) {
			for(int x = 0; x < width; x++) {
				boolean empty = true;
				int pos = grid[x][y];
				System.out.print("[");
				if(gameboard.isSnake(pos) != null) {
					System.out.print("x");
					empty = false;
				} else if(gameboard.isLadder(pos) != null) {
					System.out.print("H");
					empty = false;
				}
				for(Player currPlayer : players) {
					if(currPlayer.getPosition() == pos) {
						System.out.print(currPlayer.getPlayerToken());
						empty = false;
					}
				}
				if(empty) {
					System.out.print(" ");
				}
				System.out.print("]");
			}
			System.out.println("");
		}
		for(Player player : players) {
			System.out.println(player.getPlayerToken() + " = " + player.getPlayerName());
		}
		System.out.println("x = snake");
		System.out.println("H = ladder");

	}

}
