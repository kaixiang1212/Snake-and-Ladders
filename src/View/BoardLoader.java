package View;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import Model.*;
import unsw.dungeon.Dungeon;
import unsw.dungeon.Entity;
import unsw.dungeon.Player;

/** 
 * 
 * @author rushenkajayasuriya
 *
 * This class loads a gameboard from a JSON file
 */


public abstract class BoardLoader {
	
	private JSONObject json;

    public BoardLoader(String filename) throws FileNotFoundException, JSONException {
        json = new JSONObject(new JSONTokener(new FileReader("boards/" + filename)));
    }

	public Board load (Game game) throws JSONException {
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
        int x1 = json.getInt("x");
        int y1 = json.getInt("y");
        int startpos = gameboard.getPosition(x1, y1);
        int endpos = gameboard.getPosition(x2, y2);
        Entity entity = null;
        switch (type) {
        case "snake":
            Snake snake = new Snake(startpos, endpos);
            gameboard.addSnake(snake);
            onLoad(snake);
            entity = snake;
            break;
        case "ladder":
        	Ladder ladder = new Ladder(startpos, endpos);
        	gameboard.addLadder(ladder);
        	onLoad(ladder);
        	entity = ladder;
        	break;
        }
	}
    
}
