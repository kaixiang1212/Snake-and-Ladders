package Model;

import javafx.util.Pair;

public class Ladder extends Entity {
	
	
	private int x2, y2;
	// private int base, top;
	
	/**
	 * Create a ladder positioned at (x,y) that leads to position (x2,y2)
	 * @param x x-pos of ladder base
	 * @param y y-pos of ladder base
	 * @param x2 x-pos of ladder top
	 * @param y2 y-pos of ladder top
	 */
	public Ladder(int x, int y, int x2, int y2) {
        super(x, y, Type.LADDER);
        this.x2 = x2;
        this.y2 = y2;
    }
	
	public Pair<Integer, Integer> getTop() {
		return new Pair<>(x2,y2);
	}

	public void setTop(int x2, int y2) {
		this.x2 = x2;
		this.y2 = y2;
	}
	
	
}