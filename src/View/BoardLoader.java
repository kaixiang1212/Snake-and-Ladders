package View;

import Model.*;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONException;
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

    public BoardLoader(String filename) throws FileNotFoundException, JSONException {
    	String path = getClass().getClassLoader().getResource("boards/" + filename).getPath().replaceAll("%20", " ");
    	assert(!path.isEmpty());
        json = new JSONObject(new JSONTokener(new FileReader(path)));
    }

    /**
     * Parses the JSON to create a Board.
     * @return
     * @throws JSONException 
     */
	public GameEngine load () throws JSONException {
		int width = json.getInt("width");
		int height = json.getInt("height");
	        
		Board gameboard = new Board(width, height);
		GameEngine engine = new GameEngine(gameboard);
		
		JSONArray jsonEntities = json.getJSONArray("entities");
		
		for (int i = 0; i < jsonEntities.length(); i++) {
			loadEntity(engine, jsonEntities.getJSONObject(i));
		}
		
		
		//Set the current player to start
		//game.setPlayer(dungeon.getPlayer());
	        
		return engine;
		
	}


    private void loadEntity(GameEngine engine, JSONObject json) throws JSONException {
        String type = json.getString("type");
        int x = json.getInt("x");
        int y = json.getInt("y");

        switch (type) {
        case "player":
            Player player = new Player("player", 'c', x, y);
            //Player player = new Player(gameboard, x, y, "player");
            //gameboard.setPlayer(player);
            onLoad(player, engine);
            if (player != null) {
            	engine.addPlayer(player);
            }
            break;
        case "snake":
            Snake snake = new Snake(x, y, 1, 1);
            onLoad(snake, engine);
            if (snake != null) {
            	engine.getBoard().addEntity(snake);
            }
            break;
        // TODO Handle other possible entities
        case "ladder":
        	Ladder ladder = new Ladder(x, y, 4, 4);
        	onLoad(ladder, engine);
            if (ladder != null) {
            	engine.getBoard().addEntity(ladder);
            }
        	break;
        }
    }

    public abstract void onLoad(Player player, GameEngine engine);
    public abstract void onLoad(Snake snake, GameEngine engine);
    public abstract void onLoad(Ladder ladder, GameEngine engine);
    public abstract void changeImage(Entity entity, String string);
    // TODO Create additional abstract methods for the other entities
  
}
