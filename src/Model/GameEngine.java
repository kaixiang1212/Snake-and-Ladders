/**
 * @author Kai, Abdullah
 */

package Model;

import Controller.MusicController;

import java.util.ArrayList;

public class GameEngine {

    private ArrayList<Player> players;
    private Player currentPlayer;
    private int currentPlayerNum;
    private Board gameboard;
    private boolean finished;
    private StringBuilder console;

	private MusicController musicController;
    
    /**
     * Default constructor generates a 10x10 board with some snakes and ladders
     */
    public GameEngine(){
        players = new ArrayList<>();
        gameboard = new Board(10, 10);
//        gameboard.addEntity(new Snake(27, 5));
//        gameboard.addSnake(new Snake(40, 3));
//        gameboard.addSnake(new Snake(43, 18));
//        gameboard.addSnake(new Snake(54, 31));
//        gameboard.addSnake(new Snake(66, 45));
//        gameboard.addSnake(new Snake(76, 58));
//        gameboard.addSnake(new Snake(89, 53));
//        gameboard.addSnake(new Snake(99, 41));
//        gameboard.addLadder(new Ladder(4, 25));
//        gameboard.addLadder(new Ladder(33, 49));
//        gameboard.addLadder(new Ladder(42, 63));
//        gameboard.addLadder(new Ladder(50, 69));
//        gameboard.addLadder(new Ladder(62, 81));
//        gameboard.addLadder(new Ladder(74, 92));
	    gameboard.addEntity(new Snake(6, 2, 4, 0, "snake"));
	    gameboard.addEntity(new Snake(0, 3, 2, 0, "snake"));
	    gameboard.addEntity(new Snake(2, 4, 2, 1, "snake"));
	    gameboard.addEntity(new Snake(6, 7, 9, 3, "snake"));
	    gameboard.addEntity(new Snake(5, 6, 4, 4, "pinksnake"));
	    gameboard.addEntity(new Snake(4, 7, 2, 5, "snake"));
	    gameboard.addEntity(new Snake(8, 8, 7, 5, "snake"));
	    gameboard.addEntity(new Snake(1, 9, 0, 4, "bluesnake"));
	    gameboard.addEntity(new Ladder(3, 0, 4, 2));
	    gameboard.addEntity(new Ladder(7, 1, 5, 4));
	    gameboard.addEntity(new Ladder(1, 4, 2, 6));
	    gameboard.addEntity(new Ladder(9, 4, 8, 6));
	    gameboard.addEntity(new Ladder(1, 6, 0, 8));
	    gameboard.addEntity(new Ladder(6, 7, 8, 9));
        console = new StringBuilder();
        console.setLength(0);
        musicController = new MusicController();
        musicController.initGame();
    }
    /**
     * This constructor is used to pass in a pre-made gameboard
     * @param gameboard: pre-made gameboard
     */
    public GameEngine(Board gameboard){
        players = new ArrayList<>();
        this.gameboard = gameboard;
        console = new StringBuilder();
        console.setLength(0);
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
        //updatePosition(player, gameboard.getMinPos());
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
    	int x,y;
    	x = player.getX();
    	y = player.getY();
    	int position = gameboard.getPosition(x, y);
        return position;
    }
    
    /**
     * Update player's position to pos
     * @param player: Player object to update their position
     * @param pos: player's new position
     * @return updated player position
     */
	public int updatePosition(Player player, int pos) {
		pos = Math.min(pos, gameboard.getMaxPos());
		pos = Math.max(pos, gameboard.getMinPos());
		int x,y;
		x = gameboard.getCoords(pos).getX();
		y = gameboard.getCoords(pos).getY();
		player.setX(x);
		player.setY(y);
		musicController.playMove();
		return pos;
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
	
	public ArrayList<Player> getPlayers(){
		return players;
	}
	
	public void setBoard(Board gameboard) {
		this.gameboard = gameboard;
	}
	
	public Board getBoard() {
		return gameboard;
	}
	
    /**
     * Set the next player in turn as current player, looping over the list of all players
     */
    public Player nextPlayer(){
        currentPlayerNum = (currentPlayerNum + 1) % getPlayerNum();
        currentPlayer = players.get(currentPlayerNum);
        return currentPlayer;
    }
	
	/**
	 * Checks whether the game is finished
	 * @return true if finished, false otherwise
	 */
	public boolean isFinished() {
		updateState();
		if (finished) musicController.playVictory();
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
			int currX, currY;
			currX = currPlayer.getX();
			currY = currPlayer.getY();
			int currPos = gameboard.getPosition(currX, currY);
			if(currPos == -1)
				break;
			if(currPos == gameboard.getMaxPos()) {
				finished = true;
				return;
			} else if (gameboard.isSnake(currX, currY) != null) {
				int newX, newY;
				newX = gameboard.isSnake(currX, currY).getTail().getKey();
				newY = gameboard.isSnake(currX, currY).getTail().getValue();
				int newPos = updatePosition(currPlayer, gameboard.getPosition(newX, newY));
				musicController.playSnake();
				console.append(currPlayer.getPlayerName())
						.append(" gets eaten by a snake and moves back from ")
						.append(currPos).append(" to ")
						.append(newPos).append("\n");
				updateState();
			} else if (gameboard.isLadder(currX, currY) != null) {
				int newX, newY;
				newX = gameboard.isLadder(currX, currY).getTop().getKey();
				newY = gameboard.isLadder(currX, currY).getTop().getValue();
				int newPos = updatePosition(currPlayer, gameboard.getPosition(newX, newY));
				musicController.playLadder();
				console.append(currPlayer.getPlayerName())
						.append(" climbs a ladder moves up from ")
						.append(currPos).append(" to ")
						.append(newPos).append("\n");
				updateState();
			}
		}
		finished = false;
	}

	public String getConsole(){
		return console.toString();
	}

	public void clearConsole(){
		console.setLength(0);
	}
	
	public String printBoard() {
		StringBuilder sb = new StringBuilder();
		int width = gameboard.getWidth();
		int height = gameboard.getHeight();
		for(int y = height-1; y >= 0; y--) {
			for(int x = 0; x < width; x++) {
				boolean empty = true;
				sb.append("[");
				if(gameboard.isSnake(x, y) != null) {
					sb.append("x");
					empty = false;
				} else if(gameboard.isLadder(x, y) != null) {
					sb.append("H");
					empty = false;
				}
				for(Player currPlayer : players) {
					if(currPlayer.getX() == x && currPlayer.getY() == y) {
						sb.append(currPlayer.getPlayerToken());
						empty = false;
					}
				}
				if(empty) {
					sb.append(" ");
				}
				sb.append("]");
			}
			sb.append("\n");
		}
		for(Player player : players) {
			sb.append(player.getPlayerToken());
			sb.append(" = ");
			sb.append(player.getPlayerName());
			sb.append("\n");
		}
		sb.append("x = snake\n");
		sb.append("H = ladder\n");
		return sb.toString();
	}

	public int getCurrentPlayerToken(){
		return getCurrentPlayer().getPlayerToken();
	}

}
