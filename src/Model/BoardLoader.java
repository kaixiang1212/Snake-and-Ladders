package Sneks_and_Ladders;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/** 
 * 
 * @author rushenkajayasuriya
 *
 * This class loads a gameboard from a JSON file
 */


public abstract class BoardLoader {
	
	private JSONObject json;

    public BoardLoader(String filename) throws FileNotFoundException {
        json = new JSONObject(new JSONTokener(new FileReader("boards/" + filename)));
    }

    /**
     * Parses the JSON to create a Board.
     * @return
     */
	public Board load (Game game) {
		int width = json.getInt("width") + 1;
		int height = json.getInt("height");
	        
		Board gameboard = new Board(width, height, game);
	        
		JSONArray jsonEntities = json.getJSONArray("entities");

		for (int i = 0; i < jsonEntities.length(); i++) {
			loadEntity(gameboard, jsonEntities.getJSONObject(i));
		}

		//Set the current player to start
		//game.setPlayer(dungeon.getPlayer());
	        
		return gameboard;
		
	}


    private void loadEntity(Board gameboard, JSONObject json) {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        Entity entity = null;
        switch (type) {
        case "player":
            Player player = new Player("player", 'c', x, y);
            //Player player = new Player(gameboard, x, y, "player");
            gameboard.setPlayer(player);
            onLoad(player);
            entity = player;
            break;
        case "snake":
            Snake snake = new Snake(x, y, "snake");
            onLoad(snake);
            entity = snake;
            break;
        // TODO Handle other possible entities
        case "ladder":
        	Ladder ladder = new Ladder(x, y, "ladder");
        	onLoad(ladder);
        	entity = ladder;
        	break;
        }
        if (entity != null) {
        	gameboard.addEntity(entity);
        }
    }

    public abstract void onLoad(Entity player);
    public abstract void onLoad(Snake snake);
    public abstract void onLoad(Ladder ladder);
    public abstract void changeImage(Entity entity, String string);
    // TODO Create additional abstract methods for the other entities
  
}
