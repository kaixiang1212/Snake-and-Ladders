package Sneks_and_Ladders;

import java.util.ArrayList;
import java.util.List;




public class Board {
	private int width, height;
	private List<Entity> entities;
	private Player player;
	private Game game;
	
	public Board(int width, int height, Game game) {
		this.width = width;
		this.height = height;
		this.entities = new ArrayList<>();
		this.player = null;
		this.game = game;
	}
	
	public List<Entity> getEntities() {
    	return entities;
    }
    
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    
    public Game getGame() {
		return game;
	}

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
    
    public void addEntity(Entity entity) {
    	if(entity != null) {
		    entities.add(entity);
    	}
    }
}
