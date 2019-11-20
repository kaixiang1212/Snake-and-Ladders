package Model;

import Controller.MusicController;
import Controller.AnimationController;

import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.ArrayList;
import javafx.animation.PauseTransition;

public class GameEngine {
	private static final int pickedUpItemExpiry = -1000;	// Special expiry counter number for picked up items to remove on pickup
    private static final int poisonChance = 90;				// Chance of being poisoned by a snake

    private static ArrayList<Player> players;
    private static Player currentPlayer;
    private static int currentPlayerNum;
    private static Board gameboard;
    private static boolean finished;
    private static StringBuilder console;
    private static int board;
    private static boolean reverse;
	
    /**
     * Default constructor generates a 10x10 board with some snakes and ladders
     */
    public GameEngine(){
        players = new ArrayList<>();
        currentPlayer = null;
        currentPlayerNum = 0;
        gameboard = new Board(10, 10);
        finished = false;
        console = new StringBuilder();
        console.setLength(0);
        MusicController.initGame();
        reverse = false;
    }

    public GameEngine(int boardNum){
    	players = new ArrayList<>();
        currentPlayer = null;
        currentPlayerNum = 0;
        gameboard = new Board(10, 10);
        finished = false;
        console = new StringBuilder();
        console.setLength(0);
        MusicController.initGame();
        board = boardNum;
        reverse = false;
    }

    /**
     * This constructor is used to pass in a pre-made gameboard
     * @param gameboard: pre-made gameboard
     */
    public GameEngine(Board board){
        players = new ArrayList<>();
        gameboard = board;
        finished = false;
        console = new StringBuilder();
        console.setLength(0);
        MusicController.initGame();
        reverse = false;
    }

    /**
     * Add a player into the game
     * @param player: A player object to be added into the game
     */
    public static void addPlayer(Player player){
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
    public static int getPlayerNum(){
        return players.size();
    }

    /**
     * Update player's position to pos
     * @param player: Player object to update their position
     * @param pos: player's new position
     * @return updated player position
     */
	public static int updatePosition(Player player, int pos) {
		pos = Math.min(pos, gameboard.getMaxPos());
		pos = Math.max(pos, gameboard.getMinPos());
		int x,y;
		x = gameboard.getCoords(pos).getKey();
		y = gameboard.getCoords(pos).getValue();
		player.setX(x);
		player.setY(y);
		MusicController.playMove();
		Item item = gameboard.isItem(x, y);
		if(item != null) {
			player.pickupItem(item);
			item.setExpiry(pickedUpItemExpiry);
			System.out.println("[!] " + item.getName() + " item picked up by " + player.getPlayerName() + ".\n");
		}
		return pos;
	}

	/**
     * Get current player
     * @return current player
     */
    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Get index of current player
     * @return current player index
     */
	public static int getCurrentPlayerNum() {
		return currentPlayerNum;
	}

	public static ArrayList<Player> getPlayers(){
		return players;
	}

	public static void setBoard(Board board) {
		gameboard = board;
	}

	public static Board getBoard() {
		return gameboard;
	}

	public static int getBoardType() {
		return board;
	}

	/**
     * Set the next player in turn as current player, looping over the list of all players
     */
    public static Player setNextPlayer(){
    	if(!isReverse()) {
    		currentPlayerNum = (currentPlayerNum + 1) % getPlayerNum();
    	} else {
    		currentPlayerNum = (currentPlayerNum + getPlayerNum()-1) % getPlayerNum();
    	}
        
        currentPlayer = players.get(currentPlayerNum);
        if(currentPlayer.isSkipped()) {
        	currentPlayer.setSkipped(false);
        	setNextPlayer();
        }
        return currentPlayer;
    }
    
    public static Player getNextPlayer(){
    	int nextPlayerNum;
    	if(!isReverse()) {
    		nextPlayerNum = (currentPlayerNum + 1) % getPlayerNum();
    	} else {
    		nextPlayerNum = (currentPlayerNum + getPlayerNum()-1) % getPlayerNum();
    	}
        Player nextPlayer = players.get(nextPlayerNum);
        return nextPlayer;
    }
    
    public static ArrayList<Player> getNextNearestPlayers() {
    	ArrayList<Player> targetPlayers = new ArrayList<Player>();
    	int dist = gameboard.getMaxPos() - gameboard.getMinPos();
    	for(Player player : players) {
    		if(player == getCurrentPlayer())
    			continue;
    		int pos1 = gameboard.getPosition(getCurrentPlayer().getX(), getCurrentPlayer().getY());
    		int pos2 = gameboard.getPosition(player.getX(), player.getY());
    		if((pos2-pos1 >= 0) && (pos2-pos1 < dist)) {
    			targetPlayers.clear();
    			dist = pos2-pos1;
    			targetPlayers.add(player);
    		} else if ((pos2-pos1 >= 0) && (pos2-pos1 == dist)) {
    			targetPlayers.add(player);
    		}
    	}
    	return targetPlayers;
    }
    
    public static Player getLeadingPlayer() {
    	Player targetPlayer = null;
    	int dist = 0;
    	for(Player player : players) {
    		int pos1 = gameboard.getPosition(getCurrentPlayer().getX(), getCurrentPlayer().getY());
    		int pos2 = gameboard.getPosition(player.getX(), player.getY());
    		if((pos2-pos1 >= 0) && (pos2-pos1 >= dist)) {
    			dist = pos2-pos1;
    			targetPlayer = player;
    		}
    	}
    	return targetPlayer;
    }
    
    public static void swapPlayers(Player player1, Player player2) {
    	int x1, y1;
    	x1 = player1.getX();
    	y1 = player1.getY();
    	player1.setX(player2.getX());
    	player1.setY(player2.getY());
    	player2.setX(x1);
    	player2.setY(y1);
    }

	/**
	 * Checks whether the game is finished
	 * @return true if finished, false otherwise
	 */
	public static boolean isFinished() {
		updateState();
		if (finished) MusicController.playVictory();
		return finished;
	}

	/**
	 * Updates the state of the game. This does:
	 * - Checks if player lands on snake, and changes their position
	 * - Checks if player lands on ladder, and changes their position
	 * - Checks if player lands on end position, and updates finished flag
	 */
	public static void updateState() {
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
			} else if ((gameboard.isSnake(currX, currY) != null) && !currPlayer.isSnakeImmunity()) {
				int newX, newY;
				newX = gameboard.isSnake(currX, currY).getTail().getKey();
				newY = gameboard.isSnake(currX, currY).getTail().getValue();
				int newPos = updatePosition(currPlayer, gameboard.getPosition(newX, newY));
				MusicController.playSnake();
				console.append(currPlayer.getPlayerName())
						.append(" gets eaten by a snake and moves back from ")
						.append(currPos).append(" to ")
						.append(newPos).append("\n");
				if(Math.random() < ((float) poisonChance/100f) )
					currPlayer.setPoison(3);
				updateState();
			} else if (gameboard.isLadder(currX, currY) != null) {
				int newX, newY;
				Ladder currLadder = gameboard.isLadder(currX, currY);
				newX = gameboard.isLadder(currX, currY).getTop().getKey();
				newY = gameboard.isLadder(currX, currY).getTop().getValue();
				int newPos = updatePosition(currPlayer, gameboard.getPosition(newX, newY));
				MusicController.playLadder();

				ImageView ladderGif = AnimationController.getGifView(currLadder.getId());
				ImageView ladderImg = AnimationController.getImgView(currLadder.getId());
				// Shake the ladder
				// Shake the ladder
				AnimationController.shakeLadder(ladderGif, ladderImg);
				// Stop laddershake after 1 second
				PauseTransition pause = new PauseTransition(Duration.seconds(1));
				pause.setOnFinished(event ->
					AnimationController.stopShakeLadder(ladderGif, ladderImg)
				);
				pause.play();


				console.append(currPlayer.getPlayerName())
						.append(" climbs a ladder moves up from ")
						.append(currPos).append(" to ")
						.append(newPos).append("\n");
				//updateState();
			}
		}
		finished = false;
	}

	public static String getConsole(){
		return console.toString();
	}

	public static void clearConsole(){
		console.setLength(0);
	}

	public static String printBoard() {
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

	public static int getCurrentPlayerToken(){
		return getCurrentPlayer().getPlayerToken();
	}

	/**
	 * Spawns a random item in the board. Item spawns at position < top player position && position > last player position && position != any player or existing item position
	 * @return spawned item object
	 */
	public static Item spawnRandomItem() {
		// Set item position to be < top player's position
		int maxPlayerPos = gameboard.getMinPos();
		int minPlayerPos = gameboard.getMaxPos();
		for(Player player : players) {
			maxPlayerPos = Math.max(maxPlayerPos, gameboard.getPosition(player.getX(), player.getY()));
			minPlayerPos = Math.min(minPlayerPos, gameboard.getPosition(player.getX(), player.getY()));
		}
		int itemPos = (int) (minPlayerPos + (Math.random() * (maxPlayerPos - minPlayerPos)));
		int itemX = gameboard.getCoords(itemPos).getKey();
		int itemY = gameboard.getCoords(itemPos).getValue();

		// Check that position != any player position
		for(Player player : players) {
			int playerPos = gameboard.getPosition(player.getX(), player.getY());
			if(playerPos == itemPos)
				return null;
		}

		// Check that position != any existing item position
		if(gameboard.isItem(itemX, itemY) != null) {
			return null;
		}

		// Obtain a random item from the pool of available items
		ArrayList<Item> pool = gameboard.getItemPool();
		int size = pool.size();
		int index = (int) (Math.random()*size);
		Item itemTemplate = pool.get(index);


		Item item = new Item(itemX, itemY, itemTemplate.getItemType(), itemTemplate.getName(), itemTemplate.getFrequency(), itemTemplate.getExpiry());
		gameboard.addEntity(item);
		return item;
	}

	public static int getPickedUpItemExpiry() {
		return pickedUpItemExpiry;
	}
	
	public static boolean isReverse() {
		return reverse;
	}
	
	public static void setReverse(boolean reverse) {
		GameEngine.reverse = reverse;
	}

	public static void decrementExpiry() {
		for(Item item : gameboard.getSpawnedItems()) {
			item.decrementExpiry();
		}
	}
}
