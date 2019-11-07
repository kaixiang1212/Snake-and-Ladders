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
 * Started code taken from:
 * Edited by: 
 *
 * This class loads a Game Board from a JSON file
 * 
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
	public GameEngine load (GameEngine engine) throws JSONException {
		int width = json.getInt("width");
		int height = json.getInt("height");
	        
		Board gameboard = new Board(width, height);
		engine.setBoard(gameboard);
		
		JSONArray jsonEntities = json.getJSONArray("entities");
		
		for (int i = 0; i < jsonEntities.length(); i++) {
			loadEntity(engine, jsonEntities.getJSONObject(i));
		}
		
		for(int i = engine.getPlayers().size()-1; i >= 0; i--) {
			Player player = engine.getPlayers().get(i);
			onLoad(player);
		}
		
		return engine;
		
	}


    private void loadEntity(GameEngine engine, JSONObject json) throws JSONException {
        String type = json.getString("type");
        String id = json.getString("id");
        int x = json.getInt("x");
        int y = json.getInt("y");
        int x2 = json.getInt("x2");
        int y2 = json.getInt("y2");
        switch (type) {
        case "player":
        	int nextTokenNum = engine.getPlayerNum();
            Player player = new Player("Player " + nextTokenNum+1, nextTokenNum, x, y);
            //Player player = new Player(gameboard, x, y, "player");
            //gameboard.setPlayer(player);
            onLoad(player);
            if (player != null) {
            	engine.addPlayer(player);
            }
            break;
        // All Snake Entities:
        case "snake":
            Snake snake = new Snake(x, y, x2, y2, type);
            onLoad(snake);
            if (snake != null) {
            	engine.getBoard().addEntity(snake);
            }
            break;
        case "bluesnake":
            Snake bsnake = new Snake(x, y, x2, y2, type);
            onLoad(bsnake);
            if (bsnake != null) {
            	engine.getBoard().addEntity(bsnake);
            }
            break;
        case "pinksnake":
            Snake psnake = new Snake(x, y, x2, y2, type);
            onLoad(psnake);
            if (psnake != null) {
            	engine.getBoard().addEntity(psnake);
            }
            break;
        case "ladder":
        	Ladder ladder = new Ladder(x, y, x2, y2, id);
        	onLoad(ladder);
            if (ladder != null) {
            	engine.getBoard().addEntity(ladder);
            }
        	break;
        }
    }

    public abstract void onLoad(Player player);
    public abstract void onLoad(Snake snake);
    public abstract void onLoad(Ladder ladder);
    public abstract void changeImage(Entity entity, String string);
    // TODO Create additional abstract methods for the other entities
  
}
