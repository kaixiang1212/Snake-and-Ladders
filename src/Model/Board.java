package Model;
import java.util.*;

public class Board {
	
	private final int WIDTH;
	private final int HEIGHT;
	private final int MINPOS;
	private final int MAXPOS;
	
	private int[][] board;		
	private ArrayList<Snake> snakes;
	private ArrayList<Ladder> ladders;
	
	
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
		snakes = new ArrayList<Snake>();
		ladders = new ArrayList<Ladder>();
	}
	
	/**
	 * Initialises a board with width*height dimensions and a custom layout (i.e., different shaped boards)
	 * where board[x][y] = number on the board (ex. 1 to 100 for a standard 10x10 board)
	 * and board[x][y] = -1 are blocked off squares
	 * @param width
	 * @param height
	 */
	public Board(int[][] board) {
		WIDTH = board.length;
		HEIGHT = board[0].length;
		this.board = board;
		MINPOS = _getMinPos();
		MAXPOS = _getMaxPos();
		snakes = new ArrayList<Snake>();
		ladders = new ArrayList<Ladder>();
	}
	
	/**
	 * Initialises a board with WIDTH*HEIGHT dimensions, and positions increasing in a snake-like pattern
	 */
	public void initBoard() {
		this.board = new int[WIDTH][HEIGHT];
		int pos = 1;
		for (int y = 0; y < HEIGHT; y++) {
			if(y % 2 == 0) {
				for(int x = 0; x < WIDTH; x++) {
					board[x][y] = pos;
					pos++;
				}
			} else {
				for(int x = WIDTH-1; x >= 0; x--) {
					board[x][y] = pos;
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
		         if ( board[i][j] == pos)
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
		         if ( board[x][y] > 0 && board[x][y] < min)
		         {
		        	min = board[x][y];
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
		         if ( board[x][y] > 0 && board[x][y] > max)
		         {
		        	max = board[x][y];
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

//	public void setHeight(int height) {
//		HEIGHT = height;
//	}

	public int getWidth() {
		return WIDTH;
	}
	
	public int getPosition(int x, int y) {
		if(x < board.length && y < board[0].length)
			return board[x][y];
		else
			return -1;
	}

//	public void setWidth(int width) {
//		this.width = width;
//	}
	
	public void addSnake(Snake snake) {
		snakes.add(snake);
	}
	
	public void addLadder(Ladder ladder) {
		ladders.add(ladder);
	}
	
	
	/**
	 * Checks whether position is on the head of a snake
	 * @param position
	 * @return Snake object if true, else null
	 */
	public Snake isSnake(int position) {
		for(Snake snake : snakes) {
			if(position == snake.getHead()) {
				return snake;
			}
		}
		return null;
	}
	
	/**
	 * Checks whether position is on the base of a ladder
	 * @param position
	 * @return Ladder object if true, else null
	 */
	public Ladder isLadder(int position) {
		for(Ladder ladder : ladders) {
			if(position == ladder.getBase()) {
				return ladder;
			}
		}
		return null;
	}
	
}
