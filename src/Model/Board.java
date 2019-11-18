package Model;
import java.util.*;

import Model.Entity.Type;

public class Board {
	
	private final int WIDTH;
	private final int HEIGHT;
	private final int MINPOS;
	private final int MAXPOS;
	
	private int[][] grid;		
	private ArrayList<Entity> entities;
	private ArrayList<Item> itemPool;	
	
	/**
	 * Initialises a standard board with width*height dimensions
	 * where board[x][y] = number on the board (ex. 1 to 100 for a standard 10x10 board)
	 * @param width
	 * @param height
	 */
	public Board(int width, int height) {
		HEIGHT = height;
		WIDTH = width;
		initBoard();
		MINPOS = _getMinPos();
		MAXPOS = _getMaxPos();
		entities = new ArrayList<Entity>();
		itemPool = new ArrayList<Item>();
	}
	
	/**
	 * Initialises a board with width*height dimensions and a custom layout (i.e., different shaped boards)
	 * where board[x][y] = number on the board (ex. 1 to 100 for a standard 10x10 board)
	 * and board[x][y] = -1 are blocked off squares
	 * @param width
	 * @param height
	 */
	public Board(int[][] grid) {
		WIDTH = grid.length;
		HEIGHT = grid[0].length;
		this.grid = grid;
		MINPOS = _getMinPos();
		MAXPOS = _getMaxPos();
		entities = new ArrayList<Entity>();
		itemPool = new ArrayList<Item>();
	}
	
	/**
	 * Initialises a board with WIDTH*HEIGHT dimensions, and positions increasing in a snake-like pattern
	 */
	public void initBoard() {
		this.grid = new int[WIDTH][HEIGHT];
		int pos = 1;
		for (int y = 0; y < HEIGHT; y++) {
			if(y % 2 == 0) {
				for(int x = 0; x < WIDTH; x++) {
					grid[x][y] = pos;
					pos++;
				}
			} else {
				for(int x = WIDTH-1; x >= 0; x--) {
					grid[x][y] = pos;
					pos++;
				}
			}
		}
	}
	
	/**
	 * Given a position number, get the x,y coordinates of it
	 * @param position on board
	 * @return x-y coordinates
	 */
	public Coords getCoords(int pos) {
		for (int i = 0 ; i < WIDTH; i++) {
		    for(int j = 0 ; j < HEIGHT ; j++)
		    {
		         if ( grid[i][j] == pos)
		         {
		        	 return new Coords(i,j); 
		         }
		    }
		}
		return null;
	}
	
	/**
	 * Get lowest position in the board (i.e., starting position)
	 * @return minimum position number
	 */
	private int _getMinPos() {
		int min = Integer.MAX_VALUE;
		for (int x = 0 ; x < WIDTH; x++) {
		    for(int y = 0 ; y < HEIGHT ; y++)
		    {
		         if ( grid[x][y] > 0 && grid[x][y] < min)
		         {
		        	min = grid[x][y];
		         }
		    }
		}
		return min;
	}
	
	
	/**
	 * Get highest position in the board (i.e., end position)
	 * @return maximum position number
	 */
	private int _getMaxPos() {
		int max = -1;
		for (int x = 0 ; x < WIDTH; x++) {
		    for(int y = 0 ; y < HEIGHT ; y++)
		    {
		         if ( grid[x][y] > 0 && grid[x][y] > max)
		         {
		        	max = grid[x][y];
		         }
		    }
		}
		return max;
	}
	
	public int getMaxPos() {
		return MAXPOS;
	}
	
	public int getMinPos() {
		return MINPOS;
	}
	
	public int getHeight() {
		return HEIGHT;
	}

	public int getWidth() {
		return WIDTH;
	}
	
	public int getPosition(int x, int y) {
		if(x < grid.length && y < grid[0].length)
			return grid[x][y];
		else
			return -1;
	}
	
	public int[][] getGrid() {
		return grid;
	}
	
	public ArrayList<Entity> getEntities() {
		return entities;
	}
	
	public void addEntity(Entity entity) {
		if(entity != null)
			entities.add(entity);
	}
	
	/**
	 * Include this item in the pool of spawnable items
	 * @param item Item to include
	 */
	public void includeItem(Item item) {
		int freq = item.getFrequency();
		for(int i = 0; i < freq; i++) {
			itemPool.add(item);
		}
		Collections.shuffle(itemPool);
	}
	
	/**
	 * Get the list of spawnable items
	 * @return item pool list
	 */
	public ArrayList<Item> getItemPool() {
		return itemPool;
	}
	
	/**
	 * Checks whether x,y is on the head of a snake
	 * @param x,y
	 * @return Snake object if true, else null
	 */
	public Snake isSnake(int x, int y) {
		for(Entity entity : entities) {
			if(entity.type == Type.SNAKE && entity instanceof Snake) {
				Snake snake = (Snake) entity;
				if(x == snake.getX() && y == snake.getY()) {
					return snake;
				}
			}
		}
		return null;
	}
	
	/**
	 * Checks whether x,y is on a ladder
	 * @param x,y
	 * @return Ladder object if true, else null
	 */
	public Ladder isLadder(int x, int y) {
		for(Entity entity : entities) {
			if(entity.type == Type.LADDER && entity instanceof Ladder) {
				Ladder ladder = (Ladder) entity;
				if(x == ladder.getX() && y == ladder.getY()) {
					return ladder;
				}
			}
		}
		return null;
	}
	
	public Item isItem(int x, int y) {
		for(Entity entity : entities) {
			if(entity.type == Type.ITEM && entity instanceof Item) {
				Item item = (Item) entity;
				if(x == item.getX() && y == item.getY()) {
					return item;
				}
			}
		}
		return null;
	}
	
	public ArrayList<Item> getSpawnedItems() {
		ArrayList<Item> spawnedItems = new ArrayList<Item>();
		for(Entity entity : entities) {
			if(entity instanceof Item) {
				Item item = (Item) entity;
				spawnedItems.add(item);
			}
		}
		return spawnedItems;
	}
	
	public void removeItem(Item item) {
		entities.remove(item);
	}
	
	public void removeItems(ArrayList<Item> items) {
		entities.removeAll(items);
	}
	
}
